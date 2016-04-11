package vn.edu.uit.models.json;

import vn.edu.uit.models.Candidate;

public class CandidateJson {
	private long id;
	private String ordinal;
	private String name;
	private String dateOfBirth;
	private String gender;
	private String unitName;
	private String position;
	private String selectedCodeImage;
	private String unselectedCodeImage;

	public CandidateJson(Candidate candidate) {
		this.setId(candidate.getId());
		this.setName(candidate.getDelegate().getName());
		this.setDateOfBirth(candidate.getDelegate().getDateOfBirth());
		this.setGender(candidate.getDelegate().getGender());
		try {
			this.setUnitName(candidate.getDelegate().getUnit().getName());
		} catch (Exception e) {
		}
		this.setPosition(candidate.getDelegate().getPosition());

		try {
			this.setSelectedCodeImage(candidate.getSelectedBarcode().getImagePath());
		} catch (Exception e) {
		}

		try {
			this.setUnselectedCodeImage(candidate.getUnselectedBarcode().getImagePath());
		} catch (Exception e) {
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(String ordinal) {
		this.ordinal = ordinal;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelectedCodeImage() {
		return selectedCodeImage;
	}

	public void setSelectedCodeImage(String selectedCodeImage) {
		this.selectedCodeImage = selectedCodeImage;
	}

	public String getUnselectedCodeImage() {
		return unselectedCodeImage;
	}

	public void setUnselectedCodeImage(String unselectedCodeImage) {
		this.unselectedCodeImage = unselectedCodeImage;
	}
}
