package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.google.gson.Gson;

import dto.DTO;

public class Cliente {
	
	private final int PUERTO = 5005;
	private final String IP = "127.0.0.1";
	
	EnviarThread enviarCliente = null;
	RecibirThread recibirCliente = null;
	
	public void iniciar() {
		
		Socket cliente = null;
		ObjectInputStream entrada = null;
		ObjectOutputStream salida = null;
		Gson gson = new Gson();	
		try {
			
			cliente = new Socket(IP, PUERTO);
			
//		while(true) {
				
				System.out.println("Conexión realizada con servidor");
				salida = new ObjectOutputStream(cliente.getOutputStream());
				entrada = new ObjectInputStream(cliente.getInputStream());
								
				String json = "{ 'operacion' : 'login',"
						+ " 'userName' : 'admin',"
						+ " 'password' : 'admin',"
						+ " 'campoBusqueda' : 'Bilbao'}".replace('"', '"' );
				
				salida.writeObject(json);
				
				String usuarioJson = (String) entrada.readObject();
				
				DTO datosCliente = gson.fromJson(usuarioJson, DTO.class);
				
			//	String loginValidado2 = (String) entrada.readObject();
				
				System.out.println("Recibido: " + datosCliente.isLoginValidador());
				
//			}
			

		
		} catch (IOException e) {
			System.out.println("Error ioe : " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
	} 

	public static void main(String[] args) {
		Cliente c = new Cliente();
		c.iniciar();
	}
	
	
	
}