package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
	
	private final int PUERTO = 5005;
	private final String IP = "127.0.0.1";

	public void iniciar() {
		Socket cliente = null;
		ObjectInputStream entrada = null;
		ObjectOutputStream salida = null;
		
		try {
			
			cliente = new Socket(IP, PUERTO);
			System.out.println("Conexión realizada con servidor");
			salida = new ObjectOutputStream(cliente.getOutputStream());
			entrada = new ObjectInputStream(cliente.getInputStream());

			salida.writeObject("Hola servidor, soy un cliente");
			String linea = (String) entrada.readObject();
			System.out.println("Recibido: " + linea);
		
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Error: " + e.getMessage());
		} finally {
			try {
				if (cliente != null)
					cliente.close();
				if (entrada != null)
					entrada.close();
				if (salida != null)
					salida.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Cliente c = new Cliente();
		c.iniciar();
	}
}