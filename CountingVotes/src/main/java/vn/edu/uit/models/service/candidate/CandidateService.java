package vn.edu.uit.models.service.candidate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.models.Candidate;
import vn.edu.uit.models.Delegate;

@Service("candidateService")
@Transactional
public class CandidateService implements ICandidateDao {

	@Autowired
	private ICandidateDao candidateDao;

	public boolean delete(long id){
		return candidateDao.delete(id);
	}

	@Override
	public List<Candidate> fetch(long votingId) {
		return candidateDao.fetch(votingId);
	}

	@Override
	public List<Delegate> getNotCandidate(long votingId) {
		return candidateDao.getNotCandidate(votingId);
	}

	@Override
	public List<Delegate> getIsCandidate(long votingId) {
		return candidateDao.getIsCandidate(votingId);
	}

	@Override
	public boolean persist(Candidate candidate) {
		return candidateDao.persist(candidate);
	}

	@Override
	public boolean isExists(List<Delegate> candidates, long delegateId) {
		return candidateDao.isExists(candidates, delegateId);
	}

	@Override
	public boolean merge(Candidate candidate) {
		return candidateDao.merge(candidate);
	}

	
}
