package vn.edu.uit.models.service.voting;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Voting;
import vn.edu.uit.models.common.AbstractDao;

@Repository("votingDao")
public class VotingDao extends AbstractDao implements IVotingDao {

	private static final Logger logger = LoggerFactory.getLogger(VotingDao.class);

	@Override
	public boolean persist(Voting voting) {
		return this.persist(voting);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Voting> fetch(int min, int max) {
		Criteria crit = getSession().createCriteria(Voting.class);
		crit.add(Restrictions.eq("isEnabled", true));

		if (min >= 0 && max > 0 && min < max) {
			crit.setFirstResult(min);
			crit.setMaxResults(max);
		}

		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}
}
