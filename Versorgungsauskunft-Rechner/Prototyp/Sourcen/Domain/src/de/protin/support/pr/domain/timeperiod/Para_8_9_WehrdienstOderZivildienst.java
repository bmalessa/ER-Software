package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;

/**
 * � 8 Berufsm��iger Wehrdienst und vergleichbare Zeiten

		1) Als ruhegehaltf�hig gilt die Dienstzeit, in der ein Beamter vor der Berufung in das Beamtenverh�ltnis berufsm��ig im Dienst der Bundeswehr, 
		der Nationalen Volksarmee der ehemaligen Deutschen Demokratischen Republik oder im Vollzugsdienst der Polizei gestanden hat.


   � 9 Nichtberufsm��iger Wehrdienst und vergleichbare Zeiten
   
       1) Als ruhegehaltf�hig gilt die Zeit, w�hrend der ein Beamter vor der Berufung in das Beamtenverh�ltnis
			1. nichtberufsm��igen Wehrdienst in der Bundeswehr oder der Nationalen Volksarmee der ehemaligen Deutschen Demokratischen Republik oder einen 
			   vergleichbaren zivilen Ersatzdienst oder Polizeivollzugsdienst geleistet hat oder
			2. sich insgesamt l�nger als drei Monate in einem Gewahrsam (� 1 Abs. 1 Nr. 1 in Verbindung mit � 9 des H�ftlingshilfegesetzes in der bis 
			   zum 28. Dezember 1991 geltenden Fassung) befunden hat oder
			3. sich auf Grund einer Krankheit oder Verwundung als Folge eines Dienstes nach Nummer 1 oder im Sinne des � 8 Abs. 1 im Anschluss an die Entlassung 
			   arbeitsunf�hig in einer Heilbehandlung befunden hat.

 * @author Bernd
 *
 */
public class Para_8_9_WehrdienstOderZivildienst extends BaseTimePeriod {

	public static final String ZEITART = "Wehrdienst-/Zivildienst und vergleichbare Zeiten (�8,9 BeamtVG)";
	
	public Para_8_9_WehrdienstOderZivildienst(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.BUNDESWEHR_ODER_ZIVILDIENST_PARA_8_9, startDate, endDate, factor);
	}

	public String getZeitart() {
		return ZEITART;
	}
}
