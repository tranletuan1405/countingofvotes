package vn.edu.uit.models.service.delegate;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import vn.edu.uit.models.Delegate;


public interface IDelegateDao {

	boolean persist(Delegate delegate);
	
	boolean delete(Delegate delegate);
	
	Delegate fetch(long id);
	
	List<Delegate> fetch(int min, int max);
	
	List<Delegate> getByDocument(InputStream is);

	long getNumOfDelegate(long congressId, long unitId, long typeId);
	
	long getNumOfAttendees(long congressId);
}
