package vn.edu.uit.models.service.voting;

import java.math.BigInteger;
import java.util.ArrayList;
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
import vn.edu.uit.models.json.CongressJson;
import vn.edu.uit.models.json.VotingJson;

@Repository("votingDao")
public class VotingDao extends AbstractDao implements IVotingDao {

	private static final Logger logger = LoggerFactory.getLogger(VotingDao.class);

	@Override
	public boolean persist(Voting voting) {
		return persistObject(voting);
	}

	@Override
	public long recreate(long oldId) {
		return 0;
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

	@Override
	public boolean getCountingType(long votingId) {
		String hql = "SELECT countingRule.isResidual FROM Voting v WHERE v.id = :votingId AND v.isEnabled = :isEnabled";
		Query query = getSession().createQuery(hql);
		query.setParameter("votingId", votingId);
		query.setParameter("isEnabled", true);
		return (Boolean) query.uniqueResult();
	}

	@Override
	public boolean checkCountingCode(long votingId) {
		String sql = "SELECT COUNT(*) FROM delegate INNER JOIN candidate "
				+ "ON delegate.id = candidate.delegate_id "
				+ "WHERE candidate.voting_id = :votingId AND delegate.counting_code_id IS NULL;";
		
		Query query = getSession().createSQLQuery(sql);
		query.setParameter("votingId", votingId);
		Long result = ((BigInteger) query.uniqueResult()).longValue();
		
		return result > 0 ? false : true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VotingJson> getStatisticsVotings(long congressId) {
		String sql = "SELECT  v.id, v.name, v.version, v.start_time, v.end_time, v.num_of_valid_ballot, v.total_ballot, count(*) as total_candidate, counted_ballot FROM candidate inner join "
				+ "(SELECT voting.id, voting.name, voting.version, voting.start_time, voting.end_time, voting.num_of_valid_ballot, voting.total_ballot, count(*) as counted_ballot FROM voting inner join ballot on voting.id = ballot.voting_id "
				+ "WHERE voting.congress_id = :congressId AND (SELECT COUNT(*) as counted_ballot FROM ballot WHERE ballot.voting_id = voting.id) > 0 "
				+ "GROUP BY voting.id) as v "
				+ "on v.id = candidate.voting_id "
				+ "GROUP BY v.id";
		
		Query query = getSession().createSQLQuery(sql);
		query.setParameter("congressId", congressId);
		List<Object[]> votings = query.list();
		List<VotingJson> result = new ArrayList<VotingJson>();
		
		for(Object[] v : votings){
			VotingJson voting = new VotingJson();
			voting.setId(Long.valueOf(v[0].toString()));
			voting.setName(v[1].toString());
			voting.setVersion(Integer.valueOf(v[2].toString()));
			try {voting.setStartTime(v[3].toString());}catch(Exception e){}
			try {voting.setEndTime(v[4].toString());}catch(Exception e){}
			voting.setNumOfValidBallots(Integer.valueOf(v[5].toString()));
			voting.setNumOfBallots(Integer.valueOf(v[6].toString()));
			voting.setNumOfCandidates(Long.valueOf(v[7].toString()));
			voting.setCountedBallots(Long.valueOf(v[8].toString()));
			result.add(voting);
		}
	
		return result;
	}

}
