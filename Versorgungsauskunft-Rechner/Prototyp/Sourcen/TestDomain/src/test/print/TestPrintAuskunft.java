package test.print;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.tabelle.BesoldungstabelleFactory;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.pension.EngagierterRuhestand;
import de.protin.support.pr.domain.timeperiod.Para_6_AusbildungBeamterAufWiderruf;
import de.protin.support.pr.domain.timeperiod.Para_6_Beamterdienstzeit;
import de.protin.support.pr.domain.utils.SelectionConstants;
import de.protin.support.pr.gui.RuhegehaltBerechnungPart;
import test.domain.factories.DateFactory;
import test.domain.factories.PersonFactory;

public class TestPrintAuskunft {
	
	
private IPension pension;
private Image image;
private Display display;
private Shell shell;
private Button btnPrint;

	@Before
	public void setUp() throws Exception {
		
		PersonFactory pf = PersonFactory.getInstance();
		Person person = pf.setupPersonForDienstunfaehigkeit();
		person.setDateOfSubstantiationString("01.09.1981");
		person.setDateOfBirthString("12.07.1963");
		person.setDateOfRetirementString("30.04.2020");
		person.setDateOfLegalRetirementString("31.05.2030");
		
		
		
		person.setBesoldung(new Grundgehalt("A", 12, 8));
		person.setFamilienzuschlag(new Familienzuschlag(2));
		
		person.setSchwerbehindert(true);
		//person.setBesondereAltersgrenze(true);
		//person.setBesondereAltersgrenzeJahre(65);
		
		//pension = new EngagierterRuhestand(person, SelectionConstants.ANZUWENDENDES_RECHT_BUND);
		pension = new Dienstunfaehigkeit(person, SelectionConstants.ANZUWENDENDES_RECHT_BUND);
		pension.setBesoldungstabelle(BesoldungstabelleFactory.getInstance().
				getBesoldungstabelle(pension.getAnzwRecht(), person.getDateOfRetirement()));
	
		addTimePeriods(pension);
		
		
		this.display = new Display();
	    this.shell = new Shell(display);
	    
	    FillLayout shellLayout = new FillLayout();
	    shellLayout.type = SWT.VERTICAL;

	    shell.setLayout(shellLayout);
	      
	}



	@After
	public void tearDown() throws Exception {
		
	}
	
	
	@Test
	public void testPart() {
		 RuhegehaltBerechnungPart part = new RuhegehaltBerechnungPart(shell, true);
		 part.setPension(pension);
		 part.initPart();
		 shell.setSize(900, 700);
		 shell.open();
		 while (!shell.isDisposed()) {
		      if (!display.readAndDispatch())
		        display.sleep();
		    }
		    
		    if (image != null && !image.isDisposed()) {
			   	image.dispose();
			}
		    
		display.dispose();
		
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
	



}

