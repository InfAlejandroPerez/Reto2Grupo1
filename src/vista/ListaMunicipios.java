package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import objetos.Municipio;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JRadioButton;
import java.awt.Font;

public class ListaMunicipios extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField TxtFiltroMunicipios;
	private JList<String> ListMunicipios;
	ArrayList<Municipio> Listamunicipios = new ArrayList<Municipio>();
	private JLabel lblNewLabel;
	private JButton BtnBuscar;
	DefaultListModel<String> modelListaMun;
	private JRadioButton RadioBizkaia;
	private JRadioButton RadioGipuzkoa;
	private JRadioButton RadioAraba;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListaMunicipios frame = new ListaMunicipios();
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
	public ListaMunicipios() {
		setTitle("Lista de municipios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		TxtFiltroMunicipios = new JTextField();
		TxtFiltroMunicipios.setBounds(164, 11, 100, 20);
		// contentPane.add(TxtFiltroMunicipios);
		TxtFiltroMunicipios.setColumns(10);

		RadioBizkaia = new JRadioButton();
		RadioBizkaia.setText("Bizkaia");
		RadioBizkaia.setBounds(274, 99, 109, 23);
		RadioBizkaia.addActionListener(this);
		contentPane.add(RadioBizkaia);

		RadioGipuzkoa = new JRadioButton("Gipuzkoa");
		RadioGipuzkoa.addActionListener(this);
		RadioGipuzkoa.setBounds(274, 125, 109, 23);
		contentPane.add(RadioGipuzkoa);

		RadioAraba = new JRadioButton("Araba");
		RadioAraba.setBounds(274, 151, 109, 23);
		RadioAraba.addActionListener(this);
		contentPane.add(RadioAraba);

		JRadioButton RadioTodo = new JRadioButton("Mostrar todo");
		RadioTodo.setBounds(274, 73, 109, 23);
		RadioTodo.addActionListener(this);
		contentPane.add(RadioTodo);

		ButtonGroup G = new ButtonGroup();
		G.add(RadioBizkaia);
		G.add(RadioGipuzkoa);
		G.add(RadioAraba);
		G.add(RadioTodo);

		RadioTodo.setSelected(true);

		modelListaMun = new DefaultListModel<String>();
		ListMunicipios = new JList<String>(modelListaMun);

		JScrollPane ScrollMunicipios = new JScrollPane();
		ListMunicipios.setBounds(54, 42, 180, 274);
		ScrollMunicipios.setSize(200, 250);
		ScrollMunicipios.setLocation(50, 50);
		ScrollMunicipios.setViewportView(ListMunicipios);
		ListMunicipios.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(ScrollMunicipios);

		try {

			String[] item = Cliente.getArrayListas(Cliente.MUNICIPIO);

			int i = 0;
			for (String st : item) {
				modelListaMun.add(i, st);
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		JButton BtnDetallesMunicipio = new JButton("Mas informacion");
		BtnDetallesMunicipio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ListMunicipios.getSelectedValue() != null) {
					String str = ListMunicipios.getSelectedValue().toString();
					DetallesMunicipio detallesMun = new DetallesMunicipio(str);// obj created for class Second()
//					detallesMun.NombreMun(str); // Execute the method my_update to pass str
					detallesMun.setVisible(true); // Open the Second.java window
					dispose(); // Close the First.java window
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione un municipio");
				}
			}
		});
		BtnDetallesMunicipio.setBounds(50, 311, 200, 23);
		contentPane.add(BtnDetallesMunicipio);

		lblNewLabel = new JLabel("Municipios");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(121, 11, 89, 28);
		contentPane.add(lblNewLabel);

//		BtnBuscar = new JButton("Buscar");
//		BtnBuscar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//			}
//		});
//		BtnBuscar.setBounds(274, 10, 89, 23);
//		//contentPane.add(BtnBuscar);

		JLabel lblNewLabel_1 = new JLabel("Buscar por provincias");
		lblNewLabel_1.setBounds(274, 52, 143, 14);
		contentPane.add(lblNewLabel_1);

		JButton BtnSalir = new JButton("Salir");
		BtnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaLogin login = new VentanaLogin();// obj created for class Second()
				login.setVisible(true); // Open the Second.java window
				dispose(); // Close the First.java window
			}
		});
		BtnSalir.setBounds(328, 327, 89, 23);
		contentPane.add(BtnSalir);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JRadioButton theJRB = (JRadioButton) e.getSource();
		String provincia = theJRB.getText();
		String[] item = null;
		try {

			if(provincia.equals("Mostrar todo")) {
				item = Cliente.getArrayListas(Cliente.MUNICIPIO);
			}else {
				item = Cliente.getArrayListasMunicipiosPorProvincia(provincia);
			}
			
			modelListaMun.clear();
			int i = 0;
			for (String st : item) {
				modelListaMun.add(i, st);
				i++;
			}

		} catch (Exception eX) {
			eX.printStackTrace();
		}

	}
}
