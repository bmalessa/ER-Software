package de.protin.support.pr.domain;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import de.protin.support.pr.domain.besoldung.Amtszulage;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.SonstigeZulage;
import de.protin.support.pr.domain.besoldung.Stellenzulage;
import de.protin.support.pr.domain.utils.DateCalculator;
import de.protin.support.pr.domain.utils.DateUtil;
import de.protin.support.pr.domain.utils.MonthCalculator;
import de.protin.support.pr.domain.utils.PrintUtils;



/**
 * Repräsentiert die Person, für die eine Versorgungsauskunft erstellt werden soll.
 * 
 * @author Bernd
 *
 */
public class Person { 
	
	private String name;
	private String vorname;
	private String anrede;
	
	
	private boolean schwerbehindert; // im Sinne des § 2 Abs. 2 des Neunten Buches Sozialgesetzbuch
	private boolean schwerbehindertBefore20001117; // 
	
	//besondere Altersgrenze für den Beamten (z.B. Polizei, Feuerwehr, 45 ruhegehaltsfähige Dienstjahre
	private boolean besondereAltersgrenze;
	private int besondereAltersgrenzeJahre;
	
	//Geburtsdatum
	private Date dateOfBirth;
	private String dateOfBirthString;
	//Datum der Zurruhesetzung
	private Date dateOfRetirement;
	private String dateOfRetirementString;
	//Antragaltersgrenze - Vollendung des 63 Lebesjahr
	private Date dateOfRequestRetirement;
	private String dateOfRequestRetirementString;
	//gesetzliche Altersgrenze für den Beamten (kann varieren z.B. ER mit 55 Jahren , ab Jahrgang 1964 bei 67 Jahren) 
	private Date dateOfLegalRetirement; 
	private String dateOfLegalRetirementString; 
	//Datum der Begründung des Beamtenverhältnis
	private Date dateOfSubstantiation; 
	private String dateOfSubstantiationString;
	
	
	//Position der Ruhegehaltfähige Dienstbezüge nach § 5 BeamtVG 
	private Grundgehalt besoldung;
	private Familienzuschlag familienzuschlag;
	private Stellenzulage stellenzulage;
	private Amtszulage amtszulage;
	private SonstigeZulage sonstigeZulage;
	
	
	public Person(String name, String vorname) {
		this.name = name;
		this.vorname = vorname;
	}
	
	public Person(String name, String vorname, String anrede, Date dateOfBirth) {
		this(name, vorname);
		this.anrede = anrede;
		this.dateOfBirth = dateOfBirth;
	}
	
	public Person(String name, String vorname, String titel, Date dateOfBirth, Date dateOfRetirement, Date dateOfRequestRetirement, Date dateOfLegalRetirement) {
		this(name, vorname, titel, dateOfBirth);
		this.dateOfRequestRetirement = dateOfRequestRetirement;
		this.dateOfRetirement = dateOfRetirement;
		this.dateOfLegalRetirement = dateOfLegalRetirement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getAnrede() {
		return anrede;
	}

	public void setAnrede(String titel) {
		this.anrede = titel;
	}

	public boolean isSchwerbehindert() {
		return schwerbehindert;
	}

	public void setSchwerbehindert(boolean schwerbehindert) {
		this.schwerbehindert = schwerbehindert;
	}
	
	public boolean isBesondereAltersgrenze() {
		return besondereAltersgrenze;
	}

	public boolean isSchwerbehindertBefore20001117() {
		return schwerbehindertBefore20001117;
	}

	public void setSchwerbehindertBefore20001117(boolean schwerbehindertBefore20001117) {
		this.schwerbehindertBefore20001117 = schwerbehindertBefore20001117;
	}

	public void setBesondereAltersgrenze(boolean besondereAltersgrenze) {
		this.besondereAltersgrenze = besondereAltersgrenze;
	}

	public int getBesondereAltersgrenzeJahre() {
		return besondereAltersgrenzeJahre;
	}

	public void setBesondereAltersgrenzeJahre(int besondereAltersgrenzeJahre) {
		this.besondereAltersgrenzeJahre = besondereAltersgrenzeJahre;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfRequestRetirement() {
		return dateOfRequestRetirement;
	}

	public void setDateOfRequestRetirement(Date dateOfRequestRetirement) {
		this.dateOfRequestRetirement = dateOfRequestRetirement;
	}

	public Date getDateOfRetirement() {
		return dateOfRetirement;
	}

	public void setDateOfRetirement(Date dateOfRetirement) {
		this.dateOfRetirement = dateOfRetirement;
	}
	
	public Date getDateOfLegalRetirement() {
		return dateOfLegalRetirement;
	}

	
	public void setDateOfLegalRetirement(Date dateOfLegalRetirement) {
		this.dateOfLegalRetirement = dateOfLegalRetirement;
	}
	
	
	public Date getDateOfSpecialLegalRetirement() {
		if(!this.besondereAltersgrenze || this.besondereAltersgrenzeJahre == 0) {
			return getDateOfLegalRetirement();
		}
		return DateUtils.addYears(dateOfBirth, this.besondereAltersgrenzeJahre);
	}


	public String getDateOfBirthString() {
		if(dateOfBirthString == null && dateOfBirth != null) {
			dateOfBirthString = PrintUtils.formatValue(dateOfBirth);
		}
		return dateOfBirthString;
	}

	public void setDateOfBirthString(String dateOfBirthString) {
		this.dateOfBirthString = dateOfBirthString;
		this.dateOfBirth = DateUtil.getDate(dateOfBirthString);
	}

	public String getDateOfRetirementString() {
		if(dateOfRetirementString == null && dateOfRetirement != null) {
			dateOfRetirementString = PrintUtils.formatValue(dateOfRetirement);
		}
		return dateOfRetirementString;
	}

	public void setDateOfRetirementString(String dateOfRetirementString) {
		this.dateOfRetirementString = dateOfRetirementString;
		this.dateOfRetirement = DateUtil.getDate(dateOfRetirementString);
	}

	public String getDateOfRequestRetirementString() {
		if(dateOfRequestRetirementString == null && dateOfRequestRetirement != null) {
			dateOfRequestRetirementString = PrintUtils.formatValue(dateOfRequestRetirement);
		}
		return dateOfRequestRetirementString;
	}

	public void setDateOfRequestRetirementString(String dateOfRequestRetirementString) {
		this.dateOfRequestRetirementString = dateOfRequestRetirementString;
		this.dateOfRequestRetirement = DateUtil.getDate(dateOfRequestRetirementString);
	}

	public String getDateOfLegalRetirementString() {
		if(dateOfLegalRetirementString == null && dateOfLegalRetirement != null) {
			dateOfLegalRetirementString = PrintUtils.formatValue(dateOfLegalRetirement);
		}		
		return dateOfLegalRetirementString;
	}

	
	public void setDateOfLegalRetirementString(String dateOfLegalRetirementString) {
		this.dateOfLegalRetirementString = dateOfLegalRetirementString;
		this.dateOfLegalRetirement = DateUtil.getDate(dateOfLegalRetirementString);
	}
	
	public Date getDateOfSubstantiation() {
		return dateOfSubstantiation;
	}

	public void setDateOfSubstantiation(Date dateOfSubstantiation) {
		this.dateOfSubstantiation = dateOfSubstantiation;
		
	}

	public String getDateOfSubstantiationString() {
		if(dateOfSubstantiationString == null && dateOfSubstantiation != null) {
			dateOfSubstantiationString = PrintUtils.formatValue(dateOfSubstantiation);
		}		
		return dateOfSubstantiationString;
	}

	public void setDateOfSubstantiationString(String dateOfSubstantiationString) {
		this.dateOfSubstantiationString = dateOfSubstantiationString;
		this.dateOfSubstantiation = DateUtil.getDate(dateOfSubstantiationString);
	}

	public Grundgehalt getBesoldung() {
		return besoldung;
	}

	public void setBesoldung(Grundgehalt besoldung) {
		this.besoldung = besoldung;
	}

	public Familienzuschlag getFamilienzuschlag() {
		return familienzuschlag;
	}

	public void setFamilienzuschlag(Familienzuschlag familienzuschlag) {
		this.familienzuschlag = familienzuschlag;
	}

	public Stellenzulage getStellenzulage() {
		return stellenzulage;
	}

	public void setStellenzulage(Stellenzulage stellenzulage) {
		this.stellenzulage = stellenzulage;
	}

	
	public Amtszulage getAmtszulage() {
		return amtszulage;
	}

	public void setAmtszulage(Amtszulage amtszulage) {
		this.amtszulage = amtszulage;
	}

	public SonstigeZulage getSonstigeZulage() {
		return sonstigeZulage;
	}

	public void setSonstigeZulage(SonstigeZulage sonstigeZulage) {
		this.sonstigeZulage = sonstigeZulage;
	}

	public String getPrintableDateOfLegalRetirement() {
		StringBuffer sb = new StringBuffer();
		Date date = DateCalculator.calculateDateOfLegalRetirement(this);
		date = DateCalculator.ceilingDateToEndOfMonth(date);
		sb.append(PrintUtils.formatValue(date));
		
		int amountOfMonth = 0;
		amountOfMonth = MonthCalculator.calculateMonthForRegelaltersgrenze(this);
		
		sb.append("\t( " + amountOfMonth/12 + " Jahre");
		
		int month = amountOfMonth % 12;
		if(month > 0) {
			sb.append(", " +  month + " Monate");
		}
		sb.append(")");
		return sb.toString();
	}
	
	
	public String getPrintableDateOfLegalRetirementForSchwerbehinderung() {
		StringBuffer sb = new StringBuffer();
		Date date = DateCalculator.calculateDateOfLegalRetirementForSchwerbehinderung(this);
		date = DateCalculator.ceilingDateToEndOfMonth(date);
		sb.append(PrintUtils.formatValue(date));
		
		int amountOfMonth = 0;
		amountOfMonth = MonthCalculator.calculateMonthForAltersgrenzeSchwerbehinderung(this);
		
		sb.append("\t( " + amountOfMonth/12 + " Jahre");
		
		int month = amountOfMonth % 12;
		if(month > 0) {
			sb.append(", " +  month + " Monate");
		}
		sb.append(")");
		return sb.toString();
	}
	
	
	
	public String getPrintableDateOfSpecialLegalRetirement() {
		StringBuffer sb = new StringBuffer();
		Date date = 	DateCalculator.calculateDateOfSpecialLegalRetirement(this);
		date = DateCalculator.ceilingDateToEndOfMonth(date);
		sb.append(PrintUtils.formatValue(date));
		sb.append("\t( " + this.besondereAltersgrenzeJahre + " Jahre)");
		return sb.toString();
	}

	public String getPrintableDateOfRequestRetirement() {
		StringBuffer sb = new StringBuffer();
		Date date = 	DateCalculator.calculateDateOfRequestRetirement(this);
		date = DateCalculator.ceilingDateToEndOfMonth(date);
		sb.append(PrintUtils.formatValue(date));
		
		int amountOfMonth = 63*12;
		if(this.isSchwerbehindert()) {
			amountOfMonth = MonthCalculator.calculateMonthForAntragsalterSchwerbehinderung(this);
		}
		
		sb.append("\t( " + amountOfMonth/12 + " Jahre");
		
		int month = amountOfMonth % 12;
		if(month > 0) {
			sb.append(", " +  month + " Monate");
		}
		sb.append(")");
		
		
		
		return sb.toString();
	}
	
}
