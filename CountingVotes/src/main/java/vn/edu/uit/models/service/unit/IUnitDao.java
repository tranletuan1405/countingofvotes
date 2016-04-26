package vn.edu.uit.models.service.unit;

import java.util.List;

import vn.edu.uit.models.Unit;

public interface IUnitDao {

	boolean persist(Unit unit);
	
	boolean delete(Unit unit);
	
	List<Unit> fetch();
	
	List<Unit> fetch(Long congressId);
	
	Unit fetch(String name, long congressId);
	
	Unit fetch(long id, long congressId);
	
	long getTotalUnit(long congressId);
}
