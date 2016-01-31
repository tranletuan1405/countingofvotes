package vn.edu.uit.models.service.delegate.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

public class AnotationColumnDetecter {

	private String mergeField;
	private Map<Integer, AnotationParagraphDetecter> paragraphFormat = new HashMap<Integer, AnotationParagraphDetecter>(
			0);

	public String getMergeField() {
		return mergeField;
	}

	public void setMergeField(String mergeField) {
		this.mergeField = mergeField;
	}

	public Map<Integer, AnotationParagraphDetecter> getParagraphFormat() {
		return paragraphFormat;
	}

	public void setParagraphFormat(Map<Integer, AnotationParagraphDetecter> paragraphFormats) {
		this.paragraphFormat = paragraphFormats;
	}

	public String toString() {
		String result = "";
		if (mergeField != null && !mergeField.isEmpty())
			result += "Merge Field : " + mergeField + "\n";

		for (Entry<Integer, AnotationParagraphDetecter> entry : paragraphFormat.entrySet()) {
			result += "Paragraph : " + (entry.getKey() + 1) + "\n";
			result += entry.getValue().toString();
		}

		return result;
	}

}
