package server;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import hibernateUtil.HibernateUtil;
import objetos.EspaciosNaturales;
import objetos.Estacion;
import objetos.Users;
import dto.DTO;

public class Controller {
		
	
	public String controlador(DTO dto) {

		String operacion = dto.getOperacion();

		switch (operacion) {
		case "login": {
			return validarLogin(dto);

		}
		case "registrar": {
			return (String) registrar(dto);

		}
		case "lista_municipios": {
			return listaMunucipios(dto);

		}
		case "estaciones": {
			return (String) listaEstaciones(dto);

		}case "espacios": {
			return (String) listaEspaciosNaturales(dto);

		}
		/*case "estacionesPorMunicipio": {
			return estacionesPorMunicipio(dto);

		}*/case "espaciosPorMunicipio": {
			return espaciosPorMunicipio(dto);
		}
		case "detallesEstacion": {
			return  detallesEstacion(dto);
		}
		case "detallesMunicipio": {
			return (String) detallesMunicipio(dto);
		}
		case "favoritos": {
			return favoritos(dto);

		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + operacion);
		}

	}

	private String espaciosPorMunicipio(DTO dto) {
		
		String respuesta = null;
		
		ArrayList<EspaciosNaturales> estaciones = new ArrayList<EspaciosNaturales>();
		
		try {
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();

			String hql = "from EspaciosNaturales WHERE municipio=:municipio";

			Query q = session.createQuery(hql);
			q.setString("municipio", dto.getCampoBusqueda());

			estaciones.addAll(q.list());

		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}

		return respuesta;
	}

	private String favoritos(DTO dto) {
		
		String respuesta = null;

		try {
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();

			String hql = "select nombre from favoritos Where idUser=:idUser";

			Query q = session.createQuery(hql);
			q.setString("idUser", dto.getIdUsuario());
			
			dto.setListaLugares((ArrayList) q.list());

		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}

		return respuesta;
	}
	

	private String detallesEstacion(DTO dto) {
		Gson gson = new Gson();
		Estacion estacion = null;
		String respuesta = null;	
		try {
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();

			String hql = "from Estacion WHERE nombre=:nombre";

			Query q = session.createQuery(hql);
			q.setString("nombre", dto.getCampoBusqueda());

			estacion = (Estacion) q.uniqueResult();
			//ObjectMapper mapper = new ObjectMapper();
			
			//respuesta = mapper.writeValueAsString(estacion);
			//respuesta = gson.toJson(estacion);
			
			
		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("Error en el metodo");
			e.printStackTrace();
		}

		return estacion.toString();
	}

	private Object detallesMunicipio(DTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<Estacion> estacionesPorMunicipio(DTO dto) {
		DTO respuesta = dto;
		ArrayList<Estacion> estaciones = new ArrayList<Estacion>();
		try {
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();

			String hql = "from Estacion WHERE municipio=:municipio";

			Query q = session.createQuery(hql);
			
			q.setString("municipio", dto.getCampoBusqueda());

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

			dto.setListaLugares((ArrayList) q.list());

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

	private String validarLogin(DTO dto) {

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
				
				return respuesta.toString();
			} else {
				System.out.println("false");
				respuesta.setLoginValidador(false);
				return respuesta.toString();
			}

		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error en el metodo");
			e.printStackTrace();
		}
		return null;

	}

	private String listaMunucipios(DTO dto) {

		String respuesta = null;
		String json = "";
		try {
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();

			String hql = " select nombre from Municipio";

			Query q = session.createQuery(hql);
			
			ArrayList<String> listaMunicipios = (ArrayList<String>) q.list();
			
		for(int i = 0 ; i < listaMunicipios.size(); i++){
			
			json += listaMunicipios.get(i).toString() + "/";
			
		}		
			 System.out.println(json);
   			

		} catch (HibernateException e) {
			System.out.println("Problem creating session factory");
			e.printStackTrace();
		}
		
		return json;
	}
	
	

}
