package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import objetos.Municipio;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JRadioButton;

public class ListaMunicipios extends JFrame {

	private JPanel contentPane;
	private JTextField TxtFiltroMunicipios;
	private JList <String> ListMunicipios;
	ArrayList<Municipio> Listamunicipios = new ArrayList<Municipio>();
	private JLabel lblNewLabel;
	private JButton BtnBuscar;
	
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
		contentPane.add(TxtFiltroMunicipios);
		TxtFiltroMunicipios.setColumns(10);
		
		JRadioButton RadioBizkaia = new JRadioButton("Bizkaia");
		RadioBizkaia.setBounds(274, 99, 109, 23);
		contentPane.add(RadioBizkaia);
		
		JRadioButton RadioGipuzkoa = new JRadioButton("Gipuzkoa");
		RadioGipuzkoa.setBounds(274, 125, 109, 23);
		contentPane.add(RadioGipuzkoa);
		
		JRadioButton RadioAraba = new JRadioButton("Araba");
		RadioAraba.setBounds(274, 151, 109, 23);
		contentPane.add(RadioAraba);
		
		JRadioButton RadioTodo = new JRadioButton("Mostrar todo");
		RadioTodo.setBounds(274, 73, 109, 23);
		contentPane.add(RadioTodo);
		
		ButtonGroup G = new ButtonGroup();
		G.add(RadioBizkaia);
		G.add(RadioGipuzkoa);
		G.add(RadioAraba);
		G.add(RadioTodo);
		
		RadioTodo.setSelected(true);
		
		DefaultListModel<String> modelListaMun = new DefaultListModel<String>();
        ListMunicipios = new JList<String>(modelListaMun);
        
        JScrollPane scrollPane = new JScrollPane();
        ListMunicipios.setBounds(54, 42, 180, 274);
        scrollPane.setSize(200, 250);
        scrollPane.setLocation(50, 50);
        scrollPane.setViewportView(ListMunicipios);
        ListMunicipios.setLayoutOrientation(JList.VERTICAL);
        contentPane.add(scrollPane);
        
        ArrayList<String> list = new ArrayList<String>();
        list.add("Inida");
        list.add("China");
        list.add("Aus");
        list.add("Japan");
        list.add("patata");
        list.add("zanahoria");
        list.add("tomate");
        list.add("Bilbao");
        list.add("Zaragoza");
        list.add("Luxemburgo");
        list.add("Rusia");
        list.add("La calle de al lado");
        list.add("Areilza");
        list.add("Madrid");
        list.add("Londres");
        list.add("Prusia");
        list.add("Persia");
        list.add("Barcelona");
        list.add("Barakaldo");
        list.add("Laredo");
        list.add("Santander");
        list.add("Granada");
        list.add("Avila");
        list.add("Biarritz");
        list.add("Paris");

        int i = 0;
        for (String st : list) {
        	modelListaMun.add(i, st);
            i++;
        }
        
		JButton BtnDetallesMunicipio = new JButton("Mas informacion");
		BtnDetallesMunicipio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ListMunicipios.getSelectedValue() != null) {
					String str = ListMunicipios.getSelectedValue().toString();
					DetallesMunicipio detallesMun= new DetallesMunicipio();// obj created for class Second()
					detallesMun.NombreMun(str); // Execute the method my_update to pass str
					detallesMun.setVisible(true); // Open the Second.java window
					dispose(); // Close the First.java window
				}
			}
		});
		BtnDetallesMunicipio.setBounds(50, 311, 200, 23);
		contentPane.add(BtnDetallesMunicipio);
		
		lblNewLabel = new JLabel("Filtrar por nombre");
		lblNewLabel.setBounds(50, 14, 110, 14);
		contentPane.add(lblNewLabel);
		
		BtnBuscar = new JButton("Buscar");
		BtnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		BtnBuscar.setBounds(274, 10, 89, 23);
		contentPane.add(BtnBuscar);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Buscar por provincias");
		lblNewLabel_1.setBounds(274, 52, 143, 14);
		contentPane.add(lblNewLabel_1);
	}
}
