package de.protin.support.pr.domain.besoldung.zuschlag;

import java.util.Iterator;
import java.util.Set;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.pension.AbstractPension;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.timeperiod.Para_13_VerwendungInLaendernMitGesundheitsschaedlichenKlima;

/**
 * BeamtVG §13
 * 
 * Abs 1) Ist der Beamte vor Vollendung des sechzigsten Lebensjahres wegen Dienstunfähigkeit in den Ruhestand versetzt worden, wird die Zeit vom Beginn des 
 * Ruhestandes bis zum Ablauf des Monats der Vollendung des sechzigsten Lebensjahres, soweit diese nicht nach anderen Vorschriften als ruhegehaltfähig berücksichtigt 
 * wird, für die Berechnung des Ruhegehalts der ruhegehaltfähigen Dienstzeit zu zwei Dritteln hinzugerechnet (Zurechnungszeit). Ist der Beamte nach § 46 des 
 * Bundesbeamtengesetzes erneut in das Beamtenverhältnis berufen worden, so wird eine der Berechnung des früheren Ruhegehalts zugrunde gelegene Zurechnungszeit 
 * insoweit berücksichtigt, als die Zahl der dem neuen Ruhegehalt zugrunde liegenden Dienstjahre hinter der Zahl der dem früheren Ruhegehalt zugrunde gelegenen 
 * Dienstjahre zurückbleibt.
 * 
 * Abs 2) Die Zeit der Verwendung eines Beamten in Ländern, in denen er gesundheitsschädigenden klimatischen Einflüssen ausgesetzt ist, kann bis zum Doppelten als 
 * ruhegehaltfähige Dienstzeit berücksichtigt werden, wenn sie ununterbrochen mindestens ein Jahr gedauert hat. Entsprechendes gilt für einen beurlaubten Beamten, dessen 
 * Tätigkeit in den in Satz 1 genannten Gebieten öffentlichen Belangen oder dienstlichen Interessen diente, wenn dies spätestens bei Beendigung des Urlaubs anerkannt worden ist.

   Abs(3) Zeiten einer besonderen Verwendung im Ausland können bis zum Doppelten als ruhegehaltfähige Dienstzeit berücksichtigt werden, wenn sie
   1.   einzeln ununterbrochen mindestens 30 Tage gedauert haben und
   2.   insgesamt mindestens 180 Tage gedauert haben.
   
   
 * @author Bernd
 *
 */
public class Zurechnungszeiten {

	
	
	public static boolean isZurechnungszeitAvailable(AbstractPension pension) {
		if(pension instanceof Dienstunfaehigkeit) {
			return true;
		}
		
		
		Set<ITimePeriod> timePeriods = pension.getTimePeriods();
		for (Iterator<ITimePeriod> iterator = timePeriods.iterator(); iterator.hasNext();) {
			ITimePeriod type = (ITimePeriod) iterator.next();
			if(type instanceof Para_13_VerwendungInLaendernMitGesundheitsschaedlichenKlima) {
				return true;
			}
		}
		
		return false;
	}

}
