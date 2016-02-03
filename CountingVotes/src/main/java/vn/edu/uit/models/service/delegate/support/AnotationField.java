package vn.edu.uit.models.service.delegate.support;

public class AnotationField {

	private String fieldName;
	private String defaultValue;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String toString() {
		String result = "";
		result += "Field Name : " + fieldName + "\n";
		
		if (defaultValue != null && !defaultValue.isEmpty())
			result += "Default Value : " + defaultValue + "\n";
		
		return result;
	}

}
