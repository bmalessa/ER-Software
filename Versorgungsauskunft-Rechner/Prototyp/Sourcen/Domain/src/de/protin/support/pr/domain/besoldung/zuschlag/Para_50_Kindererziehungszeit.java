package de.protin.support.pr.domain.besoldung.zuschlag;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.timeperiod.BaseTimePeriod;
import de.protin.support.pr.domain.utils.Constants;
import de.protin.support.pr.domain.utils.DateUtil;
import de.protin.support.pr.domain.utils.MonthCalculator;

public class Para_50_Kindererziehungszeit extends BaseTimePeriod {

	public static final String ZEITART = "Kindererziehungszeiten (§50 BeamtVG)";
	
	public static final String VOLLER_KEZ_ANSPRUCH = "Voller Anspruch nach BeamtVG (keine Anwartschaft in gesetzlicher RV)";
	public static final String TEIL_KEZ_ANSPRUCH = "Teil Anspruch nach BeamtVG (Teile der Anwartschaft in gesetzlicher RV)";
	public static final String KEIN_KEZ_ANSPRUCH = "Kein Anspruch nach BeamtVG (Anwartschaft in gesetzlicher RV vorhanden)";
	
	private String childName;
	private Date birthDate;
	private int legalMonths;
	private int zulageMonths;
	private boolean vollerAnspruch;
	private String anspruch;
	
	private Date date_19911231 = DateUtil.getDate("31.12.1991");
	private Date date_19920101 = DateUtil.getDate("01.01.1992");
	
	/**
	 * Voller Anspruch, wenn keine Versicherungspflicht in Rentenversicherung bestand oder kein Anspruch auf Rentenversicherung vorhanden ist.
	 * Kinder nach dem 31.12.1991 geboren: 36 Monate
	 * Kinder vor dem 31.12.1991 geboren:  
	 * 
	 * 
	 * 
	 * @param childName
	 * @param birthdate		
	 * @param startDate		nur nötig, wenn keine voller Anspruch vorhanden ist , ansonsten kann NULL übergeben werden
	 * @param endDate		nur nötig, wenn keine voller Anspruch vorhanden ist , ansonsten kann NULL übergeben werden
	 * @param vollerAnspruch - wenn keine Pflichtversicherung in der Rentenversichung vorhanden war oder die ANwartschaft nicht errecht wurde.
	 * 						   alle 36 Monate (nach 31.12.1991 geboren) bzw. 12 Monate (vor 01.01.1992 geboren) werden anerkannt 								
	 */
	public Para_50_Kindererziehungszeit(String childName, Date birthdate, Date startDate, Date endDate, boolean vollerAnspruch) {
		super(ITimePeriod.KINDERERZIEHUNGUNGSZEITEN_PARA_50, startDate, endDate, 0.0f);
		
		this.childName = childName;
		this.birthDate = birthdate;
		this.vollerAnspruch = vollerAnspruch;
		calculateLegalMonths();
		calculateDates();
	}


	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
		calculateLegalMonths();
		calculateDates();
	}

	public int getLegalMonths() {
		return legalMonths;
	}

	public void setLegalMonths(int months) {
		this.legalMonths = months;
	}

	public int getZulageMonths() {
		if(zulageMonths == 0) {
			zulageMonths = legalMonths;
		}
		return zulageMonths;
	}

	public void setZulageMonths(int zulageMonths) {
		this.zulageMonths = zulageMonths;
	}
	
	
	
	public boolean isVollerAnspruch() {
		return vollerAnspruch;
	}


	public void setVollerAnspruch(boolean vollerAnspruch) {
		this.vollerAnspruch = vollerAnspruch;
		calculateLegalMonths();
	}


	private void calculateDates() {
		Date ceilingDate = DateUtils.ceiling(birthDate,Calendar.MONTH);
		this.setStartDate(ceilingDate);
		this.setEndDate(DateUtils.addMonths(this.getStartDate(), legalMonths));
	}

	protected void calculateLegalMonths() {
		if(this.vollerAnspruch) {
			this.factor = 1.0f;
			
			//Kind nach 31.12.1991 geboren
			if(date_19911231.before(this.birthDate)) {
				this.legalMonths = Constants.KINDERERZIEHUNG_MAX_ANZAHL_MONATE_NACH_31_12_1991_PARA_50a;
			}
			//Kind vor 01.01.1992 geboren
			else if(date_19920101.after(this.birthDate)) {
				this.legalMonths = Constants.KINDERERZIEHUNG_MAX_ANZAHL_MONATE_VOR_01_01_1992_PARA_50a;
			}
		}
		else {
			int monthsBetween = MonthCalculator.monthsBetween(startDate, endDate);
			//Kind nach 31.12.1991 geboren
			if(date_19911231.before(this.birthDate)) {
				if(monthsBetween < Constants.KINDERERZIEHUNG_MAX_ANZAHL_MONATE_NACH_31_12_1991_PARA_50a) {
					this.legalMonths = monthsBetween;
				}
				else {
					this.legalMonths = Constants.KINDERERZIEHUNG_MAX_ANZAHL_MONATE_NACH_31_12_1991_PARA_50a;
				}
			}
			//Kind vor 01.01.1992 geboren
			else if(date_19920101.after(this.birthDate)) {
				if(monthsBetween < Constants.KINDERERZIEHUNG_MAX_ANZAHL_MONATE_VOR_01_01_1992_PARA_50a) {
					this.legalMonths = monthsBetween;
				}
				else {
					this.legalMonths = Constants.KINDERERZIEHUNG_MAX_ANZAHL_MONATE_VOR_01_01_1992_PARA_50a;
				}
			}
		}
	}


	@Override
	public String getZeitart() {
		return ZEITART;
	}


	public String getAnspruch() {
		return anspruch;
	}


	public void setAnspruch(String anspruch) {
		this.anspruch = anspruch;
	}

	

	
}
