package vn.edu.uit.extra;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class SupportMethods {

	public static String getRandomString(int length) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

	// Covert String to Date
	public static Date toDate(String dateString, String dateFormat) throws ParseException {
		try {
			DateFormat df = new SimpleDateFormat(dateFormat);
			Date rsDate = df.parse(dateString);
			return rsDate;
		} catch (Exception e) {
			return null;
		}
	}

	// Convert Date to Date
	public static Date toDate(Date date, String dateFormat) {
		try {
			DateFormat df = new SimpleDateFormat(dateFormat);
			String dateString = df.format(date);
			Date rsDate = df.parse(dateString);
			return rsDate;
		} catch (ParseException e) {
			return null;
		}
	}

	// Convert Date to String
	public static String dateToString(Date date, String dateFormat) {
		if(date == null) return "";
		DateFormat df = new SimpleDateFormat(dateFormat);
		String rsDate = df.format(date);
		return rsDate;
	}

	public static String getUID() {
		return UUID.randomUUID().toString();
	}
}
