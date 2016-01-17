package vn.edu.uit.models.service.voting;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.models.Voting;

@Service("votingService")
@Transactional
public class VotingService {
	
	@Autowired
	private IVotingDao votingDao;
	
	public boolean persist(Voting voting){
		return votingDao.persist(voting);
	}
	
	public long recreate(long oldId){
		return votingDao.recreate(oldId);
	}
	
	public Voting fetch(long id){
		return votingDao.fetch(id);
	}
	
	public List<Voting> fetch(int min, int max){
		return votingDao.fetch(min, max);
	}
	
	
}
