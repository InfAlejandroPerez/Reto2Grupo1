package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.gson.JsonElement;

import cipher.Cifrado;
import hibernateUtil.HibernateUtil;
import objetos.EspaciosNaturales;
import objetos.Estacion;
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
				if (i == 0) {
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

	public static void detalles(Iterator<Entry<String, JsonElement>> iter, ObjectOutputStream salidaRecive, int type) {

		String nameDetail = iter.next().getValue().getAsString();

		String hql = "";

		switch (type) {
		case 0:
			hql = "FROM Municipio WHERE nombre = :nombres";
			break;
		case 1:
			hql = "FROM EspaciosNaturales WHERE nombre = :nombres";
			break;
		case 2:
			hql = "FROM Estacion WHERE nombre = :nombres";
			break;
		}

		SessionFactory sessionFac = HibernateUtil.getSessionFactory();
		Session session = sessionFac.openSession();

		Query q = session.createQuery(hql);
		q.setString("nombres", nameDetail);

		switch (type) {
		case 0:
			Municipio municipio = (Municipio) q.uniqueResult();

			String estacionesM = "null";

			Iterator<Map.Entry<String, JsonElement>> itermunicipio = municipio.getEstacions().iterator();

			int count = 0;
			while (itermunicipio.hasNext()) {
				if (count == 0) {
					Estacion es = (Estacion) itermunicipio.next();
					estacionesM = es.getNombre();
				} else {
					Estacion es = (Estacion) itermunicipio.next();
					estacionesM = estacionesM + ",hola" + es.getNombre();
				}
				count++;
			}

			String espaciosM = "null";

			Iterator<Map.Entry<String, JsonElement>> iters = municipio.getEspaciosNaturaleses().iterator();

			count = 0;
			while (iters.hasNext()) {
				if (count == 0) {
					EspaciosNaturales ess = (EspaciosNaturales) iters.next();
					espaciosM = ess.getNombre();
				} else {
					EspaciosNaturales ess = (EspaciosNaturales) iters.next();
					espaciosM = espaciosM + ",hola" + ess.getNombre();
				}
				count++;
			}

			String jsonMunicipio = "{ 'jsonData': [{ " + "'name': '" + municipio.getNombre() + "'," + "'descripcion': '"
					+ municipio.getDescripcion() + "'," + "'localidad': '" + municipio.getLocalidad() + "',"
					+ "'territorio': '" + municipio.getTerritorio() + "'," + "'estaciones': '" + estacionesM + "',"
					+ "'espaciosNaturaleses': '" + espaciosM + "'}]}";

			sender(jsonMunicipio, salidaRecive);
			break;
		case 1:
			EspaciosNaturales espacios = (EspaciosNaturales) q.uniqueResult();
			String jsonEspacios = "{ 'jsonData': [{ " + "'name': '" + espacios.getNombre() + "'," + "'descripcion': '"
					+ espacios.getDescripcion() + "'," + "'localidad': '" + espacios.getLocalidad() + "',"
					+ "'territorio': '" + espacios.getTerritorio() + "'," + "'marca': '" + espacios.getMarca() + "',"
					+ "'naturaleza': '" + espacios.getNaturaleza() + "'," + "'municipio': '"
					+ espacios.getMunicipio().getNombre() + "'}]}";

			sender(jsonEspacios, salidaRecive);

			break;
		case 2:
			Estacion estacion = (Estacion) q.uniqueResult();
			String jsonEstacion = "{ 'jsonData': [{ " + "'name': '" + estacion.getNombre() + "'," + "'provincia': '"
					+ estacion.getProvincia() + "'," + "'direccion': '" + estacion.getDireccion() + "',"
					+ "'latitud': '" + estacion.getLatitud() + "'," + "'longitud': '" + estacion.getLongitud() + "'}]}";

			sender(jsonEstacion, salidaRecive);
			break;
		}

	}
}