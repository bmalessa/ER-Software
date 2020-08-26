package de.protin.support.pr.domain.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.besoldung.zuschlag.KindererziehungszeitenZuschlag;
import de.protin.support.pr.domain.besoldung.zuschlag.Para_50_Kindererziehungszeit;
import de.protin.support.pr.domain.service.IKindererziehungszeitenService;
import de.protin.support.pr.domain.utils.Constants;
import de.protin.support.pr.domain.utils.DateUtil;

public class KindererziehungszeitenServiceImpl implements IKindererziehungszeitenService{
	
	private Date date_19920101 = DateUtil.getDate("01.01.1992");

	
	private int rentenWertID;
	private float aktuellerRentenwert = 0.0f;
	
	
	public KindererziehungszeitenServiceImpl() {
		
	}
	

	@Override
	public float calculateKindererziehungszuschlag(IPension pension) {
		float kindererziehungszuschlag = 0.0f;
		KindererziehungszeitenZuschlag kindererziehungsZuschlag = pension.getKindererziehungsZuschlag();
		Set<Para_50_Kindererziehungszeit> kindererziehungszeiten = kindererziehungsZuschlag.getKindererziehungszeiten();
		
		for (Iterator iterator = kindererziehungszeiten.iterator(); iterator.hasNext();) {
			Para_50_Kindererziehungszeit kez = (Para_50_Kindererziehungszeit) iterator.next();
			kindererziehungszuschlag += this.calculateKindererziehungszuschlag(kez);
		}
		
		return kindererziehungszuschlag;
	}
	
	

	@Override
	public float calculateKindererziehungszuschlag(Para_50_Kindererziehungszeit kindererziehungszeit) {
		float kindererziehungszuschlag = 0.0f;
		Date birthDate = kindererziehungszeit.getBirthDate();
		if(birthDate.before(date_19920101)) {
			kindererziehungszuschlag += calculateKindererziehungszuschlagBis1992(kindererziehungszeit, aktuellerRentenwert);
		}
		else {
			kindererziehungszuschlag += calculateKindererziehungszuschlagAb1992(kindererziehungszeit, aktuellerRentenwert);
		}
		return kindererziehungszuschlag;
	}

	
	/**
	 * 
	 * @param kindererziehungszeit
	 * @param aktuellerRentenwert
	 * @return
	 */
	public float calculateKindererziehungszuschlagAb1992(Para_50_Kindererziehungszeit kindererziehungszeit, float aktuellerRentenwert) {
		int amountOfMonths =  kindererziehungszeit.getZulageMonths();
		float kindererziehungszuschlag = amountOfMonths * Constants.KINDERERZIEHUNGSZUSCHLAG_ENTGELTPUNKTE_MONAT_PARA_70_Abs_2_SGB_6 * aktuellerRentenwert; 
		return kindererziehungszuschlag;
	}

	
	
	/**
	 * 
	 * @param kindererziehungszeit
	 * @param aktuellerRentenwert
	 * @return
	 */
	public float calculateKindererziehungszuschlagBis1992(Para_50_Kindererziehungszeit kindererziehungszeit, float aktuellerRentenwert) {
		//erstmal nur übernommen aus der Berechnung calculateKindererziehungszuschlagAb1992().
		//TODO muss noch angepasst werden.
		int amountOfMonths =  kindererziehungszeit.getZulageMonths();
		float kindererziehungszuschlag = amountOfMonths * Constants.KINDERERZIEHUNGSZUSCHLAG_ENTGELTPUNKTE_MONAT_PARA_70_Abs_2_SGB_6 * aktuellerRentenwert; 
		return kindererziehungszuschlag;
	}

	

	@Override
	public void setRentenwertID(int rentenwertID) {
		this.rentenWertID = rentenwertID;
		
	}


	@Override
	public void setAktuellerRentenwert(float aktuellerRentenwert) {
		this.aktuellerRentenwert = aktuellerRentenwert;
	}

	







	
	
}
