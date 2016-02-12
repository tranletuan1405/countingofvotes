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
import vn.edu.uit.models.service.delegate.support.AnotationParagraphDetecter;
import vn.edu.uit.models.service.delegate.support.AnotationColumnDetecter;
import vn.edu.uit.models.service.delegate.support.AnotationDetecter;
import vn.edu.uit.models.service.delegate.support.AnotationField;
import vn.edu.uit.models.service.delegate.support.EnumDelegateField;

public class App {

	public static void main(String[] args) throws Exception {
		String fileName = "C:\\Users\\tuantran\\Desktop\\test.docx";
		String imagePath = "test.png";

		/*String uniqueID = UUID.randomUUID().toString(); //1da80825-f4be-4887-94ee-d44967efb559
		String congressKey = SupportMethods.getRandomString(24);
		String congressIv = SupportMethods.getRandomString(8);
		
		TripleDes tDes = new TripleDes(congressKey, congressIv);
		String encrypt = tDes.encryptText(uniqueID);
		String decrypt = tDes.decryptText(encrypt);
		
		//df07c5ea-71d0-49bd-b63d-a127a0a247fe
		//020d3144-abd1-4368-adf8-e8d5de265c14
		BarcodeGenerator barcode = new BarcodeGenerator();
		String result = barcode.generateQR("barcode/123123", "test.png", encrypt, 350);
		
		System.out.println(uniqueID);
		System.out.println(encrypt);
		System.out.println(decrypt);
		System.out.println("Create barcode " + result);*/
		
		String path = SupportMethods.dateToString(new Date(), DataConfig.DATE_TIME_FORMAT) + "_" + SupportMethods.getUID();
		System.out.println(path);
	}
	
}
