package vn.edu.uit.models.service.congress;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Congress;
import vn.edu.uit.models.common.AbstractDao;

@Repository
public class CongressDao extends AbstractDao implements ICongressDao {

	private static final Logger logger = LoggerFactory.getLogger(CongressDao.class);

	public CongressDao() {
	}

	@Override
	public boolean persist(Congress congress) {
		return persistObject(congress);
	}

	@Override
	public Congress fetch(long id) {
		return (Congress) getSession().get(Congress.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Congress> fetch(int min, int max) {
		try {
			Criteria crit = getSession().createCriteria(Congress.class);
			crit.add(Restrictions.eq("isEnabled", true));

			if (min < max && min >= 0 && max > 0) {
				crit.setFirstResult(min);
				crit.setMaxResults(max);
			}

			crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			return crit.list();

		} catch (Exception e) {
			logger.error("fetch(min , max)");
			return new ArrayList<Congress>(0);
		}

	}

	@Override
	public boolean update(Congress congress) {
	
		return mergeObject(congress);
	}

	@Override
	public String getCongressPath(long congressId) {
		String hql = "SELECT congressPath FROM Congress WHERE congress.id = :congressId";
		Query query = getSession().createQuery(hql);
		query.setParameter("congressId", congressId);
		return (String) query.uniqueResult();
	}

}
