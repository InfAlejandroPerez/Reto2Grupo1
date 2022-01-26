package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cliente.Cliente;
import objetos.EspaciosNaturales;
import objetos.Municipio;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.Font;

public class DetallesMunicipio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	JLabel LblMunicipio = new JLabel("");
	public String lugarSeleccionado;

	private JList<String> jListEstaciones;
	private JList<String> jListEspaciosNaturales;
	ArrayList<Municipio> listaEstaciones = new ArrayList<Municipio>();
	ArrayList<EspaciosNaturales> listaEspacios = new ArrayList<EspaciosNaturales>();
	JTextPane textPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// DetallesMunicipio frame = new DetallesMunicipio();
					// frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DetallesMunicipio(String municipio) {
		setTitle("Detalles Municipio");
		setResizable(false);
		LblMunicipio.setFont(new Font("Tahoma", Font.BOLD, 12));
		LblMunicipio.setBounds(89, 11, 298, 14);

		LblMunicipio.setText(municipio);

		setTitle("Detalles municipio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 518, 486);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		textPane = new JTextPane();
//		contentPane.add(textPane);
		textPane.setEditable(false);
		textPane.setBounds(176, 152, 331, 105);

		JScrollPane ScrollDescripcion = new JScrollPane();
		ScrollDescripcion.setSize(337, 52);
		ScrollDescripcion.setLocation(130, 36);
		ScrollDescripcion.setViewportView(textPane);
		contentPane.add(ScrollDescripcion);

		DefaultListModel<String> modelListaEst = new DefaultListModel<String>();
		jListEstaciones = new JList<String>(modelListaEst);

		DefaultListModel<String> modelListaNat = new DefaultListModel<String>();
		jListEspaciosNaturales = new JList<String>(modelListaNat);

		JScrollPane ScrollEstaciones = new JScrollPane();
		ScrollEstaciones.setBounds(89, 175, 150, 150);
		jListEstaciones.setBounds(54, 42, 180, 274);
		ScrollEstaciones.setViewportView(jListEstaciones);
		jListEstaciones.setLayoutOrientation(JList.VERTICAL);
		jListEstaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jListEstaciones.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (jListEstaciones.getSelectedValue() != null) {
					jListEspaciosNaturales.clearSelection();

				}
			}

		});
		contentPane.setLayout(null);
		contentPane.add(ScrollEstaciones);

		JScrollPane ScrollNaturales = new JScrollPane();
		ScrollNaturales.setBounds(288, 175, 150, 150);
		ScrollNaturales.setViewportView(jListEspaciosNaturales);
		jListEspaciosNaturales.setLayoutOrientation(JList.VERTICAL);
		jListEspaciosNaturales.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (jListEspaciosNaturales.getSelectedValue() != null) {
					jListEstaciones.clearSelection();

				}
			}

		});
		contentPane.add(ScrollNaturales);

		try {

			String[] item = Cliente.getArrayListasLugaresPorMunicipio(municipio, 1);

			int i = 0;
			for (String st : item) {
				modelListaEst.add(i, st);
				i++;
			}

			String[] item2 = Cliente.getArrayListasLugaresPorMunicipio(municipio, 2);

			int j = 0;
			for (String st : item2) {
				modelListaNat.add(j, st);
				j++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		JButton BtnDetallesEstacion = new JButton("Mas informacion");
		BtnDetallesEstacion.setFont(new Font("Tahoma", Font.BOLD, 12));
		BtnDetallesEstacion.setBounds(199, 350, 140, 23);
		BtnDetallesEstacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String operacion = "";

				if (jListEstaciones.getSelectedValue() != null) {
					lugarSeleccionado = jListEstaciones.getSelectedValue().toString();
					operacion = "detalles_estaciones";
					DetallesEstacion detallesEst = new DetallesEstacion(lugarSeleccionado, operacion, municipio);// obj
																								// Second()
					detallesEst.setVisible(true);
				} else if (jListEspaciosNaturales.getSelectedValue() != null) {
					lugarSeleccionado = jListEspaciosNaturales.getSelectedValue().toString();
					operacion = "detalles_espacios";
					DetallesEspacioNatural detallesEpacioNatural = new DetallesEspacioNatural(lugarSeleccionado,
							operacion, municipio);// obj created for class Second()
					detallesEpacioNatural.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione un lugar");
					return;
				}

				// Open the Second.java window
				dispose(); // Close the First.java window
			}
		});
		contentPane.add(BtnDetallesEstacion);

		JButton BtnAtrasToLista = new JButton("Volver");
		BtnAtrasToLista.setFont(new Font("Tahoma", Font.BOLD, 12));
		BtnAtrasToLista.setBounds(378, 400, 89, 23);
		BtnAtrasToLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListaMunicipios lista = new ListaMunicipios();// obj created for class Second()
				lista.setVisible(true); // Open the Second.java window
				dispose(); // Close the First.java window
			}
		});
		contentPane.add(BtnAtrasToLista);

		JLabel lblDetallesDe = new JLabel("Detalles de:");
		lblDetallesDe.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDetallesDe.setBounds(10, 11, 69, 14);
		contentPane.add(lblDetallesDe);
		contentPane.add(LblMunicipio);

		JLabel lblNewLabel_1 = new JLabel("Lista de estaciones");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(89, 150, 150, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Lista de Zonas naturales");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(288, 150, 150, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Descripción");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(31, 45, 89, 23);
		contentPane.add(lblNewLabel_3);

		JLabel lblLocalidad = new JLabel("Localidad:");
		lblLocalidad.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLocalidad.setBounds(50, 99, 70, 14);
		contentPane.add(lblLocalidad);

		JLabel lblLocalidInfo = new JLabel("");
		lblLocalidInfo.setBounds(130, 99, 128, 14);
		contentPane.add(lblLocalidInfo);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(47, 137, 419, 2);
		contentPane.add(separator);
		
		try {
			String json = Cliente.getDetalles(municipio, "detalles_municipio");

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
					case "descripcion":
						textPane.setText(value);
						break;
					case "localidad":
						lblLocalidInfo.setText(value);
						break;

					}
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		

	}

}