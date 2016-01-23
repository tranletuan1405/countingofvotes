package vn.edu.uit.models.service.unit;

import java.util.List;

import vn.edu.uit.models.Unit;

public interface IUnitDao {

	boolean persist(Unit unit);
	
	boolean delete(Unit unit);
	
	List<Unit> fetch();
	
	Unit fetch(String name);
}
