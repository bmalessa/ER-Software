package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;


/**
 * https://www.gesetze-im-internet.de/beamtv_v/BJNR006300991.html
 * 
 * § 3 Verwendung von Beamten und Richtern
		(1) Die Zeit der Verwendung eines Beamten oder eines Richters aus dem früheren Bundesgebiet zum Zwecke der Aufbauhilfe im Beitrittsgebiet wird doppelt 
			als ruhegehaltfähige Dienstzeit berücksichtigt, wenn sie ununterbrochen mindestens ein Jahr gedauert hat.
		(2) Die Regelung des Absatzes 1 ist bis zum 31. Dezember 1995 befristet. Sie gilt nicht für eine Verwendung, die nach dem 31. Dezember 1994 beginnt.

 * @author Bernd
 *
 */
public class AufbauhilfeOst extends BaseTimePeriod {
	
	public static final String ZEITART = "Aufbauhilfe Neue Bundesländer";
	
	public AufbauhilfeOst(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.AUFBAUHILFE_NEUE_BUNDESLAENDER, startDate, endDate, factor);
	}

	public String getZeitart() {
		return ZEITART;
	}
}
