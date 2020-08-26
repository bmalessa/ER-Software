package de.protin.support.pr.domain.pension;

import de.protin.support.pr.domain.Person;


/**
 * Diese Type wird   verwendet, wenn der Vorruhestand nach abgeleisteter Altersteilzeit beantragt wird.
 * 
 * Die Berechnung des Abschlag nach $14 BeamtVG orientiert sich bei diesem Typ der Ruhestandberechnung an
 * der Berechnung eines Ruhestand auf Antrag. D.h., wenn nach abgeleisteter Altersteilzeit die gesetzliche
 * Altersgrenze bzw. 45 ruhegehaltsfähige Dienstjahre noch nicht erreicht sind, dann werden Abzüge vom
 * Ruhegehalt berechnet.
 *   
 * @author Bernd
 *
 */
public class Vorruhestand extends AntragsRuhestand {

	
	public Vorruhestand(Person person, String anzuwendendesRecht) {
		super(person, AbstractPension.VORRUHESTAND, anzuwendendesRecht);
	}

	@Override
	public String getPensionTyp() {
		return new String("Vorruhestand");
	}
}
