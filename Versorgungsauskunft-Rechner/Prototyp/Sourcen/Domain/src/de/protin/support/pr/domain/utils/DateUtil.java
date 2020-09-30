package de.protin.support.pr.domain.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

	private static String[] acceptedFormats = {"dd.MM.yyyy"};
	
	public static Date getDate(String dateInString) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;
		try {
			date = sdf.parse(dateInString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	public static Date parseDateString(String dateInString) throws ParseException{
		 return DateUtils.parseDate(dateInString, acceptedFormats );
	}
	
}