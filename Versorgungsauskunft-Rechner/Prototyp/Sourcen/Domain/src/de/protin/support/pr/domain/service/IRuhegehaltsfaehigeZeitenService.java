package de.protin.support.pr.domain.service;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.utils.TimePeriodDetails;

public interface IRuhegehaltsfaehigeZeitenService {

	public TimePeriodDetails calculateRuhegehaltsfaehigeDienstzeiten_Para_14(IPension pension);
	
}
