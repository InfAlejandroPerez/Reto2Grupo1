package cliente;


import java.util.ArrayList;

import dto.DTO;
import objetos.Estacion;

public class Teste {

	public static void main(String[] args) {
		
		String json = "{ 'operacion' : 'estacionesPorMunicipio',"
				+ " 'userName' : 'admin2',"
				+ " 'password' : 'admin2s',"
				+ " 'municipio' : 'Bilbao'}".replace('"', '"' );
		
		Cliente cliente = new Cliente();

		ArrayList<Estacion> datosCliente = (ArrayList<Estacion>) cliente.iniciar(json);
			
		
		System.out.println(datosCliente.get(0).getDireccion());
		
	}
	
	
}
