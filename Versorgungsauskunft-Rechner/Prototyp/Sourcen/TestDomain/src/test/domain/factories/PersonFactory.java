package test.domain.factories;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.utils.MonthCalculator;

public class PersonFactory {
	
	private static PersonFactory instance;

	private PersonFactory() {
	}

	public static PersonFactory getInstance() {
		if (null == instance) {
			instance = new PersonFactory();
		}
		return instance;
	}
	
	
	public Person setupPerson() {
		Person person = new Person("Mustermann", "Hans");
		person.setAnrede("Herr");
		//Geburtstag
		Date birthDate = DateFactory.getDate("12.07.1963");
		person.setDateOfBirth(birthDate);
		//Begründung des Beamtenverhältnis
		Date substantiationDate = DateFactory.getDate("01.09.1981");
		person.setDateOfSubstantiation(substantiationDate);
		
		return person;
	}
	
	
	
	public Person setupPersonForRegelaltersgrenze() {
		Person person = setupPerson();
		//Regelaltersgrenze
		int month = MonthCalculator.calculateMonthForRegelaltersgrenze(person);
		calculateDatesForRetirement(person, month);
		return person;
	}
	
	public Person setupPersonForSpecialLegalAltersgrenze(int years) {
		Person person = setupPersonForRegelaltersgrenze();
		
		Date dateOfBirth = person.getDateOfBirth();
		Date dateOfSpecialRetirement = DateUtils.addYears(dateOfBirth, years);
		
		//person.setDateOfSpecialLegalRetirement(dateOfSpecialRetirement);
		person.setBesondereAltersgrenze(true);
		person.setBesondereAltersgrenzeJahre(years);
		
		person.setDateOfRequestRetirement(dateOfSpecialRetirement);
		person.setDateOfRetirement(dateOfSpecialRetirement);
		return person;
	}
	
	
	
	public Person setupPersonForAntragsaltersgrenze() {
		Person person = setupPerson();
		int month = MonthCalculator.calculateMonthForRegelaltersgrenze(person);
		calculateDatesForRetirement(person, month);
		//Antragsaltersgrenze -- hier mit 63 Jahren angenommen
		Date dateOfBirth = person.getDateOfBirth();
		Date dateOfRequestRetirement = DateUtils.addYears(dateOfBirth, 63);
		dateOfRequestRetirement = calculateEndOfMonth(dateOfRequestRetirement);
		
		dateOfRequestRetirement = DateUtils.addDays(dateOfRequestRetirement, 1);
		person.setDateOfRequestRetirement(dateOfRequestRetirement);
		person.setDateOfRetirement(dateOfRequestRetirement);
		return person;
	}
	

	public Person setupPersonForEngagierterRuhestand() {
		Person person = setupPerson();
		//Antragsaltersgrenze -- hier mit 63 Jahren angenommen
		int month = 55 * 12;
		calculateDatesForRetirement(person, month);
		return person;
	}

	
	public Person setupPersonForDienstunfaehigkeit() {
		Person person = setupPerson();
		//Dienstunfähigkeit -- hier mit 52 Jahren angenommen
		int month = 52 * 12;
		calculateDatesForRetirement(person, month);
		return person;
	}

	
	public Person setupPersonForVorruhestand() {
		Person person = setupPerson();
		int month = MonthCalculator.calculateMonthForRegelaltersgrenze(person);
		calculateDatesForRetirement(person, month);
		//Antragsaltersgrenze -- hier mit 63 Jahren angenommen
		Date dateOfBirth = person.getDateOfBirth();
		Date dateOfRequestRetirement = DateUtils.addYears(dateOfBirth, 65);
		dateOfRequestRetirement = calculateEndOfMonth(dateOfRequestRetirement);
		person.setDateOfRetirement(dateOfRequestRetirement);
		return person;
	}
	
	
	private void calculateDatesForRetirement(Person person, int month) {
		Date calculatedDateOfLegalRetirement = DateUtils.addMonths(person.getDateOfBirth(), month);
		
		person.setDateOfLegalRetirement(calculatedDateOfLegalRetirement);
		
		//Letzte Tag des Monats erechnen
		Date ceilingDate = DateUtils.ceiling(calculatedDateOfLegalRetirement,Calendar.MONTH);
		Date calculatedDateOfRetirement = DateUtils.addDays(ceilingDate, -1);
		person.setDateOfRetirement(calculatedDateOfRetirement);
	}
	
	private Date calculateEndOfMonth(Date date) {
		
		//Letzte Tag des Monats erechnen
		Date ceilingDate = DateUtils.ceiling(date,Calendar.MONTH);
		date = DateUtils.addDays(ceilingDate, -1);
		return date;
	}

}
