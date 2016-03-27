package vn.edu.uit.models.service.candidate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.models.Candidate;

@Service("candidateService")
@Transactional
public class CandidateService implements ICandidateDao {

	@Autowired
	private ICandidateDao candidateDao;
	
	public boolean create(long votingId, long delegateId){
		return candidateDao.create(votingId, delegateId);
	}
	
	public boolean delete(long id){
		return candidateDao.delete(id);
	}

	@Override
	public List<Candidate> fetch(long votingId) {
		return candidateDao.fetch(votingId);
	}

}
