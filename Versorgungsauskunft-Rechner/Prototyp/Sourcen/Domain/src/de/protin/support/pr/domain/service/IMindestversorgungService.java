package de.protin.support.pr.domain.service;

import de.protin.support.pr.domain.IPension;

public interface IMindestversorgungService {
	
	public float calculateMindestversorgung(IPension pension);
	
	public float calculateAmtsunabhaengigeMindestversorgung(IPension pension);
	
	public float calculateAmtsabhaengigeMindestversorgung(IPension pension);
	
}
