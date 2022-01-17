package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class EnviarThread implements Runnable {

	Socket cliente = null;
	private boolean running = true;
	private ObjectOutputStream salida;
	private static String json;

	public EnviarThread(Socket cliente) {
		super();
		this.cliente = cliente;

		try {
			this.salida = new ObjectOutputStream(cliente.getOutputStream());
			Thread t1 = new Thread(this);
			t1.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

//		while (running) {

			try {
					System.out.println("Conexión realizada con servidor");
					salida = new ObjectOutputStream(cliente.getOutputStream());
														
					 json = "{ 'operacion' : 'login',"
							+ " 'userName' : 'admin',"
							+ " 'password' : 'admin',"
							+ " 'campoBusqueda' : 'Bilbao'}".replace('"', '"' );
					
					salida.writeObject(json);

			} catch (IOException e) {
				e.printStackTrace();
			}

//		}
	}

}
