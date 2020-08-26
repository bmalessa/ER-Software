package de.protin.support.pr.domain.pension;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.service.impl.ServiceRegistry;
import de.protin.support.pr.domain.timeperiod.Para_13_Zurechnungszeit_DDU;
import de.protin.support.pr.domain.utils.Constants;
import de.protin.support.pr.domain.utils.DayCalculator;
import de.protin.support.pr.domain.utils.TimePeriodDetails;


/**
 * §14 BeamtVG
 * 
(3) Das Ruhegehalt vermindert sich um 3,6 Prozent für jedes Jahr, um das der Beamte


	3.	vor Ablauf des Monats, in dem er das 65. Lebensjahr vollendet, wegen Dienstunfähigkeit, die nicht auf einem 
		Dienstunfall beruht, in den Ruhestand versetzt wird;
			
	
	... die Minderung des Ruhegehalts darf 10,8 vom Hundert in den Fällen der Nummern 3 nicht übersteigen.
	 
	Absatz 1 Satz 2 bis 4 gilt entsprechend. Gilt für den Beamten eine vor der Vollendung des 
	65. Lebensjahres liegende Altersgrenze, tritt sie in den Fällen des Satzes 1 Nr. 1 und 3 an die Stelle des 65. Lebensjahres. 
	Gilt für den Beamten eine nach Vollendung des 67. Lebensjahres liegende Altersgrenze, wird in den Fällen des Satzes 1 Nr. 2 
	nur die Zeit bis zum Ablauf des Monats berücksichtigt, in dem der Beamte das 67. Lebensjahr vollendet. 
	
*/
public class Dienstunfaehigkeit extends AbstractPension {

	
	protected ITimePeriod zurechungszeit;  // 2/3 der Zeit von Beginn des Ruhestand bis 60 Lebensjahr
	protected Date zurechungszeitStartDate;
	protected Date zurechungszeitEndDate;
	
	protected boolean dienstunfall;
	
	public Dienstunfaehigkeit(Person person, String anzuwendendesRecht) {
		super(person, AbstractPension.DIENSTUNFAEHIGKEIT, anzuwendendesRecht);
	}


	@Override
	public float calculateAbschlag_Para_14_BeamtVG() {
		return ServiceRegistry.getInstance().getRuhegehaltsabschlagService().calculateAbschlag_Para_14_BeamtVG(this);
	}

	
	@Override
	public float calculateMaxAbschlag_Para_14_BeamtVG() {
		return Constants.MAX_MINDERUNG_PARA_14_Abs_3_DDU_BeamtVG;
	}   



	
	/**
	 *  13 Zurechnungszeit und Zeit gesundheitsschädigender Verwendung

		(1) Ist der Beamte vor Vollendung des sechzigsten Lebensjahres wegen Dienstunfähigkeit in den Ruhestand getreten, wird die Zeit vom Eintritt in den 
			Ruhestand bis zum Ablauf des Monats der Vollendung des sechzigsten Lebensjahres, soweit diese nicht nach anderen Vorschriften als ruhegehaltfähig 
			berücksichtigt wird, für die Berechnung des Ruhegehalts der ruhegehaltfähigen Dienstzeit zu zwei Dritteln hinzugerechnet (Zurechnungszeit). 
			Ist der Beamte nach § 45 des Bundesbeamtengesetzes oder dem entsprechenden Landesrecht erneut in das Beamtenverhältnis berufen worden, so wird 
			eine der Berechnung des früheren Ruhegehalts zugrunde gelegene Zurechnungszeit insoweit berücksichtigt, als die Zahl der dem neuen Ruhegehalt 
			zugrunde liegenden Dienstjahre hinter der Zahl der dem früheren Ruhegehalt zugrunde gelegenen Dienstjahre zurückbleibt. § 6 Abs. 1 Satz 4 gilt 
			entsprechend.
			
	 * @return
	 */
	


	
	public TimePeriodDetails calculateZurechnungzeit_Para_13_BeamtVG() {
		this.zurechungszeitStartDate = DateUtils.addDays(this.getPerson().getDateOfRetirement(),1);
		this.zurechungszeitEndDate = calculateTargetDateForZurechnungszeit(this.getPerson().getDateOfBirth());
		//ITimePeriod zurechnungszeit = new Para_13_Zurechnungszeit_DDU(this.zurechungszeitStartDate, this.zurechungszeitEndDate, 2.0f/3.0f);
		//this.addTimePeriod(zurechnungszeit);
		
		long calculatedDays = DayCalculator.calculateDaysForZurechnungszeitDDU(this.person);
		calculatedDays = (calculatedDays * 2 / 3);
		
		TimePeriodDetails tpd = new TimePeriodDetails(calculatedDays);
		return tpd;
	}
	
	
	
	private Date calculateTargetDateForZurechnungszeit(Date birthDate) {
		Date targetDate = DateUtils.addYears(birthDate, 60);
		targetDate = DateUtils.ceiling(targetDate, Calendar.MONTH);
		targetDate = DateUtils.addDays(targetDate, -1);
		return targetDate;
	}
	

	public ITimePeriod getZurechungszeit() {
		return zurechungszeit;
	}


	public void setZurechungszeit(ITimePeriod zurechungszeit) {
		this.zurechungszeit = zurechungszeit;
	}


	public boolean isDienstunfall() {
		return dienstunfall;
	}


	public void setDienstunfall(boolean dienstunfall) {
		this.dienstunfall = dienstunfall;
	}
	
	
	
	public Date getZurechungszeitStartDate() {
		return zurechungszeitStartDate;
	}



	public Date getZurechungszeitEndDate() {
		return zurechungszeitEndDate;
	}





	@Override
	public String getPensionTyp() {
		if(!dienstunfall)
			return new String("Ruhestand wegen Dienstunfähigkeit");
		else
			return new String("Ruhestand wegen Dienstunfall");	
			
	}



	
}
