package vn.edu.uit.extra;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.krysalis.barcode4j.impl.codabar.CodabarBean;
import org.krysalis.barcode4j.impl.datamatrix.DataMatrixBean;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.QRCode;

public class BarcodeGenerator {
	private static final String CHARSET = "UTF-8";

	public String generateQR(String imagePath, String imageName, String content, int width) {
		String filePath = imagePath + "\\" + imageName + ".png";

		try {

			File path = new File(imagePath);

			if (!path.exists()) {
				String.valueOf(path.mkdirs());
			}

			File file = new File(filePath);
			OutputStream stream = new FileOutputStream(file);

			if (!file.exists()) {
				file.createNewFile();
				filePath = file.getAbsolutePath();
			}

			Map hintMap = new HashMap();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

			BitMatrix matrix = new MultiFormatWriter().encode(new String(content.getBytes(CHARSET), CHARSET),
					BarcodeFormat.QR_CODE, width, width, hintMap);

			MatrixToImageWriter.writeToStream(matrix, "PNG", stream);

		} catch (Exception e) {
			return "";
		}

		return filePath;
	}

}
