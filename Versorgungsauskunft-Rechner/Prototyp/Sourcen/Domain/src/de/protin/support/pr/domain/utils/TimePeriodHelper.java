package de.protin.support.pr.domain.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.timeperiod.AufbauhilfeOst;
import de.protin.support.pr.domain.timeperiod.Para_10_VordienstzeitenAlsArbeitnehmerImOeffentlichenDienst;
import de.protin.support.pr.domain.timeperiod.Para_11_SonstigeZeiten;
import de.protin.support.pr.domain.timeperiod.Para_12_FoerderlichePraktischeAusbildung;
import de.protin.support.pr.domain.timeperiod.Para_12_FoerderlichePraktischeHauptberuflicheTaetigkeit;
import de.protin.support.pr.domain.timeperiod.Para_12_VorgeschriebeneFachschulzeiten;
import de.protin.support.pr.domain.timeperiod.Para_12_VorgeschriebeneHochschulstudienzeiten;
import de.protin.support.pr.domain.timeperiod.Para_12_VorgeschriebenePraktischeAusbildung;
import de.protin.support.pr.domain.timeperiod.Para_12_VorgeschriebenePraktischeHauptberuflicheTaetigkeit;
import de.protin.support.pr.domain.timeperiod.Para_13_VerwendungInLaendernMitGesundheitsschaedlichenKlima;
import de.protin.support.pr.domain.timeperiod.Para_6_AltersteilzeitBlockmodel;
import de.protin.support.pr.domain.timeperiod.Para_6_AltersteilzeitTeilzeitmodel;
import de.protin.support.pr.domain.timeperiod.Para_6_AusbildungBeamterAufWiderruf;
import de.protin.support.pr.domain.timeperiod.Para_6_Beamterdienstzeit;
import de.protin.support.pr.domain.timeperiod.Para_8_9_WehrdienstOderZivildienst;
import de.protin.support.pr.domain.timeperiod.UOB_Ruhegehaltsfaehig;

public class TimePeriodHelper {
	
		public static final String NO = "Nr";
		public static final String ZEITART = "Zeitart";
		public static final String DATE_FROM = "von";
		public static final String DATE_TO = "bis";
		public static final String FACTOR = "Faktor";
		public static final String AMOUNT_OF_DAYS = "Tage";
		public static final String RGF_DAYS = "RGF/Tage";
		
		public static String[] getTableColums() {
			String[] columns = new String[] {
				NO,
				ZEITART,
				DATE_FROM,
				DATE_TO,
				FACTOR,
				AMOUNT_OF_DAYS,
				RGF_DAYS
			};
			return columns;
		}
		
		
		/**
		 * 	//siehe auch http://www.beamtenversorgung.nrw.de/fsiframe.wrkexec
			public static final int BEAMTEN_DIENSTZEIT_RUHEGEHALTSFAEHIG_PARA_6 = 1;
			public static final int AUSBILDUNG_BEAMTER_WIDERRUF_PARA_6 = 2;
			public static final int BUNDESWEHR_ODER_ZIVILDIENST_PARA_8_9 = 3;
			public static final int ANGESTELLTENVERHAELTNIS_IM_OEFFENTLICHEN_DIENST_PARA_10 = 4;
			public static final int UOB_RUHEGEHALTSFAEHIG = 5; 
			public static final int KINDERERZIEHUNGUNGSZEITEN_PARA_50 = 6;  // wird in GUI eigenständig erfasst und zugerechnet
			public static final int ALTERSTEILZEIT_BLOCKMODELL = 7;
			public static final int ALTERSTEILZEIT_TEILZEITMODELL = 8;
			public static final int AUFBAUHILFE_NEUE_BUNDESLAENDER = 9;
			public static final int VERWENDUNG_IN_LAENDERN_MIT_GESUNDHEITSSCHAEDLICHEM_KLIMA_PARA_13 = 10;
			
			//siehe auch Prot-In Rechner
			public static final int SONSTIGE_ZEITEN_PARA_11 = 11;
			public static final int VORGESCHR_FACHSCHULZEITEN_PARA_12_1_1 = 12;
			public static final int VORGESCHR_HOCHSCHULSTUDIEN_ZEITEN_PARA_12_1_1 = 13;
			public static final int VORGESCHR_PRAKTISCHE_AUSBILDUNG_PARA_12_1_1 = 14;
			public static final int VORGESCHR_PRAKTISCHE_HAUPTBERUFLICHE_TAETIGKEIT_PARA_12_1_2 = 15;
			public static final int FOERDERLICHE_PRAKTISCHE_AUSBILDUNG_PARA_12_2 = 16;
			public static final int FOERDERLICHE_PRAKTISCHE_HAUPTBERUFLICHE_TAETIGKEIT_PARA_12_2 = 17;
			public static final int STUDIENZEIT = 18;
		 * @param typ
		 * @return
		 */
		public static String getLabel(int typ) {
			switch(typ){
	        case 1:
	            return Para_6_Beamterdienstzeit.ZEITART;
	        case 2:
	        	return Para_6_AusbildungBeamterAufWiderruf.ZEITART;
	        case 3:
	            return Para_8_9_WehrdienstOderZivildienst.ZEITART;
	        case 4:
	        	return Para_10_VordienstzeitenAlsArbeitnehmerImOeffentlichenDienst.ZEITART;
	        case 5:
	        	return UOB_Ruhegehaltsfaehig.ZEITART;
	        case 7:
	        	return Para_6_AltersteilzeitBlockmodel.ZEITART;
	        case 8:
	        	return Para_6_AltersteilzeitTeilzeitmodel.ZEITART;
	        case 9:
	        	return AufbauhilfeOst.ZEITART;
	        case 10:
	        	return Para_13_VerwendungInLaendernMitGesundheitsschaedlichenKlima.ZEITART;
	        case 11:
	        	return Para_11_SonstigeZeiten.ZEITART;
	        case 12:
	        	return Para_12_VorgeschriebeneFachschulzeiten.ZEITART;
	        case 13:
	        	return Para_12_VorgeschriebeneHochschulstudienzeiten.ZEITART;
	        case 14:
	        	return Para_12_VorgeschriebenePraktischeAusbildung.ZEITART;
	        case 15:
	        	return Para_12_VorgeschriebenePraktischeHauptberuflicheTaetigkeit.ZEITART;
	        case 16:
	        	return Para_12_FoerderlichePraktischeAusbildung.ZEITART;
	        case 17:
	        	return Para_12_FoerderlichePraktischeHauptberuflicheTaetigkeit.ZEITART;
	        default:
	            return "--- Bitte zulässige ruhegehaltsfähige Zeitart auswählen ---";
	        }
		}
		
		
		
		public static String[] getTimePeriodTypeLabels() {
			
			String[] timePeriodTypes = new String[] {
		         Para_6_Beamterdienstzeit.ZEITART,
	        	 Para_6_AusbildungBeamterAufWiderruf.ZEITART,
	             Para_8_9_WehrdienstOderZivildienst.ZEITART,
	        	 Para_10_VordienstzeitenAlsArbeitnehmerImOeffentlichenDienst.ZEITART,
	        	 UOB_Ruhegehaltsfaehig.ZEITART,
	        	 Para_6_AltersteilzeitBlockmodel.ZEITART,
	        	 Para_6_AltersteilzeitTeilzeitmodel.ZEITART,
	        	 AufbauhilfeOst.ZEITART,
	        	 Para_13_VerwendungInLaendernMitGesundheitsschaedlichenKlima.ZEITART,
	        	 Para_11_SonstigeZeiten.ZEITART,
	        	 Para_12_VorgeschriebeneFachschulzeiten.ZEITART,
	        	 Para_12_VorgeschriebeneHochschulstudienzeiten.ZEITART,
	        	 Para_12_VorgeschriebenePraktischeAusbildung.ZEITART,
	        	 Para_12_VorgeschriebenePraktischeHauptberuflicheTaetigkeit.ZEITART,
	        	 Para_12_FoerderlichePraktischeAusbildung.ZEITART,
	        	 Para_12_FoerderlichePraktischeHauptberuflicheTaetigkeit.ZEITART
			};
			return timePeriodTypes;
		}
		
		
		public static Map<String, Integer> getTimePeriodTyps() {
			Map<String, Integer> timePeriodTypes = new HashMap<String, Integer>();
			timePeriodTypes.put(getLabel(1), new Integer(1));
			timePeriodTypes.put(getLabel(2), new Integer(2));
			timePeriodTypes.put(getLabel(3), new Integer(3));
			timePeriodTypes.put(getLabel(4), new Integer(4));
			timePeriodTypes.put(getLabel(5), new Integer(5));
			timePeriodTypes.put(getLabel(7), new Integer(7));
			timePeriodTypes.put(getLabel(8), new Integer(8));
			timePeriodTypes.put(getLabel(9), new Integer(9));
			timePeriodTypes.put(getLabel(10), new Integer(10));
			timePeriodTypes.put(getLabel(11), new Integer(11));
			timePeriodTypes.put(getLabel(12), new Integer(12));
			timePeriodTypes.put(getLabel(13), new Integer(13));
			timePeriodTypes.put(getLabel(14), new Integer(14));
			timePeriodTypes.put(getLabel(15), new Integer(15));
			timePeriodTypes.put(getLabel(16), new Integer(16));
			timePeriodTypes.put(getLabel(17), new Integer(17));
			return timePeriodTypes;
		}


		public static int getTimePeriodTypeForLabel(String timePeriodLabel) {
			Map<String, Integer> timePeriodTyps = getTimePeriodTyps();
			
			//kann sein, dass nicht alle Zeichen vorhanden sind, 
			//weil in der GUi In formation abgeschnitten wurden.
			Set<String> keySet = timePeriodTyps.keySet();
			for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if(key.startsWith(timePeriodLabel)) {
					timePeriodLabel = key;
				}
			}
			
			
			return timePeriodTyps.get(timePeriodLabel);
		}
		
		
		
		public static ITimePeriod createTimePeriod(int type, Date startDate, Date endDate, float factor) {
			ITimePeriod timePeriod = null;
			switch(type){
	        case 1:
	            return new Para_6_Beamterdienstzeit(startDate, endDate, factor );
	        case 2:
	        	return new Para_6_AusbildungBeamterAufWiderruf(startDate, endDate, factor);
	        case 3:
	            return new Para_8_9_WehrdienstOderZivildienst(startDate, endDate, factor);
	        case 4:
	        	return new Para_10_VordienstzeitenAlsArbeitnehmerImOeffentlichenDienst(startDate, endDate, factor);
	        case 5:
	        	return new UOB_Ruhegehaltsfaehig(startDate, endDate, factor);
	        case 7:
	        	return new Para_6_AltersteilzeitBlockmodel(startDate, endDate, factor);
	        case 8:
	        	return new Para_6_AltersteilzeitTeilzeitmodel(startDate, endDate, factor);
	        case 9:
	        	return new AufbauhilfeOst(startDate, endDate, factor);
	        case 10:
	        	return new Para_13_VerwendungInLaendernMitGesundheitsschaedlichenKlima(startDate, endDate, factor);
	        case 11:
	        	return new Para_11_SonstigeZeiten(startDate, endDate, factor);
	        case 12:
	        	return new Para_12_VorgeschriebeneFachschulzeiten(startDate, endDate, factor);
	        case 13:
	        	return new Para_12_VorgeschriebeneHochschulstudienzeiten(startDate, endDate, factor);
	        case 14:
	        	return new Para_12_VorgeschriebenePraktischeAusbildung(startDate, endDate, factor);
	        case 15:
	        	return new Para_12_VorgeschriebenePraktischeHauptberuflicheTaetigkeit(startDate, endDate, factor);
	        case 16:
	        	return new Para_12_FoerderlichePraktischeAusbildung(startDate, endDate, factor);
	        case 17:
	        	return new Para_12_FoerderlichePraktischeHauptberuflicheTaetigkeit(startDate, endDate, factor);
	        }
			
			return timePeriod;
		}
}
