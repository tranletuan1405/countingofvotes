package vn.edu.uit.models.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;
	
	@Column(name = "is_enabled", nullable = false)
	protected boolean isEnabled = true;
	
	@Column(name = "is_updatable", nullable = false)
	protected boolean isUpdatable = true;
	
	@Column(name = "note", columnDefinition = "varchar(500)")
	protected String note;


	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
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

	public boolean isUpdatable() {
		return isUpdatable;
	}

	public void setUpdatable(boolean isUpdatable) {
		this.isUpdatable = isUpdatable;
	}
	
}
