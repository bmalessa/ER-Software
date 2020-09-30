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
	 * �5 BeamtVG
	 * (1) Ruhegehaltf�hige Dienstbez�ge sind
			1. das Grundgehalt,
			2. der Familienzuschlag (� 50 Abs. 1) der Stufe 1,
			3. sonstige Dienstbez�ge, die im Besoldungsrecht als ruhegehaltf�hig bezeichnet sind,
			4. Leistungsbez�ge nach � 33 Abs. 1 des Bundesbesoldungsgesetzes, soweit sie 
			   nach � 33 Abs. 3 des Bundesbesoldungsgesetzes ruhegehaltf�hig sind oder auf Grund der nach � 33 Absatz 4 des Bundesbesoldungsgesetzes erlassenen 
			   Rechtsverordnungen f�r ruhegehaltf�hig erkl�rt wurden, die dem Beamten in den F�llen der Nummern 1 und 3 zuletzt zugestanden haben oder in den 
			   F�llen der Nummer 2 nach dem Besoldungsrecht zustehen w�rden; sie werden mit dem Faktor 0,9901 vervielf�ltigt. Bei Teilzeitbesch�ftigung und 
			   Beurlaubung ohne Dienstbez�ge (Freistellung) gelten als ruhegehaltf�hige Dienstbez�ge die dem letzten Amt entsprechenden vollen ruhegehaltf�higen 
			   Dienstbez�ge. Satz 2 gilt entsprechend bei eingeschr�nkter Verwendung eines Beamten wegen begrenzter Dienstf�higkeit nach � 45 des Bundesbeamtengesetzes. 
			   � 78 des Bundesbesoldungsgesetzes ist nicht anzuwenden.
			   
	
	 * Grundgehalt und Familienzuschlag werden aus der hinterlegten Besoldungstabelle entnommen.
	 * Stellenzulage und sonstige ruhegehaltsf�hige Zulagen m�ssen betragsm��ig im Frontend erfasst werden.
	 *  
	 * @return
	 */
	float calculateRuhegehaltsfaehigenDienstbezug();

	/**
	 * � 14 Absatz 4 BeamtVG
	 * Das Mindestruhegehalt ist entweder mit 35 Prozent der ruhegehaltf�higen Dienstbez�ge aus der erreichten 
	 * Besoldungsgruppe (amtsabh�ngiges Mindestruhegehalt) oder mit 65 Prozent der ruhegehaltf�higen Dienstbez�ge 
	 * aus der Endstufe der Besoldungsgruppe A 4 plus einem Fixbetrag von 30,68 Euro (amtsunabh�ngiges Mindestruhegehalt) 
	 * gesetzlich festgelegt. Es wird der im Vergleichswege festgestellte h�here Betrag gezahlt. Grundvoraussetzung ist 
	 * dabei, dass der Beamte eine zu ber�cksichtigende Dienstzeit von mindestens f�nf Jahren abgeleistet hat. 
	 * @return
	 */
	float calculateMindestversorgung();

	float calculateAmtsabhaengigeMindestversorgung();
	
	float calculateAmtsunabhaengigeMindestversorgung();
	
	
	/**
	 * � 14 H�he des Ruhegehalts
			(1) Das Ruhegehalt betr�gt f�r jedes Jahr ruhegehaltf�higer Dienstzeit 1,79375 Prozent, insgesamt jedoch 
			h�chstens 71,75 Prozent, der ruhegehaltf�higen Dienstbez�ge. Bei der Berechnung der Jahre ruhegehaltf�higer 
			Dienstzeit werden unvollst�ndige Jahre als Dezimalzahl angegeben. Dabei wird ein Jahr mit 365 Tagen angesetzt 
			und wird das Ergebnis kaufm�nnisch auf zwei Dezimalstellen gerundet. Der Ruhegehaltssatz wird ebenfalls kaufm�nnisch 
			auf zwei Dezimalstellen gerundet.
	
	 * @return
	 */
	
	
	
	
	float calculateRuhegehaltssatz_Para_14_1_Neue_Fassung();

	/**
	 * Hier erstmal nur die grobe Berechnung. Muss bei bedarf ggf. nochmal besser recherchiert werden.
	 * Die ersten 10 jahre z�hlen 35 %. 
	 * Die Jahre 11- 25 jeweils 2%.
	 * Wenn ein Rest von mehr als 182 Tage �brig ist, wird dies als ein ganzes jahr gerechnet.
	 * Mittlerweile d�rften die Vergleichsberechnung altersbedingt nicht mehr g�nstiger sein.
	 * 
	 * Anhand der Vergleichsberechnung der BAnstPT f�r 10 ruhegehaltsf�hige Jahre vor dem 01.01.1992
	 * ist dies nicht mehr g�nstiger.
	 * 
	 * Wenn evtl. mehr als 13-15 ruhegehaltsf�hige Jahre vor dem 01.01.1992 vorhanden sind, wird dies vermutlich anders aussehen.
	 * 
	 * Auf mehr als 20 ruhegehaltsf�hige Jahre vor dem 01.01.1992 d�rfte keine mehr kommen (Stand 2020)
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
	 * � 50f Abzug f�r Pflegeleistungen
		Die zu zahlenden Versorgungsbez�ge vermindern sich um den h�lftigen Prozentsatz nach � 55 Abs. 1 Satz 1 des Elften Buches Sozialgesetzbuch. 
		Versorgungsbez�ge nach Satz 1 sind
			1. Ruhegehalt, Witwengeld, Waisengeld, Unterhaltsbeitrag zuz�glich des Unterschiedsbetrages nach � 50 Abs. 1 Satz 2 bis 4,
			2. Leistungen nach � 4 Abs. 2 Nummer 3 bis 7 des Gesetzes �ber die Gew�hrung einer j�hrlichen Sonderzuwendung in der 
			   Fassung der Bekanntmachung vom 15. Dezember 1998 (BGBl. I S. 3642), das zuletzt durch Artikel 18 des Gesetzes vom 
			   10. September 2003 (BGBl. I S. 1798) ge�ndert worden ist.
			
			Die Verminderung darf den Betrag, der sich aus dem h�lftigen Prozentsatz nach � 55 Abs. 1 Satz 1 des Elften Buches Sozialgesetzbuch des zw�lften 
			Teils der j�hrlichen Beitragsbemessungsgrenze in der Pflegeversicherung (� 55 Abs. 2 des Elften Buches Sozialgesetzbuch) errechnet, nicht �bersteigen.
	
	 * @return
	 */
	void calculatePflegeleistungsabzug();

	/**
	 * 
	 * @return
	 */
	float calculateErdientesRuhegehalt();

	/**
	 * Abh�ngig von der Art der Ruhegehaltsbeantragung werden unterschiedliche Abschl�ge am Ruhegehalt vorgenommen.
	 * Deshalb erfolgt die konkrete Implementierung in den spezifische Ruhegehaltsberechnungen.
	 * 
	 * @return Abschlagsfaktor
	 */
	float calculateAbschlag_Para_14_BeamtVG();
	
	/**
	 * Liefert abh�ngig vom Context den maximal m�glichen Versorgungsabschlag.
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
	 * Validierung der f�r die Ruhegehaltsberehnung eingegebenen Parameter sowie der gesetzlichen Bestimmungen und Voraussetzungen f�r die verschiedenen Arten
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