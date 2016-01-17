package vn.edu.uit.models.service.candidate;

public interface ICandidateDao {

	boolean create(long votingId, long delegateId);
	
	boolean delete(long id);

}
