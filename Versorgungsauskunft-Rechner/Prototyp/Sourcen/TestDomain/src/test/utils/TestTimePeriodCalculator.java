package test.utils;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
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
import de.protin.support.pr.domain.utils.TimePeriodDetails;
import test.domain.factories.DateFactory;
import test.domain.factories.PersonFactory;

public class TestTimePeriodCalculator {

	IPension pension;
	
	Date date19920101;
	Date date19911231;
	Date date19810901;
	Date date19830831;
	
	Date date19830901;
	Date date20200430;
	
	
	@Before
	public void setUp() throws Exception {
		date19920101 = DateUtil.getDate("01.01.1992");
		date19911231 = DateUtil.getDate("31.12.1991");
		
		date19810901 = DateUtil.getDate("01.09.1981");
		date19830831 = DateUtil.getDate("31.08.1983");
		
		date19830901 = DateUtil.getDate("01.09.1983");
		date20200430 = DateUtil.getDate("30.04.2020");
		
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
	public void testCalculateYearsAndDaysForTimePeriod() {
		
		LocalDate ld1 = convertToLocalDate(date19810901);
		LocalDate ld2 = convertToLocalDate(DateUtils.addDays(date19830831,1));
		LocalDate ldEnde1991 = convertToLocalDate(date19911231);
		LocalDate ldStart1992 = convertToLocalDate(date19920101);
		LocalDate ldOfretirement = convertToLocalDate(DateUtils.addDays(date20200430,1));
		
		long completePeriod = ChronoUnit.DAYS.between(ld1,convertToLocalDate(DateUtils.addDays(date20200430,1)));
		long before1992 = ChronoUnit.DAYS.between(ld1,ldStart1992);
		long after1991 = ChronoUnit.DAYS.between(ldStart1992,convertToLocalDate(DateUtils.addDays(date20200430,1)));
		
		TimePeriodDetails tpd = new TimePeriodDetails(completePeriod);
		assertEquals(38, tpd.getYears());
		assertEquals(252, tpd.getDays());
		
		tpd = new TimePeriodDetails(before1992);
		assertEquals(10, tpd.getYears());
		assertEquals(124, tpd.getDays());
		
		tpd = new TimePeriodDetails(after1991);
		assertEquals(28, tpd.getYears());
		assertEquals(128, tpd.getDays());
			
		
		long period1 = ChronoUnit.DAYS.between(ld1,ld2);
		long period2 = ChronoUnit.DAYS.between(ld2,ldOfretirement);
		
		TimePeriodDetails tpd1 = new TimePeriodDetails(period1);
		assertEquals(2, tpd1.getYears());
		assertEquals(0, tpd1.getDays());
		
		TimePeriodDetails tpd2 = new TimePeriodDetails(period2);
		assertEquals(36, tpd2.getYears());
		assertEquals(252, tpd2.getDays());
		
		
		tpd1.add(tpd2);
		assertEquals(38, tpd1.getYears());
		assertEquals(252, tpd1.getDays());
		
		
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
	
	
	
	public LocalDate convertToLocalDate(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
}
