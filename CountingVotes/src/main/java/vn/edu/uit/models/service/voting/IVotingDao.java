package vn.edu.uit.models.service.voting;

import java.util.List;

import vn.edu.uit.models.CountingRule;
import vn.edu.uit.models.Voting;

public interface IVotingDao {

	boolean persist(Voting voting);
	
	boolean merge(Voting voting);
	
	long recreate(long oldId);
	
	Voting fetch(long id);
	
	List<Voting> fetch(int min, int max);

	boolean update(Voting voting);
	
	int getCurrentVersion(long congressId, String name);
	
	//Rule
	boolean persistCountingRule(CountingRule rule);
	
	CountingRule fetchRule(long votingId);
	
	boolean updateCountingRule(CountingRule rule);
}
