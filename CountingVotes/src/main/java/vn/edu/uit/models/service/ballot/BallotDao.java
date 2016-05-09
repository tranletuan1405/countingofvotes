package vn.edu.uit.models.service.ballot;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Ballot;
import vn.edu.uit.models.common.AbstractDao;

@Repository("ballotDao")
public class BallotDao extends AbstractDao implements IBallotDao {

	@Override
	public boolean persistBallot(Ballot ballot) {
		return this.persistObject(ballot);
	}

	@Override
	public boolean merge(Ballot ballot) {
		return this.mergeObject(ballot);
	}

	@Override
	public int insertDetail(long candidateId, long ballotId) {
		String sql = "INSERT INTO Ballot_Detail (candidate_id, ballot_id, is_enabled) VALUES (:candidateId, :ballotId, :isEnabled)";
		Query query = getSession().createSQLQuery(sql);
		query.setParameter("candidateId", candidateId);
		query.setParameter("ballotId", ballotId);
		query.setParameter("isEnabled", true);
		return query.executeUpdate();
	}

	@Override
	public long count(long votingId) {
		String hql = "SELECT count(*) FROM Ballot WHERE voting.id = :votingId AND isEnabled = :isEnabled";
		Query query = getSession().createQuery(hql);
		query.setParameter("votingId", votingId);
		query.setParameter("isEnabled", true);
		return (Long) query.uniqueResult();
	}

	
}
