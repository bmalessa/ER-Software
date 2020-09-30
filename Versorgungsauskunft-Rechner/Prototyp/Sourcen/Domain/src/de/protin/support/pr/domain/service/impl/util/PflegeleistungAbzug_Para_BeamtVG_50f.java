package de.protin.support.pr.domain.service.impl.util;

import java.text.ParseException;
import java.util.Date;

import de.protin.support.pr.domain.utils.DateUtil;

public class PflegeleistungAbzug_Para_BeamtVG_50f {
	
	
	private static PflegeleistungAbzug_Para_BeamtVG_50f instance;

	private PflegeleistungAbzug_Para_BeamtVG_50f() {
	}

	public static PflegeleistungAbzug_Para_BeamtVG_50f getInstance() {
		if (null == instance) {
			instance = new PflegeleistungAbzug_Para_BeamtVG_50f();
		}
		return instance;
	}
	
	
	
	public float getMaxAbzugPflegeleistung(Date dateOfRetirement) {
		
		try {
			if(dateOfRetirement.after(DateUtil.parseDateString("31.12.2015")) && dateOfRetirement.before(DateUtil.parseDateString("01.01.2017"))) {
				return 49.79f;
			}
			else if (dateOfRetirement.after(DateUtil.parseDateString("31.12.2016")) && dateOfRetirement.before(DateUtil.parseDateString("01.01.2018"))) {
				return 55.46f;
			}
			else if (dateOfRetirement.after(DateUtil.parseDateString("31.12.2017")) && dateOfRetirement.before(DateUtil.parseDateString("01.01.2019"))) {
				return 56.42f;
			}
			else if (dateOfRetirement.after(DateUtil.parseDateString("31.12.2018")) && dateOfRetirement.before(DateUtil.parseDateString("01.01.2021"))) {
				return 69.20f;
			}
			else {
				//Default ist dann ansonsten der aktuell letzte bekannte Wert
				return 69.20f;
			}
			
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return 69.20f;
		
	}
	
	
	
	public float getFaktorAbzugPflegeleistung(Date dateOfRetirement) {
		
		try {
			if(dateOfRetirement.after(DateUtil.parseDateString("31.12.2015")) && dateOfRetirement.before(DateUtil.parseDateString("01.01.2017"))) {
				return 1.175f;
			}
			else if (dateOfRetirement.after(DateUtil.parseDateString("31.12.2016")) && dateOfRetirement.before(DateUtil.parseDateString("01.01.2018"))) {
				return 1.275f;
			}
			else if (dateOfRetirement.after(DateUtil.parseDateString("31.12.2017")) && dateOfRetirement.before(DateUtil.parseDateString("01.01.2019"))) {
				return 1.275f;
			}
			else if (dateOfRetirement.after(DateUtil.parseDateString("31.12.2018")) && dateOfRetirement.before(DateUtil.parseDateString("01.01.2021"))) {
				return 1.525f;
			}
			else {
				//Default ist dann ansonsten der aktuell letzte bekannte Wert
				return 1.525f;
			}
			
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		return 1.525f;
		
	}
}
