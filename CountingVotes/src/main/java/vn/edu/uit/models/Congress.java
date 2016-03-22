package vn.edu.uit.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.SupportMethods;
import vn.edu.uit.models.common.AbstractEntity;


@Entity
@Table(name = "Congress")
public class Congress extends AbstractEntity{

	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	private Date startTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	private Date endTime;
	
	@Column(name = "logo")
	private String logo;
	
	@Column(name = "banner")
	private String banner;
	
	@OneToMany(targetEntity = Unit.class, fetch = FetchType.EAGER, mappedBy = "congress")
	private Set<Unit> units = new HashSet<Unit>(0);
	
	@OneToMany(targetEntity = Voting.class, fetch = FetchType.EAGER , mappedBy = "congress")
	private Set<Voting> votings = new HashSet<Voting>(0);
	
	@OneToMany(targetEntity = Delegate.class, fetch = FetchType.EAGER, mappedBy = "congress")
	private Set<Delegate> delegates = new HashSet<Delegate>(0);
	
	
	@Column(name = "congress_key", nullable = false, updatable = false, columnDefinition = "varchar(24)")
	private String congressKey;

	@Column(name = "congress_iv", nullable = false, updatable = false, columnDefinition = "varchar(8)")
	private String congressIv;
	
	@Column(name = "congress_path", nullable = false, updatable = false)
	private String congressPath;
	
	
	
	
	
	
	
	
	
	
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

	public String getCongressKey() {
		return congressKey;
	}

	public void setCongressKey(String congressKey) {
		this.congressKey = congressKey;
	}

	public String getCongressIv() {
		return congressIv;
	}

	public void setCongressIv(String congressIv) {
		this.congressIv = congressIv;
	}

	public String getCongressPath() {
		return congressPath;
	}

	public void setCongressPath(String congressPath) {
		this.congressPath = congressPath;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getStartTimeString() {
		return SupportMethods.dateToString(startTime, DataConfig.DATE_TIME_FORMAT);
	}
	
	public String getEndTimeString() {
		return SupportMethods.dateToString(endTime, DataConfig.DATE_TIME_FORMAT);
	}
}
