package de.protin.support.pr.domain.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang3.time.DateUtils;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.besoldung.Amtszulage;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.SonstigeZulage;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.service.IRuhegehaltsberechnungAktuellesRechtService;
import de.protin.support.pr.domain.service.impl.util.PflegeleistungAbzug_Para_BeamtVG_50f;
import de.protin.support.pr.domain.timeperiod.Para_13_Zurechnungszeit_DDU;
import de.protin.support.pr.domain.utils.Constants;
import de.protin.support.pr.domain.utils.DayCalculator;
import de.protin.support.pr.domain.utils.TimePeriodCalculator;
import de.protin.support.pr.domain.utils.TimePeriodDetails;

public class RuhegehaltsberechnungAktuellesRechtServiceImpl implements IRuhegehaltsberechnungAktuellesRechtService {


	/**
	 * �5 BeamtVG
	 * (1) Ruhegehaltf�hige Dienstbez�ge sind
			1. das Grundgehalt,
			2. der Familienzuschlag (� 50 Abs. 1) der Stufe 1,
			3. sonstige Dienstbez�ge, die im Besoldungsrecht als ruhegehaltf�hig bezeichnet sind,
			4. Leistungsbez�ge nach � 33 Abs. 1 des Bundesbesoldungsgesetzes, soweit sie 
			   nach � 33 Abs. 3 des Bundesbesoldungsgesetzes ruhegehaltf�hig sind oder auf Grund der nach � 33 Absatz 4 des Bundesbesoldungsgesetzes erlassenen 
			   Rechtsverordnungen f�r ruhegehaltf�hig erkl�rt wurden, die dem Beamten in den F�llen der Nummern 1 und 3 zuletzt zugestanden haben oder in den 
			   F�llen der Nummer 2 nach dem Besoldungsrecht zustehen w�rden; sie werden mit dem Faktor 0,9901 vervielf�ltigt. Bei Teilzeitbesch�ftigung und 
			   Beurlaubung ohne Dienstbez�ge (Freistellung) gelten als ruhegehaltf�hige Dienstbez�ge die dem letzten Amt entsprechenden vollen ruhegehaltf�higen 
			   Dienstbez�ge. Satz 2 gilt entsprechend bei eingeschr�nkter Verwendung eines Beamten wegen begrenzter Dienstf�higkeit nach � 45 des Bundesbeamtengesetzes. 
			   � 78 des Bundesbesoldungsgesetzes ist nicht anzuwenden.
			   
	 
	 * Grundgehalt und Familienzuschlag werden aus der hinterlegten Besoldungstabelle entnommen.
	 * Stellenzulage und sonstige ruhegehaltsf�hige Zulagen m�ssen betragsm��ig im Frontend erfasst werden.
	 *  
	 * @return
	 */
	@Override
	public float calculateRuhegehaltsfaehigenDienstbezug(IPension pension) {
		Grundgehalt grundgehalt = pension.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = pension.getPerson().getFamilienzuschlag();
		Amtszulage amtszulage = pension.getPerson().getAmtszulage();
		SonstigeZulage sonstigeZulage = pension.getPerson().getSonstigeZulage();
		
		
		float summeRuhegehaltsfaehigeDienstbezuege = pension.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		summeRuhegehaltsfaehigeDienstbezuege += pension.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		
		if(amtszulage != null) {
			summeRuhegehaltsfaehigeDienstbezuege += amtszulage.getZulage();
		}
		if(sonstigeZulage != null) {
			summeRuhegehaltsfaehigeDienstbezuege += sonstigeZulage.getSalary();	
		}
		
		
		
		return summeRuhegehaltsfaehigeDienstbezuege;
	}

	
	/**
	 * � 14 H�he des Ruhegehalts
			(1) Das Ruhegehalt betr�gt f�r jedes Jahr ruhegehaltf�higer Dienstzeit 1,79375 Prozent, insgesamt jedoch 
			h�chstens 71,75 Prozent, der ruhegehaltf�higen Dienstbez�ge. Bei der Berechnung der Jahre ruhegehaltf�higer 
			Dienstzeit werden unvollst�ndige Jahre als Dezimalzahl angegeben. Dabei wird ein Jahr mit 365 Tagen angesetzt 
			und wird das Ergebnis kaufm�nnisch auf zwei Dezimalstellen gerundet. Der Ruhegehaltssatz wird ebenfalls kaufm�nnisch 
			auf zwei Dezimalstellen gerundet.
	
	 * @return
	 */
	@Override
	public float calculateRuhegehaltssatz_Para_14_1_Neue_Fassung(IPension pension) {
		
		boolean zurechnungAlreadyIncluded = false;
		int sumYears = 0;
		int restDays = 0;
		int sumRgfDays = 0;
		
		for (Iterator<ITimePeriod> iterator = pension.getTimePeriods().iterator(); iterator.hasNext();) {
			ITimePeriod timePeriod = (ITimePeriod) iterator.next();
			sumRgfDays += timePeriod.getRuhegehaltsfaehigeTage();
			
			if(timePeriod instanceof Para_13_Zurechnungszeit_DDU) {
				zurechnungAlreadyIncluded = true;
			}
		}
		
		if(pension instanceof Dienstunfaehigkeit && !zurechnungAlreadyIncluded) {
			
			Date zurechungszeitStartDate = DateUtils.addDays(pension.getPerson().getDateOfRetirement(),1);
			Date zurechungszeitEndDate = calculateTargetDateForZurechnungszeit(pension.getPerson().getDateOfBirth());
			
			ITimePeriod zurechnungszeit = new Para_13_Zurechnungszeit_DDU(zurechungszeitStartDate, zurechungszeitEndDate, 2.0f/3.0f);
			sumRgfDays += zurechnungszeit.getRuhegehaltsfaehigeTage();
			
			pension.getTimePeriods().add(zurechnungszeit);
			zurechnungAlreadyIncluded = true;
		}
		
		sumYears += sumRgfDays / 365;
		restDays += sumRgfDays % 365;
		
		if(restDays >= 365) {
			sumYears += restDays / 365;
			restDays = restDays % 365;
		}
		
		float ruhegehaltsSatz = sumYears * Constants.VERSORGUNGSSATZ_JAHR_PARA_14_Abs_1_BeamtVG; 
		ruhegehaltsSatz += restDays * (Constants.VERSORGUNGSSATZ_JAHR_PARA_14_Abs_1_BeamtVG / 365); 
		
		if(ruhegehaltsSatz > Constants.VERSORGUNGSSATZ_MAX_PARA_14_Abs_1_BeamtVG) {
			return Constants.VERSORGUNGSSATZ_MAX_PARA_14_Abs_1_BeamtVG;
		}
		
		
		ruhegehaltsSatz = (float) (Math.round(100.0 * ruhegehaltsSatz) / 100.0); // runden auf die 2 Nachkommastelle
	    
		return ruhegehaltsSatz;
	}
	
	
	
	@Override
	public float calculateErdientesRuhegehalt(IPension pension) {
		float erdientesRuhegehalt = calculateRuhegehaltsfaehigenDienstbezug(pension) * Constants.KORREKTURFAKTOR_PARA_5_Abs_1_BeamtVG;
		erdientesRuhegehalt = erdientesRuhegehalt * calculateRuhegehaltssatz_Para_14_1_Neue_Fassung(pension) / 100;
		return erdientesRuhegehalt;
	}



	@Override
	public float getMaxAbzugPflegeleistung(IPension pension) {
		return PflegeleistungAbzug_Para_BeamtVG_50f.getInstance().getMaxAbzugPflegeleistung(pension.getPerson().getDateOfRetirement());
	}
	
	
	@Override
	public float getFaktorAbzugPflegeleistung(IPension pension) {
		return PflegeleistungAbzug_Para_BeamtVG_50f.getInstance().getFaktorAbzugPflegeleistung(pension.getPerson().getDateOfRetirement());
	}
	
	
	private Date calculateTargetDateForZurechnungszeit(Date birthDate) {
		Date targetDate = DateUtils.addYears(birthDate, 60);
		targetDate = DateUtils.ceiling(targetDate, Calendar.MONTH);
		targetDate = DateUtils.addDays(targetDate, -1);
		return targetDate;
	}
}
