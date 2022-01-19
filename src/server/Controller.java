package server;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import hibernateUtil.HibernateUtil;
import objetos.Estacion;
import objetos.Users;
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
			return listaEstaciones(dto);

		}case "estacionesPorMunicipio": {
			return estacionesPorMunicipio(dto);

		}
		case "espacios": {
			return listaEspaciosNaturales(dto);

		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + operacion);
		}

	}

	private ArrayList<Estacion> estacionesPorMunicipio(DTO dto) {
		DTO respuesta = dto;
		ArrayList<Estacion> estaciones = new ArrayList<Estacion>();
		try {
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();

			String hql = "from Estacion WHERE municipio=:municipio";

			Query q = session.createQuery(hql);
			//TODO poner IDMunicipio q.setString("municipio", dto.toString());
			q.setString("municipio", "1");
			
			 estaciones.addAll(q.list());			
			
		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}
		
		
		return estaciones;
	}

	private Object listaEspaciosNaturales(DTO dto) {
		DTO respuesta = dto;

		try {
				SessionFactory sessionFac = HibernateUtil.getSessionFactory();
				Session session = sessionFac.openSession();
	
				String hql = "select nombre from EspaciosNaturales";
	
				Query q = session.createQuery(hql);
					
				dto.setListaLugares( (ArrayList) q.list());

			} catch (HibernateException e) {
				System.out.println("Problem creating session factory");
				e.printStackTrace();
			}

		return respuesta;
	}

	private Object listaEstaciones(DTO dto) {
		
		DTO respuesta = dto;

		try {
				SessionFactory sessionFac = HibernateUtil.getSessionFactory();
				Session session = sessionFac.openSession();
	
				String hql = "select nombre from Estacion";
	
				Query q = session.createQuery(hql);
				
				dto.setListaLugares((ArrayList) q.list());

			} catch (HibernateException e) {
				System.out.println("Problem creating session factory");
				e.printStackTrace();
			}

		return respuesta;
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
				
				dto.setListaLugares( (ArrayList) q.list());

			} catch (HibernateException e) {
				System.out.println("Problem creating session factory");
				e.printStackTrace();
			}

		return respuesta;
	}

}
