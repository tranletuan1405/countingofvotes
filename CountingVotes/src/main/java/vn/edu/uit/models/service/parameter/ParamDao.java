package vn.edu.uit.models.service.parameter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Parameter;
import vn.edu.uit.models.common.AbstractDao;

@Repository("paramDao")
public class ParamDao extends AbstractDao implements IParamDao {

	private static final Logger logger = LoggerFactory.getLogger(ParamDao.class);

	
	@Override
	public boolean persits(Parameter param) {
		return this.persistObject(param);
	}

	@Override
	public boolean delete(Parameter param) {
		return this.deleteObject(param);
	}

	@Override
	public Parameter fetch(String paramName) {
		Criteria crit = getSession().createCriteria(Parameter.class);
		crit.add(Restrictions.eq("name", paramName));
		
		return (Parameter) crit.uniqueResult();
	}

	@Override
	public boolean saveOrUpdate(Parameter param) {
		return this.saveOrUpdateObject(param);
	}
}
