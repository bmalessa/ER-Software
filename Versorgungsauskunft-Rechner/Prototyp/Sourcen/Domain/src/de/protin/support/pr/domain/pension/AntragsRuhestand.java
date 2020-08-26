package de.protin.support.pr.domain.pension;

import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.service.impl.ServiceRegistry;
import de.protin.support.pr.domain.utils.Constants;


/**
 * BeamtVG §14
 * 
(3) Das Ruhegehalt vermindert sich um 3,6 Prozent für jedes Jahr, um das der Beamte

1.	vor Ablauf des Monats, in dem er das 65. Lebensjahr vollendet, nach § 52 Abs. 1 und 2 des Bundesbeamtengesetzes 
	in den Ruhestand versetzt wird,
	(das 62. Lebensjahr vollendet und schwerbehindert im Sinne des § 2 Abs. 2 des Neunten Buches Sozialgesetzbuch sind.)
	(wie unter Nr 1, aber vor 1952 -1963 geboren, gestaffelt von 60 Jahren bis 61 Jahre und 10 Monate)
	
2.	vor Ablauf des Monats, in dem er die für ihn geltende gesetzliche Altersgrenze erreicht, nach § 52 Abs. 3 des 
	Bundesbeamtengesetzes in den Ruhestand versetzt wird,
	Beamte auf Lebenszeit können auf ihren Antrag in den Ruhestand versetzt werden, wenn sie das 63. Lebensjahr vollendet haben.)
	
	
	... die Minderung des Ruhegehalts darf 10,8 vom Hundert in den Fällen der Nummern 1 .. und 14,4 vom Hundert in den Fällen 
	der Nummer 2 nicht übersteigen. 
	Absatz 1 Satz 2 bis 4 gilt entsprechend. Gilt für den Beamten eine vor der Vollendung des 
	65. Lebensjahres liegende Altersgrenze, tritt sie in den Fällen des Satzes 1 Nr. 1 und 3 an die Stelle des 65. Lebensjahres. 
	Gilt für den Beamten eine nach Vollendung des 67. Lebensjahres liegende Altersgrenze, wird in den Fällen des Satzes 1 Nr. 2 
	nur die Zeit bis zum Ablauf des Monats berücksichtigt, in dem der Beamte das 67. Lebensjahr vollendet. 
	
	In den Fällen des Satzes 1 Nr. 2 ist das Ruhegehalt nicht zu vermindern, wenn der Beamte zum Zeitpunkt des Eintritts in den Ruhestand das 65. Lebensjahr 
	vollendet und mindestens 45 Jahre mit ruhegehaltfähigen Dienstzeiten nach den §§ 6, 8 bis 10 und nach § 14a Abs. 2 Satz 1 
	berücksichtigungsfähigen Pflichtbeitragszeiten, soweit sie nicht im Zusammenhang mit Arbeitslosigkeit stehen, und Zeiten 
	nach § 50d sowie Zeiten einer dem Beamten zuzuordnenden Erziehung eines Kindes bis zu dessen vollendetem zehnten Lebensjahr 
	zurückgelegt hat.
*/
public class AntragsRuhestand extends AbstractPension {
	
	
	public AntragsRuhestand(Person person, String anzuwendendesRecht) {
		super(person, AbstractPension.ANTRAG_ALTERSGRENZE, anzuwendendesRecht);
	}
	
	public AntragsRuhestand(Person person, int type, String anzuwendendesRecht ) {
		super(person, type, anzuwendendesRecht);
	}
	

	
	/**
	 * Liefert die Prozentzahl des Ruhegehaltsabschlag zurück. 
	 */
	@Override
	public float calculateAbschlag_Para_14_BeamtVG() {
		return ServiceRegistry.getInstance().getRuhegehaltsabschlagService().calculateAbschlag_Para_14_BeamtVG(this);
	}

	
	@Override
	public float calculateMaxAbschlag_Para_14_BeamtVG() {
		if(this.getPerson().isSchwerbehindert()) {
			return Constants.MAX_MINDERUNG_PARA_14_Abs_3_DDU_BeamtVG;
		}
		else {
			return Constants.MAX_MINDERUNG_PARA_14_Abs_3_ANTRAG_BeamtVG;
		}
	}
	
	
	
	@Override
	public String getPensionTyp() {
		return new String("Ruhestand auf Antrag");
	}   





}
