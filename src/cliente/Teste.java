package cliente;


import dto.DTO;
import objetos.Estacion;

public class Teste {

	public static void main(String[] args) {
		
		String json = "{ 'operacion' : 'estacion',"
				+ " 'userName' : 'admin2',"
				+ " 'password' : 'admin2s',"
				+ " 'campoBusqueda' : 'Bilbao'}".replace('"', '"' );
		
		Cliente cliente = new Cliente();

		Estacion datosCliente = (Estacion) cliente.iniciar(json);
			
		
		System.out.println(datosCliente.getLatitud());
		
	}
	
	
}
