package test.domain.factories;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFactory {

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
