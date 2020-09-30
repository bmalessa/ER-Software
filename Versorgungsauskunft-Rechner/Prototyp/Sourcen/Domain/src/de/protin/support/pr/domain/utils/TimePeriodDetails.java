package de.protin.support.pr.domain.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;

public class TimePeriodDetails {

	public static int DAYS_IN_YEAR = 365;
	public static int DAYS_IN_MONTH = 30; // wird hier mal mit 30 angesetzt
	
	private Date startDate;
	private Date endDate;
	private long years;
	private long days;
	private long daysForTimePeriod;
	
	public TimePeriodDetails() {}
	

	public TimePeriodDetails(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		calculateYearsAndDays();
	}
	
	public TimePeriodDetails(long amonutOfYears, long amountOfDays) {
		this.years = amonutOfYears;
		this.days = amountOfDays;
		this.daysForTimePeriod = (years * DAYS_IN_YEAR) + days;
	}
	
	public TimePeriodDetails(long anzahlTage) {
		this.daysForTimePeriod = anzahlTage;
		calculateYearsAndDays(anzahlTage);
	}

	
	public long getAmountOfDays() {
		if(this.daysForTimePeriod == 0 && this.startDate != null && this.getEndDate() != null) {
			calculateYearsAndDays();
		}
		return this.daysForTimePeriod;
	}
	
	
	public float getFactorizedAmountOfDays(float factor) {
		return ((years * DAYS_IN_YEAR) + days) * factor; 
	}
	
	
	public void add(TimePeriodDetails timePeriod) {
		long newAmounOfDays = this.daysForTimePeriod + timePeriod.getAmountOfDays();
		calculateYearsAndDays(newAmounOfDays);
	}
	

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public long getYears() {
		return years;
	}

	public long getDays() {
		return days;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		resetNumbers();
	}

	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		resetNumbers();
	}
	
	public void setStartDateWithoutReset(Date startDate) {
		this.startDate = startDate;
	}

	
	public void setEndDateWithoutReset(Date endDate) {
		this.endDate = endDate;
	}
	
	public void setYears(long years) {
		this.years = years;
		resetDates();
	}


	public void setDays(long days) {
		this.days = days;
		resetDates();
	}
	
	
	private void resetDates() {
		this.startDate = null;
		this.endDate = null;
		resetNumbers();
	}
	
	private void resetNumbers() {
		this.daysForTimePeriod = 0;
		this.years = 0;
		this.days = 0;
	}
	
	
	
	private void calculateYearsAndDays() {
		LocalDate startDateInclusive = convertToLocalDate(this.startDate);
		LocalDate enddateExclusive = convertToLocalDate(DateUtils.addDays(this.endDate,1));
		
		long daysInPeriod = ChronoUnit.DAYS.between(startDateInclusive,enddateExclusive);
		
		//Schaltjahre in vollständigen Jahren müssen ebenfalls mit 365 und nicht mit 366 tagen gezählt werden
		//deshalb diese Tage hier nochmal separate ermitteln.
		long leafYearsInTimeperiod = calculateLeafYears();
		
		// und müssen deshalb vorher abgezogen werden
		daysInPeriod -= leafYearsInTimeperiod;
		calculateYearsAndDays(daysInPeriod);
	}
	
	

	private void calculateYearsAndDays(long amountOfDays) {
		resetNumbers();
		
		this.daysForTimePeriod = amountOfDays;
		
		if(this.daysForTimePeriod < DAYS_IN_YEAR) {
			this.days = amountOfDays;
		}
		else {
			this.days = amountOfDays % DAYS_IN_YEAR;
			this.years = amountOfDays / DAYS_IN_YEAR;
		}
	}

	
	private LocalDate convertToLocalDate(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	
	private long calculateLeafYears() {
		int startYear;
		int endYear;
		long result = 0;
		GregorianCalendar calendar = new GregorianCalendar();
		
		if(DateUtils.getFragmentInDays(this.startDate, Calendar.YEAR) < 365) {
			Date start = DateUtils.ceiling(this.startDate,Calendar.YEAR);
			start = DateUtils.addDays(start, 1);
			calendar.setTime(start);
			startYear = calendar.get(Calendar.YEAR);
		}
		else {
			calendar.setTime(this.startDate);
			startYear = calendar.get(Calendar.YEAR);
		}
		
		if(DateUtils.getFragmentInDays(this.endDate, Calendar.YEAR) < 365) {
			Date end = DateUtils.addYears(this.endDate, -1);
			calendar.setTime(end);
			endYear = calendar.get(Calendar.YEAR);
		}
		else {
			calendar.setTime(this.endDate);
			endYear = calendar.get(Calendar.YEAR);
		}
		
		for (int i = startYear; i < endYear+1; i++) {
			if(calendar.isLeapYear(i)) {
				result++;
			}
		}
		
		return result;
	}
}
