package vn.edu.uit.models.service.delegate_type;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.models.DelegateType;

@Service
@Transactional
public class DelegateTypeService implements IDelegateTypeDao {

	@Autowired
	private IDelegateTypeDao delegateTypeDao;

	@Override
	public boolean persist(DelegateType type) {
		return delegateTypeDao.persist(type);
	}

	@Override
	public boolean delete(DelegateType type) {
		return delegateTypeDao.delete(type);
	}

	@Override
	public DelegateType fetch(String shortName) {
		return delegateTypeDao.fetch(shortName);
	}

	@Override
	public List<DelegateType> fetch(int min, int max) {
		return delegateTypeDao.fetch(min, max);
	}

	public List<DelegateType> fetch() {
		return delegateTypeDao.fetch(0, 0);
	}
}
