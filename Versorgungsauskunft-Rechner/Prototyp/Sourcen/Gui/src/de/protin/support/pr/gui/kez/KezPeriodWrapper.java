package de.protin.support.pr.gui.kez;

import de.protin.support.pr.domain.besoldung.zuschlag.Para_50_Kindererziehungszeit;
import de.protin.support.pr.domain.utils.KindererziehungszeitenHelper;

public class KezPeriodWrapper {

	private Para_50_Kindererziehungszeit kindererziehungszeit;
	private String label;
	
	public KezPeriodWrapper(Para_50_Kindererziehungszeit kez) {
		this.kindererziehungszeit  = kez;
		if(kez.isVollerAnspruch()) {
			this.label = Para_50_Kindererziehungszeit.VOLLER_KEZ_ANSPRUCH;
		}
		else {
			this.label = Para_50_Kindererziehungszeit.KEIN_KEZ_ANSPRUCH;
		}
	}

	public Para_50_Kindererziehungszeit getKindererziehungszeit() {
		return kindererziehungszeit;
	}

	public void setKindererziehungszeit(Para_50_Kindererziehungszeit kez) {
		this.kindererziehungszeit = kez;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
