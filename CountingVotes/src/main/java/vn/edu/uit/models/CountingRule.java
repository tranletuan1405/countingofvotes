package vn.edu.uit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "Counting_Rule")
public class CountingRule extends AbstractEntity {

	@Column(name = "max_selected", nullable = false)
	private int maxSelected;
	
	@Column(name = "min_percent", nullable = false)
	private int minPercent;

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

}
