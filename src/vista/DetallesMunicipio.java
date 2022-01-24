package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.Cliente;
import objetos.EspaciosNaturales;
import objetos.Municipio;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class DetallesMunicipio extends JFrame implements ActionListener {

	private JPanel contentPane;

	JLabel LblMunicipio = new JLabel("");
	public String lugarSeleccionado;

	private JList<String> jListEstaciones;
	private JList<String> jListEspaciosNaturales;
	ArrayList<Municipio> listaEstaciones = new ArrayList<Municipio>();
	ArrayList<EspaciosNaturales> listaEspacios = new ArrayList<EspaciosNaturales>();

//	String municipio = "";

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

		LblMunicipio.setText(municipio);

		setTitle("Detalles municipio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultListModel<String> modelListaEst = new DefaultListModel<String>();
		jListEstaciones = new JList<String>(modelListaEst);

		DefaultListModel<String> modelListaNat = new DefaultListModel<String>();
		jListEspaciosNaturales = new JList<String>(modelListaNat);

		JScrollPane ScrollEstaciones = new JScrollPane();
		jListEstaciones.setBounds(54, 42, 180, 274);
		ScrollEstaciones.setSize(150, 150);
		ScrollEstaciones.setLocation(50, 60);
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
		contentPane.add(ScrollEstaciones);

		JScrollPane ScrollNaturales = new JScrollPane();
		ScrollNaturales.setBounds(237, 60, 150, 150);
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
		BtnDetallesEstacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String operacion = "";

				if (jListEstaciones.getSelectedValue() != null) {
					lugarSeleccionado = jListEstaciones.getSelectedValue().toString();
					operacion = "detalles_estaciones";
					DetallesEstacion detallesEst = new DetallesEstacion(lugarSeleccionado, operacion, municipio);// obj
																													// created
																													// for
																													// class
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
		BtnDetallesEstacion.setBounds(10, 327, 140, 23);
		contentPane.add(BtnDetallesEstacion);

		JButton BtnAtrasToLista = new JButton("Atras");
		BtnAtrasToLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListaMunicipios lista = new ListaMunicipios();// obj created for class Second()
				lista.setVisible(true); // Open the Second.java window
				dispose(); // Close the First.java window
			}
		});
		BtnAtrasToLista.setBounds(335, 327, 89, 23);
		contentPane.add(BtnAtrasToLista);

		JLabel lblNewLabel = new JLabel("Detalles de:");
		lblNewLabel.setBounds(10, 11, 69, 14);
		contentPane.add(lblNewLabel);

		LblMunicipio.setBounds(89, 11, 298, 14);
		contentPane.add(LblMunicipio);

		JLabel lblNewLabel_1 = new JLabel("Lista de estaciones");
		lblNewLabel_1.setBounds(50, 36, 150, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Lista de Zonas naturales");
		lblNewLabel_2.setBounds(237, 36, 150, 14);
		contentPane.add(lblNewLabel_2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}