package vn.edu.uit.extra;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

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
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class App {

	public static void main(String[] args) throws Exception {
		String fileName = "C:\\Users\\tuantran\\Desktop\\test.docx";

		FileInputStream is = new FileInputStream(new File(fileName));
		/*XWPFDocument document = new XWPFDocument(is);

		List<XWPFTable> table = document.getTables();
		
		if(table.size() > 0){
			XWPFTable t = table.get(0);
			List<XWPFTableRow> rows =  t.getRows();
			
			for(int i = 0; i < rows.size(); i++){
				XWPFTableRow row = rows.get(i);
				
				List<XWPFTableCell> cells = row.getTableCells();
				for(int j = 0; j < cells.size(); j++){
					XWPFTableCell cell = cells.get(j);
					System.out.print(cell.getText() + " ");
					
				}
				
				System.out.println("");
			}
		}*/
		
		HWPFDocument doc = new HWPFDocument(is);
		ListTables tables = doc.getListTables();
		
		

	}

}
