package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;


/**
 * § 6 Regelmäßige ruhegehaltfähige Dienstzeit
 * 
 * ... (1) 1 Ruhegehaltfähig ist die Dienstzeit, die der Beamte vom Tage seiner ersten Berufung in das Beamtenverhältnis an im Dienst eines 
 *  öffentlich-rechtlichen Dienstherrn im Beamtenverhältnis zurückgelegt hat. 
 * 
 * @author Bernd
 *
 */
public class Para_6_Beamterdienstzeit extends BaseTimePeriod {

	public static final String ZEITART = "Beamtendienstzeiten nach dem 17. Lebensjahr (§6 BeamtVG)";
	
	
	public Para_6_Beamterdienstzeit(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.BEAMTEN_DIENSTZEIT_RUHEGEHALTSFAEHIG_PARA_6, startDate, endDate, factor);
	}

	
	public String getZeitart() {
		return ZEITART;
	}
	
}
