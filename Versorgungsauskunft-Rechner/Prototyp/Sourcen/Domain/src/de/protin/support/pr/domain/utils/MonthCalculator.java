package de.protin.support.pr.domain.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import de.protin.support.pr.domain.Person;

public class MonthCalculator {
	
	/**
	 * Errechnet die Regelaltersgrenze
	 * @return Anzahl der Monate , z.b 66 Jahre + 10 Monate = 66*12 + 10
	 */
	public static int calculateMonthForRegelaltersgrenze(Person person) {
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(person.getDateOfBirth());
		int year = calendar.get(Calendar.YEAR);
		
		if(year >= 1964) {
			return 67*12;
		}
		else if(year == 1956) {
			return 65*12 + 10;
		}
		else if(year == 1957) {
			return 65*12 + 11;
		}
		else if(year == 1958) {
			return 66*12;
		}
		else if(year == 1959) {
			return 66*12 + 2;
		}
		else if(year == 1960) {
			return 66*12 + 4;
		}
		else if(year == 1961) {
			return 66*12 + 6;
		}
		else if(year == 1962) {
			return 66*12 + 8;
		}
		else if(year == 1963) {
			return 66*12 + 10;
		}
		
		return 67*12;
	}
	
	
	
	public static int calculateMonthForAltersgrenzeSchwerbehinderung(Person person) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(person.getDateOfBirth());
		int year = calendar.get(Calendar.YEAR);
		
		if(year >= 1964) {
			return 65*12;
		}
		else if(year == 1956) {
			return 63*12 + 10;
		}
		else if(year == 1957) {
			return 63*12 + 11;
		}
		else if(year == 1958) {
			return 64*12;
		}
		else if(year == 1959) {
			return 64*12 + 2;
		}
		else if(year == 1960) {
			return 64*12 + 4;
		}
		else if(year == 1961) {
			return 64*12 + 6;
		}
		else if(year == 1962) {
			return 64*12 + 8;
		}
		else if(year == 1963) {
			return 64*12 + 10;
		}
		
		return 65*12;
	}
	
	
	
	/**
	 * Errechnet die Regelaltersgrenze
	 * @return Anzahl der Monate , z.b 66 Jahre + 10 Monate = 66*12 + 10
	 */
	public static int calculateMonthForAntragsalterSchwerbehinderung(Person person) {
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(person.getDateOfBirth());
		int year = calendar.get(Calendar.YEAR);
		
		if(year >= 1964) {
			return 62*12;
		}
		else if(year == 1956) {
			return 60*12 + 10;
		}
		else if(year == 1957) {
			return 60*12 + 11;
		}
		else if(year == 1958) {
			return 61*12;
		}
		else if(year == 1959) {
			return 61*12 + 2;
		}
		else if(year == 1960) {
			return 61*12 + 4;
		}
		else if(year == 1961) {
			return 61*12 + 6;
		}
		else if(year == 1962) {
			return 61*12 + 8;
		}
		else if(year == 1963) {
			return 61*12 + 10;
		}
		
		return 63*12;
	}
	
	
	/**
	 * Wird benötigt zur Berechnung der Anzahl an Lebensmonate, die benötigt wird bis eine besondere Altersgrenze
	 * erreicht ist. Die besondere Altersgrenze bei bestimmten Berufsgruppen kann von der gesetzlichen Altersgrenze 
	 * abweichen.
	 *  
	 * @param person
	 * @return
	 */
	public static int calculateMonthForBesondereAltersgrenze(Person person) {
		return monthsBetween(person.getDateOfBirth(), person.getDateOfSpecialLegalRetirement());
	}
	
	
	public static int calculateMonthBetweenBirthdateAndTargetDate(Person person, Date targetDate) {
		return monthsBetween(person.getDateOfBirth(), targetDate);
	}
	
	
	/**
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int monthsBetween(Date startDate, Date endDate) {
	    final Calendar d1 = Calendar.getInstance();
	    d1.setTime(startDate);
	    final Calendar d2 = Calendar.getInstance();
	    d2.setTime(endDate);
	    int diff = (d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR)) * 12 + d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
	    return diff;
	}






	
	
	

}
