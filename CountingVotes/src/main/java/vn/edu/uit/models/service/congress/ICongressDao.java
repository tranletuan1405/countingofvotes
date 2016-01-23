package vn.edu.uit.models.service.congress;

import java.util.List;

import vn.edu.uit.models.Congress;

public interface ICongressDao {

	boolean persist(Congress congress);
	
	Congress fetch(long id);
	
	List<Congress> fetch(int min, int max);
	
}
