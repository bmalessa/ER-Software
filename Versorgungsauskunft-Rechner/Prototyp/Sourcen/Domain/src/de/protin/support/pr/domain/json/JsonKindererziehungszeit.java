package de.protin.support.pr.domain.json;

import java.util.Date;

import de.protin.support.pr.domain.besoldung.zuschlag.Para_50_Kindererziehungszeit;

public class JsonKindererziehungszeit extends Para_50_Kindererziehungszeit {
    		
	public JsonKindererziehungszeit() {
		super("", new Date(), new Date(), new Date(), false);
	}
	
	public Para_50_Kindererziehungszeit getDomainObject() {
		return this;
	}
}
