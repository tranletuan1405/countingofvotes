package vn.edu.uit.models.service.parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.models.Parameter;

@Service("paramService")
@Transactional
public class ParamService implements IParamDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ParamService.class);


	@Autowired
	private ParamDao paramDao;
	
	@Override
	public boolean persits(Parameter param) {
		return paramDao.persits(param);
	}

	@Override
	public boolean delete(Parameter param) {
		return paramDao.delete(param);
	}

	@Override
	public Parameter fetch(String paramName) {
		return paramDao.fetch(paramName);
	}

	@Override
	public boolean saveOrUpdate(Parameter param) {
		return paramDao.saveOrUpdate(param);
	}
	
	
}
