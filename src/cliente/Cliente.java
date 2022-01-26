package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import cipher.Cifrado;
import dto.DTO;
import objetos.Estacion;
import vista.ListaMunicipios;
import vista.VentanaLogin;

public class Cliente {

	private final int PUERTO = 5005;
	public static final int MUNICIPIO = 2020;
	public static final int ESPACIOS = 2021;
	public static final int ESTACION = 2022;
	public static final int ESTACION_POR_MUNICPIO = 2023;
	public static final int MUNICIPIO_POR_PROVINCIA = 2024;
	private static final String IP = "localhost";

	EnviarThread enviarCliente = null;
	RecibirThread recibirCliente = null;

	public String iniciar(String json) {

		Socket cliente = null;
		ObjectInputStream entrada = null;
		ObjectOutputStream salida = null;

		Gson gson = new Gson();

		try {

			cliente = new Socket(IP, PUERTO);

//		while(true) {

			System.out.println("Conexi�n realizada con servidor");
			salida = new ObjectOutputStream(cliente.getOutputStream());
			entrada = new ObjectInputStream(cliente.getInputStream());

			salida.writeObject(json);

			String usuarioJson = (String) entrada.readObject();
			System.out.println(usuarioJson);

			// Estacion datosCliente = gson.fromJson(usuarioJson, Estacion.class);

			// System.out.println("Recibido cliente: " + datosCliente.isLoginValidador());

			return usuarioJson;
//			}

		} catch (IOException e) {
			System.out.println("Error ioe : " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error cliente: " + e.getMessage());
		}

		return null;

	}

	public static void login(String user, String password) {
		try {

			Socket client = new Socket(IP, 5005); // connect to server
			System.out.println("Conectado con el servidor");
			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			String json = "{ \"operacion\" : \"login\"," + " \"userName\" : \"" + user + "\"," + " \"password\" : \""
					+ password + "\"}";

			salida.writeObject(json);
			salida.flush();

			try {
				String usuarioJson = (String) entrada.readObject();
				DTO datosCliente = (new Gson()).fromJson(usuarioJson, DTO.class);

				if (datosCliente.isLoginValidador()) {
					ListaMunicipios listaMun = new ListaMunicipios();
					listaMun.setVisible(true);
				} else if (!datosCliente.isLoginValidador()) {
					JOptionPane.showMessageDialog(null, "Nombre de usuario o contrase�a incorrectos");
				} else {
					JOptionPane.showMessageDialog(null, "Server error");
				}

			} catch (ClassNotFoundException e) {

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void login2(String user, String password) {
		try {

			Socket client = new Socket(IP, 5005); // connect to server
			System.out.println("Conectado con el servidor");
			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			String json = Cifrado.encode("{ \"jsonData\": [ { \"operacion\" : \"login\",  \"user\" : \"" + user + "\", "
					+ "\"password\" : \"" + password + "\"} ]}");

			salida.writeObject(json);
			salida.flush();

			try {
				String response = Cifrado.decode((String) entrada.readObject());
				// DTO datosCliente = (new Gson()).fromJson(usuarioJson, DTO.class);

				if (response.equals("true")) {
					System.out.println(" ha entrado" + response.equals("true"));
					ListaMunicipios listaMun = new ListaMunicipios();
					listaMun.setVisible(true);
				} else if (response.equals("false")) {
					JOptionPane.showMessageDialog(null, "Nombre de usuario o contrase�a incorrectos");
				} else {
					JOptionPane.showMessageDialog(null, "Server error");
				}

			} catch (ClassNotFoundException e) {

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void register(String user, String password) {
		try {
			Socket client = new Socket(IP, 5005); // connect to server

			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			String json = "{ 'operacion' : 'registrar'," + " \"userName\" : \"" + user + "\"," + " \"password\" : \""
					+ password + "\"}";

			salida.writeObject(json);

			salida.flush();

			try {
				String usuarioJson = (String) entrada.readObject();
				DTO datosCliente = (new Gson()).fromJson(usuarioJson, DTO.class);

				if (datosCliente.isUsuarioRegistrado()) {
					VentanaLogin VentLog = new VentanaLogin();
					VentLog.setVisible(true);
				} else if (!datosCliente.isUsuarioRegistrado()) {
					JOptionPane.showMessageDialog(null, "Error de registro");
				} else {
					JOptionPane.showMessageDialog(null, "Server error");
				}

			} catch (ClassNotFoundException e) {

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void register2(String user, String password) {

		try {
			Socket client = new Socket(IP, 5005); // connect to server

			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			String json = Cifrado.encode("{ 'jsonData': [{ " + "'operacion' : 'registrar'," + "'user': '" + user + "',"
					+ "'password': '" + password + "'}]}");

			salida.writeObject(json);

			salida.flush();

			try {
				String response = Cifrado.decode((String) entrada.readObject());

				if (response.equals("true")) {
					VentanaLogin VentLog = new VentanaLogin();
					VentLog.setVisible(true);
				} else if (response.equals("false")) {
					JOptionPane.showMessageDialog(null, "Usuario no registrado");
				} else {
					JOptionPane.showMessageDialog(null, "Server error");
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getArrayNamesData(int type) {
		ArrayList<String> response = new ArrayList<>();

		String typeJson = "";

		switch (type) {
		case MUNICIPIO:
			typeJson = "lista_municipios";
			break;
		case ESPACIOS:
			typeJson = "espacios";
			break;
		case ESTACION:
			typeJson = "estaciones";
			break;
		}

		String json = "{ \"operacion\" : \"" + typeJson + "\"}";

		return getInfo(json);
	}

	private static ArrayList<String> getInfo(String json) {

		ArrayList<String> listaInfos = new ArrayList<String>();

		try {
			Socket client = new Socket(IP, 5005);

			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			salida.writeObject(json);

			salida.flush();

			try {

				String reciveJson = (String) entrada.readObject();

				String[] arrayString = reciveJson.split("/");

				System.out.println("Get info:" + reciveJson);

				for (int i = 0; i < arrayString.length; i++) {

					System.out.println(arrayString[i].toString());

					listaInfos.add(arrayString[i].toString());

				}

				return listaInfos;

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] getArrayListas(int type) {
		String typeJson = "";

		switch (type) {
		case MUNICIPIO:
			typeJson = "lista_municipio";
			break;
		case ESPACIOS:
			typeJson = "lista_espacios";
			break;
		case ESTACION:
			typeJson = "lista_estaciones";
			break;

		}

		String json = Cifrado.encode("{ 'jsonData': [{ " + "'operacion' : '" + typeJson + "'}]}");

		return getIfoV2(json);
	}

	public static String[] getArrayListasMunicipiosPorProvincia(String lugar) {

		String typeJson = "lista_municipio_por_provincia";

		String json = Cifrado
				.encode("{ 'jsonData': [{ " + "'operacion' : '" + typeJson + "'," + "'name': '" + lugar + "'}]}");

		return getIfoV2(json);
	}

	public static String[] getArrayListasLugaresPorMunicipio(String lugar, int opcion) {

		String typeJson = "lista_lugares_por_municipio";

		String json = Cifrado.encode("{ 'jsonData': [{ " + "'operacion' : '" + typeJson + "'," + "'name': '" + lugar
				+ "', 'opcion': '" + opcion + "' }]}");

		return getIfoV2(json);
	}

	private static String[] getIfoV2(String json) {
		try {
			Socket client = new Socket(IP, 5005);

			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			salida.writeObject(json);

			salida.flush();

			try {
				return Cifrado.decode((String) entrada.readObject()).split(",");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDetalles(String lugar, String operacion) {
		try {
			Socket client = new Socket(IP, 5005);

			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			String json = Cifrado
					.encode("{ 'jsonData': [{ " + "'operacion' : '" + operacion + "'," + "'name': '" + lugar + "'}]}");

			salida.writeObject(json);

			salida.flush();

			try {

				return Cifrado.decode((String) entrada.readObject());

//	                				JsonObject jsonObject = (JsonObject) (new JsonParser()).parse(recive);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String getFavorito(String idParqueNatural, String idUser) {
		try {
			Socket client = new Socket(IP, 5005);

			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			String json = Cifrado
					.encode("{ 'jsonData': [{ " + "'operacion' : 'es_favorito', 'idParqueNatural': '" + idParqueNatural +"', 'idUser': '"+ idUser + "'}]}");

			salida.writeObject(json);

			salida.flush();

			try {

				return Cifrado.decode((String) entrada.readObject());


			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String setFavorito(String idParqueNatural, String idMunicipio , String idUser, int opcion) {
		try {
			Socket client = new Socket(IP, 5005);

			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			String operacion = (opcion==1)?"addFavorito":"quitarFavorito";
			
			String json = Cifrado
					.encode("{ 'jsonData': [{ " + "'operacion' : '"+ operacion +"', 'idParqueNatural': '" + idParqueNatural +
							"', 'idUser': '"+ idUser + "', 'idMunicipio': '"+ idMunicipio + "'}]}");

			salida.writeObject(json);

			salida.flush();

			try {

				return Cifrado.decode((String) entrada.readObject());


			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}