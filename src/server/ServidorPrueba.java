package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorPrueba {
	
	private final int PORT = 5005;
	private ServerSocket servidor; 
	
	public void init() {
		
		try {
			servidor = new ServerSocket(PORT);
			Socket client = null;

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
			System.out.println("Error ioe: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());	
		}
		
		
	}
	
	public static void main(String args[]) {
		ServidorPrueba server = new ServidorPrueba();
		server.init();
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
				this.stop();
			} catch (IOException e) {
				System.out.println("Error ioe: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}
