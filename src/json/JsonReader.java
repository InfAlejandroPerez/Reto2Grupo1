package json;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonReader {
	private static final String url = "https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2021/es_def/adjuntos/index.json";
	
	public static void main(String args[]) {
		refreshHorarios();
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
			iter2.next();
			
			String jsonString = iter2.next().getValue().getAsJsonPrimitive().getAsString();
			
			switch(count) {
			case 0:
				
				count++;
				break;
			case 1:
				
				count++;
				break;
			case 2:
				
				count = 0;
				break;
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

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}