package vn.edu.uit.models.json;

import java.util.ArrayList;
import java.util.List;

public class StaffList {

	private List<Staff> data = new ArrayList<Staff>();

	public StaffList(){
		
	}
	
	public List<Staff> getData() {
		return data;
	}

	public void setData(List<Staff> data) {
		this.data = data;
	}

	
	
}
