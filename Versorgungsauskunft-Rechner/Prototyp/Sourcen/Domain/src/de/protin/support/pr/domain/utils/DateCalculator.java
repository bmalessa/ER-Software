package de.protin.support.pr.domain.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import de.protin.support.pr.domain.Person;

/**
 * 
 * @author Bernd
 *
 */
public class DateCalculator {

	
	/**
	 * Berechnung für eine Ruhestandsberecnung mit Regelaltersgrenze.
	 * Die gesetzliche Altersgrenze für den Beamten (kann varieren z.B. ER mit 55 Jahren , ab Jahrgang 1964 bei 67 Jahren).
	 * Erstmal unabhängig davon, ob eine Schwerbehinderung vorhanden ist. 
	 * 
	 *  
	 * @return Datum der gesetzlichen Altersgrenze
	 */
	public static Date calculateDateOfLegalRetirement(Person person) {
		//Regelaltersgrenze 67 Jahre ab Jahrgang 1964 erreicht?
		int month = MonthCalculator.calculateMonthForRegelaltersgrenze(person);
		Date dateOfLegalRetirement = DateUtils.addMonths(person.getDateOfBirth(), month);
		return ceilingDateToEndOfMonth(dateOfLegalRetirement);
	}
		
	

	public static Date calculateDateOfLegalRetirementForSchwerbehinderung(Person person) {
		int month = MonthCalculator.calculateMonthForAltersgrenzeSchwerbehinderung(person);
		Date dateOfLegalRetirement = DateUtils.addMonths(person.getDateOfBirth(), month);
		return ceilingDateToEndOfMonth(dateOfLegalRetirement);
	}
	
	
	
	/**
	 * Die besondere gesetzliche Altersgrenze für den Beamten (kann abhängig vom Dienstherrn varieren, z.B. Polizei, 
	 * Feuerwehr, Bundeswehr etc.).
	 * Erstmal unabhängig davon, ob eine Schwerbehinderung vorhanden ist. 
	 * 
	 * @return Datum der besonderen gesetzlichen Altersgrenze
	 */
	public static Date calculateDateOfSpecialLegalRetirement(Person person) {
		Date dateOfSpecialLegalRetirement = null;
		
		if(!person.isBesondereAltersgrenze()) {
			dateOfSpecialLegalRetirement = calculateDateOfLegalRetirement(person);
		}
		else {
			int years = person.getBesondereAltersgrenzeJahre();
			dateOfSpecialLegalRetirement = DateUtils.addYears(person.getDateOfBirth(), years);
		}
		
		return ceilingDateToEndOfMonth(dateOfSpecialLegalRetirement);
	}
	
	
	/**
	 * Die Antragsaltersgrenze für den Beamten.
	 * Bei Schwerbehinderung gelten angepasste Zeiten
	 *  
	 * @return Datum der gesetzlichen Altersgrenze
	 */
	public static Date calculateDateOfRequestRetirement(Person person) {
		Date dateOfRequestRetirement = null;
		int minMonthOhneSchwerbehinderung = 63*12;
		int minMonthMitSchwerbehinderung = 62*12;
		
		
		if(person.isSchwerbehindert()) {
			if(person.getDateOfBirth().before(DateUtil.getDate(Constants.ALTERSGRENZE_67_JAHRE_DATUM))) {
				dateOfRequestRetirement = DateUtils.addMonths(person.getDateOfBirth(), MonthCalculator.calculateMonthForAntragsalterSchwerbehinderung(person));
			}
			else {
				dateOfRequestRetirement = DateUtils.addMonths(person.getDateOfBirth(), minMonthMitSchwerbehinderung);
			}
		}
		else {
			dateOfRequestRetirement = DateUtils.addMonths(person.getDateOfBirth(), minMonthOhneSchwerbehinderung);
		}
	
		return ceilingDateToEndOfMonth(dateOfRequestRetirement);
	}
	
	
	public static Date ceilingDateToEndOfMonth(Date date) {
		Date ceilingDate = DateUtils.ceiling(date,Calendar.MONTH);
		return DateUtils.addDays(ceilingDate, -1);
	}


}
