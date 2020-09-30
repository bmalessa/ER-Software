package de.protin.support.pr.domain.utils;

import java.util.Date;
import java.util.Iterator;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.ITimePeriod;

public class TimePeriodCalculator {
	
	private static Date date19920101 = DateUtil.getDate("01.01.1992");
	private static Date date19911231 = DateUtil.getDate("31.12.1991");
	
	/**
	 * Errechne die Anzahl der Jahre und Tage zwischen zwei Datumswerten 
	 * @param startDate
	 * @param endDate
	 * @return Anzahl der Jahre und Tage gekapselt in Objecttype TimePeriodDetails
	 */
	
	
	public static TimePeriodDetails calculateYearsAndDaysForTimePeriod(Date startDate, Date endDate) {
		return new TimePeriodDetails(startDate, endDate);
	}
	

	/**
	 * Errechne die Anzahl der Jahre und Tage zwischen zwei Datumswerten 
	 * @param startDate
	 * @param endDate
	 * @return Anzahl der Tage in einer TimePeriodDetails
	 */
	public static long calculateDaysForTimePeriod(Date startDate, Date endDate) {
		TimePeriodDetails tpd = calculateYearsAndDaysForTimePeriod(startDate, endDate);
		return tpd.getAmountOfDays();
	}
	
	
	
	/**
	 * Errechne die Anzahl der Jahre und Tage anhand der übergebenen Anzahl von Tagen 
	 * @param startDate
	 * @param startDate
	 * @param endDate
	 * @return Anzahl der Jahre und Tage gekapselt in Objecttype TimePeriodDetails
	 */
	public static TimePeriodDetails calculateYearsAndDaysForTimePeriod(long anzahlTage) {
		return new TimePeriodDetails(anzahlTage);
	}



	/**
	 * Ermittelt für die Vergleichsberechnung gem. §85 BeamtVG die rugehaltsfähigen Zeiten, die vor dem 01.01.1992
	 * geleistet wurden.
	 * 
	 * @return
	 */
	public static  TimePeriodDetails getTimePeriodBeforeDate19920101(IPension pension) {
		
		int sumYearsBefore19920101 = 0;
		int restDaysBefore19920101 = 0;
		float factorizedAmountOfDays = 0.0f;
		
		for (Iterator<ITimePeriod> iterator = pension.getTimePeriods().iterator(); iterator.hasNext();) {
			ITimePeriod timePeriod = (ITimePeriod) iterator.next();
			
			if(timePeriod.getRuhegehaltsfaehigeTage() < 1) {
				continue;
			}
			
			if(timePeriod.getStartDate().before(date19920101) && timePeriod.getEndDate().before(date19920101)) {
				TimePeriodDetails timePeriodDetails = new TimePeriodDetails(timePeriod.getStartDate(), timePeriod.getEndDate());
				factorizedAmountOfDays = timePeriodDetails.getFactorizedAmountOfDays(timePeriod.getFactor());
				sumYearsBefore19920101 += factorizedAmountOfDays / TimePeriodDetails.DAYS_IN_YEAR;
				restDaysBefore19920101 += factorizedAmountOfDays % TimePeriodDetails.DAYS_IN_YEAR;
			}
			else if (timePeriod.getStartDate().before(date19920101) && timePeriod.getEndDate().after(date19911231)) {
				TimePeriodDetails timePeriodDetailsBefore = new TimePeriodDetails(timePeriod.getStartDate(), date19911231);
				factorizedAmountOfDays = timePeriodDetailsBefore.getFactorizedAmountOfDays(timePeriod.getFactor());
				sumYearsBefore19920101 += factorizedAmountOfDays / TimePeriodDetails.DAYS_IN_YEAR;
				restDaysBefore19920101 += factorizedAmountOfDays % TimePeriodDetails.DAYS_IN_YEAR;
			}
		}

		
		if(restDaysBefore19920101 >= TimePeriodDetails.DAYS_IN_YEAR) {
			int years = restDaysBefore19920101 / TimePeriodDetails.DAYS_IN_YEAR;
			sumYearsBefore19920101 += years;
			restDaysBefore19920101 = restDaysBefore19920101 - (years * TimePeriodDetails.DAYS_IN_YEAR);
		}
		
		TimePeriodDetails result = new TimePeriodDetails(sumYearsBefore19920101, restDaysBefore19920101);
		return result;
	}
	
	
	/**
	 * Ermittelt für die Vergleichsberechnung gem. §85 BeamtVG die rugehaltsfähigen Zeiten, die nach dem 01.01.1992
	 * geleistet wurden.
	 * 
	 * @return
	 */
	public static  TimePeriodDetails getTimePeriodAfterDate199112231(IPension pension) {
		int sumYearsAfter19911231 = 0;
		int restDaysAfter19911231 = 0;
		
		float factorizedAmountOfDays = 0.0f;
		
		for (Iterator<ITimePeriod> iterator = pension.getTimePeriods().iterator(); iterator.hasNext();) {
			ITimePeriod timePeriod = (ITimePeriod) iterator.next();
			
			if (timePeriod.getStartDate().after(date19911231) && timePeriod.getEndDate().after(date19911231)) {
				TimePeriodDetails timePeriodDetails = new TimePeriodDetails(timePeriod.getStartDate(), timePeriod.getEndDate());
				factorizedAmountOfDays = timePeriodDetails.getFactorizedAmountOfDays(timePeriod.getFactor());
				sumYearsAfter19911231 += factorizedAmountOfDays / TimePeriodDetails.DAYS_IN_YEAR;
				restDaysAfter19911231 += factorizedAmountOfDays % TimePeriodDetails.DAYS_IN_YEAR;
			}
			else if (timePeriod.getStartDate().before(date19920101) && timePeriod.getEndDate().after(date19911231)) {
				TimePeriodDetails timePeriodDetailsAfter = new TimePeriodDetails(date19920101, timePeriod.getEndDate());
				factorizedAmountOfDays = timePeriodDetailsAfter.getFactorizedAmountOfDays(timePeriod.getFactor());
				sumYearsAfter19911231 += factorizedAmountOfDays / TimePeriodDetails.DAYS_IN_YEAR;
				restDaysAfter19911231 += factorizedAmountOfDays % TimePeriodDetails.DAYS_IN_YEAR;
			}
		}

		
	
		if(restDaysAfter19911231 >= TimePeriodDetails.DAYS_IN_YEAR) {
			sumYearsAfter19911231 += restDaysAfter19911231 / TimePeriodDetails.DAYS_IN_YEAR;
			restDaysAfter19911231 = restDaysAfter19911231 % TimePeriodDetails.DAYS_IN_YEAR;
		}
		
		
		TimePeriodDetails result = new TimePeriodDetails(sumYearsAfter19911231, restDaysAfter19911231);
		return result;
	}
	
	
	
	public static  boolean isTimePeriodForDateAvailable(IPension pension, Date date) {
		
		boolean result = false;
		
		for (Iterator<ITimePeriod> iterator = pension.getTimePeriods().iterator(); iterator.hasNext();) {
			ITimePeriod timePeriod = (ITimePeriod) iterator.next();
			
			if(timePeriod.getStartDate().before(date) && timePeriod.getEndDate().after(date)) {
				return true;
			}
			else if (timePeriod.getStartDate().equals(date) || timePeriod.getEndDate().equals(date)) {
				return true;
			}
		}


		return result;
	}
	

}
