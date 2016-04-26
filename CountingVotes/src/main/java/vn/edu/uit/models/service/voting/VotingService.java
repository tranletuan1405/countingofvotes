package vn.edu.uit.models.service.voting;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.models.CountingRule;
import vn.edu.uit.models.Voting;

@Service("votingService")
@Transactional
public class VotingService implements IVotingDao {

	@Autowired
	private IVotingDao votingDao;

	public boolean persist(Voting voting) {
		return votingDao.persist(voting);
	}

	public long recreate(long oldId) {
		return votingDao.recreate(oldId);
	}

	public Voting fetch(long id) {
		return votingDao.fetch(id);
	}

	@Override
	public boolean merge(Voting voting) {
		return votingDao.merge(voting);
	}

	@Override
	public int getCurrentVersion(long congressId, String name) {
		return votingDao.getCurrentVersion(congressId, name);
	}

	@Override
	public boolean persistCountingRule(CountingRule rule) {
		return votingDao.persistCountingRule(rule);
	}

	@Override
	public boolean update(Voting voting) {
		return votingDao.update(voting);
	}

	@Override
	public CountingRule fetchRule(long votingId) {
		return votingDao.fetchRule(votingId);
	}

	@Override
	public boolean updateCountingRule(CountingRule rule) {
		return votingDao.updateCountingRule(rule);
	}

	@Override
	public boolean savePattern(long votingId, String pattern) {
		return votingDao.savePattern(votingId, pattern);
	}

	@Override
	public String getPattern(long votingId) {
		return votingDao.getPattern(votingId);
	}

	@Override
	public long getTotalVoting(long congressId) {
		return votingDao.getTotalVoting(congressId);
	}

	@Override
	public List<Voting> fetchAll(long congressId) {
		return votingDao.fetchAll(congressId);
	}

}
