package vn.edu.uit.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import vn.edu.uit.models.common.AbstractEntity;

@Entity
@Table(name = "Delegate")
public class Delegate extends AbstractEntity {

	@Column(name = "ordinal", columnDefinition="VARCHAR(10)")
	private String ordinal;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "gender", columnDefinition = "varchar(20)")
	private String gender;

	@Column(name = "date_of_birth")
	private String dateOfBirth;

	@Column(name = "place_of_birth")
	private String placeOfBirth;

	@Column(name = "ethnic")
	private String ethnic;

	@Column(name = "religion")
	private String religion;

	@Column(name = "date_of_youth_union")
	private String dateOfYouthUnion;

	@Column(name = "date_of_party")
	private String dateOfParty;

	@Column(name = "field_of_study")
	private String fieldOfStudy;

	@Column(name = "political_theory")
	private String politicalTheory;

	@Column(name = "position")
	private String position;
	
	@Column(name = "achievement", columnDefinition = "varchar(2000)")
	private String achievement;

	@Column(name = "image_path")
	private String imagePath;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "unit_id")
	private Unit unit;

	@Temporal(TemporalType.DATE)
	@Column(name = "arivalTime", columnDefinition = "DATETIME")
	private Date arivalTime;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "hash_code_id", nullable = false)
	private Barcode hashCode;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "congress_id", nullable = false)
	private Congress congress;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "delegate")
	private Set<Candidate> candidateDetails = new HashSet<Candidate>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getDateOfYouthUnion() {
		return dateOfYouthUnion;
	}

	public void setDateOfYouthUnion(String dateOfYouthUnion) {
		this.dateOfYouthUnion = dateOfYouthUnion;
	}

	public String getDateOfParty() {
		return dateOfParty;
	}

	public void setDateOfParty(String dateOfParty) {
		this.dateOfParty = dateOfParty;
	}

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

	public String getPoliticalTheory() {
		return politicalTheory;
	}

	public void setPoliticalTheory(String politicalTheory) {
		this.politicalTheory = politicalTheory;
	}

	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public Date getArivalTime() {
		return arivalTime;
	}

	public void setArivalTime(Date arivalTime) {
		this.arivalTime = arivalTime;
	}

	public Barcode getHashCode() {
		return hashCode;
	}

	public void setHashCode(Barcode hashCode) {
		this.hashCode = hashCode;
	}

	public Congress getCongress() {
		return congress;
	}

	public void setCongress(Congress congress) {
		this.congress = congress;
	}

	public Set<Candidate> getCandidateDetails() {
		return candidateDetails;
	}

	public void setCandidateDetails(Set<Candidate> candidateDetails) {
		this.candidateDetails = candidateDetails;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(String ordinal) {
		this.ordinal = ordinal;
	}

}
