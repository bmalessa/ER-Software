package de.protin.support.pr.domain.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

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
}