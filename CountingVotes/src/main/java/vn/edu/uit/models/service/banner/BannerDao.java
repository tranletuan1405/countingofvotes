package vn.edu.uit.models.service.banner;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Banner;
import vn.edu.uit.models.common.AbstractDao;

@Repository("bannerDao")
public class BannerDao extends AbstractDao implements IBannerDao {

	private static final Logger logger = LoggerFactory.getLogger(BannerDao.class);

	@Override
	public boolean persist(Banner banner) {
		return this.saveOrUpdate(banner);
	}

	@Override
	public boolean delete(Banner banner) {
		try {
			banner.setEnabled(false);
			return this.persist(banner);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Banner> fetch() {
		Criteria crit = getSession().createCriteria(Banner.class);
		crit.add(Restrictions.eq("isEnabled", true));

		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}

}
