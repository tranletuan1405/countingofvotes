package vn.edu.uit.extra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.POIXMLProperties;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.HWPFOldDocument;
import org.apache.poi.hwpf.model.ListTables;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;

import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.DelegateType;
import vn.edu.uit.models.Unit;
import vn.edu.uit.models.service.delegate.support.AnotationDetecter;
import vn.edu.uit.models.service.delegate.support.EnumDelegateField;

public class App {

	public static void main(String[] args) throws Exception {
		String fileName = "C:\\Users\\tuantran\\Desktop\\test.docx";

		/*
		 * String str = "  @ChucVu[123]";
		 * 
		 * String anotaionStr = getAnotationString(str);
		 * 
		 * System.out.println("Anotation String : " + anotaionStr);
		 * 
		 * String[] anotations = anotaionStr.split("@"); for(int i = 1; i <
		 * anotations.length; i++){ String anotation = anotations[i]; String
		 * linkStr = getLinkString(anotation, DataConfig.ACCESS_STRING); String
		 * field = anotation; if(!linkStr.isEmpty()){ field =
		 * anotation.split(linkStr)[0]; } }
		 */

		List<Delegate> delegates = getByDocument(fileName);
	}

	public static List<Delegate> getByDocument(String filePath) {

		List<Delegate> delegates = new ArrayList<Delegate>(0);

		try {

			FileInputStream is = new FileInputStream(new File(filePath));
			XWPFDocument document = new XWPFDocument(is);

			List<XWPFTable> tables = document.getTables();
			XWPFTable table = null;
			int maxRow = 1;
			for (XWPFTable t : tables) {
				int rowCount = t.getNumberOfRows();
				if (rowCount > maxRow) {
					maxRow = rowCount;
					table = t;
				}
			}

			if (table == null || table.getNumberOfRows() < 1) {
				System.out.println("Table is Null");
				return delegates;
			}

			// Detect title format
			XWPFTableRow title = table.getRow(0);
			XWPFTableRow title2 = null;
			int firstData = 1;
			if (AnotationDetecter.isMerged(table)) {
				title2 = table.getRow(1);
				firstData = 2;
			}

			Map<Integer, AnotationDetecter> format = AnotationDetecter.getFormat(title, title2);
			List<XWPFTableRow> dataRows = table.getRows();

			for(Entry<Integer, AnotationDetecter> entry : format.entrySet()){
				System.out.println("Column : " + (entry.getKey() + 1));
				System.out.println(entry.getValue().toString());
				System.out.println("++++++++++++++++++++++");
				
			}
			/*for (int i = firstData; i < dataRows.size(); i++) {
				List<XWPFTableCell> columns = dataRows.get(0).getTableCells();
				
				for(int j = 0; j < columns.size(); j++){
					XWPFTableCell column = columns.get(i);
					
				}
				
			}*/

			return delegates;
		} catch (FileNotFoundException e) {
			System.out.println("Table is Null");
			e.printStackTrace();
			return delegates;
		} catch (IOException e) {
			System.out.println("Table is Null");
			e.printStackTrace();
			return delegates;
		}
	}

	public static Delegate fillData(){
		Delegate delegate = new Delegate();
		
		
		return delegate;
	}
}
