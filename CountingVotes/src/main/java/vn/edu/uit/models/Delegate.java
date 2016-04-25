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

	
	//Basic field
	@Column(name = "ordinal", columnDefinition="VARCHAR(10)") //STT
	private String ordinal;
	
	@Column(name = "name", nullable = false) //Ho Ten
	private String name;

	@Column(name = "place_of_birth") //Que Quan
	private String placeOfBirth;
	
	@Column(name = "address") //Noi O
	private String address;
	
	@Column(name = "date_of_birth") //NamSinh
	private String dateOfBirth;
	
	@Column(name = "gender", columnDefinition = "varchar(20)")  //Gioi Tinh
	private String gender;

	@Column(name = "ethnic") //Dan Toc
	private String ethnic;

	@Column(name = "religion") //Ton Giao
	private String religion;

	@Column(name = "date_of_youth_union") //Ngay Vao Doan
	private String dateOfYouthUnion;

	@Column(name = "date_of_party_preparatory") //Ngay Vao Dang Du Bi
	private String dateOfPartyPreparatory;
	
	@Column(name = "date_of_party_offical") //Ngay Vao Dang Chinh Thuc
	private String dateOfPartyOfficial;

	@Column(name = "position") //Chuc Vu
	private String position;
	
	@OneToOne(targetEntity = Unit.class, fetch = FetchType.EAGER) //Don Vi
	@JoinColumn(name = "unit_id")
	private Unit unit;
	
	@OneToOne(targetEntity = DelegateType.class, fetch = FetchType.EAGER) //Co Cau
	@JoinColumn(name = "type_id")
	private DelegateType type;
	
	@OneToOne(targetEntity = DelegateExtension.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL) //Mo rong (danh cho ung cu vien)
	@JoinColumn(name = "extension_id")
	private DelegateExtension extension;
	
	@Column(name = "achievement", columnDefinition = "varchar(2500)")
	private String achievement;
	
	
	
	//Manage Field
	@Column(name = "image_path")
	private String imagePath;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "arivalTime")
	private Date arivalTime;
	
	@Column(name = "attended", columnDefinition = "boolean default false", nullable = false)
	private boolean attended = false;

	@OneToOne(targetEntity = Barcode.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "hash_code_id", nullable = false)
	private Barcode hashCode;

	@OneToOne(targetEntity = Congress.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "congress_id", nullable = false, updatable = false)
	private Congress congress;

	@OneToMany(targetEntity = Candidate.class, mappedBy = "delegate")
	private Set<Candidate> candidateDetails = new HashSet<Candidate>(0);

	
	
	
	
	
	
	///////////////////////////////////
	public String getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(String ordinal) {
		this.ordinal = ordinal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getDateOfPartyPreparatory() {
		return dateOfPartyPreparatory;
	}

	public void setDateOfPartyPreparatory(String dateOfPartyPreparatory) {
		this.dateOfPartyPreparatory = dateOfPartyPreparatory;
	}

	public String getDateOfPartyOfficial() {
		return dateOfPartyOfficial;
	}

	public void setDateOfPartyOfficial(String dateOfPartyOfficial) {
		this.dateOfPartyOfficial = dateOfPartyOfficial;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public DelegateType getType() {
		return type;
	}

	public void setType(DelegateType type) {
		this.type = type;
	}

	public DelegateExtension getExtension() {
		return extension;
	}

	public void setExtension(DelegateExtension extension) {
		this.extension = extension;
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

	public String getAchievement() {
		return achievement;
	}

	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}

	public boolean isAttended() {
		return attended;
	}

	public void setAttended(boolean attended) {
		this.attended = attended;
	}
	
}
