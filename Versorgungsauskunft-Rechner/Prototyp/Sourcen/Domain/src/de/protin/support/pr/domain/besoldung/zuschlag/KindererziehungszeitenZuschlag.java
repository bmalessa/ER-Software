package de.protin.support.pr.domain.besoldung.zuschlag;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.service.IKindererziehungszeitenService;
import de.protin.support.pr.domain.service.impl.ServiceRegistry;

/**
 * � 50a BeamtVG Kindererziehungszuschlag

 * 
 *F�r nach dem 31.12.1991 geborene Kinder
		
	Die Kindererziehungszeit beginnt nach Ablauf des Monats der Geburt und endet nach 36
	Kalendermonaten, sp�testens jedoch mit Ablauf des Monats, in dem die Erziehung endet. Wird
	w�hrend dieses Zeitraums ein weiteres Kind erzogen, wird die 36-monatige Kindererziehungszeit
	f�r jedes Kind gesondert ber�cksichtigt, indem sich die Kindererziehungszeit um die Anzahl der
	Kalendermonate der gleichzeitigen Erziehung verl�ngert.

F�r vor dem 01.01.1992 geborene Kinder

	Die Kindererziehungszeit beginnt nach Ablauf des Monats der Geburt und endet nach 24
	Kalendermonaten, sp�testens jedoch mit Ablauf des Monats, in dem die Erziehung endet. Wird
	w�hrend dieses Zeitraums ein weiteres Kind erzogen, wird die 24-monatige Kindererziehungszeit
	f�r jedes Kind gesondert ber�cksichtigt, indem sich die Kindererziehungszeit um die Anzahl der
	Kalendermonate der gleichzeitigen Erziehung verl�ngert
	
	
	
H�he des Kindererziehungszuschlages

	Die H�he des Kindererziehungszuschlags wird zum Beginn des Ruhestands festgesetzt und
	entspricht f�r jeden Monat der Kindererziehungszeit dem in � 70 Abs. 2 S. 1 SGB VI bestimmten
	Bruchteil (= 0,0833 Entgeltpunkte - EP) des aktuellen Rentenwerts. 12 Monate zuzuordnende
	Kindererziehungszeit ergeben einen Entgeltpunkt (12 x 0,0833 EP, der dem Wert eines aktuellen
	Rentenwertes 2 entspricht.

	2 Stand 01.07.2019: 31,89 EUR (Ost) / 33,05 EUR (West)


Die Gew�hrung des Kindererziehungszuschlags wird in zweifacher Hinsicht begrenzt:

	Das Versorgungsrecht ist gegen�ber dem Rentenrecht nachrangig. Das Ruhegehalt wird also
	nicht um einen Kindererziehungszuschlag erh�ht, wenn die Kindererziehungszeit rentenrechtlich
	ber�cksichtigt wird.
	Durch den Kindererziehungszuschlag darf die H�chstversorgung nicht �berschritten werden
	(= 71,75 % der ruhegehaltf�higen Dienstbez�ge aus der Endstufe der Besoldungsgruppe, aus
	der sich das Ruhegehalt berechnet; � 57 Abs. 5 Satz 1 S�chsBeamtVG). Haben
	Versorgungsberechtigte ihre H�chstversorgung erreicht, wird jedoch abweichend von Satz 1 je
	Kind maximal ein Kindererziehungszuschlag in H�he des Betrages, der dem Wert eines zum
	Versorgungsbeginn geltenden rentenrechtlichen Entgeltpunktes entspricht, �ber die
	H�chstversorgung hinaus gew�hrt.

 * 
 * @author Bernd
 *
 */
public class KindererziehungszeitenZuschlag {
	
	
	public static final int RENTENWERT_WEST = 1;
	public static final int RENTENWERT_OST = 2;
	
	public static final int KEZ_VOR_1992 = 1;
	public static final int KEZ_NACH_1991 = 2;
	
	
	
	private IKindererziehungszeitenService kezService;
	
	
	protected Set<Para_50_Kindererziehungszeit> kindererziehungszeitenVor1992;
	protected Set<Para_50_Kindererziehungszeit> kindererziehungszeitenNach1991;
	//protected int rentenwertID;
	
	
	public KindererziehungszeitenZuschlag() {
		this.kindererziehungszeitenVor1992 = new HashSet<Para_50_Kindererziehungszeit>();
		this.kindererziehungszeitenNach1991 = new HashSet<Para_50_Kindererziehungszeit>();
		this.kezService = ServiceRegistry.getInstance().getKindererziehungszeitenService();
	}

	
	public Set<Para_50_Kindererziehungszeit> getKindererziehungszeiten() {
		Set<Para_50_Kindererziehungszeit> result = new HashSet<Para_50_Kindererziehungszeit>();
		result = getKindererziehungszeitenVor1992();
		result.addAll(getKindererziehungszeitenNach1991());
		return result;
	}

	public Set<Para_50_Kindererziehungszeit> getKindererziehungszeitenVor1992() {
		return kindererziehungszeitenVor1992;
	}

	public Set<Para_50_Kindererziehungszeit> getKindererziehungszeitenNach1991() {
		return kindererziehungszeitenNach1991;
	}

	public void setKindererziehungszeiten(Set<Para_50_Kindererziehungszeit> kindererziehungszeiten, int typ) {
		if(typ == KEZ_VOR_1992) {
			this.kindererziehungszeitenVor1992 = kindererziehungszeiten;
		}
		else if (typ == KEZ_NACH_1991){
			this.kindererziehungszeitenNach1991 = kindererziehungszeiten;
		}
		
	}
	
	public void addKindererziehungszeit(Para_50_Kindererziehungszeit kindererziehungszeit, int typ) {
		if(typ == KEZ_VOR_1992) {
			this.kindererziehungszeitenVor1992.add(kindererziehungszeit);
		}
		else if (typ == KEZ_NACH_1991){
			this.kindererziehungszeitenNach1991.add(kindererziehungszeit);
		}
	}
	
	
	public void removeKindererziehungszeit(Para_50_Kindererziehungszeit kindererziehungszeit, int typ) {
		if(typ == KEZ_VOR_1992) {
			this.kindererziehungszeitenVor1992.remove(kindererziehungszeit);
		}
		else if (typ == KEZ_NACH_1991){
			this.kindererziehungszeitenNach1991.remove(kindererziehungszeit);
		}
	}
	
	
	
	/**
	 * Liefert den berechneten Kindererziehungszuschlag zur�ck.
	 * Eine ggf. notwendige K�rzung (weil durch diesen Zuschlag die Summe des Ruhegehalt �ber 71.75% ansteigt)
	 * erfolgt nicht hier sondern innerhalb der �bergeordneten Pensionsberechnung.  
	 * 
	 * H�he des KEZ  = Anzahl Kalendermonate x  0,0833 x aktueller Rentenwert (West/Ost)
	 *
	 * @return
	 */
	
	public float calculateKindererziehungszuschlag(IPension pension) {
		float kindererziehungszuschlag = 0.0f;
		for (Iterator<Para_50_Kindererziehungszeit> iterator = kindererziehungszeitenVor1992.iterator(); iterator.hasNext();) {
			Para_50_Kindererziehungszeit kindererziehungszeit = (Para_50_Kindererziehungszeit) iterator.next();
			kindererziehungszuschlag += this.kezService.calculateKindererziehungszuschlag(kindererziehungszeit, pension);
		}
		for (Iterator<Para_50_Kindererziehungszeit> iterator = kindererziehungszeitenNach1991.iterator(); iterator.hasNext();) {
			Para_50_Kindererziehungszeit kindererziehungszeit = (Para_50_Kindererziehungszeit) iterator.next();
			kindererziehungszuschlag += this.kezService.calculateKindererziehungszuschlag(kindererziehungszeit, pension);
		}
		
		return kindererziehungszuschlag;
	}

	
}
