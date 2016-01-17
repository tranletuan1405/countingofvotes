package vn.edu.uit.models.service.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("candidateService")
@Transactional
public class CandidateService {

	@Autowired
	private ICandidateDao candidateDao;
	
	public boolean create(long votingId, long delegateId){
		return candidateDao.create(votingId, delegateId);
	}
	
	public boolean delete(long id){
		return candidateDao.delete(id);
	}

}
