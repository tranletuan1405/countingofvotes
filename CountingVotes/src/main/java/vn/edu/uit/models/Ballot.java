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

import com.sun.org.glassfish.gmbal.ManagedAttribute;

import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "Ballot")
public class Ballot extends AbstractEntity {

	@Temporal(TemporalType.DATE)
	@Column(name = "start_time", columnDefinition = "DATETIME")
	private Date startTime;

	@Temporal(TemporalType.DATE)
	@Column(name = "end_time", columnDefinition = "DATETIME")
	private Date endTime;

	@Column(name = "is_valid")
	private Boolean isValid;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "hash_code_id", nullable = false)
	private Barcode hashCode;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "voting_id", nullable = false)
	private Voting voting;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "ballot")
	private Set<BallotDetail> details = new HashSet<BallotDetail>(0);

	public Ballot() {

	}

	public Ballot(Ballot b) {
		this.setVoting(b.getVoting());
		this.setHashCode(b.getHashCode());
		this.copyBallotDetail(b.getDetails());
	}

	public void copyBallotDetail(Set<BallotDetail> details) {
		for (BallotDetail bd : details) {
			this.details.add(new BallotDetail(bd));
		}
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

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Barcode getHashCode() {
		return hashCode;
	}

	public void setHashCode(Barcode hashCode) {
		this.hashCode = hashCode;
	}

	public Voting getVoting() {
		return voting;
	}

	public void setVoting(Voting voting) {
		this.voting = voting;
	}

	public Set<BallotDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<BallotDetail> details) {
		this.details = details;
	}

}
