package vn.edu.uit.models.service.unit;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.models.Unit;
import vn.edu.uit.models.service.congress.CongressService;

@Service
@Transactional
public class UnitService implements IUnitDao {

	@Autowired
	private IUnitDao unitDao;

	@Override
	public boolean persist(Unit unit) {
		return unitDao.persist(unit);
	}

	@Override
	public boolean delete(Unit unit) {
		return unitDao.delete(unit);
	}

	@Override
	public List<Unit> fetch() {
		return unitDao.fetch();
	}

	@Override
	public Unit fetch(String name, long congressId) {
		return unitDao.fetch(name, congressId);
	}

	@Override
	public List<Unit> fetch(Long congressId) {
		return unitDao.fetch(congressId);
	}

	@Override
	public Unit fetch(long id, long congressId) {
		return unitDao.fetch(id, congressId);
	}

	@Override
	public long getTotalUnit(long congressId) {
		return unitDao.getTotalUnit(congressId);
	}
}
