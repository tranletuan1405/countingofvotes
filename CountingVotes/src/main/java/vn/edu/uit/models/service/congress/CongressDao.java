package vn.edu.uit.models.service.congress;

import java.math.BigInteger;
import java.sql.Timestamp;
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
import vn.edu.uit.models.json.CongressJson;

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
		String hql = "SELECT congressPath FROM Congress WHERE id = :congressId AND isEnabled = :isEnabled";
		Query query = getSession().createQuery(hql);
		query.setParameter("congressId", congressId);
		query.setParameter("isEnabled", true);
		
		return (String) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CongressJson> fetchJson() {
		String sql = "SELECT eec.cid as id, cn as name, cd as totalDelegate, cu as totalUnit, count(v.id) as totalVoting, eec.start_time as startTime, eec.end_time as endTime "
				+ "FROM (SELECT cid, cn, cd, count(u.id) as cu, start_time, end_time "
				+ "FROM (SELECT c.id as cid, c.name as cn, count(d.id) as cd, start_time, end_time "
				+ "FROM Congress as c INNER JOIN Delegate as d ON c.id = d.congress_id "
				+ "GROUP BY c.id) as ec INNER JOIN Unit as u ON ec.cid = u.congress_id "
				+ "GROUP BY cid) eec INNER JOIN voting as v ON eec.cid = v.congress_id GROUP BY eec.cid";
		
		Query query = getSession().createSQLQuery(sql);
		List<Object[]> congresses = query.list();
		List<CongressJson> result = new ArrayList<CongressJson>();
		for(Object[] c : congresses){
			CongressJson congress = new CongressJson();
			congress.setId((BigInteger) c[0]);
			congress.setName((String) c[1]);
			congress.setTotalDelegate((BigInteger) c[2]);
			congress.setTotalUnit((BigInteger) c[3]);
			congress.setTotalVoting((BigInteger) c[4]);
			congress.setStartTime((Timestamp)c[5]);
			congress.setEndTime((Timestamp)c[6]);
			
			result.add(congress);
		}
		
		return result;
	}

	

}
