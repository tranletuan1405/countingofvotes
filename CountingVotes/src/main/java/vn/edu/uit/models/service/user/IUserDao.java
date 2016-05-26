package vn.edu.uit.models.service.user;

import vn.edu.uit.models.User;

public interface IUserDao {

	User fetch(String username);
	
	boolean persist(User user);
	
	int ban(User user);
	
	int unban(User user);
}
