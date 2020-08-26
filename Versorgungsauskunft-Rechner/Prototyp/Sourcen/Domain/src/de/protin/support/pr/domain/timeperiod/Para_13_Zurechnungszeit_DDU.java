package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

public class Para_13_Zurechnungszeit_DDU extends BaseTimePeriod {
	
public static final String ZEITART = "Zurechnungszeit bei Dienstunfähigkeit (§13 BeamtVG)";
public static final int TYPE_ID = 30;

	public Para_13_Zurechnungszeit_DDU(Date startDate, Date endDate, float factor) {
		super(TYPE_ID, startDate, endDate, factor);
	}
	
	public String getZeitart() {
		return ZEITART;
	}
	
}

