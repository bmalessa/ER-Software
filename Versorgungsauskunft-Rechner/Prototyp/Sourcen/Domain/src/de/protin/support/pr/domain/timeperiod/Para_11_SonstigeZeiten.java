package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;


/**
 * � 11 Sonstige Zeiten
 * 
 * Die Zeit, w�hrend der ein Beamter vor der Berufung in das Beamtenverh�ltnis
	1.
		a) als Rechtsanwalt oder Verwaltungsrechtsrat oder als Beamter oder Notar, der ohne Ruhegehaltsberechtigung nur Geb�hren bezieht, oder
		b) hauptberuflich im Dienst �ffentlich-rechtlicher Religionsgesellschaften oder ihrer Verb�nde (Artikel 140 des Grundgesetzes) oder im 
		   �ffentlichen oder nicht�ffentlichen Schuldienst oder
		c) hauptberuflich im Dienst der Fraktionen des Bundestages oder der Landtage oder kommunaler Vertretungsk�rperschaften oder
		d) hauptberuflich im Dienst von kommunalen Spitzenverb�nden oder ihren Landesverb�nden sowie von Spitzenverb�nden der Sozialversicherung oder ihren Landesverb�nden
		   t�tig gewesen ist oder
	2.
		hauptberuflich im ausl�ndischen �ffentlichen Dienst gestanden hat oder
	3.
		a) auf wissenschaftlichem, k�nstlerischem, technischem oder wirtschaftlichem Gebiet besondere Fachkenntnisse erworben hat, die die notwendige Voraussetzung f�r 
		   die Wahrnehmung seines Amtes bilden, oder
		b) als Entwicklungshelfer im Sinne des Entwicklungshelfer-Gesetzes t�tig gewesen ist,
		   kann als ruhegehaltf�hige Dienstzeit ber�cksichtigt werden, die Zeit nach Nummer 1 Buchstabe a und Nr. 3 jedoch h�chstens bis zur H�lfte und in der Regel nicht 
		   �ber zehn Jahre hinaus.
 * 
 * @author Bernd
 *
 */
public class Para_11_SonstigeZeiten extends BaseTimePeriod {

	public static final String ZEITART = "Sonstige Zeiten (�11 BeamtVG)";
	
	public Para_11_SonstigeZeiten(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.SONSTIGE_ZEITEN_PARA_11, startDate, endDate, factor);
	}
	
	public String getZeitart() {
		return ZEITART;
	}

}
