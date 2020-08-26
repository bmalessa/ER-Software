package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;


/**
 * � 12 Ausbildungszeiten
 * 
 * (1) Die verbrachte Mindestzeit
		1.  der au�er der allgemeinen Schulbildung vorgeschriebenen Ausbildung (Fachschul-, Hochschul- und praktische Ausbildung, Vorbereitungsdienst, �bliche Pr�fungszeit),
		2.  einer praktischen hauptberuflichen T�tigkeit, die f�r die �bernahme in das Beamtenverh�ltnis vorgeschrieben ist,
			kann als ruhegehaltf�hige Dienstzeit ber�cksichtigt werden, die Zeit einer Fachschulausbildung einschlie�lich der Pr�fungszeit bis zu 1 095 Tagen und die Zeit einer 
			Hochschulausbildung einschlie�lich der Pr�fungszeit bis zu 855 Tagen, insgesamt h�chstens bis zu 1 095 Tagen. Wird die allgemeine Schulbildung durch eine andere Art 
			der Ausbildung ersetzt, so steht diese der Schulbildung gleich. Zum Zeitpunkt des Ruhestandseintritts ist das Ruhegehalt unter Ber�cksichtigung von 
			Hochschulausbildungszeiten nach Satz 1 zu berechnen.
	
	(1a) Ergibt eine Berechnung des Ruhegehalts unter Ber�cksichtigung von Hochschulausbildungszeiten nach Absatz 1 Satz 1 in der bis zum 11. Februar 2009 geltenden Fassung gegen�ber 
	der Ruhegehaltsberechnung nach Absatz 1 Satz 3 einen Differenzbetrag, der gr��er ist als der Rentenbetrag, der sich durch Vervielf�ltigung des aktuellen Rentenwertes mit dem 
	Faktor 2,25 ergibt, bleibt es bei der Berechnung des Ruhegehalts unter Ber�cksichtigung von Hochschulausbildungszeiten nach Absatz 1 Satz 1 in der bis zum 11. Februar 2009 
	geltenden Fassung, soweit dadurch eine ruhegehaltf�hige Gesamtdienstzeit von 40 Jahren nicht �berschritten wird. Die der Berechnung nach Satz 1 zugrunde gelegten 
	Hochschulausbildungszeiten sind um die Hochschulausbildungszeiten zu vermindern, die dem Rentenbetrag entsprechen, der sich durch Vervielf�ltigung des aktuellen Rentenwertes 
	mit dem Faktor 2,25 ergibt.
	
	(2) F�r Beamte des Vollzugsdienstes und des Einsatzdienstes der Feuerwehr k�nnen verbrachte Zeiten einer praktischen Ausbildung und einer praktischen hauptberuflichen T�tigkeit 
	an Stelle einer Ber�cksichtigung nach Absatz 1 bis zu einer Gesamtzeit von f�nf Jahren als ruhegehaltf�hige Dienstzeit ber�cksichtigt werden, wenn sie f�r die Wahrnehmung des 
	Amtes f�rderlich sind. Absatz 1 Satz 2 gilt entsprechend.
	
	(3) Hat der Beamte sein Studium nach der Festsetzung von Regelstudienzeiten in dem jeweiligen Studiengang begonnen, kann die tats�chliche Studiendauer nur insoweit ber�cksichtigt 
	werden, als die Regelstudienzeit einschlie�lich der Pr�fungszeit nicht �berschritten ist.

	(4) Bei anderen als Laufbahnbewerbern k�nnen Zeiten nach Absatz 1 als ruhegehaltf�hig ber�cksichtigt werden, wenn und soweit sie f�r Laufbahnbewerber vorgeschrieben sind. Ist eine 
	Laufbahn der Fachrichtung des Beamten bei einem Dienstherrn noch nicht gestaltet, so gilt das Gleiche f�r solche Zeiten, die bei Gestaltung der Laufbahn mindestens vorgeschrieben 
	werden m�ssen.
	
	(5) (weggefallen)
	
 * 
 * @author Bernd
 *
 */
public class Para_12_VorgeschriebenePraktischeAusbildung extends BaseTimePeriod {

	public static final String ZEITART = "Praktische Ausbildung, Vorbereitungsdienst (�12,1,1 BeamtVG)";
	
	public Para_12_VorgeschriebenePraktischeAusbildung(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.VORGESCHR_PRAKTISCHE_AUSBILDUNG_PARA_12_1_1, startDate, endDate, factor);
	}

	public String getZeitart() {
		return ZEITART;
	}
}
