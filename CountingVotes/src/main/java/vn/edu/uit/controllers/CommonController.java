package vn.edu.uit.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.omg.CORBA_2_3.portable.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.edu.uit.extra.DataConfig;

@Controller
public class CommonController {

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@RequestMapping(value = "img/barcode/{congress_path}/{name}",  produces = MediaType.IMAGE_PNG_VALUE)
	@ResponseBody
	public byte[] getImage(
			@PathVariable("congress_path") String path,
			@PathVariable("name") String name) throws IOException {
		
		String fullPath = DataConfig.BARCODE_DIRECTORY + path + "/" + name + ".png";
		FileInputStream is = new FileInputStream(fullPath);
		byte[] image = IOUtils.toByteArray(is);
		
		return image;
	}
}
