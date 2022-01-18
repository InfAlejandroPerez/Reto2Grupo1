package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;
import dto.DTO;


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
