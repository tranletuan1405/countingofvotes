package vn.edu.uit.models.service.voting;

import java.util.List;

import vn.edu.uit.models.CountingRule;
import vn.edu.uit.models.Voting;

public interface IVotingDao {

	boolean persist(Voting voting);

	boolean merge(Voting voting);

	boolean update(Voting voting);

	boolean savePattern(long votingId, String pattern);
	
	String getPattern(long votingId);

	long recreate(long oldId);

	Voting fetch(long id);

	List<Voting> fetchAll(long congressId);

	int getCurrentVersion(long congressId, String name);
	
	long getTotalVoting(long congressId);

	// Rule
	boolean persistCountingRule(CountingRule rule);

	CountingRule fetchRule(long votingId);

	boolean updateCountingRule(CountingRule rule);
	
	boolean getCountingType(long votingId);
	
	boolean checkCountingCode(long votingId);

}
