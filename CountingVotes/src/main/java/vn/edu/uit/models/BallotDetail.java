package vn.edu.uit.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "Ballot_Detail")
public class BallotDetail extends AbstractEntity{

	@Column(name = "is_selected")
	private Boolean isSelected;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "checked_time", columnDefinition = "DATETIME")
	private Date checkedTime;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "candidate_id", nullable = false, referencedColumnName = "id")
	private CandidateDetail candidate;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ballot_id", nullable = false)
	private Ballot ballot;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "selected_code_id", nullable = false)
	private Barcode selectedCode; //It Always exists
	
	@OneToOne(fetch  =FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "unselected_code_id")
	private Barcode unselectedCode; //Use it for non-residue

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Date getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(Date checkedTime) {
		this.checkedTime = checkedTime;
	}

	public CandidateDetail getCandidate() {
		return candidate;
	}

	public void setCandidateDetail(CandidateDetail candidate) {
		this.candidate = candidate;
	}

	public Ballot getBallot() {
		return ballot;
	}

	public void setBallot(Ballot ballot) {
		this.ballot = ballot;
	}

	public Barcode getSelectedCode() {
		return selectedCode;
	}

	public void setSelectedCode(Barcode selectedCode) {
		this.selectedCode = selectedCode;
	}

	public Barcode getUnselectedCode() {
		return unselectedCode;
	}

	public void setUnselectedCode(Barcode unselectedCode) {
		this.unselectedCode = unselectedCode;
	}
	

}
