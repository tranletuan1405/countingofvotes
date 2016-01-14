package vn.edu.uit.models.service.banner;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Banner;
import vn.edu.uit.models.common.AbstractDao;

@Repository("bannerDao")
public class BannerDao extends AbstractDao implements IBannerDao {

	@Override
	public boolean persist(Banner banner) {
		return this.persist(banner);
	}
	
	@Override
	public boolean delete(Banner banner) {
		banner.setEnabled(false);
		return this.persist(banner);
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
