package de.protin.support.pr.domain.besoldung.tabelle;

import java.util.Date;

import de.protin.support.pr.domain.IBesoldungstabelle;
import de.protin.support.pr.domain.besoldung.tabelle.impl.InMemory_2020;
import de.protin.support.pr.domain.utils.SelectionConstants;

public class BesoldungstabelleFactory {
	
	
	private static BesoldungstabelleFactory instance;

	private BesoldungstabelleFactory() {
	}

	public static BesoldungstabelleFactory getInstance() {
		if (null == instance) {
			instance = new BesoldungstabelleFactory();
		}
		return instance;
	}
	
	
	public IBesoldungstabelle getBesoldungstabelle(String anzuwendendesRecht, Date dateOfRetirement) {
		IBesoldungstabelle result = null;	
		
		//todo - hier anhand des angegebenen Ruhestandseintrittsdatum die richtige Besoldungstabelle ermitteln
		// wird hier erstmal fix gesetzt.
		if(SelectionConstants.ANZUWENDENDES_RECHT_BUND.equalsIgnoreCase(anzuwendendesRecht)) {
			result = new InMemory_2020();
		}
		
		return result;
	}
}
