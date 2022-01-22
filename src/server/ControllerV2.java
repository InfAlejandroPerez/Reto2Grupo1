package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.gson.JsonElement;
import hibernateUtil.HibernateUtil;
import objetos.Municipio;
import objetos.Users;

public class ControllerV2 {
	public static void login(Iterator<Entry<String, JsonElement>> iterKey,
			Iterator<Entry<String, JsonElement>> iterValue, ObjectOutputStream salida) {

		Users user = new Users();

		while (iterKey.hasNext()) {
			String key = iterKey.next().getKey().toString();
			String value = iterValue.next().getValue().getAsString();

			switch (key) {
			case "user":
				user.setUserName(value);
				break;
			case "password":
				user.setPassword(value);
				break;
			}
		}

		SessionFactory sessionFac = HibernateUtil.getSessionFactory();
		Session session = sessionFac.openSession();

		String hql = " from Users where userName= :userName and password= :password";
		Query q = session.createQuery(hql);
		q.setString("userName", user.getUserName());
		q.setString("password", user.getPassword());

		Users userConnected = (Users) q.uniqueResult();

		if (null != userConnected) {
			loginSend(true, salida);
		} else {
			loginSend(false, salida);
		}
	}

	private static void loginSend(boolean valid, ObjectOutputStream salida) {
		try {
			if (valid) {
				salida.writeObject(cipher.Cifrado.encode("true"));
			} else {
				salida.writeObject(cipher.Cifrado.encode("false"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void register(Iterator<Entry<String, JsonElement>> iterKey,
			Iterator<Entry<String, JsonElement>> iterValue, ObjectOutputStream salida) {

		Users user = new Users();

		while (iterKey.hasNext()) {
			String key = iterKey.next().getKey().toString();
			String value = iterValue.next().getValue().getAsString();

			switch (key) {
			case "user":
				user.setUserName(value);
				break;
			case "password":
				user.setPassword(value);
				break;
			}
		}

		try {
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();
			Transaction tx = session.beginTransaction();

			int usuarioInsertado = (int) session.save(user);

			tx.commit();

			if (usuarioInsertado > 0) {
				loginSend(true, salida);
			} else {
				loginSend(false, salida);
			}

		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param iterKey
	 * @param iterValue
	 * @param salidaRecive
	 * @param type         0 = municipio, 1 = estaciones, 2 = espacios naturales
	 */

	public static void listados(ObjectOutputStream salidaRecive, int type) {

		try {
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();

			String hql = "SELECT nombre FROM";

			switch (type) {
			case 0:
				hql = hql + " Municipio";
				break;
			case 1:
				hql = hql + " Estacion";
				break;
			case 2:
				hql = hql + " EspaciosNaturales";
				break;
			}

			Query q = session.createQuery(hql);

			List<String> items = q.list();
			String json = "";

			for (int i = 0; i < items.size(); i++) {
				if (i == 0) {
					json = items.get(i);
				} else {
					json = json + "," + items.get(i);
				}
			}

			sender(json, salidaRecive);

		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}

	}

	private static void sender(String msg, ObjectOutputStream salida) {
		try {
			salida.writeObject(cipher.Cifrado.encode(msg));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void estacionesFiltro(Iterator<Entry<String, JsonElement>> iter, ObjectOutputStream salidaRecive) {
		String whereFilter = iter.next().getValue().getAsString();

		try {
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();

			String hql = "SELECT nombre FROM Estacion WHERE municipio = (FROM Municipio WHERE nombre = :municipios)";

			Query q = session.createQuery(hql);
			q.setString("municipios", whereFilter);

			List<String> items = q.list();

			String names = "";

			for (int i = 0; i < items.size(); i++) {
				if (i == 0) {
					names = items.get(i);
				} else {
					names = names + "," + items.get(i);
				}
			}
			
			sender(names, salidaRecive);

		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}
	}

	public static void gallery(Iterator<Entry<String, JsonElement>> iter, ObjectOutputStream salidaRecive) {
		String userName = iter.next().getValue().getAsString();

		try {
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();

			String hql = "SELECT image FROM Gallery WHERE users = (SELECT idUser FROM Users WHERE userName = :iduser)";
			Query q = session.createQuery(hql);
			q.setString("iduser", userName);

			List<byte[]> items = q.list();
			
			String imageString = "";
			
			for (int i = 0; i < items.size(); i++) {
				if(i == 0) {
					imageString = (Base64.getEncoder().encodeToString(items.get(i)));
				} else {
					imageString = imageString + "holaimagen" + (Base64.getEncoder().encodeToString(items.get(i)));
				}
			}
			
			sender(imageString, salidaRecive);
			
		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}
	}
}