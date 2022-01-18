package cliente;


import dto.DTO;

public class Teste {

	public static void main(String[] args) {
		
		String json = "{ 'operacion' : 'lista_municipios',"
				+ " 'userName' : 'admin2',"
				+ " 'password' : 'admin2s',"
				+ " 'campoBusqueda' : 'Bilbao'}".replace('"', '"' );
		
		Cliente cliente = new Cliente();

		DTO datosCliente = (DTO) cliente.iniciar(json);
			
		
		/*for(int i=0; i < datosCliente.getListaMunicipios().size(); i++) {
			
			System.out.println("Respuesta: " + datosCliente.getListaMunicipios().get(i).toString());
		}*/
		
	}
	
	
}
