package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;


/**
 * � 10 Zeiten im privatrechtlichen Arbeitsverh�ltnis im �ffentlichen Dienst
 * 
 * Als ruhegehaltf�hig sollen auch folgende Zeiten ber�cksichtigt werden, in denen ein Beamter vor der Berufung in das Beamtenverh�ltnis im privatrechtlichen 
 * Arbeitsverh�ltnis im Dienst eines �ffentlich-rechtlichen Dienstherrn ohne von dem Beamten zu vertretende Unterbrechung t�tig war, sofern diese T�tigkeit zu 
 * seiner Ernennung gef�hrt hat:

	1. Zeiten einer hauptberuflichen in der Regel einem Beamten obliegenden oder sp�ter einem Beamten �bertragenen entgeltlichen Besch�ftigung oder
	2. Zeiten einer f�r die Laufbahn des Beamten f�rderlichen T�tigkeit.
	
    Der T�tigkeit im Dienst eines �ffentlich-rechtlichen Dienstherrn steht die T�tigkeit im Dienst von Einrichtungen gleich, die von mehreren der im Satz 1 bezeichneten 
    Dienstherren durch Staatsvertrag oder Verwaltungsabkommen zur Erf�llung oder Koordinierung ihnen obliegender hoheitsrechtlicher Aufgaben geschaffen worden sind. 
    Zeiten mit einer geringeren als der regelm��igen Arbeitszeit d�rfen nur zu dem Teil als ruhegehaltf�hig ber�cksichtigt werden, der dem Verh�ltnis der tats�chlichen 
    zur regelm��igen Arbeitszeit entspricht.
 * 
 * @author Bernd
 *
 */
public class Para_10_VordienstzeitenAlsArbeitnehmerImOeffentlichenDienst extends BaseTimePeriod {

	public static final String ZEITART = "Vordienstzeiten als Arbeitnehmer im �ffentl. Dienst (�10 BeamtVG)";
	
	public Para_10_VordienstzeitenAlsArbeitnehmerImOeffentlichenDienst(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.ANGESTELLTENVERHAELTNIS_IM_OEFFENTLICHEN_DIENST_PARA_10, startDate, endDate, factor);
	}

	public String getZeitart() {
		return ZEITART;
	}
	
}
