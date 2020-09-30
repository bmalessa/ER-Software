package de.protin.support.pr.domain.besoldung.tabelle;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import de.protin.support.pr.domain.IBesoldungstabelle;
import de.protin.support.pr.domain.besoldung.tabelle.impl.InMemory_2016;
import de.protin.support.pr.domain.besoldung.tabelle.impl.InMemory_2017;
import de.protin.support.pr.domain.besoldung.tabelle.impl.InMemory_2018;
import de.protin.support.pr.domain.besoldung.tabelle.impl.InMemory_2019;
import de.protin.support.pr.domain.besoldung.tabelle.impl.InMemory_Actual;
import de.protin.support.pr.domain.utils.SelectionConstants;

public class BesoldungstabelleFactory {
	
	
	private static BesoldungstabelleFactory instance;
	
	private static IBesoldungstabelle besTabBund_2016;
	private static IBesoldungstabelle besTabBund_2017;
	private static IBesoldungstabelle besTabBund_2018;
	private static IBesoldungstabelle besTabBund_2019;
	private static IBesoldungstabelle besTabBund_2020;

	private BesoldungstabelleFactory() {
		besTabBund_2016 = new InMemory_2016();
		besTabBund_2017 = new InMemory_2017();
		besTabBund_2018 = new InMemory_2018();
		besTabBund_2019 = new InMemory_2019();
		besTabBund_2020 = new InMemory_Actual();
	}

	public static BesoldungstabelleFactory getInstance() {
		if (null == instance) {
			instance = new BesoldungstabelleFactory();
		}
		return instance;
	}
	
	
	public IBesoldungstabelle getBesoldungstabelle(String anzuwendendesRecht, Date dateOfRetirement) {
		
		if(SelectionConstants.ANZUWENDENDES_RECHT_BUND.equalsIgnoreCase(anzuwendendesRecht)) {
			
				if(dateOfRetirement.after(DateUtils.addDays(BesoldungstabelleFactory.besTabBund_2016.getValidFrom(), -1)) && dateOfRetirement.before(DateUtils.addDays(BesoldungstabelleFactory.besTabBund_2016.getValidTo(), 1))) {
					return BesoldungstabelleFactory.besTabBund_2016;
				}
				else if (dateOfRetirement.after(DateUtils.addDays(BesoldungstabelleFactory.besTabBund_2017.getValidFrom(), -1)) && dateOfRetirement.before(DateUtils.addDays(BesoldungstabelleFactory.besTabBund_2017.getValidTo(), 1))) {
					return BesoldungstabelleFactory.besTabBund_2017;
				}
				else if (dateOfRetirement.after(DateUtils.addDays(BesoldungstabelleFactory.besTabBund_2018.getValidFrom(), -1)) && dateOfRetirement.before(DateUtils.addDays(BesoldungstabelleFactory.besTabBund_2018.getValidTo(), 1))) {
					return BesoldungstabelleFactory.besTabBund_2018;
				}
				else if (dateOfRetirement.after(DateUtils.addDays(BesoldungstabelleFactory.besTabBund_2019.getValidFrom(), -1)) && dateOfRetirement.before(DateUtils.addDays(BesoldungstabelleFactory.besTabBund_2019.getValidTo(), 1))) {
					return BesoldungstabelleFactory.besTabBund_2019;
				}
				else if (dateOfRetirement.after(DateUtils.addDays(BesoldungstabelleFactory.besTabBund_2020.getValidFrom(), -1))) {
					return BesoldungstabelleFactory.besTabBund_2020;
				}
				else {
					return null;
				}
			
		}
		
		return null;
	}
}
