package de.protin.support.pr.gui;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import de.protin.support.pr.domain.IBesoldungstabelle;
import de.protin.support.pr.domain.besoldung.Amtszulage;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.tabelle.BesoldungstabelleFactory;
import de.protin.support.pr.domain.utils.SelectionConstants;

public class BesoldungstabellePart {

	
	private Composite parent;
	private Composite part;
	private Table tableBesOrdnungA;
	private Table tableBesOrdnungB;
	private Table tableAmtszulagen;
	private Table tableFamZuschlag;
	private Table tableSonstige;
	
	private IBesoldungstabelle besoldungstabelle;
	
	public BesoldungstabellePart (Composite parent) {
		this.parent = parent;
		this.besoldungstabelle = 
				BesoldungstabelleFactory.getInstance().getBesoldungstabelle(SelectionConstants.ANZUWENDENDES_RECHT_BUND, new Date());
	}
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void initPart(Composite parent) {
		part = new Composite(parent, SWT.BORDER);
		GridLayout layout = new GridLayout(1, true);
		part.setLayout (layout);
		GridData gridData = new GridData (GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH);
		part.setLayoutData (gridData);
		
		Label lblHeader = new Label(part, SWT.BORDER);
		lblHeader.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblHeader.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		lblHeader.setText(this.besoldungstabelle.getTitel());
		
		Label lblNewLabel = new Label(part, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText(this.besoldungstabelle.getSubTitel());
		

		
		ScrolledComposite scroll = new ScrolledComposite(part, SWT.BORDER | SWT.V_SCROLL);
	    scroll.setLayout(new GridLayout(1, true));
	    scroll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    scroll.setExpandVertical(true);
	    scroll.setExpandHorizontal(true);
	    scroll.setAlwaysShowScrollBars(true);
	   
	    Composite scrollContent = createControls(scroll);
	    scroll.setContent(scrollContent);
	    scroll.setMinSize(scrollContent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	    
	    
	}


	protected Composite createControls(Composite parent ) {
		
		Composite scrollComp = new Composite(parent, SWT.BORDER);
		scrollComp.setLayout(new GridLayout(1, false));
		
		Composite compBesOrdungA = new Composite(scrollComp, SWT.NONE);
		compBesOrdungA.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		compBesOrdungA.setLayout(new GridLayout(1, false));
		
		Label lblHeaderBesOrdnungA = new Label(compBesOrdungA, SWT.NONE);
		lblHeaderBesOrdnungA.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblHeaderBesOrdnungA.setText("Besoldungsordnung A");
		
		tableBesOrdnungA = new Table(compBesOrdungA, SWT.BORDER | SWT.FULL_SELECTION);
		tableBesOrdnungA.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableBesOrdnungA.setHeaderVisible(true);
		tableBesOrdnungA.setLinesVisible(true);
		
		Composite compBesOrdnungB = new Composite(scrollComp, SWT.NONE);
		compBesOrdnungB.setLayout(new GridLayout(1, false));
		
		Label lblHeaderBesOrdnungB = new Label(compBesOrdnungB, SWT.NONE);
		lblHeaderBesOrdnungB.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblHeaderBesOrdnungB.setText("Besoldungsordnung B");
		
		tableBesOrdnungB = new Table(compBesOrdnungB, SWT.BORDER | SWT.FULL_SELECTION);
		tableBesOrdnungB.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableBesOrdnungB.setHeaderVisible(true);
		tableBesOrdnungB.setLinesVisible(true);
		
		Composite compAmtszulagen = new Composite(scrollComp, SWT.NONE);
		compAmtszulagen.setLayout(new GridLayout(1, false));
		GridData gd_compAmtszulagen = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		//gd_compAmtszulagen.widthHint = 442;
		compAmtszulagen.setLayoutData(gd_compAmtszulagen);
		
		Label lblHeaderAmtszulagen = new Label(compAmtszulagen, SWT.NONE);
		lblHeaderAmtszulagen.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblHeaderAmtszulagen.setText("Amtszulagen");
		
		tableAmtszulagen = new Table(compAmtszulagen, SWT.BORDER | SWT.FULL_SELECTION);
		tableAmtszulagen.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableAmtszulagen.setHeaderVisible(true);
		tableAmtszulagen.setLinesVisible(true);
		
		Composite compFamZuschlag = new Composite(scrollComp, SWT.NONE);
		compFamZuschlag.setLayout(new GridLayout(1, false));
		GridData gd_compFamZuschlag = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		//gd_compFamZuschlag.widthHint = 440;
		compFamZuschlag.setLayoutData(gd_compFamZuschlag);
		
		Label lblHeaderFamZuschlag = new Label(compFamZuschlag, SWT.NONE);
		lblHeaderFamZuschlag.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblHeaderFamZuschlag.setText("Familienzuschlag");
		
		tableFamZuschlag = new Table(compFamZuschlag, SWT.BORDER | SWT.FULL_SELECTION);
		tableFamZuschlag.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableFamZuschlag.setHeaderVisible(true);
		tableFamZuschlag.setLinesVisible(true);
		
		
		
		Composite compSonstige = new Composite(scrollComp, SWT.NONE);
		compSonstige.setLayout(new GridLayout(1, false));
		GridData gd_compSonstige = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		//gd_compSonstige.widthHint = 440;
		compSonstige.setLayoutData(gd_compSonstige);
		
		
		
		Label lblHeaderSonstige = new Label(compSonstige, SWT.NONE);
		lblHeaderSonstige.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblHeaderSonstige.setText("Ruhegaltsrelevante Werte für die Berechnung des Kindererziehungszuschlag");
		
		tableSonstige = new Table(compSonstige, SWT.BORDER | SWT.FULL_SELECTION);
		tableSonstige.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableSonstige.setHeaderVisible(true);
		tableSonstige.setLinesVisible(true);
		
		
		initSections();
		
		return scrollComp;
	}
	
	
	private void initSections() {
		initBesOrdnungA(tableBesOrdnungA);
		initBesOrdnungB(tableBesOrdnungB);
		initAmtszulagen(tableAmtszulagen);
		initFamZuschlag(tableFamZuschlag);
		initSonstige(tableSonstige);
	}


	private void initSonstige(Table table) {
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 3;
		table.setLayoutData(gridData);
		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		TableColumn column = new TableColumn(table, SWT.LEFT, 0);
		column.setText("Parameter");
		column.setWidth(160);
	
		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText("Betrag in Euro");
		column.setWidth(90);
	
		TableItem item = new TableItem(table, SWT.NULL);
		item.setText(0, "Aktueller Rentenwert West");
		item.setText(1, String.format("%,.2f", this.besoldungstabelle.getAktuellenRentenwertWestForKindererziehungszuschlag()));
		
		item = new TableItem(table, SWT.NULL);
		item.setText(0, "Aktueller Rentenwert Ost");
		item.setText(1, String.format("%,.2f", this.besoldungstabelle.getAktuellenRentenwertOstForKindererziehungszuschlag()));
	}


	private void initFamZuschlag(Table table) {
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 3;
		table.setLayoutData(gridData);
		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		TableColumn column = new TableColumn(table, SWT.LEFT, 0);
		column.setText("Art des Familienzuschlag");
		column.setWidth(160);
		
		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText("Stufe");
		column.setWidth(160);
	
		column = new TableColumn(table, SWT.LEFT, 2);
		column.setText("Betrag in Euro");
		column.setWidth(90);
		
		TableItem item = new TableItem(table, SWT.NULL);
		item.setText(0, "Voller Familienzuschlag");
		item.setText(1, "Stufe 1 (BBesG § 40  Abs. 1)");  
		item.setText(2, String.format("%,.2f", this.besoldungstabelle.getSalaryForFamiliezuschlag(new Familienzuschlag(1))));
		
		item = new TableItem(table, SWT.NULL);
		item.setText(0, "Halber Familienzuschlag");
		item.setText(1, "Stufe 1/2 (BBesG § 40  Abs. 4)");  
		item.setText(2, String.format("%,.2f", this.besoldungstabelle.getSalaryForFamiliezuschlag(new Familienzuschlag(1))/2));
	}


	private void initAmtszulagen(Table table) {
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 3;
		table.setLayoutData(gridData);		
					
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		TableColumn column = new TableColumn(table, SWT.LEFT, 0);
		column.setText("Besoldungsgruppe");
		column.setWidth(120);
		
		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText("Betrag in Euro");
		column.setWidth(90);
	
		column = new TableColumn(table, SWT.LEFT, 2);
		column.setText("Fussnote");
		column.setWidth(70);
		
		Map<String, Amtszulage[]> amtszulagen = this.besoldungstabelle.getAmtszulagen();
		
		for (Iterator<String> iterator = amtszulagen.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Amtszulage[] az = amtszulagen.get(key);
			for (int j = 0; j < az.length; j++) {
				//String s = j == 0 ? az[j].getBesGroup() : new String("");
				TableItem item = new TableItem(table, SWT.NULL);
				item.setText(0, j == 0 ? az[j].getBesGroup() : new String(""));
				item.setText(1, String.format("%,.2f", az[j].getZulage()));
				item.setText(2,  az[j].getFussnote());
			}
			
		}
	}
	


	private void initBesOrdnungB(Table table) {
					
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 12;
		table.setLayoutData(gridData);		


		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		TableColumn column_1 = new TableColumn(table, SWT.LEFT, 0);
		column_1.setText("BesGr.");
		column_1.setWidth(50);
		
		for (int i = 1; i < 12 ; i++) {
			column_1 = new TableColumn(table, SWT.LEFT, i);
			column_1.setText("B " + i);
			column_1.setWidth(65);
		}		
		
		TableItem item = new TableItem(table, SWT.NULL);
		item.setText(0, "");
		
		Map<Integer, Float> besoldungsOrdnungB = this.besoldungstabelle.getBesoldungsOrdnungB();
		for (int i = 0; i < besoldungsOrdnungB.size(); i++) {
			item.setText(i+1, getGrundgehalt("B", i+1, 0));
		}

	}


	private void initBesOrdnungA(Table table) {
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 9;
		table.setLayoutData(gridData);		
		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		TableColumn column = new TableColumn(table, SWT.LEFT, 0);
		column.setText("BesGr.");
		column.setWidth(60);
		
		for (int i = 1; i < 9 ; i++) {
			column = new TableColumn(table, SWT.LEFT, i);
			column.setText("Stufe " + i);
			column.setWidth(60);
		}
		
		
		for (int i = 3; i < 17; i++) {
	        TableItem item = new TableItem(table, SWT.NULL);
	        item.setText(0, "A " + i);
	        item.setText(1, getGrundgehalt("A", i, 1));
	        item.setText(2, getGrundgehalt("A", i, 2));
	        item.setText(3, getGrundgehalt("A", i, 3));
	        item.setText(4, getGrundgehalt("A", i, 4));
	        item.setText(5, getGrundgehalt("A", i, 5));
	        item.setText(6, getGrundgehalt("A", i, 6));
	        item.setText(7, getGrundgehalt("A", i, 7));
	        item.setText(8, getGrundgehalt("A", i, 8));
	    }

	}

	
	

	public Composite getPart() {
		return this.part;
	}
	
	public Composite getParent() {
		return this.parent;
	}
	
	
	private String getGrundgehalt(String besOrdnung, int besGr, int besStufe) {
		Grundgehalt grundgehalt = new Grundgehalt(besOrdnung, besGr, besStufe);
		float gehalt = this.besoldungstabelle.getSalaryForGrundgehalt(grundgehalt);
		return String.format("%,.2f", gehalt);
				//Float.toString(gehalt);
	}

	

	public IBesoldungstabelle getBesoldungstabelle() {
		return besoldungstabelle;
	}


	public void setBesoldungstabelle(IBesoldungstabelle besoldungstabelle) {
		this.besoldungstabelle = besoldungstabelle;
		part.dispose();
		initPart(this.parent);
	}
	
	
	
}
