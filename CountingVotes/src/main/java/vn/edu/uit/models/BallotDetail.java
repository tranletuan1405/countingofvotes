package vn.edu.uit.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "Ballot_Detail")
public class BallotDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;

	
	@OneToOne(targetEntity=Candidate.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "candidate_id", nullable = false)
	private Candidate candidate;
	
	@ManyToOne(targetEntity=Ballot.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "ballot_id", nullable = false, updatable = false)
	private Ballot ballot;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checked_time")
	private Date checkedTime;

	@Column(name = "is_enabled", nullable = false)
	private boolean isEnabled = true;
	
	public BallotDetail(){
		
	}
			
	public Date getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(Date checkedTime) {
		this.checkedTime = checkedTime;
	}


	public Ballot getBallot() {
		return ballot;
	}

	public void setBallot(Ballot ballot) {
		this.ballot = ballot;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public long getId() {
		return id;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Candidate getCandidate() {
		return candidate;
	}
	

}
