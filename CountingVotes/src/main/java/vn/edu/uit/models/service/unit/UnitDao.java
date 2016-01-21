package vn.edu.uit.models.service.unit;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Unit;
import vn.edu.uit.models.common.AbstractDao;
import vn.edu.uit.models.service.voting.VotingDao;

@Repository("unitDao")
public class UnitDao extends AbstractDao implements IUnitDao {

	private static final Logger logger = LoggerFactory.getLogger(UnitDao.class);

	@Override
	public boolean persist(Unit unit) {
		return this.persist(unit);
	}

	@Override
	public boolean delete(Unit unit) {
		try {
			unit.setEnabled(false);
			return this.persist(unit);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
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