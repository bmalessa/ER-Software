package test.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.tabelle.BesoldungstabelleFactory;
import de.protin.support.pr.domain.besoldung.zuschlag.Zurechnungszeiten;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.timeperiod.Para_6_AusbildungBeamterAufWiderruf;
import de.protin.support.pr.domain.timeperiod.Para_6_Beamterdienstzeit;
import de.protin.support.pr.domain.utils.SelectionConstants;
import de.protin.support.pr.domain.utils.TimePeriodDetails;
import de.protin.support.pr.domain.validation.ValidationResult;
import test.domain.factories.DateFactory;
import test.domain.factories.PersonFactory;
import test.domain.factories.SalaryPartFactory;

public class TestDienstunfaehigkeitRuhestand {

	
	private Dienstunfaehigkeit objectUnderTest;
	
	
	@Before
	public void setUp() throws Exception {
		
		PersonFactory pf = PersonFactory.getInstance();
		Person person = pf.setupPersonForDienstunfaehigkeit();
   		
		setupBesoldung(person);
		
		objectUnderTest = new Dienstunfaehigkeit(person, SelectionConstants.ANZUWENDENDES_RECHT_BUND);
		objectUnderTest.setBesoldungstabelle(BesoldungstabelleFactory.getInstance().
				getBesoldungstabelle(objectUnderTest.getAnzwRecht(), person.getDateOfRetirement()));
		
	}
	
	protected void setupBesoldung(Person person) {
		SalaryPartFactory sf = SalaryPartFactory.getInstance();
		Grundgehalt grundgehalt  = sf.setupGrundgehalt();
		Familienzuschlag familienzuschlag = sf.setupFamilienzuschlag();
		
		person.setBesoldung(grundgehalt);
		person.setFamilienzuschlag(familienzuschlag);
	}
	
	

	@After
	public void tearDown() throws Exception {
	}
	
	
	
	@Test
	public void testValidationFalse() {
		//§5 BeamtVG mindestens 5 jahre Rgf-Zeiten erforderlich
		ValidationResult validationResult = objectUnderTest.validate();
		assertFalse(validationResult.isSuccess());
	}
	
	@Test
	public void testValidationTrue() {
		addTimePeriods1(objectUnderTest);
		ValidationResult validationResult = objectUnderTest.validate();
		
		//Validation for Begründung des beaV und Eintritt in Ruhestand Datum sind unplausibel 
		assertTrue(validationResult.getItems().size() == 2);
	}
	
	@Test
	public void testCalculateMinderungRuhegehalt_1() {
		
		objectUnderTest.getPerson().setDateOfBirthString("12.07.1965");
		addTimePeriods1(objectUnderTest);
		
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = objectUnderTest.getPerson().getFamilienzuschlag();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		float salaryFZ =  objectUnderTest.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		assertEquals((double)salaryFZ, (double)74.68f, 0.1);
		
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		
		//Minderung max. 10.8%
		assertEquals((double)ruhegehaltMinderung, (double)10.8f, 0.1);
	}
	

	@Test
	public void testCalculateMinderungRuhegehalt_2() {
		
		objectUnderTest.getPerson().setDateOfBirthString("01.03.1957");
		objectUnderTest.getPerson().setDateOfRetirementString("30.04.2020");
		addTimePeriods2(objectUnderTest);
		
		
		
		
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = objectUnderTest.getPerson().getFamilienzuschlag();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		float salaryFZ =  objectUnderTest.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		assertEquals((double)salaryFZ, (double)74.68f, 0.1);
		
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		
		
		
		//keine Minderung, wenn 63 Lebensjahr vollendet und 40 ruhegehaltsfähige Dienstjahre
		assertEquals((double)ruhegehaltMinderung, (double)0.0f, 0.1);
	}

	
	@Test
	public void testCalculateMinderungRuhegehalt_3() {
		
		objectUnderTest.getPerson().setDateOfBirthString("01.03.1962");
		objectUnderTest.getPerson().setDateOfRetirementString("30.04.2020");
		objectUnderTest.getPerson().setBesondereAltersgrenze(true);
		objectUnderTest.getPerson().setBesondereAltersgrenzeJahre(60);
		addTimePeriods1(objectUnderTest);
		
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = objectUnderTest.getPerson().getFamilienzuschlag();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		float salaryFZ =  objectUnderTest.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		assertEquals((double)salaryFZ, (double)74.68f, 0.1);
		
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		
		//Minderung auf Basis der besondere Altersgrenze (60 Jahre). Nicht 65 Jahre
		assertEquals((double)ruhegehaltMinderung, (double)6.6f, 0.1);
	}

	
	
	@Test
	public void testCalculateZurechnung_1() {
		
		objectUnderTest.getPerson().setDateOfBirthString("20.04.1965");
		objectUnderTest.getPerson().setDateOfRetirementString("30.09.2020");
		addTimePeriods1(objectUnderTest);
		
		
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		
		TimePeriodDetails zurechnung = objectUnderTest.calculateZurechnungzeit_Para_13_BeamtVG();
		
		assertEquals(zurechnung.getYears(), 3);
		assertEquals(zurechnung.getDays(), 13);
	}
	
	
	@Test
	public void testCalculatePension_1() {
		
		objectUnderTest.getPerson().setDateOfBirthString("20.04.1965");
		objectUnderTest.getPerson().setDateOfRetirementString("30.09.2020");
		objectUnderTest.getPerson().setFamilienzuschlag(new Familienzuschlag(1));
		addTimePeriods1(objectUnderTest);
		
		
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		
		
		
		TimePeriodDetails calculateRuhegehaltsfaehigeDienstzeiten_Para_14 = objectUnderTest.calculateRuhegehaltsfaehigeDienstzeiten_Para_14();
		calculateRuhegehaltsfaehigeDienstzeiten_Para_14.getYears();
		assertEquals((int)calculateRuhegehaltsfaehigeDienstzeiten_Para_14.getYears(), 33);
		assertEquals((int)calculateRuhegehaltsfaehigeDienstzeiten_Para_14.getDays(), 44);
		
		float calculateAbschlag_Para_14_BeamtVG = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		assertEquals((double)calculateAbschlag_Para_14_BeamtVG, (double)10.8f, 0.1);
		
		TimePeriodDetails zurechnung = objectUnderTest.calculateZurechnungzeit_Para_13_BeamtVG();
		assertEquals(zurechnung.getYears(), 3);
		assertEquals(zurechnung.getDays(), 13);
		
		float calculateMindestversorgung = objectUnderTest.calculateMindestversorgung();
		assertEquals((double)calculateMindestversorgung, (double)1875.19f, 0.1);
		
		float calculateRuhegehaltsfaehigenDienstbezug = objectUnderTest.calculateRuhegehaltsfaehigenDienstbezug();
		assertEquals((double)calculateRuhegehaltsfaehigenDienstbezug, (double)5315.55f, 0.1);
		
		float calculateRuhegehaltssatz = objectUnderTest.calculateRuhegehaltssatz_Para_14_1_Neue_Fassung();
		assertEquals((double)calculateRuhegehaltssatz, (double)59.43f, 0.1);
		
		float pflegeLeistungAbzug = objectUnderTest.getAbzugPflegeleistung();
		assertEquals((double)pflegeLeistungAbzug, (double)47.70f, 0.1);
		
		float erdientesRuhegehalt = objectUnderTest.calculateErdientesRuhegehalt();
		assertEquals((double)erdientesRuhegehalt, (double)3128.25f, 0.1);
		
	}
	
	
	@Test
	public void testCalculatePension_2() {
		
		objectUnderTest.getPerson().setDateOfBirthString("12.07.1963");
		objectUnderTest.getPerson().setDateOfRetirementString("30.04.2020");
		objectUnderTest.getPerson().setFamilienzuschlag(new Familienzuschlag(1));
		addTimePeriods3(objectUnderTest);
		
		
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		
		
		
		TimePeriodDetails calculateRuhegehaltsfaehigeDienstzeiten_Para_14 = objectUnderTest.calculateRuhegehaltsfaehigeDienstzeiten_Para_14();
		calculateRuhegehaltsfaehigeDienstzeiten_Para_14.getYears();
		assertEquals((int)calculateRuhegehaltsfaehigeDienstzeiten_Para_14.getYears(), 40);
		assertEquals((int)calculateRuhegehaltsfaehigeDienstzeiten_Para_14.getDays(), 291);
		
		float calculateAbschlag_Para_14_BeamtVG = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		assertEquals((double)calculateAbschlag_Para_14_BeamtVG, (double)10.8f, 0.1);
		
		TimePeriodDetails zurechnung = objectUnderTest.calculateZurechnungzeit_Para_13_BeamtVG();
		assertEquals(zurechnung.getYears(), 2);
		assertEquals(zurechnung.getDays(), 48);
		
		float calculateMindestversorgung = objectUnderTest.calculateMindestversorgung();
		assertEquals((double)calculateMindestversorgung, (double)1875.19f, 0.1);
		
		float calculateRuhegehaltsfaehigenDienstbezug = objectUnderTest.calculateRuhegehaltsfaehigenDienstbezug();
		assertEquals((double)calculateRuhegehaltsfaehigenDienstbezug, (double)5315.55f, 0.1);
		
		float calculateRuhegehaltssatz = objectUnderTest.calculateRuhegehaltssatz_Para_14_1_Neue_Fassung();
		assertEquals((double)calculateRuhegehaltssatz, (double)71.75f, 0.1);
		
		float pflegeLeistungAbzug = objectUnderTest.getAbzugPflegeleistung();
		assertEquals((double)pflegeLeistungAbzug, (double)57.58f, 0.1);
		
		float erdientesRuhegehalt = objectUnderTest.calculateErdientesRuhegehalt();
		assertEquals((double)erdientesRuhegehalt, (double)3776.14f, 0.1);
		
		float abzugVomRuhegeld = erdientesRuhegehalt / 100 * calculateAbschlag_Para_14_BeamtVG;
		assertEquals((double)abzugVomRuhegeld, (double)407.82f, 0.1);
		
		float ruhegehaltNachAbzug = erdientesRuhegehalt - abzugVomRuhegeld;
		assertEquals((double)ruhegehaltNachAbzug, (double)3368.32f, 0.1);
	}
	
	
	@Test
	public void testUtils() {
		addTimePeriods1(objectUnderTest);
		assertTrue(Zurechnungszeiten.isZurechnungszeitAvailable(objectUnderTest));
	}
	
	
	
	/**
	 * Weniger als 40 Jahre ruhegehaltsfähige Dienstbezüge
	 * @param pension
	 */
	private void addTimePeriods1(Dienstunfaehigkeit pension) {
		ITimePeriod tp1 = new Para_6_AusbildungBeamterAufWiderruf(DateFactory.getDate("01.09.1990"), DateFactory.getDate("31.08.1992"), 1.0f);
		ITimePeriod tp2 = new Para_6_Beamterdienstzeit(DateFactory.getDate("01.09.1992"), DateFactory.getDate("30.09.2020"), 1.0f);
		pension.addTimePeriod(tp1);
		pension.addTimePeriod(tp2);
	}
	

	
	/**
	 * Mehr als 40 Jahre ruhegehaltsfähige Dienstbezüge
	 * @param pension
	 */
	private void addTimePeriods2(Dienstunfaehigkeit pension) {
		ITimePeriod tp1 = new Para_6_AusbildungBeamterAufWiderruf(DateFactory.getDate("01.09.1975"), DateFactory.getDate("31.08.1977"), 1.0f);
		ITimePeriod tp2 = new Para_6_Beamterdienstzeit(DateFactory.getDate("01.09.1977"), DateFactory.getDate("30.09.2020"), 1.0f);
		pension.addTimePeriod(tp1);
		pension.addTimePeriod(tp2);
	}
	
	
	
	/**
	 * Weniger als 40 Jahre ruhegehaltsfähige Dienstbezüge
	 * @param pension
	 */
	private void addTimePeriods3(Dienstunfaehigkeit pension) {
		ITimePeriod tp1 = new Para_6_AusbildungBeamterAufWiderruf(DateFactory.getDate("01.09.1981"), DateFactory.getDate("31.08.1983"), 1.0f);
		ITimePeriod tp2 = new Para_6_Beamterdienstzeit(DateFactory.getDate("01.09.1983"), DateFactory.getDate("30.04.2020"), 1.0f);
		pension.addTimePeriod(tp1);
		pension.addTimePeriod(tp2);
	}
}
