package vn.edu.uit.models;

import java.io.Serializable;

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


@Entity
@Table(name = "Candidate")
public class Candidate implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "voting_id", nullable = false)
	private Voting voting;
	
	@Id 
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "delegate_id", nullable = false)
	private Delegate delegate;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "selected_barcode", nullable = false)
	private Barcode selectedBarcode;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "unselected_barcode", nullable = false)
	private Barcode unselectedBarcode;
	
	@Column(name = "note", columnDefinition = "varchar(500)")
	private String note;
	
	@Column(name = "is_enabled", nullable = false)
	private boolean isEnabled = true;
	
	@Column(name = "is_updatable", nullable = false)
	private boolean isUpdatable = true;

	
	
	
	
	
	
	
	
	
	
	public Voting getVoting() {
		return voting;
	}

	public void setVoting(Voting voting) {
		this.voting = voting;
	}

	public Delegate getDelegate() {
		return delegate;
	}

	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public long getId() {
		return id;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isUpdatable() {
		return isUpdatable;
	}

	public void setUpdatable(boolean isUpdatable) {
		this.isUpdatable = isUpdatable;
	}

	public Barcode getSelectedBarcode() {
		return selectedBarcode;
	}

	public void setSelectedBarcode(Barcode selectedBarcode) {
		this.selectedBarcode = selectedBarcode;
	}

	public Barcode getUnselectedBarcode() {
		return unselectedBarcode;
	}

	public void setUnselectedBarcode(Barcode unselectedBarcode) {
		this.unselectedBarcode = unselectedBarcode;
	}
}
