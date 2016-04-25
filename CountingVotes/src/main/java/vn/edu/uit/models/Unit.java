package vn.edu.uit.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "Unit")
public class Unit extends AbstractEntity {

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "short_name", columnDefinition = "varchar(50)")
	private String shortName;

	@OneToOne(targetEntity = Congress.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "congress_id", updatable = false)
	private Congress congress;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Congress getCongress() {
		return congress;
	}

	public void setCongress(Congress congress) {
		this.congress = congress;
	}

}
