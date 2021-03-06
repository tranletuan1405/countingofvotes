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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(targetEntity = Voting.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "voting_id", nullable = false, updatable = false)
	private Voting voting;

	@ManyToOne(targetEntity = Delegate.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "delegate_id", nullable = false, updatable = false)
	private Delegate delegate;
	
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

}
