package de.protin.support.pr.gui;

import java.util.Locale;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.wb.swt.SWTResourceManager;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.besoldung.zuschlag.KindererziehungszeitenZuschlag;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.gui.berechnung.IVersorgungsauskunftBerechnung;
import de.protin.support.pr.gui.berechnung.PdfBerechnung;
import de.protin.support.pr.gui.berechnung.SwtBerechnung;
import de.protin.support.pr.gui.util.ControlConstants;
import de.protin.support.swt.custom.ScrolledComposite;
import de.protin.support.swt.custom.StyledText;

public class RuhegehaltBerechnungPart {
	
	private IPension pension;
	private Composite textPart;
	private Composite part;
	private StyledText styledText;
	private Button btnPrint;
	boolean editable;  
	private ResourceBundle textResources;
	private Printer printer;
	
	private IVersorgungsauskunftBerechnung berechnungHelper;
	
	
	public RuhegehaltBerechnungPart(Composite parent, boolean editable) {
		loadTextResources();
		
		part = new Composite(parent, SWT.NONE);
		part.setLayout(new GridLayout(1, false));
		
		textPart = new Composite(part, SWT.NONE);
		textPart.setLayout(new FillLayout());
		textPart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(textPart, SWT.BORDER | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		styledText = new StyledText (scrolledComposite, SWT.V_SCROLL |SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
		scrolledComposite.setContent(styledText);
		scrolledComposite.setMinSize(styledText.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		addPrintButton(part);
		
		//Default Formatter is SWT-GUI
		berechnungHelper = new SwtBerechnung(this);
	}


	
	
	public Control getPart() {
		return this.part;
	}
	


	public void initPart() {
		berechnungHelper.setPension(this.pension);
		styledText.setText(" ");
		
		addHeaderImage();
		appendPensionData();
		pageBreak();
		appendBerechnungVersorgungsbezug();
		pageBreak();
		appendZusammenstellungZeiten();
		if(this.pension instanceof Dienstunfaehigkeit) {
			appendZusammenstellungZurechnung();
		}	
		pageBreak();
		if(this.pension.calculateAbschlag_Para_14_BeamtVG() > 0.1f) {
			appendZusammenstellungVersorgungsabschlag();
		}
		appendBerechnungRuhegehaltssatz();
		if(this.pension.isVergleichsberechnungBeamtVG_PARA_85()) {
			appendVergleichsberechnungPara85();
		}
		
		
		
		if(this.pension.getKindererziehungsZuschlag() != null) {
			KindererziehungszeitenZuschlag kindererziehungsZuschlag = this.pension.getKindererziehungsZuschlag();
			if (kindererziehungsZuschlag.calculateKindererziehungszuschlag(this.pension) > 0) {
				pageBreak();
				appendKindererziehungszuschlag();
			}
		}
		
		styledText.setEditable(this.editable);
	}
	






	/**
	 * 
	 */
	public void loadTextResources() {
		this.textResources = ResourceBundle.getBundle("de.protin.support.pr.gui.text.berechnung", Locale.getDefault());
	}









	public IPension getPension() {
		return pension;
	}
	
	public void setPension(IPension pension) {
		this.pension = pension;
	}


	private void addHeaderImage() {
		this.berechnungHelper.buildHeader();
	}
	
	  
	private void appendPensionData() {
		this.berechnungHelper.buildPensionSection();
	}

	
	private void appendBerechnungVersorgungsbezug() {
		this.berechnungHelper.buildBerechnungSection();
	}
	
	
	
	private void appendZusammenstellungZeiten() {
		this.berechnungHelper.buildRuhegehaltsfaehigeZeitenSection();
	}

	
	private void appendBerechnungRuhegehaltssatz() {
		this.berechnungHelper.buildBerechnungRuhegehaltssatzSection();
	}
	
	private void appendVergleichsberechnungPara85() {
		if(this.berechnungHelper.buildVergleichsberechnungPara85Section()) {
			this.berechnungHelper.buildVergleichAbschlussSection();
		}
	}
	

	private void appendZusammenstellungVersorgungsabschlag() {
		this.berechnungHelper.buildVersorgungsabschlagSection();
	}
	
	

	private void appendZusammenstellungZurechnung() {
		this.berechnungHelper.buildZurechnungDDUSection();
	}
	
	

	private void appendKindererziehungszuschlag() {
		this.berechnungHelper.buildKinderziehungszuschlagSection();
	}


	private void addPrintButton(Composite parent) {
		Composite btnComposite = new Composite(parent, SWT.NONE);
		btnComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		btnComposite.setLayout(new GridLayout(1, false));
		
		Label lblSeparator = new Label(btnComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparator.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 10, SWT.BOLD));
		lblSeparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		this.btnPrint = new Button(btnComposite, SWT.NONE);
		btnPrint.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnPrint.setText("Drucken");
		btnPrint.addListener(SWT.Selection, new Listener() {
		    public void handleEvent(Event e) {
		    	switch (e.type) {
			         case SWT.Selection:
			           
					       
					       if(printer == null || printer.isDisposed()) {
					       	   PrintDialog printDialog = new PrintDialog(styledText.getShell(), SWT.NONE);
					       	   printDialog.setText("Print");
					       	   PrinterData printerData = printDialog.open();
					       	   if (!(printerData == null)) {
					       		   String printerName = printerData.name;
					       		   if (!printerName.toLowerCase().contains("pdf")) {
					       			   // show warning , expect PDF-Printer
					       			   MessageBox messageBox = new MessageBox(styledText.getShell(), SWT.ICON_QUESTION
					       		            | SWT.OK);
					       	        
					       			   messageBox.setText("Warning");
					       			   messageBox.setMessage("Es wurde kein \"Print to PDF\" -Drucker ausgewählt. Andere Druckertreiber werden nicht unterstützt.");
					       			   int buttonID = messageBox.open();
						       	        switch(buttonID) {
						       	          case SWT.OK:
						       	        	  return;
						       	        }
					       		   }

					       		   printer = new Printer(printerData);
					       	   }
					       	   else {
					       		   //Standarddrucker wird genommen 
					       		   printer = new Printer();
					       	   }
					       }
					  				       
				           //switch to PDF Formatter
					       
					       setBerechnungHelper(new PdfBerechnung(RuhegehaltBerechnungPart.this)); 
				           styledText.setVisible(false);
				           initPart();
				                
				           //das Prinint mit den kopierten Klassen funktioniert noch nicht, weil die Formatierung verloren geht
				           //Printing printJob = new Printing(styledText, printer);
				           Runnable printJob = styledText.print(printer);  
				           printJob.run();
				           
				           //switch back to SWT GUI formatter
				           styledText.setVisible(true);
				           setBerechnungHelper(new SwtBerechnung(RuhegehaltBerechnungPart.this)); 
				           initPart();
				           printer.dispose();
				           printer = null;
				           
		    		}
		    }
			
		});
	}

	


	public IVersorgungsauskunftBerechnung getBerechnungHelper() {
		return berechnungHelper;
	}
	

	public void setBerechnungHelper(IVersorgungsauskunftBerechnung berechnungHelper) {
		this.berechnungHelper = berechnungHelper;
	}


	public void pageBreak() {
		this.berechnungHelper.pageBreak();
	}

	
	public void clear() {
		styledText.setText("");
	}


	public StyledText getStyledText() {
		return styledText;
	}


	public ResourceBundle getTextResources() {
		return textResources;
	}


	public Printer getPrinter() {
		return printer;
	}
	
	
	
}
