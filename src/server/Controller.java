package server;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

	
	private String datosRecebidos;
	
	public Object controlador(DTO dto) {
		
		String operacion = dto.getOperacion();
		
		switch (operacion) {
			case "login": {
				return validarLogin(dto);
				
			}
			case "lista_municipio": {
				return listaMunucipios(dto);
				
			}
		
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + operacion);
		}

	}
	

	public static DTO validarLogin(DTO dto) {
		
		boolean ret = false;
		DTO respuesta = dto;
		
		String userName = dto.getUserName();
		String password = dto.getPassword();
	
		try {
			
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();
			
			String hql = " from Users where userName= :userName and password= :password";
			
			Query q = session.createQuery(hql);
			q.setString("userName",userName);
			q.setString("password",password);
			
			Users users = (Users) q.uniqueResult();
			
			if(null!=users) {
				System.out.println("true");
				respuesta.setLoginValidador(true);
				return respuesta;
			}else {
				System.out.println("false");
				respuesta.setLoginValidador(false);
				return respuesta;
			}
			
		} catch (HibernateException  e) {
			System.out.println("Problem creating session factory");
		     e.printStackTrace();
		}
		return null;
		
	
	}
	
	public static ArrayList<Municipio> listaMunucipios(DTO dto) {
		ArrayList municipios = new ArrayList<Municipio>();
		
		try {
			SessionFactory sessionFac = HibernateUtil.getSessionFactory();
			Session session = sessionFac.openSession();
			
			String hql = " from Municipio Where territorio= :territorio ";
			
			Query q = session.createQuery(hql);
			q.setString("territorio", "bizkaia");

			
			Users users = (Users) q.uniqueResult();
			
			if(null!=users) {
				System.out.println("true");
				
			}else {
				System.out.println("false");
				
			}
			
		} catch (HibernateException  e) {
			System.out.println("Problem creating session factory");
		     e.printStackTrace();
		}
		
		return municipios;
	}
	
	
	

}
