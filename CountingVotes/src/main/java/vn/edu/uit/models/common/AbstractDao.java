package vn.edu.uit.models.common;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	protected boolean persist(Object obj){
		try {
			getSession().saveOrUpdate(obj);
			return true;
		}
		catch (HibernateException e){
			System.out.println("ERROR : Can't persist object : " + obj.getClass() + "cause of :" + e.getMessage());
			return false;
		}
	}
}
