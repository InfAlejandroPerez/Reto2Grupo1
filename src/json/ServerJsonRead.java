package json;

import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import server.ControllerV2;

public class ServerJsonRead {
	public static void jsonMethodRead(String jsonRecive, ObjectOutputStream salidaRecive) {
		//Desciframos el String que hemos recibido
		String jsonString = cipher.Cifrado.decode(jsonRecive);
	    System.out.println(jsonString);

		System.out.println("");
		
		JsonObject json = (JsonObject) (new JsonParser()).parse(jsonString);
		
		JsonArray array = (JsonArray) json.get("jsonData");
		Iterator<JsonElement> iter = array.iterator();
		
		while (iter.hasNext()) {
			JsonElement entrada = iter.next();
			JsonObject objeto = entrada.getAsJsonObject();
			Iterator<Map.Entry<String, JsonElement>> iterKey = objeto.entrySet().iterator();
			Iterator<Map.Entry<String, JsonElement>> iterValue = objeto.entrySet().iterator();
			
			String key = iterKey.next().getValue().getAsString();
			iterValue.next();
			
			switch(key) {
			case "login":
				ControllerV2.login(iterKey, iterValue, salidaRecive);
				break;
			case "registrar":
				ControllerV2.register(iterKey, iterValue, salidaRecive);
				break;
			case "lista_municipio":
				ControllerV2.listados(salidaRecive, 0);
				break;
			case "lista_estaciones":
				ControllerV2.listados(salidaRecive, 1);
				break;
			case "lista_espacios":
				ControllerV2.listados(salidaRecive, 2);
				break;
			case "lista_municipio_por_provincia":
				ControllerV2.listadosPorProvincia(iterKey, salidaRecive);
				break;
			case "lista_lugares_por_municipio":
				ControllerV2.listadosPorMunicipio(iterKey, salidaRecive);
				break;
			case "estaciones_filtro":
				ControllerV2.estacionesFiltro(iterKey, salidaRecive);
				break;
			case "gallery":
				ControllerV2.gallery(iterKey, salidaRecive);
				break;
			case "detalles_municipio":
				ControllerV2.detalles(iterKey, salidaRecive, 0);
				break;
			case "detalles_espacios":
				ControllerV2.detalles(iterKey, salidaRecive, 1);
				break;
			case "detalles_estaciones":
				ControllerV2.detalles(iterKey, salidaRecive, 2);
				break;
			case "savephoto":
				ControllerV2.savePhoto(iterKey, iterValue,salidaRecive);
				break;
			case "es_favorito":
				ControllerV2.esFavorito(iterKey, salidaRecive);
				break;
			case "addFavorito":
				ControllerV2.setFavorito(iterKey, salidaRecive,1);
				break;	
			case "quitarFavorito":
				ControllerV2.setFavorito(iterKey, salidaRecive,2);
				break;
			case "getIdUser":
				ControllerV2.getIdUser(iterKey, salidaRecive);
				break;
			case "municipio":
				ControllerV2.getMunicipios(salidaRecive);
			case "getTopFavoritos":
				ControllerV2.getTopFavoritos(iterKey, salidaRecive, 0);
			case "getTopFavoritosPorProvincia":
				ControllerV2.getTopFavoritos(iterKey, salidaRecive, 1);
			case "getTopFavoritos_municipio":
				ControllerV2.getTopFavoritos(iterKey, salidaRecive, 2);
			case "municipio_por_espacio":
				ControllerV2.getNombreMunicipioPorEspacio(iterKey, salidaRecive);
			}
			
		}
	}
}
