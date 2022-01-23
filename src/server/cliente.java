package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class cliente {
	private final int PUERTO = 5005;
	private final String IP = "127.0.0.1";
	
	public void init() {
		Socket cliente = null;
		ObjectInputStream entrada = null;
		ObjectOutputStream salida = null;
		
		try {
			cliente = new Socket(IP, PUERTO);
			System.out.println("Conexión realizada con servidor");
			
			salida = new ObjectOutputStream(cliente.getOutputStream());
			entrada = new ObjectInputStream(cliente.getInputStream());
			

			salida.writeObject(cipher.Cifrado.encode("{ \"jsonData\": [ { \"operacion\" : \"detalles_municipio\", 'name': 'Aia' } ]}"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		cliente client = new cliente();
		client.init();
	}
}
