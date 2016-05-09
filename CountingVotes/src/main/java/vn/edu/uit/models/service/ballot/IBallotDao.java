package vn.edu.uit.models.service.ballot;

import vn.edu.uit.models.Ballot;

public interface IBallotDao {

	boolean persistBallot(Ballot ballot);
	
	boolean merge(Ballot ballot);
	
	int insertDetail(long candidateId, long ballotId);
	
	long count(long votingId);
}
