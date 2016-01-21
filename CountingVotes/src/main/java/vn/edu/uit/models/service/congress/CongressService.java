package vn.edu.uit.models.service.congress;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.controllers.CongressController;
import vn.edu.uit.models.Congress;

@Service
@Transactional
public class CongressService {

	private static final Logger logger = LoggerFactory.getLogger(CongressService.class);

	@Autowired
	private ICongressDao congressDao;
	
	public CongressService(){
	}

	public boolean persist(Congress congress) {
		return congressDao.persist(congress);
	}

	public Congress fetch(long id) {
		return congressDao.fetch(id);
	}

	public List<Congress> fetch(int min, int max) {
		return congressDao.fetch(min, max);
	}

	public List<Congress> fetch() {
		return congressDao.fetch(0, 0);
	}
	
}