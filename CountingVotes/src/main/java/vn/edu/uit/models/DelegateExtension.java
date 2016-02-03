package vn.edu.uit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "Delegate_Extension")
public class DelegateExtension extends AbstractEntity {

	@Column(name = "educational") //Hoc Van
	private String educational;
	
	@Column(name = "specialty") //Chuyen mon
	private String specialty;
	
	@Column(name = "political_theory") //Ly luan chinh tri
	private String politicalTheory;
	
	@Column(name = "foreign_languages") //Ngoai Ngu
	private String foreignLanguages;
	
	@Column(name = "information_technology") //Tin hoc
	private String informationTechnology;

	
	

	
	///////////////////////
	public String getEducational() {
		return educational;
	}

	public void setEducational(String educational) {
		this.educational = educational;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getPoliticalTheory() {
		return politicalTheory;
	}

	public void setPoliticalTheory(String politicalTheory) {
		this.politicalTheory = politicalTheory;
	}

	public String getForeignLanguages() {
		return foreignLanguages;
	}

	public void setForeignLanguages(String foreignLanguages) {
		this.foreignLanguages = foreignLanguages;
	}

	public String getInformationTechnology() {
		return informationTechnology;
	}

	public void setInformationTechnology(String informationTechnology) {
		this.informationTechnology = informationTechnology;
	}
	
}
