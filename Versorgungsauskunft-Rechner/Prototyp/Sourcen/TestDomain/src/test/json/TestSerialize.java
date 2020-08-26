package test.json;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.tabelle.BesoldungstabelleFactory;
import de.protin.support.pr.domain.json.JsonDeserializer;
import de.protin.support.pr.domain.json.JsonSerializer;
import de.protin.support.pr.domain.pension.EngagierterRuhestand;
import de.protin.support.pr.domain.timeperiod.Para_6_AusbildungBeamterAufWiderruf;
import de.protin.support.pr.domain.timeperiod.Para_6_Beamterdienstzeit;
import de.protin.support.pr.domain.utils.SelectionConstants;
import test.domain.factories.DateFactory;
import test.domain.factories.PersonFactory;

public class TestSerialize {
	
	private static String FILE_NAME = "pension.json";
			
	private IPension objectUnderTest;
	
	
	
	@Before
	public void setUp() throws Exception {
		
		PersonFactory pf = PersonFactory.getInstance();
		Person person = pf.setupPersonForEngagierterRuhestand();
		person.setDateOfBirthString("12.07.1963");
		person.setDateOfRetirementString("30.04.2020");
		person.setDateOfLegalRetirementString("31.07.2018");  //frühestens mit 55 Jahren
		person.setDateOfLegalRetirementString("31.07.2018");
		person.setDateOfSubstantiationString("01.08.1980");
		
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
	public void testSerializeToJson() {
		String json = JsonSerializer.serializeToJson(objectUnderTest);
		assertNotNull(json);
		System.out.println(json);
		saveToFile(json);
	}
	

	@Test
	public void testDeserializeFromJson() {
		
		String jsonString = readFromFile();
		IPension pension=  JsonDeserializer.deserializeFromJson(jsonString);
	    assertTrue(pension.getTimePeriods().size() == 2);
	    assertTrue(pension instanceof EngagierterRuhestand);
	
	}
	
	
	/**
	 * 
	 * @param pension
	 */
	private void addTimePeriods(IPension pension) {
		ITimePeriod tp1 = new Para_6_AusbildungBeamterAufWiderruf(DateFactory.getDate("01.09.1981"), DateFactory.getDate("31.08.1983"), 1.0f);
		ITimePeriod tp2 = new Para_6_Beamterdienstzeit(DateFactory.getDate("01.09.1983"), DateFactory.getDate("30.04.2020"), 1.0f);
		pension.addTimePeriod(tp1);
		pension.addTimePeriod(tp2);
	}
	

	

	
	
	private void saveToFile(String jsonString) {
		try (FileWriter file = new FileWriter(FILE_NAME)) {
			 
	        file.write(jsonString);
	        file.flush();
	
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	private String readFromFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			String ls = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			// delete the last new line separator
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			reader.close();
			return stringBuilder.toString();
			
		} catch (IOException e) {
	        e.printStackTrace();
	    }
		
		return null;
	}
	
	
	
	

}

