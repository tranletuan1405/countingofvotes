package vn.edu.uit.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import vn.edu.uit.models.common.AbstractEntity;


@Entity
@Table(name = "delegate_type")
public class DelegateType extends AbstractEntity {

	private String typeName;
	private String shortName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
