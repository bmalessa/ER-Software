package test.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.tabelle.BesoldungstabelleFactory;
import de.protin.support.pr.domain.besoldung.zuschlag.KindererziehungszeitenZuschlag;
import de.protin.support.pr.domain.besoldung.zuschlag.Para_50_Kindererziehungszeit;
import de.protin.support.pr.domain.pension.AntragsRuhestand;
import de.protin.support.pr.domain.timeperiod.Para_6_AusbildungBeamterAufWiderruf;
import de.protin.support.pr.domain.timeperiod.Para_6_Beamterdienstzeit;
import de.protin.support.pr.domain.utils.SelectionConstants;
import de.protin.support.pr.domain.validation.ValidationResult;
import test.domain.factories.DateFactory;
import test.domain.factories.PersonFactory;
import test.domain.factories.SalaryPartFactory;

public class TestAntragsRuhestand {

	private AntragsRuhestand objectUnderTest;
	
	@Before
	public void setUp() throws Exception {
		
		PersonFactory pf = PersonFactory.getInstance();
		Person person = pf.setupPersonForAntragsaltersgrenze();
   		
		setupBesoldung(person);
		
		objectUnderTest = new AntragsRuhestand(person, SelectionConstants.ANZUWENDENDES_RECHT_BUND);
		objectUnderTest.setBesoldungstabelle(BesoldungstabelleFactory.getInstance().
				getBesoldungstabelle(objectUnderTest.getAnzwRecht(), person.getDateOfRetirement()));
	
		addTimePeriods(objectUnderTest);
		addKindererziehungsZuschlag(objectUnderTest);
		
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
	public void testValidation() {
		ValidationResult validationResult = objectUnderTest.validate();
		assertTrue(validationResult.isSuccess());
	}
	
	
	@Test
	public void testValidationDateForAntragsaltersgrenze() {
		ValidationResult validationResult = objectUnderTest.validate();
		assertTrue(validationResult.isSuccess());
	}
	

	

	@Test
	public void testFailedValidationDateForBesondereAltersgrenze() {
		PersonFactory pf = PersonFactory.getInstance();
		Person person = pf.setupPersonForSpecialLegalAltersgrenze(61);
		person.setDateOfRetirement(DateUtils.addYears(person.getDateOfBirth(), 60));
		person.setDateOfRequestRetirement(DateUtils.addYears(person.getDateOfBirth(), 60));
		setupBesoldung(person);
		objectUnderTest = new AntragsRuhestand(person, SelectionConstants.ANZUWENDENDES_RECHT_BUND);
		objectUnderTest.setBesoldungstabelle(BesoldungstabelleFactory.getInstance().
				getBesoldungstabelle(objectUnderTest.getAnzwRecht(), person.getDateOfRetirement()));
		
		ValidationResult validationResult = objectUnderTest.validate();
		assertFalse(validationResult.isSuccess());
	}

	
	@Test
	public void testFailedValidationDateForRegelaltersgrenze() {
		PersonFactory pf = PersonFactory.getInstance();
		Person person = pf.setupPersonForAntragsaltersgrenze();
		person.setDateOfRetirement(DateUtils.addYears(person.getDateOfBirth(), 62));
		person.setDateOfRequestRetirement(DateUtils.addYears(person.getDateOfBirth(), 62));
		setupBesoldung(person);
		objectUnderTest = new AntragsRuhestand(person, SelectionConstants.ANZUWENDENDES_RECHT_BUND);
		objectUnderTest.setBesoldungstabelle(BesoldungstabelleFactory.getInstance().
				getBesoldungstabelle(objectUnderTest.getAnzwRecht(), person.getDateOfRetirement()));
		
		ValidationResult validationResult = objectUnderTest.validate();
		assertFalse(validationResult.isSuccess());
	}

	
	
	
	@Test
	public void testCalculateMinderungRuhegehalt_1() {
		
		objectUnderTest.getPerson().setDateOfBirthString("12.07.1962");
		objectUnderTest.getPerson().setFamilienzuschlag( new Familienzuschlag(1));
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = objectUnderTest.getPerson().getFamilienzuschlag();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		float salaryFZ =  objectUnderTest.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		assertEquals((double)salaryFZ, (double)149.36f, 0.1);
		
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		assertEquals((double)ruhegehaltMinderung, (double)9.60f, 0.1);
	}
	
	@Test
	public void testCalculateMinderungRuhegehalt_2() {
		
		objectUnderTest.getPerson().setDateOfBirthString("12.07.1964");
		objectUnderTest.getPerson().setFamilienzuschlag( new Familienzuschlag(0));
		
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = objectUnderTest.getPerson().getFamilienzuschlag();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		float salaryFZ =  objectUnderTest.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		assertEquals((double)salaryFZ, (double)0.0f, 0.1);
		
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		assertEquals((double)ruhegehaltMinderung, (double)13.6f, 0.1);
	}

	
	@Test
	public void testCalculateMinderungRuhegehalt_Schwerbehinderung_1() {
		
		objectUnderTest.getPerson().setSchwerbehindert(true);
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = objectUnderTest.getPerson().getFamilienzuschlag();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		float salaryFZ =  objectUnderTest.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		assertEquals((double)salaryFZ, (double)74.68f, 0.1);
		
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		assertEquals((double)ruhegehaltMinderung, (double)6.6f, 0.1);
	}
	
	
	@Test
	public void testCalculateMinderungRuhegehalt_Schwerbehinderung_2() {
		
		objectUnderTest.getPerson().setDateOfBirthString("12.09.1964");
		objectUnderTest.getPerson().setSchwerbehindert(true);
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = objectUnderTest.getPerson().getFamilienzuschlag();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		float salaryFZ =  objectUnderTest.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		assertEquals((double)salaryFZ, (double)74.68f, 0.1);
		
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		assertEquals((double)ruhegehaltMinderung, (double)11.4f, 0.1);
	}
	
	
	@Test
	public void testCalculateMinderungRuhegehalt_SchwerbehinderungMitBesondereAltergrenze() {
		PersonFactory pf = PersonFactory.getInstance();
		Person person = pf.setupPersonForSpecialLegalAltersgrenze(60);
		
		objectUnderTest.getPerson().setSchwerbehindert(true);
		objectUnderTest.getPerson().setBesondereAltersgrenze(person.isBesondereAltersgrenze());
		objectUnderTest.getPerson().setBesondereAltersgrenzeJahre(person.getBesondereAltersgrenzeJahre());
		
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = objectUnderTest.getPerson().getFamilienzuschlag();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		float salaryFZ =  objectUnderTest.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		assertEquals((double)salaryFZ, (double)74.68f, 0.1);
		
		float ruhegehaltMinderung = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		assertEquals((double)ruhegehaltMinderung, (double)0.0f, 0.1);
	}
	
	
	/**
	 * 
	 * @param pension
	 */
	private void addTimePeriods(AntragsRuhestand pension) {
		ITimePeriod tp1 = new Para_6_AusbildungBeamterAufWiderruf(DateFactory.getDate("01.09.1981"), DateFactory.getDate("31.08.1983"), 1.0f);
		ITimePeriod tp2 = new Para_6_Beamterdienstzeit(DateFactory.getDate("01.09.1983"), DateFactory.getDate("31.07.2026"), 1.0f);
		pension.addTimePeriod(tp1);
		pension.addTimePeriod(tp2);
	}
	
	

	/**
	 * Kindererziehung
	 * @param pension
	 */
	private void addKindererziehungsZuschlag(AntragsRuhestand pension) {	
		KindererziehungszeitenZuschlag kindererziehungszuschlag = new KindererziehungszeitenZuschlag(pension.getBesoldungstabelle().getAktuellenRentenwertWestForKindererziehungszuschlag());
		Para_50_Kindererziehungszeit kind1 = new Para_50_Kindererziehungszeit("Kind 1", DateFactory.getDate("12.02.1991"), null, null, true);
		Para_50_Kindererziehungszeit kind2 = new Para_50_Kindererziehungszeit("Kind 2", DateFactory.getDate("16.04.1994"), null, null, true);
		kindererziehungszuschlag.addKindererziehungszeit(kind1, KindererziehungszeitenZuschlag.KEZ_VOR_1992);
		kindererziehungszuschlag.addKindererziehungszeit(kind2, KindererziehungszeitenZuschlag.KEZ_NACH_1991);
		pension.setKindererziehungsZuschlag(kindererziehungszuschlag);
	}
 
	
	
}
