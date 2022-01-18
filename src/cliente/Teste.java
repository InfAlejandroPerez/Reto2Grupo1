package cliente;


import dto.DTO;

public class Teste {

	public static void main(String[] args) {
		
		String json = "{ 'operacion' : 'registrar',"
				+ " 'userName' : 'admin2',"
				+ " 'password' : 'admin2s',"
				+ " 'campoBusqueda' : 'Bilbao'}".replace('"', '"' );
		
		Cliente cliente = new Cliente();

		
		DTO datosCliente = (DTO) cliente.iniciar(json);
		
		
		System.out.println("Respuesta: " + datosCliente.isLoginValidador());
		
	
	}
	
	
}
