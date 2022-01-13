package json;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import objetos.CalidadAireHorario;
import objetos.Estacion;



public class JsonReader {
	private static final String url = "https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2021/es_def/adjuntos/index.json";
	
	public static void main(String args[]) {
		HttpCert.validCert();
		uploadEstaciones();
		//refreshHorarios();
	}
	
	public static void uploadEstaciones() {
		String urlEstacion = "https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/estaciones.json";
		
		JsonObject objectEstacion = readJsonCrashedFromUrl(urlEstacion);
		
		JsonArray array = (JsonArray) objectEstacion.get("jsonData");
		Iterator<JsonElement> iter = array.iterator();
		
		Estacion estacion = new Estacion();
		int count = 0;
		while (iter.hasNext()) {
			
			JsonElement entrada = iter.next();
			JsonObject objeto = entrada.getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iter2 = objeto.entrySet().iterator();
			Iterator<Map.Entry<String, JsonElement>> iter3 = objeto.entrySet().iterator();
			
			while(iter2.hasNext()) {
				String key = iter2.next().getKey().toString();
				String value = iter3.next().getValue().getAsString();
				
				switch(key) {
				case "Name":
					estacion.setNombre(value);
					break;
				case "Province":
					estacion.setProvincia(value);
					break;
				case "Town":
					estacion.setMunicipio(value);
					break;
				case "Address":
					estacion.setDireccion(value);
					break;
				case "Latitude":
					estacion.setLatitud(value);
					break;
				case "Longitude":
					estacion.setLongitud(value);
					break;
				}
				
				if(!iter2.hasNext()) {
					Transaction tx;
					SessionFactory sesion = HibernateUtil.getSessionFactory();
					Session s = sesion.openSession();
					tx = s.beginTransaction();

					// Guardar objeto en la base de datos
					s.save(estacion);
					// Actualizar información en la base de datos
					tx.commit();
					
					estacion = new Estacion();
					count++;
					System.out.println(count);
				}
			}
		}
	}
	
	public static void refreshHorarios() {
		JsonObject mainHorarios = readJsonFromUrl(url);

		JsonArray array = (JsonArray) mainHorarios.get("aggregated");
		Iterator<JsonElement> iter = array.iterator();
		
		int count = 0;
		
		while (iter.hasNext()) {
			JsonElement entrada = iter.next();
			JsonObject objeto = entrada.getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iter2 = objeto.entrySet().iterator();
			
			iter2.next();
			
			String nameEstacion = iter2.next().getValue().getAsJsonPrimitive().getAsString();
			
			String jsonStringUrl = iter2.next().getValue().getAsJsonPrimitive().getAsString();
			
			switch(count) {
			case 0:
				//horarios
				
				datosHorariosGenerator(nameEstacion, jsonStringUrl);
				
				count++;
				break;
			case 1:
				//diarios
				
				
				
				count++;
				break;
			case 2:
				//indice
				
				
				
				count = 0;
				break;
			}
			
		}
	}

	private static void datosHorariosGenerator(String name, String urlEstacion) {
		CalidadAireHorario aire = new CalidadAireHorario();
		
		JsonObject datos = readJsonCrashedFromUrl(urlEstacion);
		
		JsonArray array = (JsonArray) datos.get("jsonData");
		Iterator<JsonElement> iter = array.iterator();
		
		int count = 0;
		
		while (iter.hasNext()) {
			JsonElement entrada = iter.next();
			JsonObject objeto = entrada.getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iter2 = objeto.entrySet().iterator();
			Iterator<Map.Entry<String, JsonElement>> iter3 = objeto.entrySet().iterator();
			
			while(iter2.hasNext()) {
				String value = iter2.next().getKey().toString();
				String valueData = iter3.next().getValue().getAsString();
				
				//Para añadir los datos al objeto
				if(value.contains("Date")) {
					
					try {
						aire.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(valueData));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else if(value.equals("HourGMT")) {
					aire.setHora(valueData);
				} else if(value.equals("COmgm3")) {
					aire.setComgm3(valueData);
				} else if(value.equals("NOgm3")) {
					aire.setNogm3(valueData);
				} else if(value.equals("NO2gm3")) {
					aire.setNo2gm3(valueData);
				} else if(value.equals("NOXgm3")) {
					aire.setNoxgm3(valueData);
				} else if(value.equals("PM10gm3")) {
					aire.setPm10gm3(valueData);
				} else if(value.equals("PM25gm3")) {
					aire.setPm25gm3(valueData);
				} else if(value.equals("SO2gm3")) {
					aire.setSo2gm3(valueData);
				}
				
				if(!iter2.hasNext()) {
					
					//aire.setEstacion(new Estacion());
					
					
					SessionFactory factory = new Configuration().configure("euskalmet.hibernate.cfg.xml").addAnnotatedClass(CalidadAireHorario.class)
			                .buildSessionFactory();

			        // create session
			        Session session = factory.getCurrentSession();
					
					session.save(aire);
					session.getTransaction().commit();
					
				}
			}
			
			count++;
			
			if(count == 24) {
				return;
			}
		}
	}
	
	private static JsonObject readJsonFromUrl(String url) {
		InputStream is = null;
		try {
			is = new URL(url).openStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = null;
			try {
				jsonText = readAll(rd);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JsonObject json = new JsonParser().parse(jsonText).getAsJsonObject();
			return json;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static JsonObject readJsonCrashedFromUrl(String url) {
		InputStream is = null;
		try {
			is = new URL(url).openStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = null;
			try {
				jsonText = readAll(rd);
				
				//Estandarizamos el json para que pueda ser legible para los metodos
				jsonText = jsonText.replace("jsonCallback([", "");
				jsonText = jsonText.replace("]);", "");
				jsonText = "{ \"jsonData\": [" + jsonText + "]}";			
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JsonObject json = new JsonParser().parse(jsonText).getAsJsonObject();
			return json;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}