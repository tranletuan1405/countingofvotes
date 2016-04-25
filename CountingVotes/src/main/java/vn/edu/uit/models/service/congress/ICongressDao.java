package vn.edu.uit.models.service.congress;

import java.util.List;

import vn.edu.uit.models.Congress;
import vn.edu.uit.models.json.CongressJson;

public interface ICongressDao {

	boolean persist(Congress congress);
	
	boolean update(Congress congress);
	
	Congress fetch(long id);
	
	List<Congress> fetch(int min, int max);

	String getCongressPath(long congressId);
	
	List<CongressJson> fetchJson();
}
