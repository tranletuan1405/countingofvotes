package vn.edu.uit.models.service.candidate;

import java.util.List;

import vn.edu.uit.models.Candidate;
import vn.edu.uit.models.Delegate;

public interface ICandidateDao {

	boolean persist(Candidate candidate);
	
	boolean delete(long id);
	
	boolean merge(Candidate candidate);
	
	List<Candidate> fetch(long votingId);
	
	List<Delegate> getNotCandidate(long votingId, long congressId);
	
	List<Delegate> getIsCandidate(long votingId, long congressId);

	boolean isExists(List<Delegate> candidates, long delegateId);
	
	long getTotalCandidate(long votingId);
}
