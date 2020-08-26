package de.protin.support.pr.domain.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;

public class ValidationSupport {
	
	private static String datePattern = "dd.MM.yyyy";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
	
	
	public static ValidationResult validateForNull(String info, Object object, ValidationResult result)  {
		if(object == null) {
			result.addItem(new ValidationItem(info + " darf nicht 'NULL' sein"));
		}
		return result;
	}

	
	public static ValidationResult validateDateString(String info, String dateString, ValidationResult result)  {
		result = validateForNull(info, dateString, result);
		if(!result.isSuccess()) {
			return result;
		}
		try {
			simpleDateFormat.parse(dateString);
		}
		catch (ParseException e) {
			result.addItem(new ValidationItem(info + " muss im Format \"TT.MM.JJJJ\" angegeben werden"));	
		}
		
		return result;
	}
	
	

	public static ValidationResult validateDate(String info, Date date, ValidationResult result) {
		result = validateForNull(info, date, result);
		if(!result.isSuccess()) {
			return result;
		}
		try {
			simpleDateFormat.format(date);
		}
		catch (Exception e) {
			result.addItem(new ValidationItem(info + "Datum muss im Format \"TT.MM.JJJJ\" angegeben werden"));	
		}
		return result;
	}
	
	
	
	public static ValidationResult validateDateForLastDayOfMonth(String info, Date date, ValidationResult result) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		if(calendar.get(Calendar.DAY_OF_MONTH) != actualMaximum) {
			result.addItem(new ValidationItem(info + ": Familienzuschlag darf nicht 'NULL' sein"));
			
		}
		
		return result;
	}


	public static ValidationResult validateDateRange(String info, Date startDate, Date endDate, ValidationResult result) {
		if(endDate.before(startDate)) {
			result.addItem(new ValidationItem(info + "Start: " + simpleDateFormat.format(startDate) + " Ende: " +  simpleDateFormat.format(endDate)));	
		}
		return result;
	}
	
	
	
	public static ValidationResult validateTimePeriodOverlapping(String info, ITimePeriod timePeriod, Set<ITimePeriod> timePeriods, ValidationResult result) {
		for (Iterator<ITimePeriod> innerIterator = timePeriods.iterator(); innerIterator.hasNext();) {
			ITimePeriod innerTimePeriod = (ITimePeriod) innerIterator.next();
			if(innerTimePeriod == timePeriod) {
				continue;
			}
			
			if(timePeriod.getStartDate().before(innerTimePeriod.getEndDate())  &&  timePeriod.getEndDate().after(innerTimePeriod.getStartDate())) {
				result.addItem(new ValidationItem(info + simpleDateFormat.format(timePeriod.getStartDate()) + "-" +  simpleDateFormat.format(timePeriod.getEndDate()) +
						       " <> " + simpleDateFormat.format(innerTimePeriod.getStartDate()) + "-" +  simpleDateFormat.format(innerTimePeriod.getEndDate())));
			}
			
		}
		return result;
	}
	
	

	public static ValidationResult validateRgfDays(String info , ITimePeriod timePeriod, ValidationResult result) {
		if(timePeriod.getRuhegehaltsfaehigeTage() > timePeriod.getAnzahlTage()) {
			result.addItem(new ValidationItem(info + "Start: " + simpleDateFormat.format(timePeriod.getStartDate()) + " Ende: " +  simpleDateFormat.format(timePeriod.getEndDate())));	
		}
		return result;
	}
	
	
	
	
	
	public static ValidationResult validateGrundgehalt(Grundgehalt grundgehalt, ValidationResult result)  {
		if(grundgehalt == null) {
			result.addItem(new ValidationItem("Grundgehalt darf nicht 'NULL' sein"));
		}
		
		String laufbahnOrdnung = grundgehalt.getLaufbahnOrdnung();
		if(laufbahnOrdnung == null) {
			result.addItem(new ValidationItem("Grundgehalt darf nicht 'NULL' sein"));
		}
		else {
			if(!("A".equalsIgnoreCase(laufbahnOrdnung) || "B".equalsIgnoreCase(laufbahnOrdnung))) {
				result.addItem(new ValidationItem("Grundgehalt - zulässige Laufbahnordnung: \"A\" oder \"B\""));	
			}
		}
		
		int besoldungsGruppe = grundgehalt.getBesoldungsGruppe();
		if ("B".equalsIgnoreCase(laufbahnOrdnung) && (besoldungsGruppe < 1 ||  besoldungsGruppe > 11)) {
			result.addItem(new ValidationItem("Grundgehalt - zulässige Besoldungsgruppe in Laufbahnordnung B: 1-11"));
		}
		else if ("A".equalsIgnoreCase(laufbahnOrdnung) && (besoldungsGruppe < 3 ||  besoldungsGruppe > 16)) {
			result.addItem(new ValidationItem("Grundgehalt - zulässige Besoldungsgruppe in Laufbahnordnung A: 3-16"));
		}
		
		int besoldungsStufe = grundgehalt.getBesoldungsStufe();
		if ("A".equalsIgnoreCase(laufbahnOrdnung) && (besoldungsStufe < 1 ||  besoldungsStufe > 8)) {
			result.addItem(new ValidationItem("Grundgehalt - zulässige Besoldungsstufe in Laufbahnordnung A: 1-8"));
		}
		
		return result;
	}
	
	
	public static ValidationResult validateFamilienzuschlag(Familienzuschlag familienzuschlag, ValidationResult result)  {
		if(familienzuschlag == null) {
			result.addItem(new ValidationItem("Familienzuschlag darf nicht 'NULL' sein"));
		}
		
		int stufe = familienzuschlag.getStufe();
		if (stufe < 0 ||  stufe > 2) {
			result.addItem(new ValidationItem("Familienzuschlag - zulässige Stufen: 0, 1, 2"));
		}
		
		int kinder = familienzuschlag.getAnzahlKinder();
		if (kinder < 0) {
			result.addItem(new ValidationItem("Familienzuschlag - zulässige Anzahl der Kinder: 0 -n"));
		}
		return result;
	}


	
	


}
