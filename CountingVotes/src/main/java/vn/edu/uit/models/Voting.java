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

	@Column(name = "version")
	private int version = 1;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	private Date endTime;
	
	@Column(name = "total_ballot")
	private int totalBallot = 0;
	
	@Column(name = "num_of_valid_ballot")
	private int numOfValidBallot = 0;

	@OneToOne(targetEntity = Congress.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "congress_id", updatable = false)
	private Congress congress;

	@OneToOne(targetEntity = CountingRule.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "counting_rule_id")
	private CountingRule countingRule;


	@Column(name = "ballot_pattern", columnDefinition = "VARCHAR(20000)")
	private String ballotPattern;
	
	
	public Voting() {

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
	
	public String getStartTimeString() {
		return SupportMethods.dateToString(this.startTime, DataConfig.DATE_TIME_FORMAT);
	}
	
	public String getEndTimeString() {
		return SupportMethods.dateToString(this.endTime, DataConfig.DATE_TIME_FORMAT);
	}

	public int getTotalBallot() {
		return totalBallot;
	}

	public void setTotalBallot(int totalBallot) {
		this.totalBallot = totalBallot;
	}

	public int getNumOfValidBallot() {
		return numOfValidBallot;
	}

	public void setNumOfValidBallot(int numOfValidBallot) {
		this.numOfValidBallot = numOfValidBallot;
	}

	public String getBallotPattern() {
		return ballotPattern;
	}

	public void setBallotPattern(String ballotPattern) {
		this.ballotPattern = ballotPattern;
	}

}
