package cliente;


import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import cliente.modelos.ModeloClienteEstacion;
import dto.DTO;
import objetos.Estacion;

public class Teste {

	public static void main(String[] args) {
		
		try {
			
			String json = "{ 'operacion' : 'detallesEstacion',"
					+ " 'userName' : 'admin2',"
					+ " 'password' : 'admin2s',"
					+ " 'campoBusqueda' : 'ABANTO'}".replace( '"'  , '"' );
			
			Cliente cliente = new Cliente();
			
			String datosCliente = cliente.iniciar(json);
			
			Gson gson = new Gson();
			
			ModeloClienteEstacion estacion = gson.fromJson(datosCliente, ModeloClienteEstacion.class);
			
			
			System.out.println(estacion.getDireccion());
			
		} catch (Exception e) {
			System.out.println(" error classe teste " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
}
