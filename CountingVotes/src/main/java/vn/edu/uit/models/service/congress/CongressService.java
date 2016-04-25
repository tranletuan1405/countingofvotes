package vn.edu.uit.models.service.congress;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.controllers.CongressController;
import vn.edu.uit.models.Congress;
import vn.edu.uit.models.json.CongressJson;

@Service
@Transactional
public class CongressService implements ICongressDao {

	private static final Logger logger = LoggerFactory.getLogger(CongressService.class);

	@Autowired
	private ICongressDao congressDao;
	
	public CongressService(){
	}

	@Override
	public boolean persist(Congress congress) {
		return congressDao.persist(congress);
	}

	@Override
	public Congress fetch(long id) {
		return congressDao.fetch(id);
	}

	@Override
	public List<Congress> fetch(int min, int max) {
		return congressDao.fetch(min, max);
	}

	@Override
	public boolean update(Congress congress) {
		return congressDao.update(congress);
	}

	@Override
	public String getCongressPath(long congressId) {
		return congressDao.getCongressPath(congressId);
	}

	@Override
	public List<CongressJson> fetchJson() {
		return congressDao.fetchJson();
	}
	
}
