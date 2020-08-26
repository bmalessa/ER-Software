package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;

public class Para_6_AltersteilzeitBlockmodel extends BaseTimePeriod {

	public static final String ZEITART = "Altersteilzeit im Blockmodel";
	
	/**
	 * � 6 Regelm��ige ruhegehaltf�hige Dienstzeit
	 * 
	 * ...Zeiten einer Altersteilzeit nach � 93 des Bundesbeamtengesetzes sowie nach entsprechenden Bestimmungen f�r Richter sind zu neun Zehnteln der Arbeitszeit ruhegehaltf�hig, 
	 * die der Bemessung der erm��igten Arbeitszeit w�hrend der Altersteilzeit zugrunde gelegt worden ist.
	 * 
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param factor
	 */
	public Para_6_AltersteilzeitBlockmodel(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.ALTERSTEILZEIT_BLOCKMODELL, startDate, endDate, factor);
		this.factor = 0.9f;
	}

	public String getZeitart() {
		return ZEITART;
	}
	
	
	/**
	 * bei ATZ immer mit faktor 0.9
	 */
	public float getFactor() {
		return 0.9f;
	}


	public void setFactor(float factor) {
		//wird hier nie auf einen anderen wert als 0.9 gesetzt
	}

	
}
