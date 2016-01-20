package vn.edu.uit.models.datatable;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.models.Congress;
import vn.edu.uit.models.json.CongressJson;

public class CongressDatatable {

	private List<CongressJson> data = new ArrayList<CongressJson>(0);

	public CongressDatatable(List<Congress> list) {
		this.init(list);
	}

	public void init(List<Congress> list) {
		if (list == null || list.size() == 0)
			return;
		this.data.clear();
		for (Congress c : list) {
			this.data.add(new CongressJson(c));
		}
	}

	public List<CongressJson> getData() {
		return data;
	}

	public void setData(List<CongressJson> data) {
		this.data = data;
	}
}
