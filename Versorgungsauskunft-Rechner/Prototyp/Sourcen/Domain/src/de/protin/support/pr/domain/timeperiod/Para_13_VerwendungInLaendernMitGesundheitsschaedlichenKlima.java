package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;


/**
 * � 13 Zurechnungszeit und Zeit gesundheitssch�digender Verwendung

	(2) Die Zeit der Verwendung eines Beamten in L�ndern, in denen er gesundheitssch�digenden klimatischen Einfl�ssen 
	ausgesetzt ist, kann, soweit sie nach Vollendung des siebzehnten Lebensjahres liegt, bis zum Doppelten als 
	ruhegehaltf�hige Dienstzeit ber�cksichtigt werden, wenn sie ununterbrochen mindestens ein Jahr gedauert hat. 
	Entsprechendes gilt f�r einen beurlaubten Beamten, dessen T�tigkeit in den in Satz 1 genannten Gebieten �ffentlichen 
	Belangen oder dienstlichen Interessen diente, wenn dies sp�testens bei Beendigung des Urlaubs anerkannt worden ist.
	
 * @author Bernd
 *
 */
public class Para_13_VerwendungInLaendernMitGesundheitsschaedlichenKlima extends BaseTimePeriod {

	public static final String ZEITART = "Verw. in L�ndern mit gesundheitssch�dl. Klima (�13 BeamtVG)";
	
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
