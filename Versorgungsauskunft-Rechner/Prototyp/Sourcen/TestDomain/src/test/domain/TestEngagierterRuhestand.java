package test.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.tabelle.BesoldungstabelleFactory;
import de.protin.support.pr.domain.pension.EngagierterRuhestand;
import de.protin.support.pr.domain.timeperiod.Para_6_AusbildungBeamterAufWiderruf;
import de.protin.support.pr.domain.timeperiod.Para_6_Beamterdienstzeit;
import de.protin.support.pr.domain.utils.SelectionConstants;
import de.protin.support.pr.domain.validation.ValidationResult;
import test.domain.factories.DateFactory;
import test.domain.factories.PersonFactory;

public class TestEngagierterRuhestand {

	private EngagierterRuhestand objectUnderTest;
	
	
	@Before
	public void setUp() throws Exception {
		
		PersonFactory pf = PersonFactory.getInstance();
		Person person = pf.setupPersonForEngagierterRuhestand();
		person.setDateOfBirthString("12.07.1963");
		person.setDateOfRetirementString("01.05.2020");
		person.setDateOfLegalRetirementString("31.07.2018");  //frühestens mit 55 Jahren
		
		person.setBesoldung(new Grundgehalt("A", 12, 8));
		person.setFamilienzuschlag(new Familienzuschlag(1));
		
		objectUnderTest = new EngagierterRuhestand(person, SelectionConstants.ANZUWENDENDES_RECHT_BUND);
		objectUnderTest.setBesoldungstabelle(BesoldungstabelleFactory.getInstance().
				getBesoldungstabelle(objectUnderTest.getAnzwRecht(), person.getDateOfRetirement()));
	
		addTimePeriods(objectUnderTest);
		
		
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
	public void testCalculateRuhegehaltsfaehigeDienstbezuege() {
		
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = objectUnderTest.getPerson().getFamilienzuschlag();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		
		float salaryFZ = objectUnderTest.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		assertEquals((double)salaryFZ, (double)149.36f, 0.1);
		
		float calculateRuhegehaltsfaehigenDienstbezug = objectUnderTest.calculateRuhegehaltsfaehigenDienstbezug();
		assertEquals((double)calculateRuhegehaltsfaehigenDienstbezug, (double)5315.55f, 0.1);
	}
	
	
	@Test
	public void testCalculateErdientesRuhegehalt() {
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = objectUnderTest.getPerson().getFamilienzuschlag();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		float salaryFZ =  objectUnderTest.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		assertEquals((double)salaryFZ, (double)149.36f, 0.1);
		
		objectUnderTest.calculateRuhegehaltsfaehigenDienstbezug();
		objectUnderTest.calculateRuhegehaltssatz_Para_14_1_Neue_Fassung();
		
		float erdientesRuhegehalt = objectUnderTest.calculateErdientesRuhegehalt();
		assertEquals((double)erdientesRuhegehalt, (double)3650.37f, 0.1);
	}
		
	
	
	@Test
	public void testCalculateMindestversorgung() {
		float mindestversorgung = objectUnderTest.calculateMindestversorgung();
		assertEquals((double)mindestversorgung, (double)1875.19f, 0.1);
	}
	
	
	
	@Test
	public void testCalculateRuhegehaltssatz() {
		float calculatedVersorgungssatz = objectUnderTest.calculateRuhegehaltssatz_Para_14_1_Neue_Fassung();
		assertEquals((double)calculatedVersorgungssatz, (double)69.36f, 0.1);
	}

	

	@Test
	public void testCalculatePflegeleistungsabzug() {
		objectUnderTest.calculateRuhegehaltsfaehigenDienstbezug();
		objectUnderTest.calculateRuhegehaltssatz_Para_14_1_Neue_Fassung();
		float pflegeleistungsabzug = objectUnderTest.getAbzugPflegeleistung();
		assertEquals((double)pflegeleistungsabzug, (double)55.67f, 0.1);
	}
	
	
	
	
	
	

	@Test
	public void testcalculateAbschlag_Para_14_BeamtVG() {
		float calculateAbschlag_Para_14_BeamtVG = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
		assertEquals((double)calculateAbschlag_Para_14_BeamtVG, (double)0.0f, 0.1);
	}
	
	
	
	
	@Test
	public void testcalculateVergeichsberechnung_Para_85_BeamtVG() {
		boolean vergleichsberechnung_Para_85_required = objectUnderTest.isVergleichsberechnung_Para_85_required();
		assertTrue(vergleichsberechnung_Para_85_required);
		
		float calculateRuhegehaltssatz_Para_85_Vergleichsberechnung = objectUnderTest.calculateRuhegehaltssatz_Para_85_Vergleichsberechnung();
		assertEquals((double)calculateRuhegehaltssatz_Para_85_Vergleichsberechnung, (double)60.58f, 0.1);
	}
	
	
	
	/**
	 * 
	 * @param pension
	 */
	private void addTimePeriods(EngagierterRuhestand pension) {
		ITimePeriod tp1 = new Para_6_AusbildungBeamterAufWiderruf(DateFactory.getDate("01.09.1981"), DateFactory.getDate("31.08.1983"), 1.0f);
		ITimePeriod tp2 = new Para_6_Beamterdienstzeit(DateFactory.getDate("01.09.1983"), DateFactory.getDate("30.04.2020"), 1.0f);
		pension.addTimePeriod(tp1);
		pension.addTimePeriod(tp2);
	}
	

}
