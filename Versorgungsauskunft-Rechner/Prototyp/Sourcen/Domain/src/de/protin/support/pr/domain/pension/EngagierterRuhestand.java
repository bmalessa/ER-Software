package de.protin.support.pr.domain.pension;

import de.protin.support.pr.domain.Person;

public class EngagierterRuhestand extends AbstractPension {

	public EngagierterRuhestand (Person person, String anzuwendendesRecht) {
		super(person, AbstractPension.ENGAGIERTER_RUHESTAND, anzuwendendesRecht);
	}
	

	@Override
	/**
	 * Bei Pension aufgrund Engagierten Ruhestand erfolgt kein Abzug gem. §14 BeamtVG
	 * 
	 */
	public float calculateAbschlag_Para_14_BeamtVG() {
		return 0.0f;
	}
	
	
	@Override
	public float calculateMaxAbschlag_Para_14_BeamtVG() {
		return 0.0f;
	}
	


	@Override
	public String getPensionTyp() {
		return new String("Engagierter Ruhestand");
	}












}
