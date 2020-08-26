package de.protin.support.pr.domain.service;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.besoldung.zuschlag.Para_50_Kindererziehungszeit;

public interface IKindererziehungszeitenService {

		
	public float calculateKindererziehungszuschlag(IPension pension);

	public float calculateKindererziehungszuschlag(Para_50_Kindererziehungszeit kindererziehungszeit);
	
	public void setRentenwertID(int rentenwertID);

	public void setAktuellerRentenwert(float aktuellerRentenwert);
	
}
