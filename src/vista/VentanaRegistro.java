package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class VentanaRegistro extends JFrame {

	private JPanel contentPane;
	private JTextField TxtNuevoUser;
	private JPasswordField NuevaPass;
	private JPasswordField NuevaPass2;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaRegistro frame = new VentanaRegistro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaRegistro() {
		setTitle("Registrar");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 366, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Creaci\u00F3n de usuario");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(74, 11, 214, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(10, 36, 214, 14);
		contentPane.add(lblNewLabel_1);

		TxtNuevoUser = new JTextField();
		TxtNuevoUser.setBounds(10, 62, 214, 36);
		contentPane.add(TxtNuevoUser);
		TxtNuevoUser.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Contrase\u00F1a");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(10, 109, 214, 14);
		contentPane.add(lblNewLabel_2);

		NuevaPass = new JPasswordField();
		NuevaPass.setBounds(10, 134, 214, 32);
		contentPane.add(NuevaPass);

		NuevaPass2 = new JPasswordField();
		NuevaPass2.setBounds(10, 202, 214, 32);
		contentPane.add(NuevaPass2);

		JButton BtnCrear = new JButton("Crear usuario");
		BtnCrear.setFont(new Font("Tahoma", Font.BOLD, 13));
		BtnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (NuevaPass.getText().toString().equals(NuevaPass2.getText().toString())) {
					Cliente.register2(TxtNuevoUser.getText().toString(), NuevaPass.getText().toString());
					dispose(); // Close the First.java window
				} else {
					JOptionPane.showMessageDialog(null, "Error de confirmación, las contraseñas deben coincidir");
				}

			}
		});
		BtnCrear.setBounds(109, 261, 126, 44);
		contentPane.add(BtnCrear);

		JButton BtnCancel = new JButton("Cancelar");
		BtnCancel.setFont(new Font("Tahoma", Font.BOLD, 13));
		BtnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaLogin login = new VentanaLogin();// obj created for class Second()
				login.setVisible(true); // Open the Second.java window
				dispose(); // Close the First.java window
			}
		});
		BtnCancel.setBounds(109, 316, 126, 39);
		contentPane.add(BtnCancel);

		JLabel lblNewLabel_3 = new JLabel("Confirmar contrase\u00F1a");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(10, 177, 214, 14);
		contentPane.add(lblNewLabel_3);
	}

}