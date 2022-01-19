package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DetallesEstacion extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DetallesEstacion frame = new DetallesEstacion();
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
	public DetallesEstacion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton BtnAtrasToDetalles = new JButton("Atras");
		BtnAtrasToDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DetallesMunicipio detallesMun = new DetallesMunicipio();// obj created for class Second()
				detallesMun.setVisible(true); // Open the Second.java window
				dispose(); // Close the First.java window
			}
		});
		BtnAtrasToDetalles.setBounds(335, 327, 89, 23);
		contentPane.add(BtnAtrasToDetalles);
	}

}
