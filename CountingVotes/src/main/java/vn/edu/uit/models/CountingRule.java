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
	private int minPercent;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "start_code_id", nullable = false)
	private Barcode startCode;
	
	@Column(name = "voting_key", nullable = false, updatable = false, columnDefinition = "varchar(24)")
	private String votingKey;

	@Column(name = "voting_iv", nullable = false, updatable = false, columnDefinition = "varchar(8)")
	private String votingIv;

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

	public Barcode getStartCode() {
		return startCode;
	}

	public void setStartCode(Barcode startCode) {
		this.startCode = startCode;
	}

	public String getVotingKey() {
		return votingKey;
	}

	public void setVotingKey(String votingKey) {
		this.votingKey = votingKey;
	}

	public String getVotingIv() {
		return votingIv;
	}

	public void setVotingIv(String votingIv) {
		this.votingIv = votingIv;
	}

}
