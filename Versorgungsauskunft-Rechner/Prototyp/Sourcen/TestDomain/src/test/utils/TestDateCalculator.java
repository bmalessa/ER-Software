package test.utils;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.tabelle.BesoldungstabelleFactory;
import de.protin.support.pr.domain.pension.EngagierterRuhestand;
import de.protin.support.pr.domain.timeperiod.Para_6_AusbildungBeamterAufWiderruf;
import de.protin.support.pr.domain.timeperiod.Para_6_Beamterdienstzeit;
import de.protin.support.pr.domain.utils.DateUtil;
import de.protin.support.pr.domain.utils.SelectionConstants;
import de.protin.support.pr.domain.utils.TimePeriodCalculator;
import de.protin.support.pr.domain.utils.TimePeriodDetails;
import test.domain.factories.DateFactory;
import test.domain.factories.PersonFactory;

public class TestDateCalculator {

	IPension pension;
	
	Date date19920101;
	Date date19911231;
	Date date19810901;
	Date date19830831;
	
	Date date19830901;
	Date date20180731;
	
	
	@Before
	public void setUp() throws Exception {
		date19920101 = DateUtil.getDate("01.01.1992");
		date19911231 = DateUtil.getDate("31.12.1991");
		
		date19810901 = DateUtil.getDate("01.09.1981");
		date19830831 = DateUtil.getDate("31.08.1983");
		
		date19830901 = DateUtil.getDate("01.09.1983");
		date20180731 = DateUtil.getDate("31.07.2018");
		
		PersonFactory pf = PersonFactory.getInstance();
		Person person = pf.setupPersonForEngagierterRuhestand();
		person.setDateOfBirthString("12.07.1963");
		person.setDateOfRetirementString("30.04.2020");
		person.setDateOfLegalRetirementString("31.07.2018");  //frühestens mit 55 Jahren
		
		person.setBesoldung(new Grundgehalt("A", 12, 8));
		person.setFamilienzuschlag(new Familienzuschlag(1));
		
		pension = new EngagierterRuhestand(person, SelectionConstants.ANZUWENDENDES_RECHT_BUND);
		pension.setBesoldungstabelle(BesoldungstabelleFactory.getInstance().
				getBesoldungstabelle(pension.getAnzwRecht(), person.getDateOfRetirement()));
	
		addTimePeriods(pension);
	}
	
	

	@After
	public void tearDown() throws Exception {
	}
	
	
	
	@Test
	public void testCalculateYearsAndDaysForVergleichsberechnung() {
		
		TimePeriodDetails timePeriod = TimePeriodCalculator.getTimePeriodBeforeDate19920101(pension);
		assertEquals(10, timePeriod.getYears());
		assertEquals(122, timePeriod.getDays());
		
		timePeriod = TimePeriodCalculator.getTimePeriodAfterDate199112231(pension);
		assertEquals(26, timePeriod.getYears());
		assertEquals(212, timePeriod.getDays());
		
	}
	
	
	@Test
	public void testCalculateYearsAndDaysForTimePeriod() {
		
		TimePeriodDetails timePeriod = TimePeriodCalculator.calculateYearsAndDaysForTimePeriod(date19810901, date19830831);
		assertEquals(2, timePeriod.getYears());
		assertEquals(0, timePeriod.getDays());
		
		timePeriod = TimePeriodCalculator.calculateYearsAndDaysForTimePeriod(date19830901, date20180731);
		assertEquals(34, timePeriod.getYears());
		assertEquals(334, timePeriod.getDays());
		
	}
	
	
	
	/**
	 * 
	 * @param pension
	 */
	private void addTimePeriods(IPension pension) {
		ITimePeriod tp1 = new Para_6_AusbildungBeamterAufWiderruf(DateFactory.getDate("01.09.1981"), DateFactory.getDate("31.08.1983"), 1.0f);
		ITimePeriod tp2 = new Para_6_Beamterdienstzeit(DateFactory.getDate("01.09.1983"), DateFactory.getDate("31.07.2018"), 1.0f);
		pension.addTimePeriod(tp1);
		pension.addTimePeriod(tp2);
	}
}
