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
	public List<Delegate> getNotCandidate(long votingId, long congressId) {
		return candidateDao.getNotCandidate(votingId, congressId);
	}

	@Override
	public List<Delegate> getIsCandidate(long votingId, long congressId) {
		return candidateDao.getIsCandidate(votingId, congressId);
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

	@Override
	public long getTotalCandidate(long votingId) {
		return candidateDao.getTotalCandidate(votingId);
	}

	@Override
	public long isExists(long delegateId, long votingId) {
		return candidateDao.isExists(delegateId, votingId);
	}

	@Override
	public long getNumOfCandidates(long votingId) {
		return candidateDao.getNumOfCandidates(votingId);
	}

	
}
