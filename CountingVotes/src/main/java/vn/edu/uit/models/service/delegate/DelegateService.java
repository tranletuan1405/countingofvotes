package vn.edu.uit.models.service.delegate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.service.congress.CongressService;

@Service("delegateService")
@Transactional
public class DelegateService {
	
	@Autowired
	private IDelegateDao delegateDao;
	
	public boolean persist(Delegate delegate){
		return delegateDao.persist(delegate);
	}
	
	public boolean delete(Delegate delegate){
		return delegateDao.delete(delegate);
	}
	
	public Delegate fetch(long id){
		return delegateDao.fetch(id);
	}
	
	public List<Delegate> fetch(int min, int max){
		return delegateDao.fetch(min, max);
	}
	
}
