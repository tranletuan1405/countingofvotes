package vn.edu.uit.models.json;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.DelegateType;
import vn.edu.uit.models.Unit;

public class DelegateJson {

	private String ordinal;
	private String name;
	private String placeOfBirth;
	private String address;
	private String dateOfBirth;
	private String gender;
	private String ethnic;
	private String religion;
	private String dateOfYouthUnion;
	private String dateOfPartyPreparatory;
	private String dateOfPartyOfficial;
	private String position;
	private String unitName;
	private String typeName;
	private String note;

	private String codeImage;
	private String codeContent;

	public DelegateJson(Delegate delegate) {
		this.setOrdinal(delegate.getOrdinal());
		this.setName(delegate.getName());
		this.setPlaceOfBirth(delegate.getPlaceOfBirth());
		this.setAddress(delegate.getAddress());
		this.setDateOfBirth(delegate.getDateOfBirth());
		this.setGender(delegate.getGender());
		this.setEthnic(delegate.getEthnic());
		this.setReligion(delegate.getReligion());
		this.setDateOfYouthUnion(delegate.getDateOfYouthUnion());
		this.setDateOfPartyPreparatory(delegate.getDateOfPartyPreparatory());
		this.setDateOfPartyOfficial(delegate.getDateOfPartyOfficial());
		this.setPosition(delegate.getPosition());

		if (delegate.getHashCode() != null) {
			this.setCodeImage(delegate.getHashCode().getImagePath());
			this.setCodeContent(delegate.getHashCode().getContent());
		}

		try {
			this.setUnitName(delegate.getUnit().getName());
		} catch (Exception e) {
		}

		try {
			this.setTypeName(delegate.getType().getShortName());
		} catch (Exception e) {
		}

		this.setNote(delegate.getNote());
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(String ordinal) {
		this.ordinal = ordinal;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	public String getCodeContent() {
		return codeContent;
	}

	public void setCodeContent(String codeContent) {
		this.codeContent = codeContent;
	}

	public String getCodeImage() {
		return codeImage;
	}

	public void setCodeImage(String codeImage) {
		this.codeImage = codeImage;
	}
}
