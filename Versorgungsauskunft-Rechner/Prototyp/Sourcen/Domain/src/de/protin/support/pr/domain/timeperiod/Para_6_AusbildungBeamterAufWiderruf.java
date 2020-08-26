package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;

public class Para_6_AusbildungBeamterAufWiderruf extends BaseTimePeriod {

	public static final String ZEITART = "Ausbildung im Beamtenverhältnis auf Widerruf (§6 BeamtVG)";
	
	public Para_6_AusbildungBeamterAufWiderruf(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.AUSBILDUNG_BEAMTER_WIDERRUF_PARA_6, startDate, endDate, factor);
	}

	public String getZeitart() {
		return ZEITART;
	}
}
