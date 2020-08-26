package de.protin.support.pr.domain.service;

import de.protin.support.pr.domain.IPension;

public interface IRuhegehaltsberechnungAltesRechtService {
	

	
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
	public float calculateRuhegehaltssatz_Para_85_Vergleichsberechnung(IPension pension);

	public float calculateRuhegehaltssatz_Bis_1991(IPension pension);

	public float calculateRuhegehaltssatz_Ab_1992(IPension pension);
	
	
	

}
