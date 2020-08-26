package test.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.tabelle.BesoldungstabelleFactory;
import de.protin.support.pr.domain.pension.Vorruhestand;
import de.protin.support.pr.domain.timeperiod.Para_6_AltersteilzeitBlockmodel;
import de.protin.support.pr.domain.timeperiod.Para_6_AusbildungBeamterAufWiderruf;
import de.protin.support.pr.domain.timeperiod.Para_6_Beamterdienstzeit;
import de.protin.support.pr.domain.utils.SelectionConstants;
import de.protin.support.pr.domain.validation.ValidationResult;
import test.domain.factories.DateFactory;
import test.domain.factories.PersonFactory;
import test.domain.factories.SalaryPartFactory;

public class TestVorruhestand {

	private Vorruhestand objectUnderTest;
	
	@Before
	public void setUp() throws Exception {
		PersonFactory pf = PersonFactory.getInstance();
		Person person = pf.setupPersonForVorruhestand();
   		
		setupBesoldung(person);
		
		objectUnderTest = new Vorruhestand(person, SelectionConstants.ANZUWENDENDES_RECHT_BUND);
		objectUnderTest.setBesoldungstabelle(BesoldungstabelleFactory.getInstance().
				getBesoldungstabelle(objectUnderTest.getAnzwRecht(), person.getDateOfRetirement()));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	protected void setupBesoldung(Person person) {
		SalaryPartFactory sf = SalaryPartFactory.getInstance();
		Grundgehalt grundgehalt  = sf.setupGrundgehalt();
		Familienzuschlag familienzuschlag = sf.setupFamilienzuschlag();
		
		person.setBesoldung(grundgehalt);
		person.setFamilienzuschlag(familienzuschlag);
	}
	
	
	@Test
	public void testSuccessValidationForVorruhestand() {
		addTimePeriodsWithAltersteilzeit1(objectUnderTest);
		ValidationResult validationResult = objectUnderTest.validate();
		// Datum des Eintritt in den Ruhestand unplausibel. Kein ruhegehaltsfähiger Zeitraum mit Ende Datum 31.07.2028 vorhanden.
		assertTrue(validationResult.getItems().size() == 1);
	}
	
	@Test
	public void testValidationFailedAltersgrenzeForVorruhestand() {
		//Bei Zurruhesetzung mind 63 jahre oder 62 Jahre bei Schwerbehinderung
		objectUnderTest.getPerson().setDateOfBirthString("12.07.1960");
		objectUnderTest.getPerson().setDateOfRetirementString("31.07.2020");
		addTimePeriodsWithAltersteilzeit1(objectUnderTest);
		ValidationResult validationResult = objectUnderTest.validate();
		assertFalse(validationResult.isSuccess());
	}
	
	@Test
	public void testValidationdAltersgrenzeForVorruhestand() {
		//Bei Zurruhesetzung mind 63 jahre oder 62 Jahre bei Schwerbehinderung
		objectUnderTest.getPerson().setDateOfBirthString("12.07.1956");
		objectUnderTest.getPerson().setDateOfRetirementString("31.07.2020");
		addTimePeriodsWithAltersteilzeit1(objectUnderTest);
		ValidationResult validationResult = objectUnderTest.validate();
		//Datum des Eintritt in den Ruhestand unplausibel. Kein ruhegehaltsfähiger Zeitraum mit Ende Datum 31.07.2020 vorhanden.
		assertTrue(validationResult.getItems().size() == 1);
	}
	
	@Test
	public void testFailedValidationForVorruhestand() {
		
		addTimePeriodsWithAltersteilzeit1(objectUnderTest);
		Set<ITimePeriod> timePeriods = objectUnderTest.getTimePeriods();
		ITimePeriod altersteilzeit = null;
		for (Iterator<ITimePeriod> iterator = timePeriods.iterator(); iterator.hasNext();) {
			ITimePeriod iTimePeriod = (ITimePeriod) iterator.next();
			if(iTimePeriod instanceof Para_6_AltersteilzeitBlockmodel) {
				altersteilzeit = iTimePeriod;
			}
			
		}
		objectUnderTest.removeTimePeriod(altersteilzeit);
		ValidationResult validationResult = objectUnderTest.validate();
		assertFalse(validationResult.isSuccess());
	}
	
	
	
	@Test
	public void testCalculateRuhegehaltOhneMinderung() {
		
		
		objectUnderTest.getPerson().setDateOfBirthString("12.07.1965");
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		addTimePeriodsWithAltersteilzeit1(objectUnderTest);
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		long years = objectUnderTest.calculateRuhegehaltsfaehigeDienstzeiten_Para_14().getYears();
		assertTrue(years == 44);
		assertEquals((double)ruhegehaltMinderung, (double)6.41f, 0.1);
	}
	
	@Test
	public void testCalculateMinderungRuhegehalt_1() {
		
		objectUnderTest.getPerson().setDateOfBirthString("12.07.1963");
		addTimePeriodsWithAltersteilzeit2(objectUnderTest);
		
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		long years = objectUnderTest.calculateRuhegehaltsfaehigeDienstzeiten_Para_14().getYears();
		assertTrue(years < 45);
		assertEquals((double)ruhegehaltMinderung, (double)6.41f, 0.1);
	}

	
	@Test
	public void testCalculateMinderungRuhegehalt_Schwerbehinderung_1() {
		
		addTimePeriodsWithAltersteilzeit2(objectUnderTest);
		objectUnderTest.getPerson().setDateOfRetirementString("31.07.2026");
		objectUnderTest.getPerson().setSchwerbehindert(true);
				
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		assertEquals((double)ruhegehaltMinderung, (double)6.61f, 0.1);
	}
	
	
	@Test
	public void testCalculateMinderungRuhegehalt_Schwerbehinderung_2() {
		//bei Schwebehinderung max 10.8% Abzug
		addTimePeriodsWithAltersteilzeit2(objectUnderTest);
		objectUnderTest.getPerson().setDateOfRetirementString("31.07.2024");
		objectUnderTest.getPerson().setSchwerbehindert(true);
		
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		assertEquals((double)ruhegehaltMinderung, (double)13.80f, 0.1);
	}
	
	
	@Test
	public void testCalculateMinderungRuhegehalt_SchwerbehinderungMitBesondereAltergrenze() {
		PersonFactory pf = PersonFactory.getInstance();
		Person person = pf.setupPersonForSpecialLegalAltersgrenze(60);
		addTimePeriodsWithAltersteilzeit2(objectUnderTest);
		
		objectUnderTest.getPerson().setSchwerbehindert(true);
		objectUnderTest.getPerson().setBesondereAltersgrenze(person.isBesondereAltersgrenze());
		objectUnderTest.getPerson().setBesondereAltersgrenzeJahre(person.getBesondereAltersgrenzeJahre());
		
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		assertEquals((double)ruhegehaltMinderung, (double)0.0f, 0.1);
	}
	

	/**
	 * 
	 * @param pension
	 */
	private void addTimePeriodsWithAltersteilzeit1(Vorruhestand pension) {
		ITimePeriod tp1 = new Para_6_AusbildungBeamterAufWiderruf(DateFactory.getDate("01.09.1981"), DateFactory.getDate("31.08.1983"), 1.0f);
		ITimePeriod tp2 = new Para_6_Beamterdienstzeit(DateFactory.getDate("01.09.1983"), DateFactory.getDate("30.04.2018"), 1.0f);
		ITimePeriod tp3 = new Para_6_AltersteilzeitBlockmodel(DateFactory.getDate("01.05.2018"), DateFactory.getDate("30.04.2027"), 0.9f);
		pension.addTimePeriod(tp1);
		pension.addTimePeriod(tp2);
		pension.addTimePeriod(tp3);
	}
	

	/**
	 * 
	 * @param pension
	 */
	private void addTimePeriodsWithAltersteilzeit2(Vorruhestand pension) {
		ITimePeriod tp2 = new Para_6_Beamterdienstzeit(DateFactory.getDate("01.09.1985"), DateFactory.getDate("30.04.2018"), 1.0f);
		ITimePeriod tp3 = new Para_6_AltersteilzeitBlockmodel(DateFactory.getDate("01.05.2018"), DateFactory.getDate("30.04.2027"), 0.9f);
		pension.addTimePeriod(tp2);
		pension.addTimePeriod(tp3);
	}

}
