package de.protin.support.pr.domain.service;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.pension.AntragsRuhestand;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.utils.TimePeriodDetails;

public interface IRuhegehaltsabschlagService {
	
	public float calculateAbschlag_Para_14_BeamtVG(AntragsRuhestand pension);
	
	public float calculateAbschlag_Para_14_BeamtVG(Dienstunfaehigkeit pension);

	public TimePeriodDetails calculateTimePeriodForVersorgungsabschlag(IPension pension);
}
