package vn.edu.uit.models.service.delegate.support;

public enum EnumDelegateType {
	DBDN ("ÐBÐN"),
	DBCD ("ÐBCÐ"),
	DBBC ("ÐBBC"),
	DBDK ("ÐBDK");
	
	private final String description;
	
	private EnumDelegateType(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static EnumDelegateType getEnumByDescription(String description) {
		for (EnumDelegateType e : values()) {
			if (e.getDescription().compareTo(description) == 0) {
				return e;
			}
		}

		return null;
	}
}
