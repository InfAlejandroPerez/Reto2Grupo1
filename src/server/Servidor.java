package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	private final int PORT = 5005;
	private ServerSocket servidor; 
	
	public void init() {
		
		try {
			servidor = new ServerSocket(PORT);
			Socket client = null;
			
			//check Hash
			json.JsonReader.checkHash();
			System.out.println("Comprobando cambios en base de datos.");
			
			System.out.println("Base de datos actualizada");
			
			while(!servidor.isClosed()) {
				try {
					System.out.println("Esperando peticiones.");
					client = servidor.accept();
				} catch (IOException e) {
	                System.out.println("I/O error: " + e);
	            }
				
				new ThreadServer(client).start();
			}
			
		} catch (IOException e) {
			
		} catch (Exception e) {
			System.out.println("Error server: " + e.getMessage());	
		}

	}
	
	public static void main(String args[]) {
		while(true) {
			Servidor server = new Servidor();
			server.init();
		}
	}
	
	private class ThreadServer extends Thread {
		private Socket cliente;
		
		public ThreadServer(Socket client) {
			this.cliente = client;
		}
		
		public void run() {
			System.out.println("Peticion realizada.");
			try {
				ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				
				String jsonRecive = (String) entrada.readObject();
				
				json.ServerJsonRead.jsonMethodRead(jsonRecive, salida);
				
			} catch (IOException e) {
				System.out.println("Error ioe: " + e.getMessage());
			} catch (Exception e) {

				
			} 
			
			return;
		}
	}
}
