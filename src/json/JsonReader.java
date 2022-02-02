package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import hibernateUtil.HibernateUtil;
import objetos.CalidadAireDiario;
import objetos.CalidadAireHorario;
import objetos.CalidadAireIndice;
import objetos.EspaciosNaturales;
import objetos.Estacion;
import objetos.Hash;
import objetos.Municipio;

public class JsonReader {
	private static final String url = "https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2021/es_def/adjuntos/index.json";
	private static Session s;

	public static void main(String args[]) {
		//HttpCert.validCert();
		checkHash();
		//refillDb();
	}
	
	private static void refillDb() {
		// Municipio
		uploadMunicipio();
		// espacios naturales
		uploadEspaciosNaturales();
		// estaciones
		uploadEstaciones();
		
		//datos clima
		refreshHorarios();
	}

	public static void checkHash() {
		HttpCert.validCert();
		JsonObject mainHorarios = readJsonFromUrl(url);

		String last = mainHorarios.get("lastUpdateDate").getAsString();
		setHibernateUtils();
		try {

			String hql = "FROM Hash WHERE id = 1";
			Query q = s.createQuery(hql);

			Hash now = (Hash) q.uniqueResult();

			if (!last.equals(now.getHash())) {
				Transaction tx = s.beginTransaction();
				
				String names[] = { "calidad_aire_diario", "calidad_aire_horario", "calidad_aire_indice",
						"municipio", "espacios_naturales", "estacion" };

				//Para que pueda eliminar es necesario deshabilitar las fk
				removeAllFromTable("set foreign_key_checks=0");
				for (int i = 0; i < names.length; i++) {
					removeAllFromTable("TRUNCATE " + names[i]);
				}
				
				removeAllFromTable("set foreign_key_checks=1");
				
				//actualizamos hash
				now.setHash(last);
				s.update(now);

				// Actualizar información en la base de datos
				tx.commit();
				s.flush();
				
			} else {
				return;
			}
			
			refillDb();

		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}

	}

	private static void removeAllFromTable(String hql) {
		try {
			s.createSQLQuery(hql).executeUpdate();
		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}
	}

	private static void setHibernateUtils() {
		SessionFactory sessionFac = HibernateUtil.getSessionFactory();
		s = sessionFac.openSession();
	}

	public static void uploadMunicipio() {
		String urlMunicipio = "https://drive.google.com/uc?id=1syFXxNOiNZQ-Zf_BNk0uTDgeZxV4CaB0&export=download";

		JsonObject objectEstacion = readJsonCrashedFromUrl(urlMunicipio);

		JsonArray array = (JsonArray) objectEstacion.get("jsonData");
		Iterator<JsonElement> iter = array.iterator();

		Municipio municipio = new Municipio();
		while (iter.hasNext()) {
			JsonElement entrada = iter.next();
			JsonObject objeto = entrada.getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iter2 = objeto.entrySet().iterator();
			Iterator<Map.Entry<String, JsonElement>> iter3 = objeto.entrySet().iterator();

			while (iter2.hasNext()) {
				String key = iter2.next().getKey().toString();
				String value = iter3.next().getValue().getAsString();

				switch (key) {
				case "documentName":
					String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);
					String accentRemoved = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

					municipio.setNombre(accentRemoved);
					break;
				case "turismDescription":
					String des = value.replace("<p>", "");
					des = des.replace("</strong>", "");
					des = des.replace("<strong>", "");
					des = des.replace("</p>", "");
					des = des.replace("\"", "");
					des = des.replace("'", "");
					municipio.setDescripcion(des);
					break;
				case "locality":
					String loc[] = value.split("\\s+");
					String locB = "";

					if (loc[0].toLowerCase().equals("la") || loc[0].toLowerCase().equals("san")
							|| loc[0].toLowerCase().equals("el")) {
						locB = loc[0] + " " + loc[1];
					} else {
						locB = loc[0];
					}

					municipio.setLocalidad(locB);
					break;
				case "territory":
					if (value.equals("Araba/Álava")) {
						municipio.setTerritorio("Araba/Alava");
					} else {
						municipio.setTerritorio(value);
					}
					break;
				}

				if (!iter2.hasNext()) {
					Transaction tx = s.beginTransaction();
					// Guardar objeto en la base de datos
					s.save(municipio);
					// Actualizar información en la base de datos
					tx.commit();

					municipio = new Municipio();
				}
			}
		}
	}

	public static void uploadEspaciosNaturales() {
		String urlEspacioNatural = "https://drive.google.com/uc?id=1oi-R1Y52sPGlNxLjWg2WgHZRQ5kwZQw8&export=download";

		JsonObject objectEstacion = readJsonCrashedFromUrl(urlEspacioNatural);

		JsonArray array = (JsonArray) objectEstacion.get("jsonData");
		Iterator<JsonElement> iter = array.iterator();

		String idMunicipio = "";
		EspaciosNaturales espacio = new EspaciosNaturales();
		while (iter.hasNext()) {
			JsonElement entrada = iter.next();
			JsonObject objeto = entrada.getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iter2 = objeto.entrySet().iterator();
			Iterator<Map.Entry<String, JsonElement>> iter3 = objeto.entrySet().iterator();

			while (iter2.hasNext()) {
				String key = iter2.next().getKey().toString();
				String value = iter3.next().getValue().getAsString();

				switch (key) {
				case "documentName":
					String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);
					String accentRemoved = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
					espacio.setNombre(accentRemoved);
					break;
				case "turismDescription":
					String des = value.replace("<p>", "");
					des = des.replace("</strong>", "");
					des = des.replace("<strong>", "");
					des = des.replace("</p>", "");
					des = des.replace("\"", "");
					des = des.replace("'", "");
					espacio.setDescripcion(des);
					break;
				case "locality":
					espacio.setLocalidad(value);
					break;
				case "municipality":
					idMunicipio = value;
					break;
				case "territory":
					espacio.setTerritorio(value);
					break;
				case "marks":
					espacio.setMarca(value);
					break;
				case "natureType":
					espacio.setNaturaleza(value);
					break;
				case "latwgs84":
					espacio.setLatitud(value);
					break;
				case "lonwgs84":
					espacio.setLongitud(value);
					break;
				}

				if (!iter2.hasNext()) {
					Transaction tx = s.beginTransaction();

					String operator = "/";
					if (idMunicipio.contains("*")) {
						operator = "\\*";
					}

					String result = "";

					if (idMunicipio.contains("San Sebastián")) {
						result = "San Sebastian";
					} else {
						result = (idMunicipio.split(operator))[0];
					}

					// Obtenemos el objeto para idmunicipio
					String hql = "From Municipio where nombre = '" + result + "'";
					Query q = s.createQuery(hql);

					Municipio municipio = (Municipio) q.uniqueResult();

					if (municipio != null) {
						espacio.setMunicipio(municipio);

						// Guardar objeto en la base de datos
						s.save(espacio);
						// Actualizar información en la base de datos
						tx.commit();

						espacio = new EspaciosNaturales();
						idMunicipio = "";
					}
				}
			}
		}
	}

	public static void uploadEstaciones() {
		String urlEstacion = "https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2020/es_def/adjuntos/estaciones.json";

		JsonObject objectEstacion = readJsonCrashedFromUrl(urlEstacion);

		JsonArray array = (JsonArray) objectEstacion.get("jsonData");
		Iterator<JsonElement> iter = array.iterator();

		String idMunicipio = "";
		Estacion estacion = new Estacion();
		while (iter.hasNext()) {

			JsonElement entrada = iter.next();
			JsonObject objeto = entrada.getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iter2 = objeto.entrySet().iterator();
			Iterator<Map.Entry<String, JsonElement>> iter3 = objeto.entrySet().iterator();

			while (iter2.hasNext()) {
				String key = iter2.next().getKey().toString();
				String value = iter3.next().getValue().getAsString();

				switch (key) {
				case "Name":
					String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);
					String accentRemoved = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

					if (accentRemoved.equals("Mª DIAZ HARO")) {
						accentRemoved = "Maria Diaz de Haro";
					} else if(accentRemoved.equals("ALGORTA (BBIZI2)")) {
						accentRemoved = "ALGORTA BBIZI2";
					} else if(accentRemoved.equals("ARRAIZ (Monte)")) {
						accentRemoved = "ARRAIZ Monte";
					} else if(accentRemoved.equals("FERIA (meteo)")) {
						accentRemoved = "FERIA meteo";
					} else if(accentRemoved.equals("ZIERBENA (Puerto)")) {
						accentRemoved = "ZIERBENA Puerto";
					} else if(accentRemoved.contains(".")) {
						accentRemoved = accentRemoved.replace(".", "");
					} else if(accentRemoved.equals("BANDERAS (meteo)")) {
						accentRemoved = "BANDERAS meteo";
					}

					estacion.setNombre(accentRemoved);
					break;
				case "Province":
					String normalizeds = Normalizer.normalize(value, Normalizer.Form.NFD);
					String accentRemoveds = normalizeds.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
					estacion.setProvincia(accentRemoveds);
					break;
				case "Town":
					idMunicipio = value;
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

				if (!iter2.hasNext()) {
					Transaction tx = s.beginTransaction();

					String operator = "/";
					if (idMunicipio.contains("*")) {
						operator = "\\*";
					}

					String result = "";

					if (idMunicipio.contains("San Sebastián")) {
						result = "San Sebastián";
					} else if (idMunicipio.contains("Agurain")) {
						result = "Salvatierra/Agurain";
					} else {
						result = (idMunicipio.split(operator))[0];
					}

					// Obtenemos el objeto para idmunicipio
					String hql = "From Municipio where nombre = '" + result + "'";
					Query q = s.createQuery(hql);

					Municipio municipio = (Municipio) q.uniqueResult();

					if (municipio != null) {
						estacion.setMunicipio(municipio);

						// Guardar objeto en la base de datos
						s.save(estacion);
						// Actualizar información en la base de datos
						tx.commit();

						estacion = new Estacion();
						idMunicipio = "";
					}
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

			switch (count) {
			case 0:
				// horarios

				datosHorariosGenerator(nameEstacion, jsonStringUrl);

				count++;
				break;
			case 1:
				// diarios
				
				datosDiarioGenerator(nameEstacion, jsonStringUrl);

				count++;
				break;
			case 2:
				// indice

				datosIndiceGenerator(nameEstacion, jsonStringUrl);
				count = 0;
				break;
			}
			
			if(!iter.hasNext()) {
				s.close();
				return;
			}
		}
	}

	private static void datosDiarioGenerator(String name, String urlEstacion) {
		CalidadAireDiario aire = new CalidadAireDiario();

		JsonObject datos = readJsonCrashedFromUrl(urlEstacion);

		if (datos == null) {
			return;
		}

		JsonArray array = (JsonArray) datos.get("jsonData");
		Iterator<JsonElement> iter = array.iterator();
		
		int delimitador = 0;
		
		while (iter.hasNext()) {
			JsonElement entrada = iter.next();
			JsonObject objeto = entrada.getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iter2 = objeto.entrySet().iterator();
			Iterator<Map.Entry<String, JsonElement>> iter3 = objeto.entrySet().iterator();

			while (iter2.hasNext()) {
				String key = iter2.next().getKey().toString();
				String value = iter3.next().getValue().getAsString();

				switch (key) {
				case "Date":
					try {
						aire.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(value));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "NOgm3":
					aire.setNogm3(value);
					break;
				case "NO2gm3":
					aire.setNo2gm3(value);
					break;
				case "NOXgm3":
					aire.setNoxgm3(value);
					break;
				case "PM10gm3":
					aire.setPm10gm3(value);
					break;
				case "PM25gm3":
					aire.setPm25gm3(value);
					break;
				}
				
				if (name.equals("ABANTO")) {
					switch (key) {
					case "Date":
						try {
							aire.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(value));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "NOgm3":
						aire.setNogm3(value);
						break;
					case "NO2gm3":
						aire.setNo2gm3(value);
						break;
					case "NOXgm3":
						aire.setNoxgm3(value);
						break;
					case "PM10gm3":
						aire.setPm10gm3(value);
						break;
					case "SH2gm3":
						aire.setPm25gm3(value);
						break;
					}
				}
				

				if (!iter2.hasNext()) {
					Transaction tx = s.beginTransaction();

					String response = name;
					if (name.contains("_")) {
						response = response.replace("_", " ");
					}

					String hql = "From Estacion where nombre LIKE '%" + (response.split("/"))[0] + "%'";
					Query q = s.createQuery(hql);
					q.setMaxResults(1);
					Estacion estacion = (Estacion) q.uniqueResult();

					if (estacion != null) {
						aire.setEstacion(estacion);
						// Guardar objeto en la base de datos
						s.save(aire);
						// Actualizar información en la base de datos
						tx.commit();
						aire = new CalidadAireDiario();
						delimitador++;
						
						if(delimitador == 24) {
							return;
						}
					}
				}
			}
		}
	}

	private static void datosIndiceGenerator(String name, String urlEstacion) {
		CalidadAireIndice aire = new CalidadAireIndice();

		JsonObject datos = readJsonCrashedFromUrl(urlEstacion);

		if (datos == null) {
			return;
		}

		JsonArray array = (JsonArray) datos.get("jsonData");
		Iterator<JsonElement> iter = array.iterator();
		
		int delimiter = 0;
		
		while (iter.hasNext()) {
			JsonElement entrada = iter.next();
			JsonObject objeto = entrada.getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iter2 = objeto.entrySet().iterator();
			Iterator<Map.Entry<String, JsonElement>> iter3 = objeto.entrySet().iterator();

			while (iter2.hasNext()) {
				String key = iter2.next().getKey().toString();
				String value = iter3.next().getValue().getAsString();

				switch (key) {
				case "Date":
					try {
						aire.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(value));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "HourGMT":
					aire.setHora(value);
					break;
				case "NO2ICA":
					aire.setNo2ica(value);
					break;
				case "NOXgm3":
					aire.setNoxgm3(value);
					break;
				case "PM10ICA":
					aire.setPm10ica(value);
					break;
				case "PM25ICA":
					aire.setPm25ica(value);
					break;
				case "SO2ICA":
					aire.setSo2ica(value);
					break;
				case "ICAEstacion":
					aire.setIcaestacion(value);
					break;
				}

				if (!iter2.hasNext()) {
					Transaction tx = s.beginTransaction();

					String response = name;
					if (name.contains("_")) {
						response = response.replace("_", " ");
					}

					String hql = "From Estacion where nombre = '" + response + "'";
					Query q = s.createQuery(hql);
					q.setMaxResults(1);
					Estacion estacion = (Estacion) q.uniqueResult();

					if (estacion != null) {
						aire.setEstacion(estacion);
						// Guardar objeto en la base de datos
						s.save(aire);
						// Actualizar información en la base de datos
						tx.commit();
						aire = new CalidadAireIndice();
						delimiter++;
						
						if(delimiter == 48) {
							return;
						}
					}
				}
			}
		}
	}

	private static void datosHorariosGenerator(String name, String urlEstacion) {
		CalidadAireHorario aire = new CalidadAireHorario();

		JsonObject datos = readJsonCrashedFromUrl(urlEstacion);

		if (datos == null) {
			return;
		}

		JsonArray array = (JsonArray) datos.get("jsonData");
		Iterator<JsonElement> iter = array.iterator();
		
		int delimiter = 0;
		
		while (iter.hasNext()) {
			JsonElement entrada = iter.next();
			JsonObject objeto = entrada.getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iter2 = objeto.entrySet().iterator();
			Iterator<Map.Entry<String, JsonElement>> iter3 = objeto.entrySet().iterator();

			while (iter2.hasNext()) {
				String value = iter2.next().getKey().toString();
				String valueData = iter3.next().getValue().getAsString();

				// Para añadir los datos al objeto
				if (value.contains("Date")) {

					try {
						aire.setFecha(new SimpleDateFormat("dd/MM/yyyy").parse(valueData));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (value.equals("HourGMT")) {
					aire.setHora(valueData);
				} else if (value.equals("COmgm3")) {
					aire.setComgm3(valueData);
				} else if (value.equals("NOgm3")) {
					aire.setNogm3(valueData);
				} else if (value.equals("NO2gm3")) {
					aire.setNo2gm3(valueData);
				} else if (value.equals("NOXgm3")) {
					aire.setNoxgm3(valueData);
				} else if (value.equals("PM10gm3")) {
					aire.setPm10gm3(valueData);
				} else if (value.equals("PM25gm3")) {
					aire.setPm25gm3(valueData);
				} else if (value.equals("SO2gm3")) {
					aire.setSo2gm3(valueData);
				}

				if (!iter2.hasNext()) {

					Transaction tx = s.beginTransaction();

					String response = name;
					if (name.contains("_")) {
						response = response.replace("_", " ");
					}

					String hql = "From Estacion where nombre = '" + response + "'";
					Query q = s.createQuery(hql);
					q.setMaxResults(1);
					Estacion estacion = (Estacion) q.uniqueResult();

					if (estacion != null) {
						aire.setEstacion(estacion);
						// Guardar objeto en la base de datos
						s.save(aire);
						// Actualizar información en la base de datos
						tx.commit();
						aire = new CalidadAireHorario();
						
						delimiter++;
						
						if(delimiter == 48) {
							return;
						}
					}
				}
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
			return null;
		}
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = null;
			try {
				jsonText = readAll(rd);

				// Estandarizamos el json para que pueda ser legible para los metodos
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