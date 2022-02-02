package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class VentanaLogin extends JFrame {

	private JPanel contentPane;
	private JTextField TxtUsuario;
	private JPasswordField passwordField;

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

	public VentanaLogin() {
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 366, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(52, 112, 86, 14);
		contentPane.add(lblNewLabel_1);

		TxtUsuario = new JTextField();
		TxtUsuario.setBounds(52, 137, 214, 33);
		contentPane.add(TxtUsuario);
		TxtUsuario.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Contrase\u00F1a");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(52, 181, 86, 14);
		contentPane.add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setBounds(52, 206, 214, 33);
		contentPane.add(passwordField);

		JButton BtnIniciarSesion = new JButton("Iniciar sesion");
		BtnIniciarSesion.setFont(new Font("Tahoma", Font.BOLD, 12));
		BtnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String login = Cliente.login2(TxtUsuario.getText().toString(), passwordField.getText().toString());
				
				if(login.equals("true")) {
					
					dispose();
				}
			}
		});
		BtnIniciarSesion.setBounds(109, 261, 126, 44);
		contentPane.add(BtnIniciarSesion);

		JButton BtnRegistro = new JButton("Registrarse");
		BtnRegistro.setFont(new Font("Tahoma", Font.BOLD, 12));
		BtnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaRegistro registro = new VentanaRegistro();
				registro.setVisible(true);
				dispose();
			}
		});
		BtnRegistro.setBounds(109, 316, 126, 39);
		contentPane.add(BtnRegistro);

		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./src/euskalmet.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JLabel lblImage = new JLabel(new ImageIcon(myPicture));
		lblImage.setBounds(124, 11, 94, 100);
		contentPane.add(lblImage);

	}
}