package vn.edu.uit.models.service.delegate.support;

public class AnotationField {

	private String fieldName;
	private int paragraphIndex;
	private String defaultValue;
	private String linkField;

	public String getLinkField() {
		return linkField;
	}

	public void setLinkField(String linkField) {
		this.linkField = linkField;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getParagraphIndex() {
		return paragraphIndex;
	}

	public void setParagraphIndex(int paragraphIndex) {
		this.paragraphIndex = paragraphIndex;
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
		result += "Paragraph Index : " + paragraphIndex + "\n";
		
		if (defaultValue != null && !defaultValue.isEmpty())
			result += "Default Value : " + defaultValue + "\n";
		if (linkField != null && !linkField.isEmpty())
			result += "Link Field : " + linkField + "\n";
		
		return result;
	}

}
