package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;


/**
 * § 12 Ausbildungszeiten
 * 
 * (1) Die verbrachte Mindestzeit
		1.  der außer der allgemeinen Schulbildung vorgeschriebenen Ausbildung (Fachschul-, Hochschul- und praktische Ausbildung, Vorbereitungsdienst, übliche Prüfungszeit),
		2.  einer praktischen hauptberuflichen Tätigkeit, die für die Übernahme in das Beamtenverhältnis vorgeschrieben ist,
			kann als ruhegehaltfähige Dienstzeit berücksichtigt werden, die Zeit einer Fachschulausbildung einschließlich der Prüfungszeit bis zu 1 095 Tagen und die Zeit einer 
			Hochschulausbildung einschließlich der Prüfungszeit bis zu 855 Tagen, insgesamt höchstens bis zu 1 095 Tagen. Wird die allgemeine Schulbildung durch eine andere Art 
			der Ausbildung ersetzt, so steht diese der Schulbildung gleich. Zum Zeitpunkt des Ruhestandseintritts ist das Ruhegehalt unter Berücksichtigung von 
			Hochschulausbildungszeiten nach Satz 1 zu berechnen.
	
	(1a) Ergibt eine Berechnung des Ruhegehalts unter Berücksichtigung von Hochschulausbildungszeiten nach Absatz 1 Satz 1 in der bis zum 11. Februar 2009 geltenden Fassung gegenüber 
	der Ruhegehaltsberechnung nach Absatz 1 Satz 3 einen Differenzbetrag, der größer ist als der Rentenbetrag, der sich durch Vervielfältigung des aktuellen Rentenwertes mit dem 
	Faktor 2,25 ergibt, bleibt es bei der Berechnung des Ruhegehalts unter Berücksichtigung von Hochschulausbildungszeiten nach Absatz 1 Satz 1 in der bis zum 11. Februar 2009 
	geltenden Fassung, soweit dadurch eine ruhegehaltfähige Gesamtdienstzeit von 40 Jahren nicht überschritten wird. Die der Berechnung nach Satz 1 zugrunde gelegten 
	Hochschulausbildungszeiten sind um die Hochschulausbildungszeiten zu vermindern, die dem Rentenbetrag entsprechen, der sich durch Vervielfältigung des aktuellen Rentenwertes 
	mit dem Faktor 2,25 ergibt.
	
	(2) Für Beamte des Vollzugsdienstes und des Einsatzdienstes der Feuerwehr können verbrachte Zeiten einer praktischen Ausbildung und einer praktischen hauptberuflichen Tätigkeit 
	an Stelle einer Berücksichtigung nach Absatz 1 bis zu einer Gesamtzeit von fünf Jahren als ruhegehaltfähige Dienstzeit berücksichtigt werden, wenn sie für die Wahrnehmung des 
	Amtes förderlich sind. Absatz 1 Satz 2 gilt entsprechend.
	
	(3) Hat der Beamte sein Studium nach der Festsetzung von Regelstudienzeiten in dem jeweiligen Studiengang begonnen, kann die tatsächliche Studiendauer nur insoweit berücksichtigt 
	werden, als die Regelstudienzeit einschließlich der Prüfungszeit nicht überschritten ist.

	(4) Bei anderen als Laufbahnbewerbern können Zeiten nach Absatz 1 als ruhegehaltfähig berücksichtigt werden, wenn und soweit sie für Laufbahnbewerber vorgeschrieben sind. Ist eine 
	Laufbahn der Fachrichtung des Beamten bei einem Dienstherrn noch nicht gestaltet, so gilt das Gleiche für solche Zeiten, die bei Gestaltung der Laufbahn mindestens vorgeschrieben 
	werden müssen.
	
	(5) (weggefallen)
	
 * 
 * @author Bernd
 *
 */
public class Para_12_VorgeschriebenePraktischeAusbildung extends BaseTimePeriod {

	public static final String ZEITART = "Praktische Ausbildung, Vorbereitungsdienst (§12,1,1 BeamtVG)";
	
	public Para_12_VorgeschriebenePraktischeAusbildung(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.VORGESCHR_PRAKTISCHE_AUSBILDUNG_PARA_12_1_1, startDate, endDate, factor);
	}

	public String getZeitart() {
		return ZEITART;
	}
}
