package vn.edu.uit.models.service.delegate;

import java.util.List;

import vn.edu.uit.models.Delegate;

public interface IDelegateDao {

	boolean persist(Delegate delegate);
	
	boolean delete(Delegate delegate);
	
	Delegate fetch(long id);
	
	List<Delegate> fetch(int min, int max);
	
}
