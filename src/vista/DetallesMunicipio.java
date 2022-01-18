package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

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
	
	private JList <String> ListEstaciones;
	ArrayList<Municipio> Listaestaciones = new ArrayList<Municipio>();

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DefaultListModel<String> modelListaEst = new DefaultListModel<String>();
		ListEstaciones = new JList<String>(modelListaEst);
        
        JScrollPane scrollPane = new JScrollPane();
        ListEstaciones.setBounds(54, 42, 180, 274);
        scrollPane.setSize(150, 150);
        scrollPane.setLocation(50, 100);
        scrollPane.setViewportView(ListEstaciones);
        ListEstaciones.setLayoutOrientation(JList.VERTICAL);
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
        	modelListaEst.add(i, st);
            i++;
        }
		
		JButton BtnDetallesEstacion = new JButton("Mas informacion");
		BtnDetallesEstacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DetallesEstacion detallesEst= new DetallesEstacion();// obj created for class Second()
				detallesEst.setVisible(true); // Open the Second.java window
				dispose(); // Close the First.java window
			}
		});
		BtnDetallesEstacion.setBounds(10, 327, 140, 23);
		contentPane.add(BtnDetallesEstacion);
		
		JButton BtnAtrasToLista = new JButton("Atras");
		BtnAtrasToLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListaMunicipios lista= new ListaMunicipios();// obj created for class Second()
				lista.setVisible(true); // Open the Second.java window
				dispose(); // Close the First.java window
			}
		});
		BtnAtrasToLista.setBounds(335, 327, 89, 23);
		contentPane.add(BtnAtrasToLista);
		
		JLabel lblNewLabel = new JLabel("Detalles de:");
		lblNewLabel.setBounds(10, 11, 69, 14);
		contentPane.add(lblNewLabel);
		
		LblMunicipio.setBounds(89, 11, 46, 14);
		contentPane.add(LblMunicipio);
		
		JLabel lblNewLabel_1 = new JLabel("Lista de estaciones");
		lblNewLabel_1.setBounds(50, 75, 150, 14);
		contentPane.add(lblNewLabel_1);
	}
}
