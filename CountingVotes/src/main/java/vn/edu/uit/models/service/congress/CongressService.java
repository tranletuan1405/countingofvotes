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

	public List<CongressJson> fetchJson() {
		List<Congress> list = congressDao.fetch(0, 0);
		List<CongressJson> result = new ArrayList<CongressJson>();
		for (int i = 0; i < list.size(); i++) {
			result.add(new CongressJson(list.get(i)));
		}

		return result;
	}

	@Override
	public boolean update(Congress congress) {
		return congressDao.update(congress);
	}
	
}
