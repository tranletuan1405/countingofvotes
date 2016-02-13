package vn.edu.uit.extra;

import java.util.ArrayList;
import java.util.List;

public class ListHolder<x> {

	private List<x> data = new ArrayList<x>(0);

	public List<x> getData() {
		return data;
	}

	public void setData(List<x> data) {
		this.data = data;
	}
	
	
}
