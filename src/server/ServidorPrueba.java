package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorPrueba {
	private final int PORT = 5005;
	
	public void init() {
		ServerSocket servidor = null;
		Socket cliente = null;
		
		try {
			servidor = new ServerSocket(PORT);
			
			while(!servidor.isClosed()) {
				System.out.println("Esperando peticiones.");
				
				cliente = servidor.accept();
				
				System.out.println("Peticion realizada.");
				
				ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				
				String jsonRecive = (String) entrada.readObject();
				
				json.ServerJsonRead.jsonMethodRead(jsonRecive, salida);
			}
			
		} catch (IOException e) {
			System.out.println("Error ioe: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			
		}
		
		
	}
	
	public static void main(String args[]) {
		ServidorPrueba server = new ServidorPrueba();
		server.init();
	}
}
