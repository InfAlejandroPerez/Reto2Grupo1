package vista;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cliente.Cliente;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextPane;

public class DetallesEspacioNatural extends JFrame {

	private JPanel contentPane;
	private JLabel lblInfoMarca;
	private JLabel lblInfoNombre;
	private JLabel lblInfoNaturaleza;
	private JTextPane textPane;
	private String idMunicipio;
	private String idEspacioNatural;
	private String idUser;
	private JButton btnFavorito;
	private String latitud = "";
	private String longitud = "";
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// DetallesEstacion frame = new DetallesEstacion();
					// frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DetallesEspacioNatural(String lugarSelecionado, String opcion, String municipio, String IDUSER) {
		setResizable(false);
		idUser = IDUSER;
		setTitle("Detalles estacion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 579, 515);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		getContentPane().setLayout(null);

		JLabel lblTitulo = new JLabel("Espacio Natural");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitulo.setBounds(176, 23, 136, 40);
		getContentPane().add(lblTitulo);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNombre.setBounds(39, 87, 103, 40);
		getContentPane().add(lblNombre);

		JSeparator separator = new JSeparator();
		separator.setBounds(49, 138, 469, 7);
		getContentPane().add(separator);

		JLabel lblProvincia = new JLabel("Descripción");
		lblProvincia.setHorizontalAlignment(SwingConstants.CENTER);
		lblProvincia.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblProvincia.setBounds(39, 171, 103, 40);
		getContentPane().add(lblProvincia);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(39, 254, 479, 1);
		getContentPane().add(separator_1);

		JLabel lblDireccion = new JLabel("Marca");
		lblDireccion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDireccion.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDireccion.setBounds(39, 264, 79, 23);
		getContentPane().add(lblDireccion);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(39, 298, 479, 7);
		getContentPane().add(separator_1_1);

		JLabel lblMaps = new JLabel("Naturaleza");
		lblMaps.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaps.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMaps.setBounds(39, 316, 114, 23);
		getContentPane().add(lblMaps);

		JButton BtnAtrasToDetalles = new JButton("Volver");
		BtnAtrasToDetalles.setFont(new Font("Tahoma", Font.BOLD, 12));
		BtnAtrasToDetalles.setBounds(429, 421, 89, 23);
		BtnAtrasToDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DetallesMunicipio detallesMun = new DetallesMunicipio(municipio, idUser);// obj created for class Second()
				detallesMun.setVisible(true); // Open the Second.java window
				dispose(); // Close the First.java window
			}
		});
		contentPane.add(BtnAtrasToDetalles);

		lblInfoMarca = new JLabel();
		lblInfoMarca.setBounds(152, 266, 294, 29);
		contentPane.add(lblInfoMarca);

		lblInfoNombre = new JLabel();
		lblInfoNombre.setBounds(152, 87, 294, 38);
		contentPane.add(lblInfoNombre);

		lblInfoNaturaleza = new JLabel();
		lblInfoNaturaleza.setBounds(163, 310, 213, 29);
		contentPane.add(lblInfoNaturaleza);

		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(176, 152, 331, 105);

		JScrollPane ScrollEstaciones = new JScrollPane();
		ScrollEstaciones.setSize(366, 93);
		ScrollEstaciones.setLocation(152, 150);
		ScrollEstaciones.setViewportView(textPane);
		contentPane.add(ScrollEstaciones);

		btnFavorito = new JButton();
		btnFavorito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (btnFavorito.getText() == "Add Favorito") {

					setFavorito(1);

					btnFavorito.setText("Quitar Favorito");
				} else {
					setFavorito(2);
					btnFavorito.setText("Add Favorito");
				}

			}

		});
		btnFavorito.setBounds(394, 70, 124, 40);
		contentPane.add(btnFavorito);
		
		JLabel lblMaps_1 = new JLabel("GoogleMaps");
		lblMaps_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaps_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMaps_1.setBounds(49, 350, 103, 40);
		contentPane.add(lblMaps_1);
		
		
		JButton btnAbrirMapa = new JButton("Abrir mapa");
		btnAbrirMapa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String enlaceGoogleMaps = "https://google.com/maps?q=" + latitud + "," + longitud;
				System.out.println(enlaceGoogleMaps);
				try {
					Desktop.getDesktop().browse(new URL(enlaceGoogleMaps).toURI());

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnAbrirMapa.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAbrirMapa.setBounds(226, 356, 136, 34);
		contentPane.add(btnAbrirMapa);
				
		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setBounds(49, 345, 459, 7);
		contentPane.add(separator_1_1_1);

		/**
		 ** Cogemos los detalles del Espacio Natural
		 */

		try {
			String json = Cliente.getDetalles(lugarSelecionado, opcion);

			JsonObject jsonObject = (JsonObject) (new JsonParser()).parse(json);

			JsonArray array = (JsonArray) jsonObject.get("jsonData");
			Iterator<JsonElement> iter = array.iterator();

			while (iter.hasNext()) {
				JsonElement entradaJson = iter.next();
				JsonObject objeto = entradaJson.getAsJsonObject();
				Iterator<Map.Entry<String, JsonElement>> iterKey = objeto.entrySet().iterator();
				Iterator<Map.Entry<String, JsonElement>> iterValue = objeto.entrySet().iterator();

				while (iterKey.hasNext()) {
					String key = iterKey.next().getKey().toString();
					String value = iterValue.next().getValue().getAsString();

					switch (key) {
					case "name":
						lblInfoNombre.setText(value);
						System.out.println(json);
						break;
					case "marca":
						lblInfoMarca.setText(value);
						break;
					case "descripcion":
						textPane.setText(value);
						break;
					case "naturaleza":
						lblInfoNaturaleza.setText(value);
						break;
					case "idmunicipio":
						idMunicipio = value;
						break;
					case "id":
						idEspacioNatural = value;
						break;
					case "latitud":
						latitud = value.replace(',', '.');
						break;
					case "longitud":
						longitud = value.replace(',', '.');
					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		/***
		 * miramos si es Favoritos o no
		 * 
		 */

		try {
			String json = Cliente.getFavorito(idEspacioNatural, idUser);

			JsonObject jsonObject = (JsonObject) (new JsonParser()).parse(json);

			JsonArray array = (JsonArray) jsonObject.get("jsonData");
			Iterator<JsonElement> iter = array.iterator();

			while (iter.hasNext()) {
				JsonElement entradaJson = iter.next();
				JsonObject objeto = entradaJson.getAsJsonObject();
				Iterator<Map.Entry<String, JsonElement>> iterKey = objeto.entrySet().iterator();
				Iterator<Map.Entry<String, JsonElement>> iterValue = objeto.entrySet().iterator();

				while (iterKey.hasNext()) {
					String key = iterKey.next().getKey().toString();
					String value = iterValue.next().getValue().getAsString();
					System.out.println(json);
					switch (key) {
					case "resultado":
						if (Boolean.parseBoolean(value) == true) {
							btnFavorito.setText("Quitar Favorito");
						} else if (Boolean.parseBoolean(value) == false) {
							btnFavorito.setText("Add Favorito");
						}
						break;

					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void setFavorito(int opcion) {

		try {
			System.out.println(idEspacioNatural + " "+ idMunicipio);
			String json = Cliente.setFavorito(idEspacioNatural, idMunicipio, idUser, opcion);
			System.out.println(json);
			JsonObject jsonObject = (JsonObject) (new JsonParser()).parse(json);

			JsonArray array = (JsonArray) jsonObject.get("jsonData");
			Iterator<JsonElement> iter = array.iterator();

			while (iter.hasNext()) {
				JsonElement entradaJson = iter.next();
				JsonObject objeto = entradaJson.getAsJsonObject();
				Iterator<Map.Entry<String, JsonElement>> iterKey = objeto.entrySet().iterator();
				Iterator<Map.Entry<String, JsonElement>> iterValue = objeto.entrySet().iterator();

				while (iterKey.hasNext()) {
					String key = iterKey.next().getKey().toString();
					String value = iterValue.next().getValue().getAsString();
					System.out.println(json);
					
					switch (key) {
					
					case "resultado":
						if (Boolean.parseBoolean(value) == true) {
							btnFavorito.setText("Quitar Favorito");
						} else if (Boolean.parseBoolean(value) == false) {
							btnFavorito.setText("Add Favorito");
						}
						
						break;
					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	

}
