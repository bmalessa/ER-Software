package de.protin.support.pr.domain.service.impl;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.service.IRuhegehaltsberechnungAltesRechtService;
import de.protin.support.pr.domain.utils.Constants;
import de.protin.support.pr.domain.utils.TimePeriodCalculator;
import de.protin.support.pr.domain.utils.TimePeriodDetails;


/**
 * Bei der Berechnung nach altem Recht (i.d.F. ab 1.8.84) gilt dagegen, dass das Ruhegehalt bis zur
 * Vollendung einer zehnjährigen ruhegehaltfähigen Dienstzeit 35% beträgt, mit jedem weiteren
 * Dienstjahr bis zum 25. Dienstjahr um 2%, danach um jährlich 1% steigt, bis zum Höchstsatz von 75%.
 * Ein Rest der ruhegehaltfähigen Dienstzeit von mehr als 182 Tagen gilt als vollendetes Dienstjahr. 
 * @author Bernd
 *
 */
public class RuhegehaltsberechnungAltesRechtServiceImpl implements IRuhegehaltsberechnungAltesRechtService {
	
	/**
	 * Hier erstmal nur die grobe Berechnung. Muss bei bedarf ggf. nochmal besser recherchiert werden.
	 * Die ersten 10 jahre zählen 35 %. 
	 * Die Jahre 11- 25 jeweils 2%.
	 * Wenn ein Rest von mehr als 182 Tage übrig ist, wird dies als ein ganzes jahr gerechnet.
	 * Mittlerweile dürften die Vergleichsberechnung altersbedingt nicht mehr günstiger sein.
	 * 
	 * Anhand der Vergleichsberechnung der BAnstPT für 10 ruhegehaltsfähige Jahre vor dem 01.01.1992
	 * ist dies nicht mehr günstiger.
	 * 
	 * Wenn evtl. mehr als 13-15 ruhegehaltsfähige Jahre vor dem 01.01.1992 vorhanden sind, wird dies vermutlich anders aussehen.
	 * 
	 * Auf mehr als 20 ruhegehaltsfähige Jahre vor dem 01.01.1992 dürfte keine mehr kommen (Stand 2020)
	 * 
	 *  Bsp: 1972 - 1981 = 35%
	 *       1982 - 1991 = 35 + (10*2) = 55%
	 * 		 1992 - 2020 = 55 + (28*1) = 83%
	 * 
	 * 		 1976 - 1985 = 35
	 *       1986 - 1991 = 35 + (5*2) = 45%
	 * 		 1992 - 2020 = 45 + (28*1) = 73%
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	
	public float calculateRuhegehaltssatz_Para_85_Vergleichsberechnung(IPension pension) {
		float ruhegehaltsSatzPara85 = calculateRuhegehaltssatz_Bis_1991(pension);
		ruhegehaltsSatzPara85 += calculateRuhegehaltssatz_Ab_1992(pension);
		
		//siehe Vergleichberechnung der BAnst und §69e Abs.4
  		ruhegehaltsSatzPara85 *=  Constants.KORREKTURFAKTOR_PARA_69e_Abs_4_BeamtVG;
		return ruhegehaltsSatzPara85;
	}

	@Override
	public float calculateRuhegehaltssatz_Bis_1991(IPension pension) {
		float ruhegehaltsSatz = 0.0f;
		TimePeriodDetails timePeriodBeforeDate19920101 = TimePeriodCalculator.getTimePeriodBeforeDate19920101(pension);
		long years = timePeriodBeforeDate19920101.getYears();
		long days = timePeriodBeforeDate19920101.getDays();
		if(years > 9) {
			years = years - 10;
			ruhegehaltsSatz = 35.00f;
			if(years > 0 && years < 16) {
				ruhegehaltsSatz += years * 2.0;
			}
			if(days > 182) {
				ruhegehaltsSatz += 2.0;
			}
		}
		return ruhegehaltsSatz;
	}

	@Override
	public float calculateRuhegehaltssatz_Ab_1992(IPension pension) {
		float ruhegehaltsSatz = 0.0f;
		TimePeriodDetails timePeriodAfterDate199112231 = TimePeriodCalculator.getTimePeriodAfterDate199112231(pension);
		ruhegehaltsSatz += timePeriodAfterDate199112231.getYears() * 1.0;
		
		if(timePeriodAfterDate199112231.getDays() > 0) {
			
			ruhegehaltsSatz += Float.valueOf(timePeriodAfterDate199112231.getDays()) / 365;
		}
		return ruhegehaltsSatz;
	}


	
	
	
}
