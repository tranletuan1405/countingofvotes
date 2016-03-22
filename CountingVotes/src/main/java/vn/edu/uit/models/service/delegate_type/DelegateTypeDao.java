package vn.edu.uit.models.service.delegate_type;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.DelegateType;
import vn.edu.uit.models.common.AbstractDao;
import vn.edu.uit.models.service.delegate.DelegateDao;

@Repository
public class DelegateTypeDao extends AbstractDao implements IDelegateTypeDao {

	private static final Logger logger = LoggerFactory.getLogger(DelegateTypeDao.class);
	
	@Override
	public boolean persist(DelegateType type) {
		return persistObject(type);
	}

	@Override
	public boolean delete(DelegateType type) {
		type.setEnabled(false);
		return this.persist(type);
	}

	@Override
	public DelegateType fetch(String shortName) {
		Criteria crit = getSession().createCriteria(DelegateType.class);
		crit.add(Restrictions.eq("isEnabled",true));
		crit.add(Restrictions.and(Restrictions.eq("shortName", shortName)));
		return (DelegateType) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DelegateType> fetch(int min, int max) {
		Criteria crit = getSession().createCriteria(DelegateType.class);
		crit.add(Restrictions.eq("isEnabled",true));
		
		if(min >= 0 && max > 0 && max > min){
			crit.setFirstResult(min);
			crit.setMaxResults(max);
		}
		
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}

}
