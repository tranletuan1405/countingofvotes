package vn.edu.uit.models.service.candidate;

import java.util.List;

import vn.edu.uit.models.Candidate;
import vn.edu.uit.models.Delegate;

public interface ICandidateDao {

	boolean persist(Candidate candidate);
	
	boolean delete(long id);
	
	List<Candidate> fetch(long votingId);
	
	List<Delegate> getNotCandidate(long votingId);
	
	List<Delegate> getIsCandidate(long votingId);

	boolean isExists(List<Delegate> candidates, long delegateId);
}
