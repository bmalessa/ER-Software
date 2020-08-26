package de.protin.support.pr.domain.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PrintUtils {
	
	private static String datePattern = "dd.MM.yyyy";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
	
	private static DecimalFormat decimalFormat = new DecimalFormat("###,###.##", DecimalFormatSymbols.getInstance(Locale.GERMANY));
	
	
	public static String formatValue(float number) {
		return decimalFormat.format(roundNumber(number));
	}
	
	
	public static String formatValue(double number) {
		return decimalFormat.format(roundNumber(number));
	}
	
	
	public static String formatValue(Date date) {
		if(date == null) {
			return new String();
		}
		return simpleDateFormat.format(date);
	}
	
	
	public static float roundNumber(double number) {
		String s = String.valueOf(number);
		return new BigDecimal(s).setScale(2, RoundingMode.HALF_UP).floatValue();
	}
	
}
