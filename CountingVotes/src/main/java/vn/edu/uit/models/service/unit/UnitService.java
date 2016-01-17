package vn.edu.uit.models.service.unit;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.models.Unit;
import vn.edu.uit.models.service.congress.CongressService;

@Service("unitService")
@Transactional
public class UnitService {

	@Autowired
	private IUnitDao unitDao;
	
    public boolean persist(Unit unit){
    	return unitDao.persist(unit);
    }
	
	public boolean delete(Unit unit){
		return unitDao.delete(unit);
	}
	
	public List<Unit> fetch() {
		return unitDao.fetch();
	}
}
