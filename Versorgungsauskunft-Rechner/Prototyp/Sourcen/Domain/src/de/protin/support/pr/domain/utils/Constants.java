package de.protin.support.pr.domain.utils;

public class Constants {

	public final static float KORREKTURFAKTOR_PARA_5_Abs_1_BeamtVG = 0.9901f;
	
	public final static float KORREKTURFAKTOR_PARA_69e_Abs_4_BeamtVG = 0.95667f;
	
	// Abzug nach 50f BeamtVG (Abzug für Pflegeleistung) beträgt ab 01.01.2019 1,525%, max 69,20€
	public final static float FAKTOR_PARA_50f_BeamtVG = 1.525f;
	
	public final static float VERSORGUNGSSATZ_MAX_PARA_14_Abs_1_BeamtVG = 71.75f;
	
	public final static float VERSORGUNGSSATZ_JAHR_PARA_14_Abs_1_BeamtVG = 1.79375f;
	
	public final static float MAX_MINDERUNG_PARA_14_Abs_3_DDU_BeamtVG = 10.8f; //Ruhestand wg. DDU
	
	public final static float MAX_MINDERUNG_PARA_14_Abs_3_ANTRAG_BeamtVG = 14.4f; //AntragsRuhestand
	
	public final static float MINDERUNG_JAHR_PARA_14_Abs_3_BeamtVG = 3.6f; //AntragsRuhestand
	
	public final static float AMTSABHAENGIGE_MINDESTVERSORGUNG_PROZENTSATZ_PARA_14_Abs_4_BeamtVG = 0.35f;
	
	public final static float AMTSUNABHAENGIGE_MINDESTVERSORGUNG_PROZENTSATZ_PARA_14_Abs_4_BeamtVG = 0.65f;
	
	public final static int AMTSUNABHAENGIGE_MINDESTVERSORGUNG_BESGRUPPE_PARA_14_Abs_4_BeamtVG = 4;
	
	public final static int AMTSUNABHAENGIGE_MINDESTVERSORGUNG_BESSTUFE_PARA_14_Abs_4_BeamtVG = 8;
	
	public final static float AMTSUNABHAENGIGE_MINDESTVERSORGUNG_FIXBETRAG_PARA_14_Abs_4_BeamtVG = 30.68f;
	
	public final static float KINDERERZIEHUNGSZUSCHLAG_ENTGELTPUNKTE_MONAT_PARA_70_Abs_2_SGB_6 = 0.0833f;
	
	public final static int KINDERERZIEHUNG_MAX_ANZAHL_MONATE_NACH_31_12_1991_PARA_50a = 36;
	
	public final static int KINDERERZIEHUNG_MAX_ANZAHL_MONATE_VOR_01_01_1992_PARA_50a = 30;
	
	public final static String ALTERSGRENZE_67_JAHRE_DATUM = "01.01.1964";
}
