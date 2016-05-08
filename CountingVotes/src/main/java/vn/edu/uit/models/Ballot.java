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


	@ManyToOne(targetEntity = Voting.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "voting_id", nullable = false, updatable = false)
	private Voting voting;

	@OneToMany(targetEntity = BallotDetail.class, mappedBy = "ballot")
	private Set<BallotDetail> details = new HashSet<BallotDetail>(0);

	@Column(name = "is_valid")
	private boolean isValid = true;
	
	
	
	
	public Ballot() {

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

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
