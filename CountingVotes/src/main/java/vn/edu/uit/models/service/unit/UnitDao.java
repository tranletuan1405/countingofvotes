package vn.edu.uit.models.service.unit;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Unit;
import vn.edu.uit.models.common.AbstractDao;

@Repository("unitDao")
public class UnitDao extends AbstractDao implements IUnitDao{

	@Override
	public boolean persist(Unit unit) {
		return this.persist(unit);
	}

	@Override
	public boolean delete(Unit unit) {
		unit.setEnabled(false);
		return this.persist(unit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Unit> fetch() {
		Criteria crit = getSession().createCriteria(Unit.class);
		crit.add(Restrictions.eq("isEnabled", true));
		
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}

	
}
