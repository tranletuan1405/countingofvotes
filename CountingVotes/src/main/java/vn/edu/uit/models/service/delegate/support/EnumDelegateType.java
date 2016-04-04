package vn.edu.uit.models.service.delegate.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.edu.uit.controllers.CongressDetailController;

public enum EnumDelegateType {
	DBDN ("ĐBĐN"),
	DBCD ("ĐBCĐ"),
	DBBC ("ĐBBC"),
	DBDK ("ĐBDK");
	
	private final String description;
	private static final Logger logger = LoggerFactory.getLogger(EnumDelegateType.class);
	
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
