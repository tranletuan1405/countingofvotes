package vn.edu.uit.models.service.delegate.support;

public enum EnumDelegateField {
	Ordinal("stt"), // STT
	Name("hoten"), // Ho Ten
	PlaceOfBirth("quequan"), // QueQuan
	Address("NoiO"), // Noi O
	DateOfBirth("namsinh"), // Nam Sinh
	Gender("gioitinh"), // Gioi Tinh
	Ethnic("dantoc"), // Dan Toc
	Religion("tongiao"), // Ton Giao
	DateOfYouthUnion("ngayvaodoan"), // Ngay Vao Doan
	DateOfPartyPreparatory("dangdubi"), // Ngay Vao Dang Du Bi
	DateOfPartyOfficial("dangchinhthuc"), // Ngay Vao Dang Chinh Thuc
	Position("chucvu"), // Chuc Vu
	Unit("donvi"), // Don Vi
	Type("cocau"), // Co Cau
	Note("ghichu") //Ghi Chu
;
/*	Educational("hocvan"), // Hoc Van
	Specialty("chuyenmon"), // Chuyen Mon
	PoliticalTheory("llct"), // Ly Luan Chinh Tri
	ForeignLanguages("ngoaingu"), // Ngoai Ngu
	InformationTechnology("tinhoc"); // Tin Hoc
*/
	private final String fieldDescription;

	private EnumDelegateField(String value) {
		this.fieldDescription = value;
	}

	public String getDescription() {
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
