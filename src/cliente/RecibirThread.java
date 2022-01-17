package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class RecibirThread implements Runnable{
	
	static private String mensajeRecebido;
	private boolean running = true;
	private ObjectInputStream entrada = null;

	Socket cliente = null;
	
	public RecibirThread(Socket cliente) {
		super();
		this.cliente = cliente;
		try {
			entrada = new ObjectInputStream(cliente.getInputStream());
			Thread t1 = new Thread(this);
			t1.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

//		while(running) {
			
			//mensajeRecebido = (String) entrada.readObject();
			System.out.println(" Servidor: " );

			
			
		}
		
//	}

}
