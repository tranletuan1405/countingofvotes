package vn.edu.uit.models.common;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	protected boolean persistObject(Object obj){
		try {
			getSession().persist(obj);
			return true;
		}
		catch (HibernateException e){
			logger.error("ERROR : Can't persist object : " + obj.getClass() + " cause of :" + e.getMessage());
			return false;
		}
	}
	
	protected boolean saveOrUpdateObject(Object obj){
		try {
			getSession().saveOrUpdate(obj);
			return true;
		}
		catch (HibernateException e){
			logger.error("ERROR : Can't saveOrUpdate object : " + obj.getClass() + " cause of :" + e.getMessage());
			return false;
		}
	}
	
	protected boolean mergeObject(Object obj) {
		try {
			getSession().merge(obj);
			return true;
		}
		catch (HibernateException e){
			logger.error("ERROR : Can't merge object : " + obj.getClass() + " cause of :" + e.getMessage());
			return false;
		}
	}
	
	protected boolean deleteObject(Object obj){
		try {
			getSession().delete(obj);
			return true;
		}
		catch (HibernateException e){
			logger.error("ERROR : Can't delete object : " + obj.getClass() + "cause of :" + e.getMessage());
			return false;
		}
	}
}
