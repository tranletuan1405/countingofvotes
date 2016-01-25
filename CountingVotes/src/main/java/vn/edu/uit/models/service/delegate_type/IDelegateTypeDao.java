package vn.edu.uit.models.service.delegate_type;

import java.util.List;

import vn.edu.uit.models.DelegateType;

public interface IDelegateTypeDao {

	boolean persist(DelegateType type);
	
	boolean delete(DelegateType type);
	
	DelegateType fetch(String shortName);
	
	List<DelegateType> fetch(int min, int max);
}
