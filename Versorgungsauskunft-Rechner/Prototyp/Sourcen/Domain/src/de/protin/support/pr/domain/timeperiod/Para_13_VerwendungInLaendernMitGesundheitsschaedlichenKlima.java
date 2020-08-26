package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;


/**
 * § 13 Zurechnungszeit und Zeit gesundheitsschädigender Verwendung

	(2) Die Zeit der Verwendung eines Beamten in Ländern, in denen er gesundheitsschädigenden klimatischen Einflüssen 
	ausgesetzt ist, kann, soweit sie nach Vollendung des siebzehnten Lebensjahres liegt, bis zum Doppelten als 
	ruhegehaltfähige Dienstzeit berücksichtigt werden, wenn sie ununterbrochen mindestens ein Jahr gedauert hat. 
	Entsprechendes gilt für einen beurlaubten Beamten, dessen Tätigkeit in den in Satz 1 genannten Gebieten öffentlichen 
	Belangen oder dienstlichen Interessen diente, wenn dies spätestens bei Beendigung des Urlaubs anerkannt worden ist.
	
 * @author Bernd
 *
 */
public class Para_13_VerwendungInLaendernMitGesundheitsschaedlichenKlima extends BaseTimePeriod {

	public static final String ZEITART = "Verw. in Ländern mit gesundheitsschädl. Klima (§13 BeamtVG)";
	
	public Para_13_VerwendungInLaendernMitGesundheitsschaedlichenKlima(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.VERWENDUNG_IN_LAENDERN_MIT_GESUNDHEITSSCHAEDLICHEM_KLIMA_PARA_13, startDate, endDate, factor);
	}

	public float calculateZurechnung_Para_13_BeamtVG() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String getZeitart() {
		return ZEITART;
	}

}
