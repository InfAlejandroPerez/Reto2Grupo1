package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
import java.awt.Desktop;

import javax.swing.JSeparator;

public class DetallesEstacion extends JFrame {

	private JPanel contentPane;

	private String latitud = "";
	private String longitud = "";
	private JLabel lblInfoProvincia;
	private JLabel lblInfoDireccion;
	private JLabel lblInfoNombre;
	private JLabel lblInfoCalidad;
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

	public DetallesEstacion(String lugarSelecionado, String opcion, String municipio, String IDUSER) {
		setResizable(false);

		setTitle("Detalles estacion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 560, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		getContentPane().setLayout(null);

		JLabel lblTitulo = new JLabel("Estacion");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitulo.setBounds(176, 23, 136, 40);
		getContentPane().add(lblTitulo);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNombre.setBounds(39, 74, 136, 40);
		getContentPane().add(lblNombre);

		JSeparator separator = new JSeparator();
		separator.setBounds(49, 115, 469, 7);
		getContentPane().add(separator);

		JLabel lblProvincia = new JLabel("Provincia");
		lblProvincia.setHorizontalAlignment(SwingConstants.CENTER);
		lblProvincia.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblProvincia.setBounds(49, 125, 116, 40);
		getContentPane().add(lblProvincia);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(49, 170, 469, 11);
		getContentPane().add(separator_1);

		JLabel lblDireccion = new JLabel("Dirección");
		lblDireccion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDireccion.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDireccion.setBounds(49, 176, 116, 40);
		getContentPane().add(lblDireccion);

		lblInfoCalidad = new JLabel();
		lblInfoCalidad.setBounds(196, 237, 294, 38);
		contentPane.add(lblInfoCalidad);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(49, 219, 459, 7);
		getContentPane().add(separator_1_1);

		JLabel lblMaps = new JLabel("GoogleMaps");
		lblMaps.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaps.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMaps.setBounds(49, 306, 136, 40);
		getContentPane().add(lblMaps);

		lblInfoProvincia = new JLabel();
		lblInfoProvincia.setBounds(196, 133, 294, 38);
		getContentPane().add(lblInfoProvincia);

		JButton BtnAtrasToDetalles = new JButton("Volver");
		BtnAtrasToDetalles.setFont(new Font("Tahoma", Font.BOLD, 12));
		BtnAtrasToDetalles.setBounds(429, 383, 89, 23);
		BtnAtrasToDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DetallesMunicipio detallesMun = new DetallesMunicipio(municipio, IDUSER);// obj created for class Second()
				detallesMun.setVisible(true); // Open the Second.java window
				dispose(); // Close the First.java window
			}
		});
		contentPane.add(BtnAtrasToDetalles);

		lblInfoDireccion = new JLabel();
		lblInfoDireccion.setBounds(176, 178, 294, 38);
		contentPane.add(lblInfoDireccion);

		lblInfoNombre = new JLabel();
		lblInfoNombre.setBounds(186, 74, 294, 38);
		contentPane.add(lblInfoNombre);

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
						break;
					case "direccion":
						lblInfoDireccion.setText(value);
						break;
					case "provincia":
						lblInfoProvincia.setText(value);
						break;
					case "calidad_aire":
						lblInfoCalidad.setText(value);
						break;
					case "latitud":
						latitud = value.replace(',', '.');
						break;
					case "longitud":
						longitud = value.replace(',', '.');
						break;

					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

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
		btnAbrirMapa.setBounds(231, 306, 136, 34);
		contentPane.add(btnAbrirMapa);
		
		JLabel lblCalidadDelAire = new JLabel("Calidad del Aire");
		lblCalidadDelAire.setHorizontalAlignment(SwingConstants.CENTER);
		lblCalidadDelAire.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCalidadDelAire.setBounds(49, 237, 130, 40);
		contentPane.add(lblCalidadDelAire);
		
		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setBounds(49, 288, 459, 7);
		contentPane.add(separator_1_1_1);
		
		

	}
}