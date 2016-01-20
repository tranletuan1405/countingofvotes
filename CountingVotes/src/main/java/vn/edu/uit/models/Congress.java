package vn.edu.uit.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import vn.edu.uit.models.common.AbstractEntity;


@Entity
@Table(name = "Congress")
public class Congress extends AbstractEntity{

	@Column(name = "name", nullable = false)
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "start_time", columnDefinition = "DATETIME")
	private Date startTime;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "end_time", columnDefinition = "DATETIME")
	private Date endTime;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "congress")
	private Set<Unit> units = new HashSet<Unit>(0);

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "congress") 
	private Set<Banner> banners = new HashSet<Banner>(0);
	
	@OneToMany(fetch = FetchType.EAGER , mappedBy = "congress")
	private Set<Voting> votings = new HashSet<Voting>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "congress")
	private Set<Delegate> delegates = new HashSet<Delegate>(0);
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Set<Unit> getUnits() {
		return units;
	}

	public void setUnits(Set<Unit> units) {
		this.units = units;
	}

	public Set<Banner> getBanners() {
		return banners;
	}

	public void setBanners(Set<Banner> banners) {
		this.banners = banners;
	}

	public Set<Voting> getVotings() {
		return votings;
	}

	public void setVotings(Set<Voting> votings) {
		this.votings = votings;
	}

	public Set<Delegate> getDelegates() {
		return delegates;
	}

	public void setDelegates(Set<Delegate> delegates) {
		this.delegates = delegates;
	}
	
}
