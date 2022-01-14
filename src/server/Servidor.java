package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import objetos.Users;

class Servidor {

	private final int PUERTO = 5005;
	private ObjectInputStream entrada;
	
	public void iniciar() {

		ServerSocket servidor = null;
		Socket cliente = null;
		
		try {
			
			servidor = new ServerSocket(PUERTO);
			
			while (true) { 
				
				System.out.println("Esperando conexiones del cliente...");
				
				cliente = servidor.accept();
				System.out.println("Cliente conectado.");

				entrada = new ObjectInputStream(cliente.getInputStream());
			
				Users usuario = new Users();
				usuario = (Users) entrada.readObject();
				System.out.println("Recibido: " + usuario);
			}

		} catch (IOException e) {
			System.out.println("Error ioe: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			
		} 
	}

	public static void main(String[] args) {
		Servidor s1 = new Servidor();
		s1.iniciar();
	}

}
