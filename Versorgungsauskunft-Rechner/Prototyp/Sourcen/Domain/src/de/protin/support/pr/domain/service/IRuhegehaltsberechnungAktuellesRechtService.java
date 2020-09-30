package de.protin.support.pr.domain.service;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;

public interface IRuhegehaltsberechnungAktuellesRechtService {
	
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
	public float calculateRuhegehaltsfaehigenDienstbezug(IPension pension);
	
	/**
	 * § 14 Höhe des Ruhegehalts
			(1) Das Ruhegehalt beträgt für jedes Jahr ruhegehaltfähiger Dienstzeit 1,79375 Prozent, insgesamt jedoch 
			höchstens 71,75 Prozent, der ruhegehaltfähigen Dienstbezüge. Bei der Berechnung der Jahre ruhegehaltfähiger 
			Dienstzeit werden unvollständige Jahre als Dezimalzahl angegeben. Dabei wird ein Jahr mit 365 Tagen angesetzt 
			und wird das Ergebnis kaufmännisch auf zwei Dezimalstellen gerundet. Der Ruhegehaltssatz wird ebenfalls kaufmännisch 
			auf zwei Dezimalstellen gerundet.
	
	 * @return
	 */
	public float calculateRuhegehaltssatz_Para_14_1_Neue_Fassung(IPension pension);
	
	

	/**
	 * 
	 * @param pension
	 * @return
	 */
	public float calculateErdientesRuhegehalt(IPension pension);
	
	

	
	/**
	 * 
	 * @return
	 */
	public float getMaxAbzugPflegeleistung(IPension pension);
	
	
	public float getFaktorAbzugPflegeleistung(IPension pension);

}
