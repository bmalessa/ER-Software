package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;


/**
 * § 11 Sonstige Zeiten
 * 
 * Die Zeit, während der ein Beamter vor der Berufung in das Beamtenverhältnis
	1.
		a) als Rechtsanwalt oder Verwaltungsrechtsrat oder als Beamter oder Notar, der ohne Ruhegehaltsberechtigung nur Gebühren bezieht, oder
		b) hauptberuflich im Dienst öffentlich-rechtlicher Religionsgesellschaften oder ihrer Verbände (Artikel 140 des Grundgesetzes) oder im 
		   öffentlichen oder nichtöffentlichen Schuldienst oder
		c) hauptberuflich im Dienst der Fraktionen des Bundestages oder der Landtage oder kommunaler Vertretungskörperschaften oder
		d) hauptberuflich im Dienst von kommunalen Spitzenverbänden oder ihren Landesverbänden sowie von Spitzenverbänden der Sozialversicherung oder ihren Landesverbänden
		   tätig gewesen ist oder
	2.
		hauptberuflich im ausländischen öffentlichen Dienst gestanden hat oder
	3.
		a) auf wissenschaftlichem, künstlerischem, technischem oder wirtschaftlichem Gebiet besondere Fachkenntnisse erworben hat, die die notwendige Voraussetzung für 
		   die Wahrnehmung seines Amtes bilden, oder
		b) als Entwicklungshelfer im Sinne des Entwicklungshelfer-Gesetzes tätig gewesen ist,
		   kann als ruhegehaltfähige Dienstzeit berücksichtigt werden, die Zeit nach Nummer 1 Buchstabe a und Nr. 3 jedoch höchstens bis zur Hälfte und in der Regel nicht 
		   über zehn Jahre hinaus.
 * 
 * @author Bernd
 *
 */
public class Para_11_SonstigeZeiten extends BaseTimePeriod {

	public static final String ZEITART = "Sonstige Zeiten (§11 BeamtVG)";
	
	public Para_11_SonstigeZeiten(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.SONSTIGE_ZEITEN_PARA_11, startDate, endDate, factor);
	}
	
	public String getZeitart() {
		return ZEITART;
	}

}
