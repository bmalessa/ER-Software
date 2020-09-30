package de.protin.support.pr.domain.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;

import de.protin.support.pr.domain.IBesoldungstabelle;
import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.besoldung.tabelle.BesoldungstabelleFactory;
import de.protin.support.pr.domain.besoldung.zuschlag.Para_50_Kindererziehungszeit;
import de.protin.support.pr.domain.pension.AntragsRuhestand;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.pension.EngagierterRuhestand;
import de.protin.support.pr.domain.pension.RegelaltersgrenzeRuhestand;
import de.protin.support.pr.domain.pension.Vorruhestand;
import de.protin.support.pr.domain.service.IValidationService;
import de.protin.support.pr.domain.utils.DateCalculator;
import de.protin.support.pr.domain.utils.DateUtil;
import de.protin.support.pr.domain.utils.PrintUtils;
import de.protin.support.pr.domain.utils.TimePeriodDetails;
import de.protin.support.pr.domain.validation.ValidationItem;
import de.protin.support.pr.domain.validation.ValidationResult;
import de.protin.support.pr.domain.validation.ValidationSupport;

public class ValidationServiceImpl implements IValidationService {


	
	
	/**
	 * Validierung der für die Ruhegehaltsberehnung eingegebenen Parameter sowie der gesetzlichen Bestimmungen und Voraussetzungen für die verschiedenen Arten
	 * des Ruhestand.
	 * 
	 * @return ValidationResult
	 */
	@Override
	public ValidationResult validate(IPension pension) {
		ValidationResult validationResult = new  ValidationResult();
		validationResult = validateSystemSetup(pension, validationResult);
		if(!validationResult.isSuccess()) {
			//in diesem Fall sofort aussteigen, da ansonsten unspezifiziertes Verhalten auftritt (NPE etc.)
			return validationResult;
		}
			
		validationResult = validateInput(pension, validationResult);
		validationResult = validateGrunddaten(pension, validationResult);
		validationResult = validateTimePeriods(pension, validationResult);
		validationResult = validateKez(pension, validationResult);
		validationResult = validatePreconditions(pension, validationResult);
		validationResult = validateLegalRequirements(pension, validationResult);
		return validationResult;
	}
	



















	/**
	 * Die Validierung der Eingabeparameter ist für alle Arten der Ruhegehaltsberechnung identisch und wird wird daher in der abstrakten Basisklasse behandelt.
	 * Die Validierung der gesetzlichen Bestimmungen in den unterschiedlichen Arten der Ruhegehaltsberechnung wird in den den jeweiligen abgeleiteten 
	 * Klassen behandelt. 
	 * 
	 * @param validationResult
	 * @return ValidationResult
	 */
	
	public ValidationResult validateInput(IPension pension, ValidationResult validationResult) {
		validationResult = ValidationSupport.validateDateString("Geburtsdatum: ", pension.getPerson().getDateOfBirthString(), validationResult);
		//validationResult = ValidationSupport.validateDateString("Allgemeine Altersgrenze: ", pension.getPerson().getDateOfLegalRetirementString(), validationResult);
		validationResult = ValidationSupport.validateDateString("Eintritt in den Ruhestand zum: ", pension.getPerson().getDateOfRetirementString(), validationResult);
		
		if(pension.getPerson().getDateOfRequestRetirementString() != null) {
			validationResult = ValidationSupport.validateDateString("Antragsaltersgrenze: ", pension.getPerson().getDateOfRequestRetirementString(), validationResult);
		}
		
		
		validationResult = ValidationSupport.validateGrundgehalt(pension.getPerson().getBesoldung(), validationResult);
		validationResult = ValidationSupport.validateFamilienzuschlag(pension.getPerson().getFamilienzuschlag(), validationResult);
		
		return validationResult;
	}

	

	/**
	 * Validierung, ob im System alle erforderlichen Artefakte (gültige Besoldungstabelle etc. vorhanden sind); 
	 * @param pension
	 * @param validationResult
	 * @return
	 */
	private ValidationResult validateSystemSetup(IPension pension, ValidationResult validationResult) {
		IBesoldungstabelle besoldungstabelle = BesoldungstabelleFactory.getInstance().getBesoldungstabelle(pension.getAnzwRecht(), pension.getPerson().getDateOfRetirement());
		if(besoldungstabelle == null) {
			validationResult.addItem(new ValidationItem("Keine Besoldungstabelle für das angegebene Ruhestandsdatum " + 
					PrintUtils.formatValue(pension.getPerson().getDateOfRetirement()) + 
					" im System vorhanden."));
		}
		return validationResult;
	}






	
	/**
	 * Validierung ob die datumswerte für den Eintritt in den Ruhestand und die Begründung des Beamtenverhältnis plausibel sind.
	 * Für das Datum "Eintritt in den Ruhestand" muss ein ruhegehaltsfähiger Zeitabschnitt mit dem entsprechenden Ende-Datum erfasst worden sein.
	 * Für das Datum "Begründung des Beamtenverhältnis" muss ein ruhegehaltsfähiger Zeitabschnitt mit dem entsprechenden Start-Datum erfasst worden sein. 
	 * 
	 * @param pension
	 * @param validationResult
	 * @return
	 */

	private ValidationResult validateGrunddaten(IPension pension, ValidationResult validationResult) {
		Date dateOfRetirement = pension.getPerson().getDateOfRetirement();
		Date dateOfSubstantiation = pension.getPerson().getDateOfSubstantiation();
		
		boolean validDateOfRetirement = false;
		boolean validDateOfSubstantiation = false;
		
		for (Iterator<ITimePeriod> iterator = pension.getTimePeriods().iterator(); iterator.hasNext();) {
			ITimePeriod timePeriod = (ITimePeriod) iterator.next();
			Date startDate = timePeriod.getStartDate();
			Date endDate = timePeriod.getEndDate();
			//hier immer einen Tag draufrechnen (z:b. Eintritt in den Ruhestand am 01.05., dann muss eine Zeitperiode m it Ende-Datum 30.04. vorliegen 
			endDate = DateUtils.addDays(endDate, 1);
			
			if(startDate.equals(dateOfSubstantiation)) {
				validDateOfSubstantiation = true;
			}
			if(endDate.equals(dateOfRetirement)) {
				validDateOfRetirement = true;
			}
		}
		
		
		if(!validDateOfSubstantiation) {
			validationResult.addItem(new ValidationItem("Datum der Begründung des Beamtenverh. unplausibel. Kein ruhegehaltsfähiger Zeitraum mit Start Datum " + PrintUtils.formatValue(dateOfSubstantiation) + " vorhanden."));
		}
		
		if(!validDateOfRetirement) {
			validationResult.addItem(new ValidationItem("Datum des Eintritt in den Ruhestand unplausibel. Kein ruhegehaltsfähiger Zeitraum mit Ende Datum " + PrintUtils.formatValue(DateUtils.addDays(dateOfRetirement,-1)) + " vorhanden."));
		}
		
		
		return validationResult;
	}




	
	/**
	 * Die Validierung der eingegebenen Zeiten.
	 * Gültige Datumswerte verwendet.
	 * Innerhalb einer Zeitperiode muss das Startdatum vor dem Enddatum liegen.
	 * Keine Überpappung von Zeiten aus verschiedenen Zeitperioden.
	 * 
	 * @param validationResult
	 * @return ValidationResult
	 */
	
	public ValidationResult validateTimePeriods(IPension pension, ValidationResult validationResult) {
		for (Iterator<ITimePeriod> outerIterator = pension.getTimePeriods().iterator(); outerIterator.hasNext();) {
			ITimePeriod outerTimePeriod = (ITimePeriod) outerIterator.next();
			validationResult = ValidationSupport.validateDate("Start Datum: ", outerTimePeriod.getStartDate(), validationResult);
			validationResult = ValidationSupport.validateDate("Ende Datum: ", outerTimePeriod.getEndDate(), validationResult);
			validationResult = ValidationSupport.validateDateRange("Start Datum muss vor dem Ende Datum liegen: ", outerTimePeriod.getStartDate(), outerTimePeriod.getEndDate(), validationResult);
			validationResult = ValidationSupport.validateTimePeriodOverlapping("Zeiten nicht eindeutig erfasst: ", outerTimePeriod, pension.getTimePeriods(), validationResult);
			validationResult = ValidationSupport.validateRgfDays("Anzahl der ruhegehaltsfähigen Tage ist höher als die Anzahl von Tagen eines Zeitabschnitt: ", outerTimePeriod, validationResult);
		}
		
		return validationResult;
	}


	

	private ValidationResult validateKez(IPension pension, ValidationResult validationResult) {
		
		if (pension.getKindererziehungsZuschlag() == null) {
			return validationResult;
		}
		
		Date date_19920101 = DateUtil.getDate("01.01.1992");
		Set<Para_50_Kindererziehungszeit> kindererziehungszeiten = pension.getKindererziehungsZuschlag().getKindererziehungszeiten();
		
		for (Iterator<Para_50_Kindererziehungszeit> iterator = kindererziehungszeiten.iterator(); iterator.hasNext();) {
			Para_50_Kindererziehungszeit kindererziehungszeit = (Para_50_Kindererziehungszeit) iterator.next();
			
			String selectionContext = "Kindererziehungszeiten: Kind = " + kindererziehungszeit.getChildName() + " mit Auswahl: " + kindererziehungszeit.getAnspruch();
			
			if(kindererziehungszeit.getBirthDate().before(date_19920101)) {
				if(kindererziehungszeit.getZulageMonths() > 30) {
					validationResult.addItem(new ValidationItem(selectionContext));
					validationResult.addItem(new ValidationItem("Für vor dem 01.01.1992 geborene Kinder werden max. 30 Monate Kindererziehungszeit anerkannt."));
				}
				if(kindererziehungszeit.isVollerAnspruch() && kindererziehungszeit.getZulageMonths() != 30) {
					validationResult.addItem(new ValidationItem(selectionContext));
					validationResult.addItem(new ValidationItem("Für vor dem 01.01.1992 geborene Kinder werden bei vollem Anspruch 30 Monate Kindererziehungszeit anerkannt."));
				}
				else {
					if(Para_50_Kindererziehungszeit.KEIN_KEZ_ANSPRUCH.equalsIgnoreCase(kindererziehungszeit.getAnspruch()) && kindererziehungszeit.getZulageMonths() > 0) {
						validationResult.addItem(new ValidationItem(selectionContext));
						validationResult.addItem(new ValidationItem("Bitte KEZ/Monate auf 0 setzen."));
					}
					if(Para_50_Kindererziehungszeit.TEIL_KEZ_ANSPRUCH.equalsIgnoreCase(kindererziehungszeit.getAnspruch()) && (kindererziehungszeit.getZulageMonths() == 30 || kindererziehungszeit.getZulageMonths() == 0)) {
						validationResult.addItem(new ValidationItem(selectionContext));
						validationResult.addItem(new ValidationItem("Bitte KEZ/Monate auf einen Wert zwischen 1-29 setzen. "));
					}
				}
			}
			else {
				if(kindererziehungszeit.getZulageMonths() > 36) {
					validationResult.addItem(new ValidationItem(selectionContext));
					validationResult.addItem(new ValidationItem("Für nach dem 31.12.1991 geborene Kinder werden max. 36 Monate Kindererziehungszeit anerkannt."));
				}
				if(kindererziehungszeit.isVollerAnspruch() && kindererziehungszeit.getZulageMonths() != 36) {
					validationResult.addItem(new ValidationItem(selectionContext));
					validationResult.addItem(new ValidationItem("Für nach dem 31.12.1991 geborene Kinder werden bei vollem Anspruch 36 Monate Kindererziehungszeit anerkannt."));
				}
				else {
					if(Para_50_Kindererziehungszeit.KEIN_KEZ_ANSPRUCH.equalsIgnoreCase(kindererziehungszeit.getAnspruch()) && kindererziehungszeit.getZulageMonths() > 0) {
						validationResult.addItem(new ValidationItem(selectionContext));
						validationResult.addItem(new ValidationItem("Bitte KEZ/Monate auf 0 setzen. "));
					}
					if(Para_50_Kindererziehungszeit.TEIL_KEZ_ANSPRUCH.equalsIgnoreCase(kindererziehungszeit.getAnspruch()) && (kindererziehungszeit.getZulageMonths() == 30 || kindererziehungszeit.getZulageMonths() == 0 )) {
						validationResult.addItem(new ValidationItem(selectionContext));
						validationResult.addItem(new ValidationItem("Bitte KEZ/Monate auf einen Wert zwischen 1-35 setzen. "));
					}
				}
			}
		}
		
		return validationResult;
	}


	
	

	public ValidationResult validateLegalRequirements(IPension pension, ValidationResult validationResult) {
	    if(pension instanceof RegelaltersgrenzeRuhestand) {
	    	return validateLegalRequirements((RegelaltersgrenzeRuhestand)pension, validationResult);
	    }
	    else if(pension instanceof Vorruhestand) {
			return validateLegalRequirements((Vorruhestand)pension, validationResult);
		}
		else if(pension instanceof AntragsRuhestand) {
			return validateLegalRequirements((AntragsRuhestand)pension, validationResult);
		}
		else if(pension instanceof Dienstunfaehigkeit) {
			return validateLegalRequirements((Dienstunfaehigkeit)pension, validationResult);
		}
		else if(pension instanceof EngagierterRuhestand) {
			return validateLegalRequirements((EngagierterRuhestand)pension, validationResult);
		}
		
		return validationResult;
	}
	
	
	
	public ValidationResult validateLegalRequirements(RegelaltersgrenzeRuhestand pension, ValidationResult validationResult) {
		//Zurruhesetzungtermin in der Regel zum Monatsletzten
	
		if(pension.getPerson().isBesondereAltersgrenze()) {
			Date dateOfSpecialLegalRetirement = DateCalculator.calculateDateOfSpecialLegalRetirement(pension.getPerson());
			if(pension.getPerson().getDateOfRetirement().before(dateOfSpecialLegalRetirement)) {
				validationResult.addItem(new ValidationItem("Besondere Regelaltersgrenze("+ PrintUtils.formatValue(dateOfSpecialLegalRetirement)  + ") zum "
						+ "Antragszeitpunkt ("+ PrintUtils.formatValue(pension.getPerson().getDateOfRetirement()) +") ist noch nicht erreicht."));
			}
		}
		else {
			//Regelaltersgrenze 67 Jahre ab Jahrgang 1964 erreicht?
			Date dateOfLegalRetirement = DateCalculator.calculateDateOfLegalRetirement(pension.getPerson());
			if(pension.getPerson().getDateOfRetirement().before(dateOfLegalRetirement)) {
				validationResult.addItem(new ValidationItem("Regelaltersgrenze für Geburtsdatum " + PrintUtils.formatValue(pension.getPerson().getDateOfBirth()) + 
					" ist noch nicht erreicht (" +  PrintUtils.formatValue(dateOfLegalRetirement) +")."));
			}
		}
		
		return validationResult;
	}
	
	public ValidationResult validateLegalRequirements(AntragsRuhestand pension, ValidationResult validationResult) {
		
		Date dateOfRequestRetirement = DateCalculator.calculateDateOfRequestRetirement(pension.getPerson());
		
		if(pension.getPerson().getDateOfRetirement().before(dateOfRequestRetirement)) {
			if(pension.getPerson().isSchwerbehindert()) {
				//bei Schwerbehinderung abhängig vom Geburtsjahr - siehe MonthCalculator.calculateMonthForAntragsalterSchwerbehinderung
				validationResult.addItem(new ValidationItem("Antragsaltersgrenze bei Schwerbehinderung wurde noch nicht erreicht."));
			}
			else {
				validationResult.addItem(new ValidationItem("Antragsaltersgrenze ohne Schwerbehinderung liegt bei mindestens 63 Lebensjahren"));
			}
		}
		
		return validationResult;
	}
	
	
	
	public ValidationResult validateLegalRequirements(Dienstunfaehigkeit pension, ValidationResult validationResult) {
		// Welche Anforderung existieren hier?
		return validationResult;
	}


	
	public ValidationResult validateLegalRequirements(EngagierterRuhestand pension, ValidationResult validationResult) {
		// Mindestalter 55 Jahre?
		int years = 55;
		Date dateOfLegalRetirement = DateUtils.addYears(pension.getPerson().getDateOfBirth(), years);
		if(pension.getPerson().getDateOfRetirement().before(dateOfLegalRetirement)) {
			validationResult.addItem(new ValidationItem("Der \"Engagierter Ruhestand\" ist erst mit Vollendung des 55 Lebensjahr zulässig."));
		}
		return validationResult;
	}
	
	
	
	
	public ValidationResult validateLegalRequirements(Vorruhestand pension, ValidationResult validationResult) {
		
		validationResult = validateLegalRequirements((AntragsRuhestand)pension, validationResult);
		
		boolean timePeriodForVorruhestandAvailable = false;
		for (Iterator<ITimePeriod> iterator = pension.getTimePeriods().iterator(); iterator.hasNext();) {
			ITimePeriod timePeriod = (ITimePeriod) iterator.next();
			if (timePeriod.getType() == ITimePeriod.ALTERSTEILZEIT_BLOCKMODELL || 
				timePeriod.getType() == ITimePeriod.ALTERSTEILZEIT_TEILZEITMODELL) {
				timePeriodForVorruhestandAvailable = true;
				float factor = timePeriod.getFactor();
				if(factor != 0.9f) {
					validationResult.addItem(new ValidationItem("Faktor für die Anrechnung der Altersteilzeit muss 0.9 betragen."));
				}
			}
		}
		
		
		if(!timePeriodForVorruhestandAvailable) {
			validationResult.addItem(new ValidationItem("Keine Zeiterfassung (Block- oder Teilzeitmodel für Vorruhstand vorhanden."));
		}
		
		
		return validationResult;
	}
	
	
	/**
	 * § 5 BeamtVG
	 * 
	 * 1) Ein Ruhegehalt wird nur gewährt, wenn der Beamte

		1. eine Dienstzeit von mindestens fünf Jahren abgeleistet hat oder
		2. infolge Krankheit, Verwundung oder sonstiger Beschädigung, die er sich ohne grobes Verschulden bei Ausübung oder aus Veranlassung des Dienstes zugezogen hat, dienstunfähig geworden ist.
	 * @param pension
	 * @param validationResult
	 * @return
	 */
	private ValidationResult validatePreconditions(IPension pension, ValidationResult validationResult) {
		if(pension instanceof Dienstunfaehigkeit) {
			if (((Dienstunfaehigkeit) pension).isDienstunfall()) {
				return validationResult;
			}
			else {
				long rgfDays = 0;
				TimePeriodDetails tpDetails = null;
				for (Iterator<ITimePeriod> iterator = pension.getTimePeriods().iterator(); iterator.hasNext();) {
					ITimePeriod timePeriod = iterator.next();
					//sog. Kann-Zeiten (BeamtVG §12 werden hier nicht berücksichtigt
					if(timePeriod.getType() < 12) {
						tpDetails = new TimePeriodDetails(timePeriod.getStartDate(), timePeriod.getEndDate());
						rgfDays += tpDetails.getAmountOfDays();
					}
				}
				
				tpDetails = new TimePeriodDetails(rgfDays);
				
				if(tpDetails.getYears() < 5) {
					validationResult.addItem(new ValidationItem("Ein Ruhegehalt wird gem. §5 BeamtVG nur gewährt, wenn der Beamte eine Dienstzeit "
							+ "von mindestens 5 jahren abgeleistet hat."));
				}
 
				
			}
		}
		return validationResult;
	}
}
