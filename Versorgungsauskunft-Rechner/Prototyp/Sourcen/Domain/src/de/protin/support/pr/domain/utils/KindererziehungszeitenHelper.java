package de.protin.support.pr.domain.utils;

import de.protin.support.pr.domain.besoldung.zuschlag.Para_50_Kindererziehungszeit;

public class KindererziehungszeitenHelper {

	public static final String CHILD_NAME = "Vorname Kind";
	public static final String BIRTHDATE = "geboren am";
	public static final String DATE_FROM = "von";
	public static final String DATE_TO = "bis";
	public static final String AMOUNT_OF_MONTHS = "Monate";
	public static final String KEZ_MONTHS = "KEZ/Monate";
	public static final String ANSPRUCH = "Anspruch?";
	

	
	public static String[] getTableColums() {
		String[] columns = new String[] {
			CHILD_NAME,
			BIRTHDATE,
			DATE_FROM,
			DATE_TO,
			AMOUNT_OF_MONTHS,
			KEZ_MONTHS,
			ANSPRUCH
		};
		return columns;
	}

	public static String[] getAnspruchLabels() {
		String[] labels = new String[] {
				Para_50_Kindererziehungszeit.VOLLER_KEZ_ANSPRUCH,
				Para_50_Kindererziehungszeit.TEIL_KEZ_ANSPRUCH,
				Para_50_Kindererziehungszeit.KEIN_KEZ_ANSPRUCH
		};
		return labels;
	}
	
	
	
}
