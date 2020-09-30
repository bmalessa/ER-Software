package de.protin.support.pr.domain.pension;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import de.protin.support.pr.domain.IAdditionPart;
import de.protin.support.pr.domain.IBesoldungstabelle;
import de.protin.support.pr.domain.IDeductionPart;
import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.besoldung.zuschlag.KindererziehungszeitenZuschlag;
import de.protin.support.pr.domain.service.IRuhegehaltsberechnungAktuellesRechtService;
import de.protin.support.pr.domain.service.impl.ServiceRegistry;
import de.protin.support.pr.domain.utils.Constants;
import de.protin.support.pr.domain.utils.TimePeriodCalculator;
import de.protin.support.pr.domain.utils.TimePeriodDetails;
import de.protin.support.pr.domain.validation.ValidationResult;

public abstract class AbstractPension implements IPension {
	
	public static final int REGEL_ALTERSGRENZE  = 1;
	public static final int ANTRAG_ALTERSGRENZE  = 2;
	public static final int ENGAGIERTER_RUHESTAND = 3;
	public static final int VORRUHESTAND = 4;
	public static final int DIENSTUNFAEHIGKEIT = 5;
	public static final int DIENSTUNFALL = 6; 
	
	//Pension�r
	protected Person person;
	//Art der Pension
	protected int typ; 
	//Anzuwendendes Rechts
	protected String anzwRecht;
	
	protected Set<ITimePeriod> timePeriods;
	protected Set<IDeductionPart> deductionParts;
	protected Set<IAdditionPart> additionParts;
	
	protected IBesoldungstabelle besoldungstabelle;
	
	protected int sumYears;
	protected int restDays;
	protected float ruhegehaltsSatz;  // Jahre * 1.79374 + Tage/365 * 1.79374
	
	protected float summeRuhegehaltsfaehigeDienstbezuege;
	protected float amtsabhaengigeMindestversorgung;
	protected float amtsunabhaengigeMindestversorgung;
	protected float abzugPflegeleistung;
	
	protected KindererziehungszeitenZuschlag kindererziehungsZuschlag;
	
	protected boolean vergleichsberechnungBeamtVG_PARA_85;
	
	/**
	 * Basisklasse f�r alle Arten der Ruhegehaltsberechnung.
	 * 
	 * @param person
	 * @param typ
	 */
	protected AbstractPension(Person person , int typ, String anzuwendendesRecht) {
		this.person = person;
		this.typ = typ;
		this.anzwRecht = anzuwendendesRecht;
		this.timePeriods = new LinkedHashSet<ITimePeriod>();
		this.deductionParts = new HashSet<IDeductionPart>();
		this.additionParts = new HashSet<IAdditionPart>();
	}
	
	
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
	@Override
	public float calculateRuhegehaltsfaehigenDienstbezug() {
		this.summeRuhegehaltsfaehigeDienstbezuege = 
				ServiceRegistry.getInstance().getRuhegehaltsberechnungAktuellesRechtService().calculateRuhegehaltsfaehigenDienstbezug(this);
		return this.summeRuhegehaltsfaehigeDienstbezuege;
	}
	
	
	
	/**
	 * � 14 Absatz 4 BeamtVG
	 * Das Mindestruhegehalt ist entweder mit 35 Prozent der ruhegehaltf�higen Dienstbez�ge aus der erreichten 
	 * Besoldungsgruppe (amtsabh�ngiges Mindestruhegehalt) oder mit 65 Prozent der ruhegehaltf�higen Dienstbez�ge 
	 * aus der Endstufe der Besoldungsgruppe A 4 plus einem Fixbetrag von 30,68 Euro (amtsunabh�ngiges Mindestruhegehalt) 
	 * gesetzlich festgelegt. Es wird der im Vergleichswege festgestellte h�here Betrag gezahlt. Grundvoraussetzung ist 
	 * dabei, dass der Beamte eine zu ber�cksichtigende Dienstzeit von mindestens f�nf Jahren abgeleistet hat. 
	 * @return
	 */
	@Override
	public float calculateMindestversorgung() {
		calculateAmtsabhaengigeMindestversorgung();
		calculateAmtsunabhaengigeMindestversorgung();
		if(amtsabhaengigeMindestversorgung > amtsunabhaengigeMindestversorgung) {
			return this.amtsabhaengigeMindestversorgung;
		}
		else {
			return this.amtsunabhaengigeMindestversorgung;
		} 
	}
	
	

	public float calculateAmtsabhaengigeMindestversorgung() {
		this.amtsabhaengigeMindestversorgung = ServiceRegistry.getInstance().getMindestversorgungService().calculateAmtsabhaengigeMindestversorgung(this);
		return this.amtsabhaengigeMindestversorgung;
	}





	public float calculateAmtsunabhaengigeMindestversorgung() {
		this.amtsunabhaengigeMindestversorgung = ServiceRegistry.getInstance().getMindestversorgungService().calculateAmtsunabhaengigeMindestversorgung(this);
		return this.amtsunabhaengigeMindestversorgung;
	}
	
	
	/**
	 * � 14 H�he des Ruhegehalts
			(1) Das Ruhegehalt betr�gt f�r jedes Jahr ruhegehaltf�higer Dienstzeit 1,79375 Prozent, insgesamt jedoch 
			h�chstens 71,75 Prozent, der ruhegehaltf�higen Dienstbez�ge. Bei der Berechnung der Jahre ruhegehaltf�higer 
			Dienstzeit werden unvollst�ndige Jahre als Dezimalzahl angegeben. Dabei wird ein Jahr mit 365 Tagen angesetzt 
			und wird das Ergebnis kaufm�nnisch auf zwei Dezimalstellen gerundet. Der Ruhegehaltssatz wird ebenfalls kaufm�nnisch 
			auf zwei Dezimalstellen gerundet.
	
	 * @return
	 */
	@Override
	public float calculateRuhegehaltssatz_Para_14_1_Neue_Fassung() {
		
		this.ruhegehaltsSatz = ServiceRegistry.getInstance().getRuhegehaltsberechnungAktuellesRechtService().
				calculateRuhegehaltssatz_Para_14_1_Neue_Fassung(this);
		
		return this.ruhegehaltsSatz;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public boolean isVergleichsberechnung_Para_85_required() {
		TimePeriodDetails timePeriodBeforeDate19920101 = TimePeriodCalculator.getTimePeriodBeforeDate19920101(this);
		if (timePeriodBeforeDate19920101.getYears() > 9) {
			return true;
		}
		return false;
	}
	
	
	
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
	@Override
	public float calculateRuhegehaltssatz_Para_85_Vergleichsberechnung() {
		return ServiceRegistry.getInstance().getRuhegehaltsberechnungAltesRechtService().
				calculateRuhegehaltssatz_Para_85_Vergleichsberechnung(this);
	}
	
	
	
	
	/** 
	 * 
	 * @return
	 */
	@Override
	public TimePeriodDetails calculateRuhegehaltsfaehigeDienstzeiten_Para_14() {
		return ServiceRegistry.getInstance().getRuhegehaltsfaehigeZeitenService().
				calculateRuhegehaltsfaehigeDienstzeiten_Para_14(this);
	}
	
	
	
	
	
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
	@Override
	public void calculatePflegeleistungsabzug() {
		float erdientesRuhegehalt = calculateErdientesRuhegehalt();
		//this.abzugPflegeleistung = erdientesRuhegehalt * Constants.FAKTOR_PARA_50f_BeamtVG / 100;
		IRuhegehaltsberechnungAktuellesRechtService rgbService = ServiceRegistry.getInstance().getRuhegehaltsberechnungAktuellesRechtService();
		this.abzugPflegeleistung = erdientesRuhegehalt * rgbService.getFaktorAbzugPflegeleistung(this) /100;
		if(this.abzugPflegeleistung > rgbService.getMaxAbzugPflegeleistung(this)) {
			this.abzugPflegeleistung = rgbService.getMaxAbzugPflegeleistung(this );
		};
	}
	
	
	
	/**
	 * �14 BeamtVG
	 * 
	(3) Das Ruhegehalt vermindert sich um 3,6 Prozent f�r jedes Jahr, um das der Beamte
			
			1.	vor Ablauf des Monats, in dem er das 65. Lebensjahr vollendet, nach � 52 Abs. 1 und 2 des Bundesbeamtengesetzes 
				in den Ruhestand versetzt wird,
				(62. Lebensjahr vollendet und schwerbehindert im Sinne des � 2 Abs. 2 des Neunten Buches Sozialgesetzbuch sind.)
				(vor 1952 -1963 geboren, wie unter Nr 1 gestaffelt von 60 Jahren bis 61 Jahre und 10 Monate)
			2.	vor Ablauf des Monats, in dem er die f�r ihn geltende gesetzliche Altersgrenze erreicht, nach � 52 Abs. 3 des 
				Bundesbeamtengesetzes in den Ruhestand versetzt wird,
				(Beamte auf Lebenszeit k�nnen auf ihren Antrag in den Ruhestand versetzt werden, wenn sie das 63. Lebensjahr vollendet haben.)
			3.	vor Ablauf des Monats, in dem er das 65. Lebensjahr vollendet, wegen Dienstunf�higkeit, die nicht auf einem 
				Dienstunfall beruht, in den Ruhestand versetzt wird;
				
				
		die Minderung des Ruhegehalts darf 10,8 vom Hundert in den F�llen der Nummern 1 und 3 und 14,4 vom Hundert in den F�llen 
		der Nummer 2 nicht �bersteigen. Absatz 1 Satz 2 bis 4 gilt entsprechend. Gilt f�r den Beamten eine vor der Vollendung des 
		65. Lebensjahres liegende Altersgrenze, tritt sie in den F�llen des Satzes 1 Nr. 1 und 3 an die Stelle des 65. Lebensjahres. 
		Gilt f�r den Beamten eine nach Vollendung des 67. Lebensjahres liegende Altersgrenze, wird in den F�llen des Satzes 1 Nr. 2 
		nur die Zeit bis zum Ablauf des Monats ber�cksichtigt, in dem der Beamte das 67. Lebensjahr vollendet. In den F�llen des 
		Satzes 1 Nr. 2 ist das Ruhegehalt nicht zu vermindern, wenn der Beamte zum Zeitpunkt des Eintritts in den Ruhestand das 65. Lebensjahr 
		vollendet und mindestens 45 Jahre mit ruhegehaltf�higen Dienstzeiten nach den �� 6, 8 bis 10 und nach � 14a Abs. 2 Satz 1 
		ber�cksichtigungsf�higen Pflichtbeitragszeiten, soweit sie nicht im Zusammenhang mit Arbeitslosigkeit stehen, und Zeiten 
		nach � 50d sowie Zeiten einer dem Beamten zuzuordnenden Erziehung eines Kindes bis zu dessen vollendetem zehnten Lebensjahr 
		zur�ckgelegt hat. In den F�llen des Satzes 1 Nr. 3 ist das Ruhegehalt nicht zu vermindern, wenn der Beamte zum Zeitpunkt 
		des Eintritts in den Ruhestand das 63. Lebensjahr vollendet und mindestens 40 Jahre mit ruhegehaltf�higen Dienstzeiten nach 
		den �� 6, 8 bis 10 und nach � 14a Abs. 2 Satz 1 ber�cksichtigungsf�higen Pflichtbeitragszeiten, soweit sie nicht im Zusammenhang 
		mit Arbeitslosigkeit stehen, und Zeiten nach � 50d sowie Zeiten einer dem Beamten zuzuordnenden Erziehung eines Kindes bis zu 
		dessen vollendetem zehnten Lebensjahr zur�ckgelegt hat. Soweit sich bei der Berechnung nach den S�tzen 5 und 6 Zeiten �berschneiden, 
		sind diese nur einmal zu ber�cksichtigen.
	*/
	
	
	/**
	 * 
	 * @return
	 */
	@Override
	public float calculateErdientesRuhegehalt() {
		return ServiceRegistry.getInstance().getRuhegehaltsberechnungAktuellesRechtService().calculateErdientesRuhegehalt(this);
	}
	
	

	
	/**
	 * Abh�ngig von der Art der Ruhegehaltsbeantragung werden unterschiedliche Abschl�ge am Ruhegehalt vorgenommen.
	 * Deshalb erfolgt die konkrete Implementierung in den spezifische Ruhegehaltsberechnungen.
	 * 
	 * @return Abschlagsfaktor
	 */
	@Override
	public abstract float calculateAbschlag_Para_14_BeamtVG();
	
	
	@Override
	public abstract float calculateMaxAbschlag_Para_14_BeamtVG();
	
	
	/**
	 * Liefert den typ der Pension als String zur�ck.
	 * @return
	 */
	public abstract String getPensionTyp();

	

	/**
	 * Validierung der f�r die Ruhegehaltsberehnung eingegebenen Parameter sowie der gesetzlichen Bestimmungen und Voraussetzungen f�r die verschiedenen Arten
	 * des Ruhestand.
	 * 
	 * @return ValidationResult
	 */
	@Override
	public ValidationResult validate() {
		return ServiceRegistry.getInstance().getValidationService().validate(this);
	}

	

	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	public IBesoldungstabelle getBesoldungstabelle() {
		return besoldungstabelle;
	}


	public void setBesoldungstabelle(IBesoldungstabelle besoldungstabelle) {
		this.besoldungstabelle = besoldungstabelle;
	}


	public Set<ITimePeriod> getTimePeriods() {
		return timePeriods;
	}

	public Set<IDeductionPart> getDeductionParts() {
		return deductionParts;
	}
	
	public Set<IAdditionPart> getAdditionParts() {
		return additionParts;
	}

	
	public void setTimePeriods(Set<ITimePeriod> timePeriods) {
		this.timePeriods = timePeriods;
	}
	
	public void setDeductionParts(Set<IDeductionPart> deductionParts) {
		this.deductionParts = deductionParts;
	}
	
	public void setAdditionParts(Set<IAdditionPart> additionParts) {
		this.additionParts = additionParts;
	}

	
	@Override
	public void addTimePeriod(ITimePeriod timePeriod) {
		this.timePeriods.add(timePeriod);
	}

	@Override
	public void addDeductionPart(IDeductionPart deductionPart) {
		this.deductionParts.add(deductionPart);
	}
	
	@Override
	public void addAdditionPart(IAdditionPart additionPart) {
		this.additionParts.add(additionPart);
	}

	
	public void removeTimePeriod(ITimePeriod timePeriod) {
		this.timePeriods.remove(timePeriod);
	}

	public void removeDeductionPart(IDeductionPart deductionPart) {
		this.deductionParts.remove(deductionPart);
	}
	
	public void removeAdditionPart(IAdditionPart additionPart) {
		this.additionParts.remove(additionPart);
	}

	
	public KindererziehungszeitenZuschlag getKindererziehungsZuschlag() {
		return kindererziehungsZuschlag;
	}


	public void setKindererziehungsZuschlag(KindererziehungszeitenZuschlag kindererziehungsZuschlag) {
		this.kindererziehungsZuschlag = kindererziehungsZuschlag;
	}


	public int getSumYears() {
		return sumYears;
	}


	public int getRestDays() {
		return restDays;
	}


	public float getRuhegehaltsSatz() {
		return ruhegehaltsSatz;
	}


	public float getSummeRuhegehaltsfaehigeDienstbezuege() {
		return summeRuhegehaltsfaehigeDienstbezuege;
	}
	

	public float getAbzugPflegeleistung() {
		if(this.abzugPflegeleistung < 0.1) {
			this.calculatePflegeleistungsabzug();
		}
		return this.abzugPflegeleistung;
	}
	
	
	public float getFaktorAbzugPflegeleistungNachBeamtVG_50_f() {
		return ServiceRegistry.getInstance().getRuhegehaltsberechnungAktuellesRechtService().getFaktorAbzugPflegeleistung(this);
	}


	public float getAmtsabhaengigeMindestversorgung() {
		return amtsabhaengigeMindestversorgung;
	}


	public float getAmtsunabhaengigeMindestversorgung() {
		return amtsunabhaengigeMindestversorgung;
	}


	public boolean isVergleichsberechnungBeamtVG_PARA_85() {
		return vergleichsberechnungBeamtVG_PARA_85;
	}


	public void setVergleichsberechnungBeamtVG_PARA_85(boolean vergleichsberechnungBeamtVG_PARA_85) {
		this.vergleichsberechnungBeamtVG_PARA_85 = vergleichsberechnungBeamtVG_PARA_85;
	}


	public String getAnzwRecht() {
		return anzwRecht;
	}


	public int getTyp() {
		return typ;
	}

	
	

	
	
}
