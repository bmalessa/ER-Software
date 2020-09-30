package de.protin.support.pr.domain.service.impl;

import java.util.Iterator;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.service.IRuhegehaltsfaehigeZeitenService;
import de.protin.support.pr.domain.timeperiod.Para_13_Zurechnungszeit_DDU;
import de.protin.support.pr.domain.utils.TimePeriodDetails;

public class RuhegehaltsfaehigeZeitenServiceImpl implements IRuhegehaltsfaehigeZeitenService {

	
	@Override
	public TimePeriodDetails calculateRuhegehaltsfaehigeDienstzeiten_Para_14(IPension pension) {
		
		boolean zurechnungAlreadyIncluded = false;
		
		TimePeriodDetails ruhegehaltsfaehigeDienstzeiten = new TimePeriodDetails();
		for (Iterator<ITimePeriod> iterator = pension.getTimePeriods().iterator(); iterator.hasNext();) {
			ITimePeriod timePeriod = (ITimePeriod) iterator.next();
			if(timePeriod.getRuhegehaltsfaehigeTage() > 0) {
				TimePeriodDetails timePeriodDetails = new TimePeriodDetails(timePeriod.getRuhegehaltsfaehigeTage());
				ruhegehaltsfaehigeDienstzeiten.add(timePeriodDetails);
			}
						
			if(timePeriod instanceof Para_13_Zurechnungszeit_DDU) {
				zurechnungAlreadyIncluded = true;
			}
		}
		
		if(pension instanceof Dienstunfaehigkeit && !zurechnungAlreadyIncluded) {
			TimePeriodDetails zurechnungzeit = ((Dienstunfaehigkeit)pension).calculateZurechnungzeit_Para_13_BeamtVG();
			ruhegehaltsfaehigeDienstzeiten.add(zurechnungzeit);
			zurechnungAlreadyIncluded = true;
		}
		
		
		return ruhegehaltsfaehigeDienstzeiten;
	}

}
