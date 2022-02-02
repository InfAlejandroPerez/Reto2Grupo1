package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cipher.Cifrado;
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

			System.out.println("Conexión realizada con servidor");
			salida = new ObjectOutputStream(cliente.getOutputStream());
			entrada = new ObjectInputStream(cliente.getInputStream());

			salida.writeObject(json);

			String usuarioJson = (String) entrada.readObject();

			return usuarioJson;

		} catch (IOException e) {
			System.out.println("Error ioe : " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error cliente: " + e.getMessage());
		}

		return null;

	}


	public static String login2(String user, String password) {
		try {

			Socket client = new Socket(IP, 5005); // connect to server
			System.out.println("Conectado con el servidor");
			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			String json = Cifrado.encode("{ \"jsonData\": [ { \"operacion\" : \"login\",  \"user\" : \"" + user + "\", "
					+ "\"password\" : \"" + Cifrado.encode(password) + "\"} ]}");

			salida.writeObject(json);
			salida.flush();

			try {
				String response = Cifrado.decode((String) entrada.readObject());

				if (response.equals("true")) {
					String jsonIdUser = Cliente.getIdUser(user);
					usuarioValidado(jsonIdUser);
					return response;
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
		return "";
	}

	private static void usuarioValidado(String jsonRespuesta) {

		JsonObject json = (JsonObject) (new JsonParser()).parse(jsonRespuesta);

		JsonArray array = (JsonArray) json.get("jsonData");
		Iterator<JsonElement> iter = array.iterator();

		JsonElement entrada = iter.next();
		JsonObject objeto = entrada.getAsJsonObject();

		Iterator<Map.Entry<String, JsonElement>> iterKey = objeto.entrySet().iterator();

		String idUser = iterKey.next().getValue().getAsString();

		ListaMunicipios listaMun = new ListaMunicipios(idUser);
		listaMun.setVisible(true);
	}

	public static String getIdUser(String userName) {
		try {

			Socket client = new Socket(IP, 5005); // connect to server
			System.out.println("Conectado con el servidor");
			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			String json = Cifrado
					.encode("{ 'jsonData': [ { 'operacion' : 'getIdUser',  'user' : '" + userName + "'} ]}");

			salida.writeObject(json);
			salida.flush();

			try {

				return Cifrado.decode((String) entrada.readObject());

			} catch (ClassNotFoundException e) {

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String register2(String user, String password) {

		try {
			Socket client = new Socket(IP, 5005); // connect to server

			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			String json = Cifrado.encode("{ 'jsonData': [{ " + "'operacion' : 'registrar'," + "'user': '" + user + "',"
					+ "'password': '" + Cifrado.encode(password) + "'}]}");

			salida.writeObject(json);

			salida.flush();

			try {
				String response = Cifrado.decode((String) entrada.readObject());

				if (response.equals("true")) {
					JOptionPane.showMessageDialog(null, "Usuario creado con exito");
					VentanaLogin VentLog = new VentanaLogin();
					VentLog.setVisible(true);
					return response;
				}else if(response.equals("duplicate")) {
					JOptionPane.showMessageDialog(null, "Usuario ya existe");
				}
				else if (response.equals("false")) {
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
		return "";
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

				for (int i = 0; i < arrayString.length; i++) {

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

			String json = Cifrado.encode("{ 'jsonData': [{ " + "'operacion' : 'es_favorito', 'idParqueNatural': '"
					+ idParqueNatural + "', 'idUser': '" + idUser + "'}]}");

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

	public static String[] getTopFavoritos(String provincia, int opcion) {
		try {
			Socket client = new Socket(IP, 5005);

			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());
			String json = null;

			if (provincia.equals("") && opcion == 1) {

				json = Cifrado.encode(
						"{ 'jsonData': [{ 'operacion' : 'getTopFavoritos', 'provincia':'MostrarTodo'}]}");
			
			}else if(opcion==2) { 
				json = Cifrado.encode(
						"{ 'jsonData': [{'operacion' : 'getTopFavoritos_municipio', 'municipio':'" + provincia + "'}]}");
			}
			else {
				json = Cifrado.encode("{ 'jsonData': [{'operacion' : 'getTopFavoritosPorProvincia', 'provincia':'"
						+ provincia + "'}]}");

			}

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

	public static String setFavorito(String idParqueNatural, String idMunicipio, String idUser, int opcion) {
		try {
			Socket client = new Socket(IP, 5005);

			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			String operacion = (opcion == 1) ? "addFavorito" : "quitarFavorito";

			String json = Cifrado.encode("{ 'jsonData': [{ " + "'operacion' : '" + operacion + "', 'idParqueNatural': '"
					+ idParqueNatural + "', 'idUser': '" + idUser + "', 'idMunicipio': '" + idMunicipio + "'}]}");

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
	
	public static String getMunicipioPorEspacio(String espacioNatural) {
		try {
			Socket client = new Socket(IP, 5005);

			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream salida = new ObjectOutputStream(client.getOutputStream());

			String json = Cifrado.encode("{ 'jsonData': [{ 'operacion' : 'municipio_por_espacio', 'nombre': '"
					+ espacioNatural + "'}]}");

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