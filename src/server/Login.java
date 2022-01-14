package server;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import hibernateUtil.HibernateUtil;
import objetos.Users;


public class Login {

	public static void main(String[] args) {
			
		 validarLogin();
	}
	
	
	public static boolean validarLogin(/*User user*/) {
		boolean ret = false;
		String userName = "admin";
		String password = "admin";
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
