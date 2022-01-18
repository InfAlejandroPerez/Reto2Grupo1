package cliente;

import com.google.gson.Gson;

import dto.DTO;

public class Teste {

	public static void main(String[] args) {
		
		String json = "{ 'operacion' : 'login',"
				+ " 'userName' : 'admin',"
				+ " 'password' : 'admin',"
				+ " 'campoBusqueda' : 'Bilbao'}".replace('"', '"' );
		
		Cliente cliente = new Cliente();

		
		DTO datosCliente = (DTO) cliente.iniciar(json);
		
		
		System.out.println("Respuesta:" + datosCliente.isLoginValidador());
		
	
	}
	
	
}
