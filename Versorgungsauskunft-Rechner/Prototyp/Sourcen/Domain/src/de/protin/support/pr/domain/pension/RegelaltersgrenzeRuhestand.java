package de.protin.support.pr.domain.pension;

import de.protin.support.pr.domain.Person;



/**
 * Regelaltersgrenze enstprechend der gesetzliche Grundlagen, d.h. es wird kein Abzug nach BeamtVG §14 Abs. 3 vorgenommen.
 * Es existieren für einige Beamte eigenständige Regelaltersgrenzen, z.B. bei der Feuerwehr oder Polizei etc.
 * Wenn die Berechnung auf der Basis der Klasse RegelaltersgrenzeRuhestand durchgeführt wird, wird nicht mehr geprüft, ob
 * die besondere Altersgrenze plausibel ist. Wenn das Alter des Antragsteller nicht der gesetztlichen Regelaltersgrenze entspricht, 
 * wird davon ausgegangen, dass eine besondere Altersgrenze vorliegt oder das 45 ruhegehaltsfähige Jahre vorhanden sind.  
 *  
 * @author Bernd
 *
 */
public class RegelaltersgrenzeRuhestand extends AbstractPension {
	
	boolean besondereAltergrenze; 
	
	/**
	 * 
	 * @param person
	 * @param validate - gesetzliche Regelaltersgrenze prüfen (67 Jahre ab Jahrgang 1964)
	 */
	public RegelaltersgrenzeRuhestand(Person person, String anzuwendendesRecht) {
		super(person, AbstractPension.REGEL_ALTERSGRENZE, anzuwendendesRecht);
	}
	

	/**
	 * 
	 * @param person
	 * @param besondere Altersgrenze - in jahren
	 * 
	 */
	public RegelaltersgrenzeRuhestand(Person person, boolean besondereAltersgrenze, String anzuwendendesRecht) {
		super(person, AbstractPension.REGEL_ALTERSGRENZE, anzuwendendesRecht);
		this.besondereAltergrenze = besondereAltersgrenze;
	}


	@Override
	public float calculateAbschlag_Para_14_BeamtVG() {
		return 0.0f;
	}

	
	@Override
	public float calculateMaxAbschlag_Para_14_BeamtVG() {
		return 0.0f;
	}
	

	@Override
	public String getPensionTyp() {
		return new String("Ruhestand wegen Erreichen der Regelaltersgrenze");
	}

}
