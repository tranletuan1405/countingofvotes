package vn.edu.uit.models.service.voting;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.CountingRule;
import vn.edu.uit.models.Voting;
import vn.edu.uit.models.common.AbstractDao;

@Repository("votingDao")
public class VotingDao extends AbstractDao implements IVotingDao {

	private static final Logger logger = LoggerFactory.getLogger(VotingDao.class);

	@Override
	public boolean persist(Voting voting) {
		return persistObject(voting);
	}

	@Override
	public long recreate(long oldId) {
		try {
			Voting oldVersion = fetch(oldId);
			Voting newVersion = new Voting(oldVersion);
			boolean result = this.persist(newVersion);

			if (result) {
				return newVersion.getId();
			}

			return -1;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return -1;
		}
	}

	@Override
	public Voting fetch(long id) {
		return (Voting) getSession().get(Voting.class, id);
	}
	
	@Override
	public boolean merge(Voting voting) {
		return mergeObject(voting);
	}

	@Override
	public int getCurrentVersion(long congressId, String name) {
		String hql = "SELECT count(*) FROM Voting WHERE congress.id = :congressId AND name = :name";
		Query query = getSession().createQuery(hql);
		query.setParameter("congressId", congressId);
		query.setParameter("name", name);

		int result = (Integer) query.uniqueResult();
		return result;
	}

	@Override
	public boolean persistCountingRule(CountingRule rule) {
		return this.persistObject(rule);
	}

	@Override
	public boolean update(Voting voting) {
		return this.saveOrUpdateObject(voting);
	}

	@Override
	public CountingRule fetchRule(long votingId) {
		String hql = "SELECT countingRule FROM Voting v WHERE v.id = :votingId";
		Query query = getSession().createQuery(hql);
		query.setParameter("votingId", votingId);
		return (CountingRule) query.uniqueResult();
	}

	@Override
	public boolean updateCountingRule(CountingRule rule) {
		return this.mergeObject(rule);
	}

	@Override
	public boolean savePattern(long votingId, String pattern) {
		String hql = "UPDATE Voting SET ballotPattern = :pattern WHERE id = :votingId";
		Query query = getSession().createQuery(hql);
		query.setParameter("pattern", pattern);
		query.setParameter("votingId", votingId);
		int result = query.executeUpdate();
		return result > 0 ? true : false;
	}

	@Override
	public String getPattern(long votingId) {
		String hql = "SELECT v.ballotPattern FROM Voting v WHERE v.id = :votingId AND v.isEnabled = :isEnabled";
		Query query = getSession().createQuery(hql);
		query.setParameter("votingId", votingId);
		query.setParameter("isEnabled", true);
		return (String) query.uniqueResult();
	}

	@Override
	public long getTotalVoting(long congressId) {
		String hql = "SELECT count(*) FROM Voting WHERE congress.id = :congressId AND isEnabled = :isEnabled";
		Query query = getSession().createQuery(hql);
		query.setParameter("congressId", congressId);
		query.setParameter("isEnabled", true);
		return (Long)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Voting> fetchAll(long congressId) {
		String hql = "FROM Voting WHERE congress.id = :congressId AND isEnabled = :isEnabled";
		Query query = getSession().createQuery(hql);
		query.setParameter("congressId", congressId);
		query.setParameter("isEnabled", true);
		return query.list();
	}

}
