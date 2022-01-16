package server;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import hibernateUtil.HibernateUtil;
import objetos.Users;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Controller {

	public static void main(String[] args) {
			
		 validarLogin();
	}
	
	public void controlador(/* JsonObject raw*/) {
			
		
		String operacion = null /*json.operacion*/;
		
		switch (operacion /*json.operacion*/) {
			case "login": {
				validarLogin(/* Json raw*/);
				
			}
			case "lista_municipio": {
				validarLogin(/* Json raw*/);
				
			}
		
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + operacion);
		}

	}
	

	public static boolean validarLogin(/*Json user*/) {
		boolean ret = false;
		String userName = "admin";
		String password = "admina";
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
				ret = true;
			}else {
				System.out.println("false");
				ret = false;
			}
			
		} catch (HibernateException  e) {
			System.out.println("Problem creating session factory");
		     e.printStackTrace();
		}
		
		return ret;
	}
	
	

}
