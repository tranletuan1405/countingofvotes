package vn.edu.uit.extra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
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

import vn.edu.uit.models.Barcode;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.DelegateType;
import vn.edu.uit.models.Unit;
import vn.edu.uit.models.service.delegate.support.AnotationParagraphDetecter;
import vn.edu.uit.models.service.delegate.support.AnotationColumnDetecter;
import vn.edu.uit.models.service.delegate.support.AnotationDetecter;
import vn.edu.uit.models.service.delegate.support.AnotationField;
import vn.edu.uit.models.service.delegate.support.EnumDelegateField;

public class App {

	public static void main(String[] args) throws Exception {
		String fileName = "C:\\Users\\ASUSLP\\Desktop\\test.docx";
		String imagePath = "test.png";
		BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
		
		Barcode barcode = new Barcode();
		String content = SupportMethods.getUID();
		String congressPath = SupportMethods.dateToString(new Date(), "dd-MM-yyyy") + "_" + SupportMethods.getUID();
		
		barcode.setContent(content);
		String directory = barcodeGenerator.generateQR("barcode/" + congressPath, SupportMethods.getUID(), content, 320);
		
		InputStream is = new FileInputStream(directory);
		byte[] image = IOUtils.toByteArray(is);
		
		System.out.println("Folder Name : " + congressPath);
		System.out.println("Barcode " + directory);
		System.out.println("Image " + image.toString());
		
		
	}
	
}
