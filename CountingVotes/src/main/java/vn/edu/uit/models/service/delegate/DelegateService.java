package vn.edu.uit.models.service.delegate;

import java.io.FileInputStream;
import java.io.InputStream;
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
public class DelegateService implements IDelegateDao{
	
	@Autowired
	private IDelegateDao delegateDao;
	
	@Override
	public boolean persist(Delegate delegate){
		return delegateDao.persist(delegate);
	}
	
	@Override
	public boolean delete(Delegate delegate){
		return delegateDao.delete(delegate);
	}
	
	@Override
	public Delegate fetch(long id){
		return delegateDao.fetch(id);
	}

	@Override
	public List<Delegate> getByDocument(InputStream is) {
		return delegateDao.getByDocument(is);
	}

	@Override
	public long getNumOfDelegate(long congressId, long unitId, long typeId) {
		return delegateDao.getNumOfDelegate(congressId, unitId, typeId);
	}

	@Override
	public long getNumOfAttendees(long congressId) {
		return delegateDao.getNumOfAttendees(congressId);
	}

	@Override
	public boolean update(Delegate delegate) {
		return delegateDao.update(delegate);
	}

	@Override
	public long getTotalDelegate(long congressId) {
		return delegateDao.getTotalDelegate(congressId);
	}

	@Override
	public List<Delegate> fetchAll(long congressId) {
		return delegateDao.fetchAll(congressId);
	}

}
