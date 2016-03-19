package vn.edu.uit.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.SupportMethods;
import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "Voting")
public class Voting extends AbstractEntity {

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "version", nullable = false)
	private int version;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	private Date endTime;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "congress_id", nullable = false)
	private Congress congress;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "counting_rule_id")
	private CountingRule countingRule;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "voting")
	private Set<Ballot> ballots = new HashSet<Ballot>(0);

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "voting")
	private Set<Candidate> candidates = new HashSet<Candidate>(0);

	
	
	
	
	
	
	public Voting() {

	}

	public Voting(Voting v) {
		this.setName(v.getName());
		this.setVersion(v.getVersion() + 1);
		this.setCongress(v.getCongress());
		this.setCountingRule(v.getCountingRule());
		this.setCandidates(v.getCandidates());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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

	public Congress getCongress() {
		return congress;
	}

	public void setCongress(Congress congress) {
		this.congress = congress;
	}

	public CountingRule getCountingRule() {
		return countingRule;
	}

	public void setCountingRule(CountingRule countingRule) {
		this.countingRule = countingRule;
	}

	public Set<Ballot> getBallots() {
		return ballots;
	}

	public void setBallots(Set<Ballot> ballots) {
		this.ballots = ballots;
	}

	public Set<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(Set<Candidate> candidates) {
		this.candidates = candidates;
	}
	
	public String getStartTimeString() {
		return SupportMethods.dateToString(this.startTime, DataConfig.DATE_TIME_FORMAT);
	}
	
	public String getEndTimeString() {
		return SupportMethods.dateToString(this.endTime, DataConfig.DATE_TIME_FORMAT);
	}
}
