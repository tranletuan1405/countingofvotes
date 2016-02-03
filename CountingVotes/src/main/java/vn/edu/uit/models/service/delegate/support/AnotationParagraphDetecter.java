package vn.edu.uit.models.service.delegate.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public final class AnotationParagraphDetecter {

	private List<AnotationField> fields = new ArrayList<AnotationField>();
	private String linkRegexs = "";

	public List<AnotationField> getFields() {
		return fields;
	}

	public void setFields(List<AnotationField> fields) {
		this.fields = fields;
	}

	public String getLinkRegexs() {
		return linkRegexs;
	}

	public void setLinkRegexs(String linkRegexs) {
		if (this.linkRegexs != null && !this.linkRegexs.isEmpty()) {
			this.linkRegexs += "|(" + linkRegexs + ")";
		} else {
			this.linkRegexs = "(" + linkRegexs + ")";
		}
	}

	// Support Methods
	public String toString() {
		String result = "";
		if (linkRegexs != null && !linkRegexs.isEmpty())
			result += "Regexs : " + linkRegexs + "\n";

		for (int i = 0; i < fields.size(); i++) {
			result += fields.get(i).toString();
		}

		return result;
	}
}
