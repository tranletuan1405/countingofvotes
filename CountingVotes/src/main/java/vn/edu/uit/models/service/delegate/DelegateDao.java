package vn.edu.uit.models.service.delegate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.common.AbstractDao;

@Repository("delegateDao")
public class DelegateDao extends AbstractDao implements IDelegateDao {

	@Override
	public boolean persist(Delegate delegate) {
		return this.persist(delegate);
	}

	@Override
	public boolean delete(Delegate delegate) {
		delegate.setEnabled(false);
		return this.persist(delegate);
	}

	@Override
	public Delegate fetch(long id) {
		return (Delegate) getSession().get(Delegate.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Delegate> fetch(int min, int max) {
		Criteria crit = getSession().createCriteria(Delegate.class);
		crit.add(Restrictions.eq("isEnabled", true));
		
		if(min >= 0 && max > 0 && max > min){
			crit.setFirstResult(min);
			crit.setMaxResults(max);
		}
		
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}

}
