package vn.edu.uit.models.service.banner;

import java.util.List;

import vn.edu.uit.models.Banner;

public interface IBannerDao {

	boolean persist(Banner banner);
	
	boolean delete(Banner banner);
	
	List<Banner> fetch();
	
	
}
