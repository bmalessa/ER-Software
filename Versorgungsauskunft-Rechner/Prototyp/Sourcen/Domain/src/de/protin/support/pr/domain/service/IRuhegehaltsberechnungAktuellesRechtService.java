package de.protin.support.pr.domain.service;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;

public interface IRuhegehaltsberechnungAktuellesRechtService {
	
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
	public float calculateRuhegehaltsfaehigenDienstbezug(IPension pension);
	
	/**
	 * � 14 H�he des Ruhegehalts
			(1) Das Ruhegehalt betr�gt f�r jedes Jahr ruhegehaltf�higer Dienstzeit 1,79375 Prozent, insgesamt jedoch 
			h�chstens 71,75 Prozent, der ruhegehaltf�higen Dienstbez�ge. Bei der Berechnung der Jahre ruhegehaltf�higer 
			Dienstzeit werden unvollst�ndige Jahre als Dezimalzahl angegeben. Dabei wird ein Jahr mit 365 Tagen angesetzt 
			und wird das Ergebnis kaufm�nnisch auf zwei Dezimalstellen gerundet. Der Ruhegehaltssatz wird ebenfalls kaufm�nnisch 
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
