package de.protin.support.pr.domain;

import java.util.Set;

import de.protin.support.pr.domain.besoldung.zuschlag.KindererziehungszeitenZuschlag;
import de.protin.support.pr.domain.utils.TimePeriodDetails;
import de.protin.support.pr.domain.validation.ValidationResult;

public interface IPension {

	public Person getPerson();
	
	public IBesoldungstabelle getBesoldungstabelle();
	
	public void setBesoldungstabelle(IBesoldungstabelle besoldungstablle);
	
	public Set<ITimePeriod> getTimePeriods(); 
	
	public void setTimePeriods(Set<ITimePeriod> timePeriods);
	
	public KindererziehungszeitenZuschlag getKindererziehungsZuschlag(); 
	
	public void setKindererziehungsZuschlag(KindererziehungszeitenZuschlag kindererziehungsZuschlag);
	
	
	/**
	 * §5 BeamtVG
	 * (1) Ruhegehaltfähige Dienstbezüge sind
			1. das Grundgehalt,
			2. der Familienzuschlag (§ 50 Abs. 1) der Stufe 1,
			3. sonstige Dienstbezüge, die im Besoldungsrecht als ruhegehaltfähig bezeichnet sind,
			4. Leistungsbezüge nach § 33 Abs. 1 des Bundesbesoldungsgesetzes, soweit sie 
			   nach § 33 Abs. 3 des Bundesbesoldungsgesetzes ruhegehaltfähig sind oder auf Grund der nach § 33 Absatz 4 des Bundesbesoldungsgesetzes erlassenen 
			   Rechtsverordnungen für ruhegehaltfähig erklärt wurden, die dem Beamten in den Fällen der Nummern 1 und 3 zuletzt zugestanden haben oder in den 
			   Fällen der Nummer 2 nach dem Besoldungsrecht zustehen würden; sie werden mit dem Faktor 0,9901 vervielfältigt. Bei Teilzeitbeschäftigung und 
			   Beurlaubung ohne Dienstbezüge (Freistellung) gelten als ruhegehaltfähige Dienstbezüge die dem letzten Amt entsprechenden vollen ruhegehaltfähigen 
			   Dienstbezüge. Satz 2 gilt entsprechend bei eingeschränkter Verwendung eines Beamten wegen begrenzter Dienstfähigkeit nach § 45 des Bundesbeamtengesetzes. 
			   § 78 des Bundesbesoldungsgesetzes ist nicht anzuwenden.
			   
	
	 * Grundgehalt und Familienzuschlag werden aus der hinterlegten Besoldungstabelle entnommen.
	 * Stellenzulage und sonstige ruhegehaltsfähige Zulagen müssen betragsmäßig im Frontend erfasst werden.
	 *  
	 * @return
	 */
	float calculateRuhegehaltsfaehigenDienstbezug();

	/**
	 * § 14 Absatz 4 BeamtVG
	 * Das Mindestruhegehalt ist entweder mit 35 Prozent der ruhegehaltfähigen Dienstbezüge aus der erreichten 
	 * Besoldungsgruppe (amtsabhängiges Mindestruhegehalt) oder mit 65 Prozent der ruhegehaltfähigen Dienstbezüge 
	 * aus der Endstufe der Besoldungsgruppe A 4 plus einem Fixbetrag von 30,68 Euro (amtsunabhängiges Mindestruhegehalt) 
	 * gesetzlich festgelegt. Es wird der im Vergleichswege festgestellte höhere Betrag gezahlt. Grundvoraussetzung ist 
	 * dabei, dass der Beamte eine zu berücksichtigende Dienstzeit von mindestens fünf Jahren abgeleistet hat. 
	 * @return
	 */
	float calculateMindestversorgung();

	float calculateAmtsabhaengigeMindestversorgung();
	
	float calculateAmtsunabhaengigeMindestversorgung();
	
	
	/**
	 * § 14 Höhe des Ruhegehalts
			(1) Das Ruhegehalt beträgt für jedes Jahr ruhegehaltfähiger Dienstzeit 1,79375 Prozent, insgesamt jedoch 
			höchstens 71,75 Prozent, der ruhegehaltfähigen Dienstbezüge. Bei der Berechnung der Jahre ruhegehaltfähiger 
			Dienstzeit werden unvollständige Jahre als Dezimalzahl angegeben. Dabei wird ein Jahr mit 365 Tagen angesetzt 
			und wird das Ergebnis kaufmännisch auf zwei Dezimalstellen gerundet. Der Ruhegehaltssatz wird ebenfalls kaufmännisch 
			auf zwei Dezimalstellen gerundet.
	
	 * @return
	 */
	
	
	
	
	float calculateRuhegehaltssatz_Para_14_1_Neue_Fassung();

	/**
	 * Hier erstmal nur die grobe Berechnung. Muss bei bedarf ggf. nochmal besser recherchiert werden.
	 * Die ersten 10 jahre zählen 35 %. 
	 * Die Jahre 11- 25 jeweils 2%.
	 * Wenn ein Rest von mehr als 182 Tage übrig ist, wird dies als ein ganzes jahr gerechnet.
	 * Mittlerweile dürften die Vergleichsberechnung altersbedingt nicht mehr günstiger sein.
	 * 
	 * Anhand der Vergleichsberechnung der BAnstPT für 10 ruhegehaltsfähige Jahre vor dem 01.01.1992
	 * ist dies nicht mehr günstiger.
	 * 
	 * Wenn evtl. mehr als 13-15 ruhegehaltsfähige Jahre vor dem 01.01.1992 vorhanden sind, wird dies vermutlich anders aussehen.
	 * 
	 * Auf mehr als 20 ruhegehaltsfähige Jahre vor dem 01.01.1992 dürfte keine mehr kommen (Stand 2020)
	 * 
	 *  Bsp: 1972 - 1981 = 35%
	 *       1982 - 1991 = 35 + (10*2) = 55%
	 * 		 1992 - 2020 = 55 + (28*1) = 83%
	 * 
	 * 		 1976 - 1985 = 35
	 *       1986 - 1991 = 35 + (5*2) = 45%
	 * 		 1992 - 2020 = 45 + (28*1) = 73%
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	float calculateRuhegehaltssatz_Para_85_Vergleichsberechnung();

	/** 
	 * 
	 * @return
	 */
	TimePeriodDetails calculateRuhegehaltsfaehigeDienstzeiten_Para_14();

	/**
	 * § 50f Abzug für Pflegeleistungen
		Die zu zahlenden Versorgungsbezüge vermindern sich um den hälftigen Prozentsatz nach § 55 Abs. 1 Satz 1 des Elften Buches Sozialgesetzbuch. 
		Versorgungsbezüge nach Satz 1 sind
			1. Ruhegehalt, Witwengeld, Waisengeld, Unterhaltsbeitrag zuzüglich des Unterschiedsbetrages nach § 50 Abs. 1 Satz 2 bis 4,
			2. Leistungen nach § 4 Abs. 2 Nummer 3 bis 7 des Gesetzes über die Gewährung einer jährlichen Sonderzuwendung in der 
			   Fassung der Bekanntmachung vom 15. Dezember 1998 (BGBl. I S. 3642), das zuletzt durch Artikel 18 des Gesetzes vom 
			   10. September 2003 (BGBl. I S. 1798) geändert worden ist.
			
			Die Verminderung darf den Betrag, der sich aus dem hälftigen Prozentsatz nach § 55 Abs. 1 Satz 1 des Elften Buches Sozialgesetzbuch des zwölften 
			Teils der jährlichen Beitragsbemessungsgrenze in der Pflegeversicherung (§ 55 Abs. 2 des Elften Buches Sozialgesetzbuch) errechnet, nicht übersteigen.
	
	 * @return
	 */
	void calculatePflegeleistungsabzug();

	/**
	 * 
	 * @return
	 */
	float calculateErdientesRuhegehalt();

	/**
	 * Abhängig von der Art der Ruhegehaltsbeantragung werden unterschiedliche Abschläge am Ruhegehalt vorgenommen.
	 * Deshalb erfolgt die konkrete Implementierung in den spezifische Ruhegehaltsberechnungen.
	 * 
	 * @return Abschlagsfaktor
	 */
	float calculateAbschlag_Para_14_BeamtVG();
	
	/**
	 * Liefert abhängig vom Context den maximal möglichen Versorgungsabschlag.
	 * 
	 * @return
	 */
	float calculateMaxAbschlag_Para_14_BeamtVG();
	
	
	/**
	 * 
	 * @return
	 */
	float getAbzugPflegeleistung();

	
	/**
	 * 
	 * @return
	 */
	float getFaktorAbzugPflegeleistungNachBeamtVG_50_f();

	/**
	 * 
	 * @return
	 */
	float getAmtsabhaengigeMindestversorgung();


	/**
	 * 
	 * @return
	 */
	float getAmtsunabhaengigeMindestversorgung();
	
	
	/**
	 * 
	 * @return Art des Ruhestand
	 */
	String getPensionTyp();

	/**
	 * Validierung der für die Ruhegehaltsberehnung eingegebenen Parameter sowie der gesetzlichen Bestimmungen und Voraussetzungen für die verschiedenen Arten
	 * des Ruhestand.
	 * 
	 * @return ValidationResult
	 */
	ValidationResult validate();

	void addTimePeriod(ITimePeriod timePeriod);

	void addDeductionPart(IDeductionPart deductionPart);

	void addAdditionPart(IAdditionPart additionPart);

	String getAnzwRecht();

	boolean isVergleichsberechnungBeamtVG_PARA_85();

	void setVergleichsberechnungBeamtVG_PARA_85(boolean vergleichsberechnungBeamtVG_PARA_85);


}