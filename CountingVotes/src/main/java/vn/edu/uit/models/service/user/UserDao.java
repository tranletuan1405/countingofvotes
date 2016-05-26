package vn.edu.uit.models.service.user;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.User;
import vn.edu.uit.models.common.AbstractDao;

@Repository("userDao")
public class UserDao extends AbstractDao implements IUserDao{

	@Override
	public User fetch(String username) {
		
		Criteria crit = getSession().createCriteria(User.class);
		crit.add(Restrictions.eq("username", username));
		crit.add(Restrictions.and(Restrictions.eq("isEnabled", true)));
		return (User) crit.uniqueResult();
	}

	@Override
	public boolean persist(User user) {
		return this.persistObject(user);
	}

	@Override
	public int ban(User user) {
		String hql = "UPDATE User SET isEnabled = :isEnabled WHERE username = :username";
		Query query = getSession().createQuery(hql);
		query.setParameter("isEnabled", false);
		query.setParameter("username", user.getUsername());
		return query.executeUpdate();
	}

	@Override
	public int unban(User user) {
		String hql = "UPDATE User SET isEnabled = :isEnabled WHERE username = :username";
		Query query = getSession().createQuery(hql);
		query.setParameter("isEnabled", true);
		query.setParameter("username", user.getUsername());
		return query.executeUpdate();
	}

	
}
