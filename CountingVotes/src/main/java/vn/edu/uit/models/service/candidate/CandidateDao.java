package vn.edu.uit.models.service.candidate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Candidate;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.Voting;
import vn.edu.uit.models.common.AbstractDao;

@Repository("candidateDao")
public class CandidateDao extends AbstractDao implements ICandidateDao {

	private static final Logger logger = LoggerFactory.getLogger(CandidateDao.class);

	@Override
	public boolean create(long votingId, long delegateId) {
		try {
			Candidate candidate = new Candidate();
			Voting v = (Voting) getSession().get(Voting.class, votingId);
			Delegate d = (Delegate) getSession().get(Delegate.class, delegateId);
			candidate.setVoting(v);
			candidate.setDelegate(d);
			return this.saveOrUpdate(candidate);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean delete(long id) {
		try {
			Candidate candidate = (Candidate) getSession().get(Candidate.class, id);

			if (candidate.isEnabled() && candidate.isUpdatable()) {
				return this.delete(candidate);
			}

			return false;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}

	}

}
