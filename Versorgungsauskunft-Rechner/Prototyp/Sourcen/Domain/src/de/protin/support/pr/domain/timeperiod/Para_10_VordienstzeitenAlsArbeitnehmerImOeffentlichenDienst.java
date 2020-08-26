package de.protin.support.pr.domain.timeperiod;

import java.util.Date;

import de.protin.support.pr.domain.ITimePeriod;


/**
 * § 10 Zeiten im privatrechtlichen Arbeitsverhältnis im öffentlichen Dienst
 * 
 * Als ruhegehaltfähig sollen auch folgende Zeiten berücksichtigt werden, in denen ein Beamter vor der Berufung in das Beamtenverhältnis im privatrechtlichen 
 * Arbeitsverhältnis im Dienst eines öffentlich-rechtlichen Dienstherrn ohne von dem Beamten zu vertretende Unterbrechung tätig war, sofern diese Tätigkeit zu 
 * seiner Ernennung geführt hat:

	1. Zeiten einer hauptberuflichen in der Regel einem Beamten obliegenden oder später einem Beamten übertragenen entgeltlichen Beschäftigung oder
	2. Zeiten einer für die Laufbahn des Beamten förderlichen Tätigkeit.
	
    Der Tätigkeit im Dienst eines öffentlich-rechtlichen Dienstherrn steht die Tätigkeit im Dienst von Einrichtungen gleich, die von mehreren der im Satz 1 bezeichneten 
    Dienstherren durch Staatsvertrag oder Verwaltungsabkommen zur Erfüllung oder Koordinierung ihnen obliegender hoheitsrechtlicher Aufgaben geschaffen worden sind. 
    Zeiten mit einer geringeren als der regelmäßigen Arbeitszeit dürfen nur zu dem Teil als ruhegehaltfähig berücksichtigt werden, der dem Verhältnis der tatsächlichen 
    zur regelmäßigen Arbeitszeit entspricht.
 * 
 * @author Bernd
 *
 */
public class Para_10_VordienstzeitenAlsArbeitnehmerImOeffentlichenDienst extends BaseTimePeriod {

	public static final String ZEITART = "Vordienstzeiten als Arbeitnehmer im öffentl. Dienst (§10 BeamtVG)";
	
	public Para_10_VordienstzeitenAlsArbeitnehmerImOeffentlichenDienst(Date startDate, Date endDate, float factor) {
		super(ITimePeriod.ANGESTELLTENVERHAELTNIS_IM_OEFFENTLICHEN_DIENST_PARA_10, startDate, endDate, factor);
	}

	public String getZeitart() {
		return ZEITART;
	}
	
}
