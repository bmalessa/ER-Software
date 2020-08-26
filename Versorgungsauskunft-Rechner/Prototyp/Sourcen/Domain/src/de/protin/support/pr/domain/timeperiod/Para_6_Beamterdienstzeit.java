package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;


/**
 * � 6 Regelm��ige ruhegehaltf�hige Dienstzeit
 * 
 * ... (1) 1 Ruhegehaltf�hig ist die Dienstzeit, die der Beamte vom Tage seiner ersten Berufung in das Beamtenverh�ltnis an im Dienst eines 
 *  �ffentlich-rechtlichen Dienstherrn im Beamtenverh�ltnis zur�ckgelegt hat. 
 * 
 * @author Bernd
 *
 */
public class Para_6_Beamterdienstzeit extends BaseTimePeriod {

	public static final String ZEITART = "Beamtendienstzeiten nach dem 17. Lebensjahr (�6 BeamtVG)";
	
	
	public Para_6_Beamterdienstzeit(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.BEAMTEN_DIENSTZEIT_RUHEGEHALTSFAEHIG_PARA_6, startDate, endDate, factor);
	}

	
	public String getZeitart() {
		return ZEITART;
	}
	
}
