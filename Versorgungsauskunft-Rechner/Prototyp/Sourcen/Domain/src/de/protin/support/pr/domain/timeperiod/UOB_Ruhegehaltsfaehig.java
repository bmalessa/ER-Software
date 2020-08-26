package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;

public class UOB_Ruhegehaltsfaehig 	extends BaseTimePeriod {
		
		public static final String ZEITART = "Urlaub ohne Bezüge - ruhegehaltsfähig";
		
		public UOB_Ruhegehaltsfaehig(Date startDate, Date endDate, float factor) {
			super(ITimePeriod.UOB_RUHEGEHALTSFAEHIG, startDate, endDate, factor);
		}


		public String getZeitart() {
			return ZEITART;
		}
}
