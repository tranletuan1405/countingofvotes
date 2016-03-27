package vn.edu.uit.models.service.candidate;

import java.util.List;

import vn.edu.uit.models.Candidate;

public interface ICandidateDao {

	boolean create(long votingId, long delegateId);
	
	boolean delete(long id);
	
	List<Candidate> fetch(long votingId);

}
