package vn.edu.uit.models.service.parameter;

import vn.edu.uit.models.Parameter;

public interface IParamDao {

	boolean persits(Parameter param);
	
	boolean saveOrUpdate(Parameter param);
	
	boolean delete(Parameter param);
	
	Parameter fetch(String paramName);
}
