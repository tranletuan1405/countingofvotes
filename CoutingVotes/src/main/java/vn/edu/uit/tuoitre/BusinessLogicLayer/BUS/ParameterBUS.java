package vn.edu.uit.tuoitre.BusinessLogicLayer.BUS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.tuoitre.BusinessLogicLayer.DAO.ParameterDAO;
import vn.edu.uit.tuoitre.BusinessLogicLayer.DTO.ParameterDTO;

@Service("ParameterBUS")
@Transactional
public class ParameterBUS {

	@Autowired
	private ParameterDAO parameterDAO;
	
	public void save(ParameterDTO parameter){
		parameterDAO.save(parameter);
	}
	
	public ParameterDTO getByKey(String key){
		return parameterDAO.getByKey(key);
	}
}
