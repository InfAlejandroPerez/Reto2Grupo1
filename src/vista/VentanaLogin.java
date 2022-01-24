package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JPasswordField;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class VentanaLogin extends JFrame {

	private JPanel contentPane;
	private JTextField TxtUsuario;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin frame = new VentanaLogin();
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
	public VentanaLogin() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 310, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setBounds(52, 107, 86, 14);
		contentPane.add(lblNewLabel_1);

		TxtUsuario = new JTextField();
		TxtUsuario.setBounds(52, 132, 214, 20);
		contentPane.add(TxtUsuario);
		TxtUsuario.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Contrase\u00F1a");
		lblNewLabel_2.setBounds(52, 163, 86, 14);
		contentPane.add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setBounds(52, 188, 214, 20);
		contentPane.add(passwordField);

		JCheckBox ChechRecordar = new JCheckBox("Recordarme");
		ChechRecordar.setBounds(10, 144, 97, 23);
		//contentPane.add(ChechRecordar);

		JButton BtnIniciarSesion = new JButton("Iniciar sesion");
		BtnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente.login2(TxtUsuario.getText().toString(), passwordField.getText().toString());
				dispose();
			}
		});
		BtnIniciarSesion.setBounds(103, 219, 110, 23);
		contentPane.add(BtnIniciarSesion);

		JButton BtnRegistro = new JButton("Registrarse");
		BtnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaRegistro registro = new VentanaRegistro();
				registro.setVisible(true);
				dispose();
			}
		});
		BtnRegistro.setBounds(103, 253, 110, 23);
		contentPane.add(BtnRegistro);
		
		
		BufferedImage myPicture=null;
		///Euskalmet/src/euskalmet.png
			try {
				myPicture = ImageIO.read(new File("./src/euskalmet.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
		
		JLabel lblImage = new JLabel(new ImageIcon(myPicture));
		lblImage.setBounds(111, 11, 86, 85);
		contentPane.add(lblImage);
	
	}
}
