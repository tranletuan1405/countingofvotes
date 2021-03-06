package vn.edu.uit.models.service.banner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.models.Banner;
import vn.edu.uit.models.service.congress.CongressService;

@Service("bannerService")
@Transactional
public class BannerService implements IBannerDao{
	
	@Autowired
	private IBannerDao bannerDao;
	
	 public boolean persist(Banner banner){
		 return bannerDao.persist(banner);
	 }
	
	public List<Banner> fetch(){
		return bannerDao.fetch();
	}

	@Override
	public boolean delete(Banner banner) {
		// TODO Auto-generated method stub
		return false;
	}
}
