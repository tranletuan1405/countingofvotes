package vn.edu.uit.models.json;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.SupportMethods;
import vn.edu.uit.models.Delegate;

public class CandidateJson {

	private long id;
	private String name;
	private String dateOfBirth;
	private String gender;
	private String unitName;
	private String position;
	private String countingCodeImagePath;
	private boolean isResidual;
	
	
	public CandidateJson(Delegate delegate, boolean isResidual){
		if (delegate.getId() != null)
			this.setId(delegate.getId());
		this.setName(delegate.getName());
		this.setDateOfBirth(delegate.getDateOfBirth());
		this.setGender(delegate.getGender());
		this.setPosition(delegate.getPosition());

		try {
			this.setCountingCodeImagePath(delegate.getCountingCode().getImagePath());
		} catch (Exception e) {
		}

		try {
			this.setUnitName(delegate.getUnit().getName());
		} catch (Exception e) {
		}
		
		this.setResidual(isResidual);
	}
	
	
	
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getCountingCodeImagePath() {
		return countingCodeImagePath;
	}
	public void setCountingCodeImagePath(String countingCodeImagePath) {
		this.countingCodeImagePath = countingCodeImagePath;
	}







	public boolean isResidual() {
		return isResidual;
	}







	public void setResidual(boolean isResidual) {
		this.isResidual = isResidual;
	}
	
	
	

}
