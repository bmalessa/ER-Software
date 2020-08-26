package de.protin.support.pr.domain.service.impl;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.pension.AntragsRuhestand;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.service.IRuhegehaltsabschlagService;
import de.protin.support.pr.domain.utils.Constants;
import de.protin.support.pr.domain.utils.DateCalculator;
import de.protin.support.pr.domain.utils.DateUtil;
import de.protin.support.pr.domain.utils.MonthCalculator;
import de.protin.support.pr.domain.utils.TimePeriodDetails;

public class RuhegehaltsabschlagServiceImpl implements IRuhegehaltsabschlagService {

	
	/**
	 * BeamtVG § 69d Übergangsregelungen
	 * ...
	 * (5) Auf am 1. Januar 2001 vorhandene Beamte, die bis zum 16. November 1950 geboren und am 16. November 2000 schwerbehindert 
	 * im Sinne des § 2 Absatz 2 des Neunten Buches Sozialgesetzbuch sind sowie nach § 52 Absatz 1 und 2 des Bundesbeamtengesetzes 
	 * in den Ruhestand versetzt werden, ist § 14 Absatz 3 nicht anzuwenden.
	 */
	
	@Override
	public float calculateAbschlag_Para_14_BeamtVG(AntragsRuhestand pension) {
		
		Date dateOfBirth = pension.getPerson().getDateOfBirth();
		Date dateOfRetirement = pension.getPerson().getDateOfRetirement();
		//Anzahl ruhegehaltfähigen Dienstzeiten sowie berücksichtigungsfähigen Pflichtbeitragszeiten (evtl. auch Kindererziehungszeiten) ermitteln
		TimePeriodDetails ruhegehaltsfaehigeDienstzeiten = pension.calculateRuhegehaltsfaehigeDienstzeiten_Para_14();
		if(ruhegehaltsfaehigeDienstzeiten.getYears() >= 45) {
			return 0.0f;
		}
		
		long diffDays = 0;
		long years = 0;
		long days = 0;
		
		
		TimePeriodDetails tpd = calculateTimePeriodForVersorgungsabschlag(pension);
		
		/*
		if(pension.getPerson().isSchwerbehindert()) {
			
			//BeamtVG § 69d Übergangsregelungen treffen heute nicht mehr zu, da vor dem 16.11.1950 geborene heute 
			//mindestens 69 jahre alt sind. Altes Übergangsrecht wird hier also ignoriert.
			
			//Abzug bis 10.8% (schwergehindert) 
			// 1) errechne dateOfBirth + 65 Jahre
			Date targetDate = DateUtils.addYears(dateOfBirth, 65);
			
			// sofern besondere Altersgrenze unter 65 jahren, dann besondere Altersgrenze nehmen
			if(pension.getPerson().isBesondereAltersgrenze()) {
				if(pension.getPerson().getBesondereAltersgrenzeJahre() < 65) {
					targetDate = DateUtils.addYears(dateOfBirth, pension.getPerson().getBesondereAltersgrenzeJahre());
				}
			}
			
			// 2) Differenz aus Zurruhesetzungsdatum und 65. Geburtstag (bzw. besonderer Altersgrenze) )errechnen (in milliseconds)
			long betweenDiffInMilliseconds = targetDate.getTime() - dateOfRetirement.getTime();
			// 3) Umrechnung in Tage
			diffDays = betweenDiffInMilliseconds / (24 * 60 * 60 * 1000);
			
			//
			years = diffDays / TimePeriodDetails.DAYS_IN_YEAR;
			days = diffDays % TimePeriodDetails.DAYS_IN_YEAR;
			if(years >= 3) {
				return Constants.MAX_MINDERUNG_PARA_14_Abs_3_DDU_BeamtVG;
			}
		}
		else {
			//Abzug bis max 14.4%
			// 1) errechne dateOfBirth + Regelaltersgrenze
			Date targetDate = DateUtils.addMonths(dateOfBirth, MonthCalculator.calculateMonthForRegelaltersgrenze(pension.getPerson()));
			
			// 2) Differenz aus Zurruhesetzungsdatum und Regelaltersgrenze errechnen (in milliseconds)
			long betweenDiffInMilliseconds = targetDate.getTime() - dateOfRetirement.getTime();
			// 3) Umrechnung in Tage
			diffDays = betweenDiffInMilliseconds / (24 * 60 * 60 * 1000);
			
			//
			years = diffDays / TimePeriodDetails.DAYS_IN_YEAR;
			days = diffDays % TimePeriodDetails.DAYS_IN_YEAR;
			if(years >= 4) {
				return Constants.MAX_MINDERUNG_PARA_14_Abs_3_ANTRAG_BeamtVG;
			}
		}
		*/
		
		years = tpd.getYears();
		days = tpd.getDays();
		
		if(years < 0 || days < 0) {
			return 0.0f;
		}
		

		float result = years * Constants.MINDERUNG_JAHR_PARA_14_Abs_3_BeamtVG;
		result += days * Constants.MINDERUNG_JAHR_PARA_14_Abs_3_BeamtVG / TimePeriodDetails.DAYS_IN_YEAR;
		
		return result;
	}

	
	
	
	@Override
	public float calculateAbschlag_Para_14_BeamtVG(Dienstunfaehigkeit pension) {
		//bis 65 Lebensjahr oder besondere Altersgrenze
		
		Date dateOfBirth = pension.getPerson().getDateOfBirth();
		Date dateOfRetirement = pension.getPerson().getDateOfRetirement();
	
		
		//Anzahl ruhegehaltfähigen Dienstzeiten sowie berücksichtigungsfähigen Pflichtbeitragszeiten (evtl. auch Kindererziehungszeiten) ermitteln
		TimePeriodDetails ruhegehaltsfaehigeDienstzeiten = pension.calculateRuhegehaltsfaehigeDienstzeiten_Para_14();
		
		//keine Minderung, wenn 63 Lebensjahr vollendet wurde oder aufgrund Dientsunfall
		Date targetDateFor63Years = DateUtils.addYears(dateOfBirth, 63);
		if(targetDateFor63Years.before(dateOfRetirement) || pension.isDienstunfall()) {
			return 0.0f;
		}
		
		//keine Minderung, wenn vor dem 01.01.1942 geboren und 40 ruhegehaltsfähige Dienstjahre vorhanden
		//kann nicht mehr vorkommen, aber so wäre es lt Gesetz richtig
		Date date19420101 = DateUtil.getDate(" 01.01.1942");
		if(ruhegehaltsfaehigeDienstzeiten.getYears() >= 40 && dateOfBirth.before(date19420101)) {
			return 0.0f;
		}
				
	
		long diffDays = 0;
		long years = 0;
		long days = 0;
		
		//Abzug bis max. 10.8% 
		// 1) errechne dateOfBirth + 65 Lebensjahre
		Date targetDate = DateUtils.addYears(dateOfBirth, 65);
		if(pension.getPerson().isBesondereAltersgrenze()) {
			if(pension.getPerson().getBesondereAltersgrenzeJahre() < 65) {
				targetDate = DateUtils.addYears(dateOfBirth, pension.getPerson().getBesondereAltersgrenzeJahre());
			}
		}
		
		// 2) Differenz aus Zurruhesetzungsdatum und 65. Geburtstag (bzw. besondere Altersgrenze) errechnen (in milliseconds)
		long betweenDiffInMilliseconds = targetDate.getTime() - dateOfRetirement.getTime();
		// 3) Umrechnung in Tage
		diffDays = betweenDiffInMilliseconds / (24 * 60 * 60 * 1000);
		
		//
		years = diffDays / TimePeriodDetails.DAYS_IN_YEAR;
		days = diffDays % TimePeriodDetails.DAYS_IN_YEAR;
		
		
		//Abzug bis 10.8% (DDU)
		if(years >= 3) {
			return Constants.MAX_MINDERUNG_PARA_14_Abs_3_DDU_BeamtVG;
		}
		
		if(years < 0 || days < 0) {
			return 0.0f;
		}
		

		float result = years * Constants.MINDERUNG_JAHR_PARA_14_Abs_3_BeamtVG;
		result += days * Constants.MINDERUNG_JAHR_PARA_14_Abs_3_BeamtVG / TimePeriodDetails.DAYS_IN_YEAR;
		
		return result;
	}

	
	
	/**
	 * Das darf nicht bei Ruhestand wegen Erreichen der gesetzlichen Altersgrenze oder Engagierter Ruhestand aufgerufen werden.
	 * Dies wird aber in den vorher durchgeführten Validierungen sichergestellt.
	 * 
	 */
	@Override
	public TimePeriodDetails calculateTimePeriodForVersorgungsabschlag(IPension pension) {
		
		Date dateOfRetirement = pension.getPerson().getDateOfRetirement();
		
		Date dateOfLegalRetirement = DateCalculator.calculateDateOfLegalRetirement(pension.getPerson());
		
		if(pension.getPerson().isSchwerbehindert()) {
			dateOfLegalRetirement = DateCalculator.calculateDateOfLegalRetirementForSchwerbehinderung(pension.getPerson());
		}
		
		Date dateOfSpecialLegalRetirement = pension.getPerson().getDateOfSpecialLegalRetirement();
		
		Date targetDate = null;
		if(dateOfSpecialLegalRetirement == null) {
			targetDate = dateOfLegalRetirement;
		}
		else {
			targetDate = dateOfSpecialLegalRetirement.before(dateOfLegalRetirement) ? dateOfSpecialLegalRetirement : dateOfLegalRetirement ;
		}
		
		TimePeriodDetails result = new TimePeriodDetails(dateOfRetirement, targetDate);
		
		//hier ein unschöner Hack, damit wir auf die richtigen Werte kommen
		// Bsp: 01.05.2018 bis 30.04.2020 ergibt ansonsten 1 Jjahr und 364 Tage
		if(result.getDays() == 364) {
			Date startDate = result.getStartDate();
			Date endDate = result.getEndDate();
			result.setDays(0);
			result.setYears(result.getYears() + 1);
			result.setStartDateWithoutReset(startDate);
			result.setEndDateWithoutReset(endDate);
		}
		
		return result;
	}
	

	
}
