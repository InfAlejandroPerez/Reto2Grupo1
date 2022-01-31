package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cliente.Cliente;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.Font;

public class ListaMunicipios extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField TxtFiltroMunicipios;
	private JList<String> listMunicipios;
	private JList<String> listTopFavoritos;
	private JLabel lblNewLabel;
	DefaultListModel<String> modelListaMun;
	DefaultListModel<String> modelListaFavorito;
	private JRadioButton RadioBizkaia;
	private JRadioButton RadioGipuzkoa;
	private JRadioButton RadioAraba;

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

	public ListaMunicipios() {
		ListaMunicipios lista = new ListaMunicipios("");
		lista.setVisible(true);
	}

	public ListaMunicipios(String idUser) {
		System.out.println(idUser);
		setResizable(false);
		setTitle("Lista de municipios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		TxtFiltroMunicipios = new JTextField();
		TxtFiltroMunicipios.setBounds(164, 11, 100, 20);
		// contentPane.add(TxtFiltroMunicipios);
		TxtFiltroMunicipios.setColumns(10);

		RadioBizkaia = new JRadioButton();
		RadioBizkaia.setFont(new Font("Dialog", Font.BOLD, 12));
		RadioBizkaia.setText("Bizkaia");
		RadioBizkaia.setBounds(274, 99, 109, 23);
		RadioBizkaia.addActionListener(this);
		contentPane.add(RadioBizkaia);

		RadioGipuzkoa = new JRadioButton("Gipuzkoa");
		RadioGipuzkoa.setFont(new Font("Dialog", Font.BOLD, 12));
		RadioGipuzkoa.addActionListener(this);
		RadioGipuzkoa.setBounds(274, 125, 109, 23);
		contentPane.add(RadioGipuzkoa);

		RadioAraba = new JRadioButton("Araba/Álava");
		RadioAraba.setFont(new Font("Dialog", Font.BOLD, 12));
		RadioAraba.setBounds(274, 151, 109, 23);
		RadioAraba.addActionListener(this);
		contentPane.add(RadioAraba);

		JRadioButton RadioTodo = new JRadioButton("Mostrar todo");
		RadioTodo.setFont(new Font("Dialog", Font.BOLD, 12));
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
		listMunicipios = new JList<String>(modelListaMun);

		modelListaFavorito = new DefaultListModel<String>();
		listTopFavoritos = new JList<String>(modelListaFavorito);

		JScrollPane ScrollMunicipios = new JScrollPane();
		listMunicipios.setBounds(54, 42, 180, 274);
		ScrollMunicipios.setSize(200, 300);
		ScrollMunicipios.setLocation(50, 50);
		ScrollMunicipios.setViewportView(listMunicipios);
		listMunicipios.setLayoutOrientation(JList.VERTICAL);
		listMunicipios.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (listMunicipios.getSelectedValue() != null) {
					listTopFavoritos.clearSelection();

				}
			}

		});
		contentPane.add(ScrollMunicipios);

		
		listTopFavoritos.setLayoutOrientation(JList.VERTICAL);
		listTopFavoritos.setBounds(274, 226, 199, 124);
		listTopFavoritos.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (listTopFavoritos.getSelectedValue() != null) {
					listMunicipios.clearSelection();

				}
			}

		});
		contentPane.add(listTopFavoritos);

		JButton BtnDetallesMunicipio = new JButton("Mas informacion");
		BtnDetallesMunicipio.setFont(new Font("Dialog", Font.BOLD, 12));
		BtnDetallesMunicipio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listMunicipios.getSelectedValue() != null) {
					String municipio = listMunicipios.getSelectedValue().toString();
					DetallesMunicipio detallesMun = new DetallesMunicipio(municipio, idUser);// obj created for class Second()
					detallesMun.setVisible(true); // Open the Second.java window
					dispose(); // Close the First.java window
				}else if(listTopFavoritos.getSelectedValue() != null) {
					String espacioNat = listTopFavoritos.getSelectedValue().toString();
					String municipio = Cliente.getMunicipioPorEspacio(espacioNat);
					DetallesEspacioNatural detallesEspacio = new DetallesEspacioNatural(espacioNat, "detalles_espacios", municipio,idUser);// obj created for class Second()
					detallesEspacio.setVisible(true); // Open the Second.java window
					dispose();
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Seleccione un municipio");
				}
			}
		});
		BtnDetallesMunicipio.setBounds(50, 378, 200, 23);
		contentPane.add(BtnDetallesMunicipio);

		lblNewLabel = new JLabel("Municipios");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(121, 11, 89, 28);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Buscar por provincias");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel_1.setBounds(274, 52, 143, 14);
		contentPane.add(lblNewLabel_1);

		JButton BtnSalir = new JButton("Salir");
		BtnSalir.setFont(new Font("Dialog", Font.BOLD, 12));
		BtnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaLogin login = new VentanaLogin();// obj created for class Second()
				login.setVisible(true); // Open the Second.java window
				dispose(); // Close the First.java window
			}
		});
		BtnSalir.setBounds(384, 410, 89, 23);
		contentPane.add(BtnSalir);

		JLabel lblFavoritosProvincia = new JLabel("Top 5 favoritos");
		lblFavoritosProvincia.setFont(new Font("Dialog", Font.BOLD, 12));
		lblFavoritosProvincia.setBounds(274, 203, 165, 14);
		contentPane.add(lblFavoritosProvincia);

		rellenarListas();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JRadioButton theJRB = (JRadioButton) e.getSource();
		String provincia = theJRB.getText();
		String[] item = null;
		String[] listaFavoritos = null;
		try {

			if (provincia.equals("Mostrar todo")) {
				item = Cliente.getArrayListas(Cliente.MUNICIPIO);
				listaFavoritos = Cliente.getTopFavoritos("",1);
			} else {
				listaFavoritos = Cliente.getTopFavoritos(provincia,0);
				item = Cliente.getArrayListasMunicipiosPorProvincia(provincia);
			}

			modelListaMun.clear();
			int i = 0;
			for (String st : item) {
				modelListaMun.add(i, st);
				i++;
			}

			modelListaFavorito.clear();
			int j = 0;
			for (String st : listaFavoritos) {
				modelListaFavorito.add(j, st);
				j++;
			}

		} catch (Exception eX) {
			eX.printStackTrace();
		}

	}

	private void rellenarListas() {

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

		try {

			String[] item = Cliente.getTopFavoritos("",1);

			int i = 0;
			for (String st : item) {
				System.out.println(st);
				modelListaFavorito.add(i, st);
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}