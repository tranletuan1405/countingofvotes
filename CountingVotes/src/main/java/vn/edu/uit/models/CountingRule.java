package vn.edu.uit.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "Counting_Rule")
public class CountingRule extends AbstractEntity {

	
	@Column(name = "max_selected", nullable = false)
	private int maxSelected;
	
	@Column(name = "min_percent", nullable = false)
	private int minPercent = 50;
	
	@OneToOne(targetEntity = Barcode.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "submit_code")
	private Barcode submitCode;

	
	
	
	
	public int getMaxSelected() {
		return maxSelected;
	}

	public void setMaxSelected(int maxSelected) {
		this.maxSelected = maxSelected;
	}

	public int getMinPercent() {
		return minPercent;
	}

	public void setMinPercent(int minPercent) {
		this.minPercent = minPercent;
	}

	public Barcode getSubmitCode() {
		return submitCode;
	}

	public void setSubmitCode(Barcode submitCode) {
		this.submitCode = submitCode;
	}


}
