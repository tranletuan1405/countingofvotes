package vn.edu.uit.models.service.delegate.support;

public enum EnumDelegateField {
	Ordinal ("stt"),
	Name ("hoten"),
	Gender ("gioitinh"),
	DateOfBirth ("namsinh"),
	PlaceOfBirth ("noisinh"),
	Ethnic ("dantoc"),
	Religion ("tongiao"),
	DateOfYouthUnion ("ngayvaodoan"),
	DateOfParty ("ngayvaodang"),
	FieldOfStudy ("chuyenmon"),
	PoliticalTheory ("llct"),
	Position ("chucvu"),
	Achievement ("thanhtich"),
	Unit ("donvi");
	
	private final String fieldDescription;
	private EnumDelegateField(String value){
		this.fieldDescription = value;
	}
	
	public String getDescription(){
		return this.fieldDescription;
	}
	
	public static EnumDelegateField getEnumByDescription(String description) {
		for (EnumDelegateField e : values()) {
			if (e.getDescription().compareToIgnoreCase(description) == 0) {
				return e;
			}
		}

		return null;
	}
}
