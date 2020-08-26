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
import de.protin.support.pr.domain.besoldung.zuschlag.KindererziehungszeitenZuschlag;
import de.protin.support.pr.domain.besoldung.zuschlag.Para_50_Kindererziehungszeit;
import de.protin.support.pr.domain.pension.RegelaltersgrenzeRuhestand;
import de.protin.support.pr.domain.timeperiod.Para_6_AusbildungBeamterAufWiderruf;
import de.protin.support.pr.domain.timeperiod.Para_6_Beamterdienstzeit;
import de.protin.support.pr.domain.utils.SelectionConstants;
import de.protin.support.pr.domain.validation.ValidationResult;
import test.domain.factories.DateFactory;
import test.domain.factories.PersonFactory;
import test.domain.factories.SalaryPartFactory;

public class TestRegelaltersgrenzeRuhestand {

	
	private RegelaltersgrenzeRuhestand objectUnderTest;
	
	@Before
	public void setUp() throws Exception {
		
		PersonFactory pf = PersonFactory.getInstance();
		//Person person = pf.setupPersonForRegelaltersgrenze();
		
		Person person = pf.setupPersonForSpecialLegalAltersgrenze(62);
		
		SalaryPartFactory sf = SalaryPartFactory.getInstance();
		Grundgehalt grundgehalt  = sf.setupGrundgehalt();
		Familienzuschlag familienzuschlag = sf.setupFamilienzuschlag();
		
		person.setBesoldung(grundgehalt);
		person.setFamilienzuschlag(familienzuschlag);
		
		
		objectUnderTest = new RegelaltersgrenzeRuhestand(person, SelectionConstants.ANZUWENDENDES_RECHT_BUND);
		objectUnderTest.setBesoldungstabelle(BesoldungstabelleFactory.getInstance().
				getBesoldungstabelle(objectUnderTest.getAnzwRecht(), person.getDateOfRetirement()));
	
		addTimePeriods(objectUnderTest);
		addKindererziehungsZuschlag(objectUnderTest);
		
		
		addDeductionParts(objectUnderTest);
		addAdditionParts(objectUnderTest);

	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void testValidation() {
		ValidationResult validationResult = objectUnderTest.validate();
		//Datum des Eintritt in den Ruhestand unplausibel. Kein ruhegehaltsfähiger Zeitraum mit Ende Datum 11.07.2025 vorhanden.
		//Besondere Regelaltersgrenze(31.07.2025) zum Antragszeitpunkt (12.07.2025) ist noch nicht erreicht.
		assertTrue(validationResult.getItems().size() == 2);
	}

	
	
	
	@Test
	public void testCalculateRuhegehaltsfaehigeDienstbezuege() {
		
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = objectUnderTest.getPerson().getFamilienzuschlag();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		float salaryFZ =  objectUnderTest.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		assertEquals((double)salaryFZ, (double)74.68f, 0.1);
		
		float calculateRuhegehaltsfaehigenDienstbezug = objectUnderTest.calculateRuhegehaltsfaehigenDienstbezug();
		assertEquals((double)calculateRuhegehaltsfaehigenDienstbezug, (double)5240.87f, 0.1);
	}
	
	
	@Test
	public void testCalculateErdientesRuhegehalt() {
		Grundgehalt grundgehalt = objectUnderTest.getPerson().getBesoldung();
		Familienzuschlag familienzuschlag = objectUnderTest.getPerson().getFamilienzuschlag();
		
		float salaryGG = objectUnderTest.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		assertEquals((double)salaryGG, (double)5166.19f, 0.1);
		float salaryFZ =  objectUnderTest.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		assertEquals((double)salaryFZ, (double)74.68f, 0.1);
		
		objectUnderTest.calculateRuhegehaltsfaehigenDienstbezug();
		objectUnderTest.calculateRuhegehaltssatz_Para_14_1_Neue_Fassung();
		
		float erdientesRuhegehalt = objectUnderTest.calculateErdientesRuhegehalt();
		assertEquals((double)erdientesRuhegehalt, (double)3598.90f, 0.1);
	}
		
	
	
	@Test
	public void testCalculateMindestversorgung() {
		float mindestversorgung = objectUnderTest.calculateMindestversorgung();
		assertEquals((double)mindestversorgung, (double)1827.13f, 0.1);
	}
	
	
	
	@Test
	public void testCalculateRuhegehaltssatz() {
		float calculatedVersorgungssatz = objectUnderTest.calculateRuhegehaltssatz_Para_14_1_Neue_Fassung();
		assertEquals((double)calculatedVersorgungssatz, (double)69.34f, 0.1);
	}

	

	@Test
	public void testCalculatePflegeleistungsabzug() {
		objectUnderTest.calculateRuhegehaltsfaehigenDienstbezug();
		objectUnderTest.calculateRuhegehaltssatz_Para_14_1_Neue_Fassung();
		float pflegeleistungsabzug = objectUnderTest.getAbzugPflegeleistung();
		assertEquals((double)pflegeleistungsabzug, (double)54.87f, 0.1);
	}
	
	
	
	//§ 50a Kindererziehungszuschlag
	@Test
	public void testCalculateKindererziehungszuschlag() {
		
		KindererziehungszeitenZuschlag kindererziehungszuschlag = objectUnderTest.getKindererziehungsZuschlag();
		float kez = kindererziehungszuschlag.calculateKindererziehungszuschlag();
		assertEquals((double)kez, (double)187.96f, 0.1);
		
		//Höchstgrenze berechnen
		//ruhegehaltsf. Bezüge + KEZ nicht größer als 71.75%
		float ruhegehaltsfaehigenDienstbezug = objectUnderTest.calculateRuhegehaltsfaehigenDienstbezug();
		float ruhegehaltssatz_Para_14_1_Neue_Fassung = objectUnderTest.calculateRuhegehaltssatz_Para_14_1_Neue_Fassung();
		float erdientesRuhegehalt = objectUnderTest.calculateErdientesRuhegehalt();
		
		float erdientesRuhegehaltPlusKez = erdientesRuhegehalt + kez;
		float maxRuhegehaltsfaehigenDienstbezug =  ruhegehaltsfaehigenDienstbezug * 0.7175f;
		
		assertFalse(erdientesRuhegehaltPlusKez < maxRuhegehaltsfaehigenDienstbezug);
		
		//wenn erdientesRuhegehaltPlusKez > maxRuhegehaltsfaehigenDienstbezug -> dann wird maxRuhegehaltsfaehigenDienstbezug genommen
		erdientesRuhegehaltPlusKez =  erdientesRuhegehalt + 1000.00f;
		if(erdientesRuhegehaltPlusKez > maxRuhegehaltsfaehigenDienstbezug) {
			//KEZ wird gekürzt, so dass max 71.75 % erdientes Ruhegehalt  vorhanden ist.
			float gap = erdientesRuhegehaltPlusKez - maxRuhegehaltsfaehigenDienstbezug;
			kez = kez - gap;
			assertTrue(kez < 0);
		}
		 
	}
	
	
    
	@Test
	public void testcalculateAbschlag_Para_14_BeamtVG() {
		float calculateAbschlag_Para_14_BeamtVG = objectUnderTest.calculateAbschlag_Para_14_BeamtVG();
	}
	
	
	
	//todo
	//§ 50b Kindererziehungsergänzungszuschlag
	//§ 57 Kürzung der Versorgungsbezüge nach der Ehescheidung
	
	
	/**
	 * 
	 * @param pension
	 */
	private void addTimePeriods(RegelaltersgrenzeRuhestand pension) {
		ITimePeriod tp1 = new Para_6_AusbildungBeamterAufWiderruf(DateFactory.getDate("01.09.1981"), DateFactory.getDate("31.08.1983"), 1.0f);
		ITimePeriod tp2 = new Para_6_Beamterdienstzeit(DateFactory.getDate("01.09.1983"), DateFactory.getDate("30.04.2020"), 1.0f);
		pension.addTimePeriod(tp1);
		pension.addTimePeriod(tp2);
	}
	
	
	
	/**
	 * Zurechnungszeiten für Kindererziehung und DDU sowie Erhöhungszuschlag
	 * @param pension
	 */
	private void addAdditionParts(RegelaltersgrenzeRuhestand pension) {	
		
	}

	

	/**
	 * Zurechnungszeiten für Kindererziehung und DDU sowie Erhöhungszuschlag
	 * @param pension
	 */
	private void addKindererziehungsZuschlag(RegelaltersgrenzeRuhestand pension) {	
		KindererziehungszeitenZuschlag kindererziehungszuschlag = new KindererziehungszeitenZuschlag(pension.getBesoldungstabelle().getAktuellenRentenwertWestForKindererziehungszuschlag());
		//kindererziehungszuschlag.setPension(pension);
		Para_50_Kindererziehungszeit kind1 = new Para_50_Kindererziehungszeit("Kind1", DateFactory.getDate("12.02.1991"), null, null, true);
		Para_50_Kindererziehungszeit kind2 = new Para_50_Kindererziehungszeit("Kind2", DateFactory.getDate("16.04.1994"), null, null, true);
		kindererziehungszuschlag.addKindererziehungszeit(kind1, KindererziehungszeitenZuschlag.KEZ_VOR_1992);
		kindererziehungszuschlag.addKindererziehungszeit(kind2, KindererziehungszeitenZuschlag.KEZ_NACH_1991);
		pension.setKindererziehungsZuschlag(kindererziehungszuschlag);
	}
 
	/**
	 * Anrechnung von Renten, Versorgungsausgleich oder Sonstigen Abzügen
	 * @param pension
	 */
	private void addDeductionParts(RegelaltersgrenzeRuhestand pension) { }
	
}