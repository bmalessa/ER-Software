package de.protin.support.pr.gui.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.timeperiod.BaseTimePeriod;

public class TimePeriodTemplate extends BaseTimePeriod {

	private static int TEMLATE_TYP = 999;
	
	public TimePeriodTemplate(Date startDate, Date endDate, float factor) {
		super(TEMLATE_TYP,startDate, endDate, factor);
	
	}
	
	public String getZeitart() {
		return "Template";
	}


}
