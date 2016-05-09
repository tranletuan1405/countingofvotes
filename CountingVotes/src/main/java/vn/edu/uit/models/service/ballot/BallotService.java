package vn.edu.uit.models.service.ballot;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.models.Ballot;
import vn.edu.uit.models.Voting;

@Service("ballotService")
@Transactional
public class BallotService implements IBallotDao{

	@Autowired
	private IBallotDao ballotDao;
	
	@Override
	public boolean persistBallot(Ballot ballot) {
		return ballotDao.persistBallot(ballot);
	}

	@Override
	public boolean merge(Ballot ballot) {
		return ballotDao.merge(ballot);
	}

	@Override
	public int insertDetail(long candidateId, long ballotId) {
		return ballotDao.insertDetail(candidateId, ballotId);
	}

	public long submitBallot(Voting voting, Set<Long> candidates){
		
		Ballot ballot = new Ballot();
		ballot.setVoting(voting);
		boolean isCreated = this.persistBallot(ballot);
		if (!isCreated || candidates == null || candidates.size() == 0)
			return -1;

		for (Long candidateId : candidates) {
			int check = this.insertDetail(candidateId, ballot.getId());
			if(check <= 0){
				ballot.setValid(false);
				this.persistBallot(ballot);
				return -1;
			}
		}

		return ballot.getId();

	}

	@Override
	public long count(long votingId) {
		return ballotDao.count(votingId);
	}
}
