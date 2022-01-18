package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dto.DTO;
import javassist.expr.Instanceof;
import objetos.Users;

class Servidor {

	private final int PUERTO = 5005;
	
	public void iniciar() {
		
		ObjectInputStream entrada= null;
		ObjectOutputStream salida = null;
		ServerSocket servidor = null;
		Socket cliente = null;
		
		try {
			
			Gson gson = new Gson();	
			servidor = new ServerSocket(PUERTO);
			
			
			while (true) { 
								
				System.out.println("Esperando conexiones del cliente...");
				
				cliente = servidor.accept();
				System.out.println("Cliente conectado.");
				
				salida = new ObjectOutputStream(cliente.getOutputStream());
				entrada = new ObjectInputStream(cliente.getInputStream());
				
				String usuarioJson = (String) entrada.readObject();
				
				DTO datosCliente = gson.fromJson(usuarioJson, DTO.class);
		
				System.out.println("pssw: " + datosCliente.getPassword());
				System.out.println("operacion: " + datosCliente.getOperacion());
				System.out.println("campobusqueda: " + datosCliente.getCampoBusqueda());
				
				Controller controlador = new Controller();
				
				DTO dtoRespuestaControler = (DTO) controlador.controlador(datosCliente);
				
				String respuestaServer = gson.toJson(dtoRespuestaControler, DTO.class);
				
				
				salida.writeObject(respuestaServer);
				
				
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
