package vn.edu.uit.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "User")
public class User extends AbstractEntity {

	@Column(name = "username", unique = true, nullable = false, columnDefinition="VARCHAR(20)")
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@OneToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
	private Set<Role> role = new HashSet<Role>();	
	
	

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	
	
}
