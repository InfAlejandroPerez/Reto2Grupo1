package cliente;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dto.DTO;
import objetos.Users;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class Cliente {
	
	private final int PUERTO = 5005;
	private final String IP = "127.0.0.1";

	public void iniciar() {
		Socket cliente = null;
		ObjectInputStream entrada = null;
		ObjectOutputStream salida = null;

		try {
			cliente = new Socket(IP, PUERTO);
			
			while(true) {
				
				System.out.println("Conexión realizada con servidor");
				salida = new ObjectOutputStream(cliente.getOutputStream());
				entrada = new ObjectInputStream(cliente.getInputStream());
				
				Gson gson = new Gson();
				
				String json = "{\"operacion\":\"login\",\"userName\":\"admin\",\"password\":\"admin\",\"campoBusqueda\":\"Bilbao\"}";
				
				//DTO objCliente = gson.fromJson(json, DTO.class);
				
				
//			String json1 = "[{\"dorsal\":6," + "\"name\":\"Iniesta\","
//                    + "\"demarcation\":[\"Right winger\",\"Midfielder\"],"
//                    + "\"team\":\"FC Barcelona\"}]";
//			
				
				
//            JsonParser parser = new JsonParser();
//
//            JsonArray gsonArr = parser.parse(json1).getAsJsonArray();
				
				/* JsonObject postData = new JsonObject();
            postData.addProperty("usuario", "hola");*/
			
				salida.writeObject(json);
				String linea = (String) entrada.readObject();
				System.out.println("Recibido: " + linea);
				
			}
		
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
	} 

	public static void main(String[] args) {
		Cliente c = new Cliente();
		c.iniciar();
	}
	
	
	
}