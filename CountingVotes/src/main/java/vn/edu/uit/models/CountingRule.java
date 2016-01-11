package vn.edu.uit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "Counting_Rule")
public class CountingRule extends AbstractEntity {

	@Column(name = "max_selected", nullable = false)
	private int maxSelected;
	
	@Column(name = "min_percent", nullable = false)
	private int minPercent;

}
