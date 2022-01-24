package vista;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Desktop;

import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class DetallesEspacioNatural extends JFrame {

	private JPanel contentPane;

	private String latitud = "";
	private String longitud = "";
	private JLabel lblInfoDescripcion;
	private JLabel lblInfoMarca;
	private JLabel lblInfoNombre;
	private String lugarSelecionado;
	private String opcion;
	private JLabel lblInfoNaturaleza;
	JTextPane textPane;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//DetallesEstacion frame = new DetallesEstacion();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws URISyntaxException 
	 */

//	public DetallesEstacion(String lugarSelecionado, String opcion) {
//		this.lugarSelecionado = lugarSelecionado;
//		this.opcion = opcion;
//		
//		DetallesEstacion frame = new DetallesEstacion();
//		frame.setVisible(true);
//	}

	public DetallesEspacioNatural(String lugarSelecionado, String opcion, String municipio)  {

		setTitle("Detalles estacion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 560, 473);
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
		lblNombre.setBounds(39, 87, 136, 40);
		getContentPane().add(lblNombre);

		JSeparator separator = new JSeparator();
		separator.setBounds(49, 138, 469, 7);
		getContentPane().add(separator);

		JLabel lblProvincia = new JLabel("Descripción");
		lblProvincia.setHorizontalAlignment(SwingConstants.CENTER);
		lblProvincia.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblProvincia.setBounds(39, 171, 136, 40);
		getContentPane().add(lblProvincia);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(49, 268, 469, 11);
		getContentPane().add(separator_1);

		JLabel lblDireccion = new JLabel("Marca");
		lblDireccion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDireccion.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDireccion.setBounds(39, 278, 136, 29);
		getContentPane().add(lblDireccion);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(39, 318, 459, 7);
		getContentPane().add(separator_1_1);

		JLabel lblMaps = new JLabel("Naturaleza");
		lblMaps.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaps.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMaps.setBounds(39, 336, 136, 23);
		getContentPane().add(lblMaps);

		

		lblInfoDescripcion = new JLabel();
		lblInfoDescripcion.setBounds(241, 394, 159, 29);
		getContentPane().add(lblInfoDescripcion);

		JButton BtnAtrasToDetalles = new JButton("Atras");
		BtnAtrasToDetalles.setBounds(429, 383, 89, 23);
		BtnAtrasToDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DetallesMunicipio detallesMun = new DetallesMunicipio(municipio);// obj created for class Second()
				detallesMun.setVisible(true); // Open the Second.java window
				dispose(); // Close the First.java window
			}
		});
		contentPane.add(BtnAtrasToDetalles);

		lblInfoMarca = new JLabel();
		lblInfoMarca.setBounds(185, 269, 294, 38);
		contentPane.add(lblInfoMarca);

		lblInfoNombre = new JLabel();
		lblInfoNombre.setBounds(200, 87, 294, 38);
		contentPane.add(lblInfoNombre);
		
		lblInfoNaturaleza = new JLabel();
		lblInfoNaturaleza.setBounds(185, 337, 213, 46);
		contentPane.add(lblInfoNaturaleza);
		
		textPane = new JTextPane();
//		contentPane.add(textPane);
		textPane.setEditable(false);
		textPane.setBounds(176, 152, 331, 105);
		
		JScrollPane ScrollEstaciones = new JScrollPane();
		ScrollEstaciones.setSize(333, 107);
		ScrollEstaciones.setLocation(185, 138);
		ScrollEstaciones.setViewportView(textPane);
		contentPane.add(ScrollEstaciones);
		
		

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
					case "marca":
						lblInfoMarca.setText(value);
						break;
					case "descripcion":
						textPane.setText(value);
						break;
					case "naturaleza":
						lblInfoNaturaleza.setText(value);
						break;

					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		

	}
}
