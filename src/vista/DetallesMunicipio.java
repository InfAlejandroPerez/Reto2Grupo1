package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

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

public class DetallesMunicipio extends JFrame {

	private JPanel contentPane;

	JLabel LblMunicipio = new JLabel("");

	private JList<String> ListEstaciones;
	private JList<String> ListPlayas;
	ArrayList<Municipio> Listaestaciones = new ArrayList<Municipio>();
	ArrayList<EspaciosNaturales> Listaplayas = new ArrayList<EspaciosNaturales>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DetallesMunicipio frame = new DetallesMunicipio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public void NombreMun(String str) {
		LblMunicipio.setText(str);
	}

	public DetallesMunicipio() {
		setTitle("Detalles municipio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultListModel<String> modelListaEst = new DefaultListModel<String>();
		ListEstaciones = new JList<String>(modelListaEst);
		DefaultListModel<String> modelListaPla = new DefaultListModel<String>();
		ListPlayas = new JList<String>(modelListaPla);

		JScrollPane ScrollEstaciones = new JScrollPane();
		ListEstaciones.setBounds(54, 42, 180, 274);
		ScrollEstaciones.setSize(150, 150);
		ScrollEstaciones.setLocation(50, 60);
		ScrollEstaciones.setViewportView(ListEstaciones);
		ListEstaciones.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(ScrollEstaciones);

		JScrollPane ScrollPlayas = new JScrollPane();
		ScrollPlayas.setBounds(237, 60, 150, 150);
		ScrollPlayas.setViewportView(ListPlayas);
		ListPlayas.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(ScrollPlayas);

		ArrayList<String> item = Cliente.getArrayNamesData(Cliente.ESTACION);

		int i = 0;
		for (String st : item) {
			modelListaEst.add(i, st);
			i++;
		}
		
		ArrayList<String> item2 = Cliente.getArrayNamesData(Cliente.ESPACIOS);
		
		int j = 0;
		for (String st : item2) {
			modelListaPla.add(j, st);
			j++;
		}

		JButton BtnDetallesEstacion = new JButton("Mas informacion");
		BtnDetallesEstacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DetallesEstacion detallesEst = new DetallesEstacion();// obj created for class Second()
				detallesEst.setVisible(true); // Open the Second.java window
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

		JLabel lblNewLabel_2 = new JLabel("Lista de playas");
		lblNewLabel_2.setBounds(237, 36, 150, 14);
		contentPane.add(lblNewLabel_2);

	}
}
