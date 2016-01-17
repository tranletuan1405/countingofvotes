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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;

	@Id
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "candidate_id", nullable = false, referencedColumnName = "id")
	private Candidate candidate;
	
	@Id
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ballot_id", nullable = false)
	private Ballot ballot;
		
	@Column(name = "is_selected")
	private Boolean isSelected;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "checked_time", columnDefinition = "DATETIME")
	private Date checkedTime;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "selected_code_id", nullable = false)
	private Barcode selectedCode; //It Always exists
	
	@OneToOne(fetch  =FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "unselected_code_id")
	private Barcode unselectedCode; //Use it for non-residue

	@Column(name = "is_enabled", nullable = false)
	private boolean isEnabled = true;
	
	@Column(name = "note", columnDefinition = "varchar(500)")
	private String note;
	
	public BallotDetail(){
		
	}
	
	public BallotDetail(BallotDetail bd){
		this.setCandidate(bd.getCandidate());
		this.setSelectedCode(bd.getSelectedCode());
		this.setUnselectedCode(bd.getUnselectedCode());
	}
			
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Candidate getCandidate() {
		return candidate;
	}
	

}
