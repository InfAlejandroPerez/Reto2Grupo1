package server;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

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
		case "estaciones": {
			return listaMunucipios(dto);

		}

		case "espacios": {
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

	private DTO listaMunucipios(DTO dto) {
		
		DTO respuesta = dto;

		try {
				SessionFactory sessionFac = HibernateUtil.getSessionFactory();
				Session session = sessionFac.openSession();
	
				String hql = " select nombre from Municipio";
	
				Query q = session.createQuery(hql);
	
				
				dto.setListaMunicipios(q.list());
				
				/*List<String> municipios = (List<String>) q.list();
				
				for (int i = 0; i < municipios.size(); i++) {
					
					dto.setListaMunicipios(municipios.get(i).toString());
					
					//System.out.println(municipios.get(i));
					
				}*/

			} catch (HibernateException e) {
				System.out.println("Problem creating session factory");
				e.printStackTrace();
			}

		return respuesta;
	}

}
