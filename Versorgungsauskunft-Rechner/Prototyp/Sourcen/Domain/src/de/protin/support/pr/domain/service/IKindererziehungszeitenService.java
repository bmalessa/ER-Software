package de.protin.support.pr.domain.service;

import java.util.Date;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.besoldung.zuschlag.Para_50_Kindererziehungszeit;

public interface IKindererziehungszeitenService {

		
	public float calculateKindererziehungszuschlag(IPension pension);

	public float calculateKindererziehungszuschlag(Para_50_Kindererziehungszeit kindererziehungszeit, IPension pension);
	
	public float getAktuellerRentenwert(Date dateOfRetirement, int rentenwertID); 
	
}
