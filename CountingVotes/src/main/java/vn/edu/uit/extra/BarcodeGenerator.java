package vn.edu.uit.extra;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class BarcodeGenerator {
	private static final String CHARSET = "UTF-8";

	//Code Path : directory + congress path 
	public String generateQR(String congressPath, String imageName, String content, int width) {
		String imagePath = DataConfig.BARCODE_DIRECTORY + congressPath;
		String filePath = congressPath + "/" + imageName + ".png";
		String fullPath = imagePath + "/" + imageName + ".png";

		try {

			File path = new File(imagePath);

			if (!path.exists()) {
				String.valueOf(path.mkdirs());
			}

			File file = new File(fullPath);
			OutputStream stream = new FileOutputStream(file);

			if (!file.exists()) {
				file.createNewFile();
				fullPath = file.getAbsolutePath();
			}

			Map hintMap = new HashMap();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

			BitMatrix matrix = new MultiFormatWriter().encode(new String(content.getBytes(CHARSET), CHARSET),
					BarcodeFormat.QR_CODE, width, width, hintMap);

			MatrixToImageWriter.writeToStream(matrix, "PNG", stream);
			
			stream.flush();
			stream.close();
			System.gc();
		} catch (Exception e) {
			return "";
		}

		return filePath;
	}

	public String generateAztec(String congressPath, String imageName, String content, int width) {

		String imagePath = DataConfig.BARCODE_DIRECTORY + congressPath;
		String filePath = congressPath + "/" + imageName + ".png";
		String fullPath = imagePath + "/" + imageName + ".png";

		try {

			File path = new File(imagePath);

			if (!path.exists()) {
				String.valueOf(path.mkdirs());
			}

			File file = new File(fullPath);
			OutputStream stream = new FileOutputStream(file);

			if (!file.exists()) {
				file.createNewFile();
				fullPath = file.getAbsolutePath();
			}

			Map hintMap = new HashMap();
			hintMap.put(EncodeHintType.AZTEC_LAYERS, -4);

			BitMatrix matrix = new MultiFormatWriter().encode(new String(content.getBytes(CHARSET), CHARSET),
					BarcodeFormat.AZTEC, width, width, hintMap);

			MatrixToImageWriter.writeToStream(matrix, "PNG", stream);
			
			stream.flush();
			stream.close();
			System.gc();
		} catch (Exception e) {
			return "";
		}

		return filePath;
	}

}
