package vn.edu.uit.models.service.candidate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Candidate;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.Voting;
import vn.edu.uit.models.common.AbstractDao;
import vn.edu.uit.models.json.CandidateJson;

@Repository("candidateDao")
public class CandidateDao extends AbstractDao implements ICandidateDao {

	private static final Logger logger = LoggerFactory.getLogger(CandidateDao.class);

	@Override
	public boolean delete(long id) {
		try {
			String hql = "DELETE FROM Candidate WHERE delegate.id = :delegateId";
			Query query = getSession().createQuery(hql);
			query.setParameter("delegateId", id);
			int result = query.executeUpdate();
			return result > 0 ? true : false;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Candidate> fetch(long votingId) {
		String hql = "FROM Candidate WHERE voting.id = :votingId";
		Query query = getSession().createQuery(hql);
		query.setParameter("votingId", votingId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Delegate> getNotCandidate(long votingId, long congressId) {
		String hql = "FROM Delegate WHERE id NOT IN "
				+ "(SELECT delegate.id FROM Candidate WHERE voting.id = :votingId)  AND arivalTime IS NOT NULL AND congress.id = :congressId";
		Query query = getSession().createQuery(hql);
		query.setParameter("votingId", votingId);
		query.setParameter("congressId", congressId);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Delegate> getIsCandidate(long votingId, long congressId) {
		String hql = "FROM Delegate WHERE id IN ("
				+ "SELECT delegate.id FROM Candidate WHERE voting.id = :votingId) AND arivalTime IS NOT NULL AND congress.id = :congressId";
		Query query = getSession().createQuery(hql);
		query.setParameter("votingId", votingId);
		query.setParameter("congressId", congressId);
		return query.list();
	}

	@Override
	public boolean persist(Candidate candidate) {
		return this.persistObject(candidate);
	}

	@Override
	public boolean isExists(List<Delegate> candidates, long delegateId) {
		
		for(int i = 0; i < candidates.size(); i ++){
			if(candidates.get(i).getId() == delegateId)
				return true;
		}
		return false;
	}

	@Override
	public boolean merge(Candidate candidate) {
		return this.mergeObject(candidate);
	}

	@Override
	public long getTotalCandidate(long votingId) {
		String hql = "SELECT count(*) FROM Candidate WHERE voting.id = :votingId AND isEnabled = :isEnabled";
		Query query = getSession().createQuery(hql);
		query.setParameter("votingId", votingId);
		query.setParameter("isEnabled", true);
		return (Long)query.uniqueResult();
	}

	@Override
	public long isExists(long delegateId, long votingId) {
		String hql = "SELECT id FROM Candidate WHERE delegate.id = :delegateId AND voting.id = :votingId";
		Query query = getSession().createQuery(hql);
		query.setParameter("delegateId", delegateId);
		query.setParameter("votingId", votingId);
		return (Long) query.uniqueResult();
	}

	@Override
	public long getNumOfCandidates(long votingId) {
		String hql = "SELECT count(*) FROM Candidate WHERE voting.id = :votingId";
		Query query = getSession().createQuery(hql);
		query.setParameter("votingId", votingId);
		return (Long) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateJson> getStatisticsVoting(long votingId, long totalBallot) {
		String sql = "SELECT delegate.id, delegate.name, delegate.date_of_birth, delegate.gender, delegate.position, count(*) as total_vote FROM ballot_detail "
				+ "inner join candidate on (ballot_detail.candidate_id = candidate.id)"
				+ "inner join delegate on (delegate.id = candidate.delegate_id)"
				+ "WHERE ballot_id in (SELECT id FROM BALLOT WHERE voting_id = :votingId)"
				+ "GROUP BY candidate_id";
		
		Query query = getSession().createSQLQuery(sql);
		query.setParameter("votingId", votingId);
		List<Object[]> candidates = query.list();
		List<CandidateJson> result = new ArrayList<CandidateJson>();
		
		for(Object[] c : candidates){
			CandidateJson candidate = new CandidateJson();
			candidate.setId(Long.valueOf(c[0].toString()));
			candidate.setName(c[1].toString());
			candidate.setDateOfBirth(c[2].toString());
			candidate.setGender(c[3].toString());
			candidate.setPosition(c[4].toString());
			
			long totalVote = Long.valueOf(c[5].toString());
			candidate.setTotalVote(totalVote);
			candidate.setRate(((double)totalVote / (double) totalBallot) * 100);
			
			result.add(candidate);
		}
		
		return result;
	}

}
