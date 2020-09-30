package de.protin.support.pr.domain.service.impl.util;

import java.text.ParseException;
import java.util.Date;

import de.protin.support.pr.domain.besoldung.zuschlag.KindererziehungszeitenZuschlag;
import de.protin.support.pr.domain.utils.DateUtil;

public class Rentenwert {

	
	/**
	 * 
	 * @param dateOfRetirement
	 * @param rentenwertID
	 * @return
	 */
	public float getAktuellerRentenwert(Date dateOfRetirement, int rentenwertID) {
		
			
			try {
				if(dateOfRetirement.after(DateUtil.parseDateString("30.06.2016")) && dateOfRetirement.before(DateUtil.parseDateString("01.07.2017"))) {
					if(KindererziehungszeitenZuschlag.RENTENWERT_OST == rentenwertID) {
						return 28.66f;
					}
					else {
						return 30.45f;
					}
				}
				else if (dateOfRetirement.after(DateUtil.parseDateString("30.06.2017")) && dateOfRetirement.before(DateUtil.parseDateString("01.07.2018"))) {
					if(KindererziehungszeitenZuschlag.RENTENWERT_OST == rentenwertID) {
						return 29.69f;
					}
					else {
						return  31.03f;
					}
				}
				else if (dateOfRetirement.after(DateUtil.parseDateString("30.06.2018")) && dateOfRetirement.before(DateUtil.parseDateString("01.07.2019"))) {
					if(KindererziehungszeitenZuschlag.RENTENWERT_OST == rentenwertID) {
						return 30.69f;
					}
					else {
						return 32.03f;
					}
				}
				else if (dateOfRetirement.after(DateUtil.parseDateString("30.06.2019")) && dateOfRetirement.before(DateUtil.parseDateString("01.07.2020"))) {
					if(KindererziehungszeitenZuschlag.RENTENWERT_OST == rentenwertID) {
						return 31.89f;
					}
					else {
						return 33.05f;
					}
				}
				else if (dateOfRetirement.after(DateUtil.parseDateString("30.06.2020")) && dateOfRetirement.before(DateUtil.parseDateString("01.07.2021"))) {
					if(KindererziehungszeitenZuschlag.RENTENWERT_OST == rentenwertID) {
						return 33.23f;
					}
					else {
						return 34.19f;
					}
				}
				else {
					//Default ist dann ansonsten der aktuell letzte bekannte Wert für RENETENWERT_WEST
					return 34.19f;
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			return 1.0f;
			
		}
	
	
}
