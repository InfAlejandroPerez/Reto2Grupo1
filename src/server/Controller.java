package server;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import hibernateUtil.HibernateUtil;
import objetos.Municipio;
import objetos.Users;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dto.DTO;

public class Controller {

	public Object controlador(DTO dto) {

		String operacion = dto.getOperacion();

		switch (operacion) {
		case "login": {
			return validarLogin(dto);

		}
		case "registrar": {
			return registrar(dto);

		}
		case "lista_municipios": {
			return listaMunucipios(dto);

		}

		default:
			throw new IllegalArgumentException("Unexpected value: " + operacion);
		}

	}

	private Object registrar(DTO dto) {

		DTO respuesta = dto;

		try {

				SessionFactory sessionFac = HibernateUtil.getSessionFactory();
				Session session = sessionFac.openSession();
				Transaction tx = session.beginTransaction();
	
				Users user = new Users();
				user.setUserName(dto.getUserName());
				user.setPassword(dto.getPassword());
	
				int usuarioInsertado = (int) session.save(user);
			
				tx.commit();
				
				if (usuarioInsertado > 0) {
	
					respuesta.setUsuarioRegistrado(true);
					return validarLogin(respuesta);
				} else {
					respuesta.setUsuarioRegistrado(false);
					return respuesta;
				}
			

		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}

		return null;

	}

	private DTO validarLogin(DTO dto) {

		DTO respuesta = dto;

		String userName = dto.getUserName();
		String password = dto.getPassword();

		try {

			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();

			String hql = " from Users where userName= :userName and password= :password";

			Query q = session.createQuery(hql);
			q.setString("userName", userName);
			q.setString("password", password);

			Users users = (Users) q.uniqueResult();

			if (null != users) {
				System.out.println("true");
				respuesta.setIdUsuario(users.getIdUser().toString());
				respuesta.setLoginValidador(true);
				return respuesta;
			} else {
				System.out.println("false");
				respuesta.setLoginValidador(false);
				return respuesta;
			}

		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}
		return null;

	}

	private ArrayList<Municipio> listaMunucipios(DTO dto) {
		ArrayList municipios = new ArrayList<Municipio>();

		try {
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();

			String hql = " from Municipio Where territorio= :territorio ";

			Query q = session.createQuery(hql);
			q.setString("territorio", "bizkaia");

			Users users = (Users) q.uniqueResult();

			if (null != users) {
				System.out.println("true");

			} else {
				System.out.println("false");

			}

		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}

		return municipios;
	}

}
