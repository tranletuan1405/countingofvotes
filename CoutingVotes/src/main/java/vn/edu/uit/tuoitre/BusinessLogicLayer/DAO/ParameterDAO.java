package vn.edu.uit.tuoitre.BusinessLogicLayer.DAO;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import vn.edu.uit.tuoitre.BusinessLogicLayer.DTO.ParameterDTO;

@Repository("ParameterDAO")
public class ParameterDAO extends AbstractDAO{

	public void save(ParameterDTO parameter){
		persist(parameter);
	}
	
	public ParameterDTO getByKey(String key){
		Criteria criteia = getSession().createCriteria(ParameterDTO.class);
		criteia.add(Restrictions.eq("key", key));
		return (ParameterDTO) criteia.uniqueResult();
	}
}
