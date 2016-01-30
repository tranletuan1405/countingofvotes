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

public final class AnotationDetecter {

	private static final String ACCESS_STRING = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

	private String mergeField;
	private List<AnotationField> fields = new ArrayList<AnotationField>();

	public String toString() {

		String result = "";
		if(mergeField != null && !mergeField.isEmpty()){
			result += "Merge field : " + mergeField + "\n";
		}
		
		for(int i = 0; i < fields.size(); i++){
			result += fields.get(i).toString();
		}
		
		return result;
	}

	public String getMergeField() {
		return mergeField;
	}

	public void setMergeField(String mergeField) {
		this.mergeField = mergeField;
	}

	public List<AnotationField> getFields() {
		return fields;
	}

	public void setFields(List<AnotationField> fields) {
		this.fields = fields;
	}

	// Support Methods
	public static boolean isMerged(XWPFTable table) {

		if (table.getNumberOfRows() < 1)
			return false;

		List<XWPFTableCell> columns = table.getRows().get(0).getTableCells();
		for (XWPFTableCell column : columns) {
			boolean isMerage = !column.getCTTc().getTcPr().isSetVMerge();
			if (isMerage) {
				return true;
			}
		}

		return false;
	}

	public static Map<Integer, AnotationDetecter> getFormat(XWPFTableRow title, XWPFTableRow title2) {
		Map<Integer, AnotationDetecter> format = new HashMap<Integer, AnotationDetecter>();

		List<XWPFTableCell> columns = title.getTableCells();
		int mergeColumns = 0;
		int numOfMerge = 0;

		for (int i = 0; i < columns.size(); i++) {
			XWPFTableCell column = columns.get(i);

			boolean isMerage = !column.getCTTc().getTcPr().isSetVMerge();
			String mergeField = "";
			if (isMerage && title2 != null) {
				String[] mergeAnotation = getAnotationString(column.getText()).split("@");
				mergeField = mergeAnotation[mergeAnotation.length - 1];

				numOfMerge++;
				mergeColumns += Integer.valueOf(column.getCTTc().getTcPr().getGridSpan().getVal().toString());
				List<XWPFTableCell> subColumns = title2.getTableCells();

				for (int j = i; j < mergeColumns + i; j++) {
					XWPFTableCell subColumn = subColumns.get(j);

					Map<Integer, AnotationDetecter> detecters = getColumnField(subColumn, j, mergeField);
					if (detecters.size() > 0) {
						// format.addAll(detecters);
						format.putAll(detecters);
					}
				}

			} else {
				Map<Integer, AnotationDetecter> detecters = getColumnField(column, i + mergeColumns - numOfMerge,
						mergeField);
				if (detecters.size() > 0) {
					format.putAll(detecters);
				}
			}
		}

		return format;
	}

	private static Map<Integer, AnotationDetecter> getColumnField(XWPFTableCell column, int index, String mergeField) {
		Map<Integer, AnotationDetecter> columnDetecter = new HashMap<Integer, AnotationDetecter>(0);
		AnotationDetecter detecter = new AnotationDetecter();

		List<XWPFParagraph> paragraphs = column.getParagraphs();
		int paragraphIndex = 0;

		for (int j = 0; j < paragraphs.size(); j++) {

			String text = paragraphs.get(j).getText();
			String anotaionString = getAnotationString(text);
			String[] anotations = anotaionString.split("@");
			boolean isNotNull = false;

			for (int k = 1; k < anotations.length; k++) {

				AnotationField anotationField = new AnotationField();
				anotationField.setParagraphIndex(paragraphIndex);

				String anotation = anotations[k];
				String linkField = getLinkString(anotation, ACCESS_STRING);
				String fieldName = anotation;
				anotationField.setFieldName(fieldName);
				
				if (!linkField.isEmpty()) {
					fieldName = anotation.split(linkField)[0];
					anotationField.setFieldName(fieldName);
					anotationField.setLinkField(linkField);
				}

				if (mergeField != null && !mergeField.isEmpty()) {
					String defaultValue = text.split("@")[0];
					detecter.setMergeField(mergeField);
					anotationField.setDefaultValue(defaultValue);
				}

				if (fieldName != null && !fieldName.isEmpty()) {
					detecter.getFields().add(anotationField);
					isNotNull = true;
				}
			}

			if (isNotNull) {
				paragraphIndex++;
			}
		}

		if (detecter.getFields().size() > 0) {
			columnDetecter.put(index, detecter);
		}

		return columnDetecter;

	}

	private static String getAnotationString(String str) {

		int index = str.indexOf('@');
		if (index >= 0) {
			return str.substring(index, str.length());
		}

		return "";

	}

	private static String getLinkString(String source, String checkingSource) {

		for (int i = 0; i < source.length(); i++) {
			int c = source.charAt(i);
			int index = checkingSource.indexOf(c);

			if (index < 0) {
				return source.substring(i, source.length());
			}
		}

		return "";
	}

}
