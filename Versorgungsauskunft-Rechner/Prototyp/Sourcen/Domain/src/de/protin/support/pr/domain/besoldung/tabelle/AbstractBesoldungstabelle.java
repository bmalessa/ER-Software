package de.protin.support.pr.domain.besoldung.tabelle;

import java.util.Date;

import de.protin.support.pr.domain.IBesoldungstabelle;

public abstract class AbstractBesoldungstabelle implements IBesoldungstabelle {
	
	protected String titel;
	protected String quelle;
	protected Date validFrom;
	protected Date validTo;
	
}
