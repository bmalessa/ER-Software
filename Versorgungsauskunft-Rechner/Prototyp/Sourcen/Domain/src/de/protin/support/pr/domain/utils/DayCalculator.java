package de.protin.support.pr.domain.utils;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import de.protin.support.pr.domain.Person;

public class DayCalculator {
	
	
	/**
	 * Errechnet die Zurechnungszeit in Tagen bei DDU.
	 * Die Tage zählen vom Tad des Eintritt in den Ruhestand bis zum 60. Geburtstag.
	 * @return Anzahl der Tage f.d Zurechnungszeit bei DDU
	 */
	public static long calculateDaysForZurechnungszeitDDU(Person person) {
		
		Date dateOfBirth = person.getDateOfBirth();
		Date dateOfRetirement = person.getDateOfRetirement();
		
		// 1) errechne dateOfBirth + 60 Jahre
		Date targetDate = DateUtils.addYears(dateOfBirth, 60);
		
		// 2) Differenz aus Zurruhesetzungsdatum und 60. Geburtstag errechnen (in milliseconds)
		long betweenDiffInMilliseconds = targetDate.getTime() - dateOfRetirement.getTime();
		
		// 4) Umrechnung in Zurechnungszeit in Tage
		long diffDays = betweenDiffInMilliseconds / (24 * 60 * 60 * 1000);
		
		return diffDays;
	}
	
	
	
	/**
	 * Errechne die Differenz zwischen zwei Datumswerten in Tagen 
	 * @param startDate
	 * @param endDate
	 * @return Anzahl der Tage einer Zeitspanne
	 */
	public static long calculateDaysForTimePeriod(Date startDate, Date endDate) {
		
		// 1) Differenz aus Start und Endedatum errechnen (in milliseconds)
		long betweenDiffInMilliseconds = endDate.getTime() - startDate.getTime();
		// 2) Umrechnung in Zurechnungszeit in Tage
		long diffDays = betweenDiffInMilliseconds / (24 * 60 * 60 * 1000);
		
		return diffDays;
	}
	
	
	
	
}
