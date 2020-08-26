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
			TimePeriodDetails timePeriodDetails = new TimePeriodDetails(timePeriod.getRuhegehaltsfaehigeTage());
			ruhegehaltsfaehigeDienstzeiten.add(timePeriodDetails);
						
			if(timePeriod instanceof Para_13_Zurechnungszeit_DDU) {
				zurechnungAlreadyIncluded = true;
			}
		}
		
		if(pension instanceof Dienstunfaehigkeit && !zurechnungAlreadyIncluded) {
			TimePeriodDetails zurechnungzeit = ((Dienstunfaehigkeit)pension).calculateZurechnungzeit_Para_13_BeamtVG();
			ruhegehaltsfaehigeDienstzeiten.add(zurechnungzeit);
			zurechnungAlreadyIncluded = true;
		}
		
			
		/* Für Kindererziehungszeiten wird mittlerweile nur noch ein Kindererziehungszuschlag gezahlt.
		 * Diese KEZ-Zeiten werden daher hier auch nicht berücksichtigt 
		TimePeriodDetails kindererziehungszeitDetails = null;
		if(pension.getKindererziehungsZuschlag() != null) {
			Set<Para_50_Kindererziehungszeit> kindererziehungszuschlag2 = pension.getKindererziehungsZuschlag().getKindererziehungszeiten();
			for (Iterator<Para_50_Kindererziehungszeit> iterator = kindererziehungszuschlag2.iterator(); iterator.hasNext();) {
				Para_50_Kindererziehungszeit kindererziehungszeit = (Para_50_Kindererziehungszeit) iterator.next();
				
				if(kindererziehungszeit.getStartDate() != null && kindererziehungszeit.getEndDate() != null) {
					kindererziehungszeitDetails = new TimePeriodDetails(kindererziehungszeit.getStartDate(), kindererziehungszeit.getEndDate());
				}
				else if(kindererziehungszeit.getZulageMonths() > 0) {
					
					kindererziehungszeitDetails = new TimePeriodDetails();
					kindererziehungszeitDetails.addMonths(kindererziehungszeit.getZulageMonths());
				}
				
				ruhegehaltsfaehigeDienstzeiten.add(kindererziehungszeitDetails);
			}
		}
		*/
		
		if(ruhegehaltsfaehigeDienstzeiten.getDays() >= 365) {
			long days = ruhegehaltsfaehigeDienstzeiten.getDays();
			
			//hier ertsmal auf 0, weil es später wieder zugefügt wird
			ruhegehaltsfaehigeDienstzeiten.setDays(0);
			
			long years = days / 365;
			days = days % 365;
			ruhegehaltsfaehigeDienstzeiten.add(years, days);
		}
		
		return ruhegehaltsfaehigeDienstzeiten;
	}
	
	
	
	

}
