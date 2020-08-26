package de.protin.support.pr.domain.utils;

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
	
	public TimePeriodDetails() {}
	

	public TimePeriodDetails(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		
		calculateYearsAndDays();
	}
	
	
	public TimePeriodDetails(long anzahlTage) {
		calculateYearsAndDays(anzahlTage);
	}

	
	
	public long getPeriodInMonths() {
		return (years * 12) + (days / DAYS_IN_MONTH);
	}
	
	public long getDaysInMonths() {
		return days / DAYS_IN_MONTH;
	}
	
	public long getAmountOfDays() {
		return (years * 365) + days; 
	}
	
	
	public float getFactorizedAmountOfDays(float factor) {
		return ((years * 365) + days) * factor; 
	}
	
	
	public void add(TimePeriodDetails timePeriod) {
		this.years += timePeriod.getYears();
		this.days += timePeriod.getDays();
	}
	
	
	public void add(long years, long days) {
		this.years += years;
		this.days += days;
	}


	public void addMonths(int months) {
		this.years += months / 12;
		months = months % 12;
		this.days = months * DAYS_IN_MONTH;
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
	}
	
	private void resetNumbers() {
		this.years = 0;
		this.days = 0;
	}
	
	
	
	private void calculateYearsAndDays() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		int startYear = calendar.get(Calendar.YEAR);
		
		calendar.clear();
		calendar.setTime(endDate);
		int endYear = calendar.get(Calendar.YEAR);
		
		this.years = (endYear) - (startYear+1);
		
		long fragmentInDays = DateUtils.getFragmentInDays(this.startDate, Calendar.YEAR);
		fragmentInDays--; // der erste tag zählt auch mit
		long daysInStartYear = DAYS_IN_YEAR - fragmentInDays;
		
		long daysInEndYear = DateUtils.getFragmentInDays(this.endDate, Calendar.YEAR);
		
		
		
		long restDays = daysInStartYear + daysInEndYear;
		if(restDays < DAYS_IN_YEAR) {
			this.days = restDays;
		}
		else {
			this.days = restDays - DAYS_IN_YEAR;
			this.years = this.years + 1;
		}
	}
	
	

	private void calculateYearsAndDays(long amountOfDays) {
		
		if(amountOfDays < DAYS_IN_YEAR) {
			this.days = amountOfDays;
		}
		else {
			this.days = amountOfDays % DAYS_IN_YEAR;
			this.years = amountOfDays / DAYS_IN_YEAR;
		}
	}
	
}
