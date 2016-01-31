package vn.edu.uit.models.service.delegate.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class AnotationDetecter {

	private static final String ACCESS_STRING = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

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

	public static Map<Integer, AnotationColumnDetecter> getFormat(XWPFTableRow title, XWPFTableRow title2) {
		Map<Integer, AnotationColumnDetecter> format = new HashMap<Integer, AnotationColumnDetecter>(0);

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

					AnotationColumnDetecter columnFormat = getColumnFormat(subColumn, mergeField);
					if (columnFormat.getParagraphFormat().size() > 0) {
						format.put(j, columnFormat);
					}
				}

			} else {
				AnotationColumnDetecter columnFormat = getColumnFormat(column, mergeField);
				if (columnFormat.getParagraphFormat().size() > 0) {
					format.put(i + mergeColumns - numOfMerge, columnFormat);
				}
			}
		}

		return format;
	}

	private static AnotationColumnDetecter getColumnFormat(XWPFTableCell column, String mergeField) {
		AnotationColumnDetecter columnDetecter = new AnotationColumnDetecter();
		Map<Integer, AnotationParagraphDetecter> paragraphFormat = new HashMap<Integer, AnotationParagraphDetecter>(0);

		List<XWPFParagraph> paragraphs = column.getParagraphs();
		int paraIndex = 0;
		for (int i = 0; i < paragraphs.size(); i++) {
			AnotationParagraphDetecter paraDetecter = new AnotationParagraphDetecter();

			String text = paragraphs.get(i).getText();
			String anotaionString = getAnotationString(text);
			String[] anotations = anotaionString.split("@");
			boolean isNotNull = false;

			for (int k = 1; k < anotations.length; k++) {

				AnotationField anotationField = new AnotationField();

				String anotation = anotations[k];
				String linkField = getLinkString(anotation, ACCESS_STRING);
				String fieldName = anotation;
				anotationField.setFieldName(fieldName);

				if (linkField != null && !linkField.isEmpty()) {
					fieldName = anotation.split(linkField)[0];
					anotationField.setFieldName(fieldName);
					paraDetecter.setLinkRegexs(linkField);
				}

				if (mergeField != null && !mergeField.isEmpty()) {
					String defaultValue = text.split("@")[0];
					anotationField.setDefaultValue(defaultValue);
					columnDetecter.setMergeField(mergeField);
				}

				if (fieldName != null && !fieldName.isEmpty()) {
					paraDetecter.getFields().add(anotationField);
					isNotNull = true;
				}
			}

			if (isNotNull) {
				paragraphFormat.put(paraIndex, paraDetecter);
				paraIndex++;
			}
		}

		if (paragraphFormat.size() > 0) {
			columnDetecter.setParagraphFormat(paragraphFormat);
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
