package de.protin.support.pr.gui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import de.protin.support.pr.domain.IBesoldungstabelle;
import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.besoldung.tabelle.BesoldungstabelleFactory;
import de.protin.support.pr.domain.json.JsonDeserializer;
import de.protin.support.pr.domain.json.JsonSerializer;
import de.protin.support.pr.domain.pension.AntragsRuhestand;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.pension.EngagierterRuhestand;
import de.protin.support.pr.domain.pension.RegelaltersgrenzeRuhestand;
import de.protin.support.pr.domain.pension.Vorruhestand;
import de.protin.support.pr.domain.utils.DateUtil;
import de.protin.support.pr.domain.utils.SelectionConstants;
import de.protin.support.pr.domain.validation.ValidationItem;
import de.protin.support.pr.domain.validation.ValidationResult;
import de.protin.support.pr.gui.listener.GrunddatenPartListener;
import de.protin.support.pr.gui.listener.KindererziehungszeitenPartListener;
import de.protin.support.pr.gui.listener.ZeitenPartListener;
import de.protin.support.pr.gui.util.FileExportHandler;
import de.protin.support.pr.gui.util.FileImportHandler;

public class PensionGUI {

	private TabFolder tabFolder;
	
	private GrunddatenPart grunddaten;
	private ZeitenPart zeiten;
	private KindererziehungszeitenPart kez;
	private RuhegehaltBerechnungPart rgBerechnung;
	
	private static PensionGUI instance;
	
	public static void main(String[] args) {
		
		PensionGUI gui = PensionGUI.getInstance();
		
		Display display = new Display();
		Shell shell = new Shell(display);
		
		shell.setLayout(new FillLayout());
					
		gui.createContents(shell);
		gui.createMenu(shell);
		
		
		shell.setText("Prot-in Support - Berechnung einer Versorgungsauskunft nach BeamtVG");
		shell.pack();
		shell.setSize(900, 750);
        shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		//Image muss freigegeben werden
		if(gui.rgBerechnung.getBerechnungHelper() != null) {
			Image image = gui.rgBerechnung.getBerechnungHelper().getImage();
			if (image != null && !image.isDisposed()) {
				image.dispose();
			}
		}

		display.dispose();
	}


	public static PensionGUI getInstance() {
	    // Double lock for thread safety.
	    if (instance == null) {
	        synchronized(PensionGUI.class){
	            if (instance == null) {
	                instance = new PensionGUI();
	            }
	        }
	    }
	    return instance;
	}
	
	
	
	
	private void  createMenu(Shell shell) {
		
		Menu menuBar = new Menu(shell, SWT.BAR);
		MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE | SWT.BORDER);
	    fileMenuHeader.setText("&Datei");

	    Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
	    fileMenuHeader.setMenu(fileMenu);

	    MenuItem fileNew = new MenuItem(fileMenu, SWT.PUSH);
	    fileNew.setText("Neue Versorgungsauskunft");
	    fileNew.addSelectionListener(new SelectionAdapter () {
	    	public void widgetSelected(SelectionEvent event) {
	    		clear();
	    		tabFolder.setSelection(0);
	    	}
	    });
	    
	    
	    MenuItem fileJsonExport = new MenuItem(fileMenu, SWT.PUSH);
	    fileJsonExport.setText("Versorgungsauskunft speichern");
	    fileJsonExport.addSelectionListener(new SelectionAdapter () {
	    	public void widgetSelected(SelectionEvent event) {
	    		boolean valid = validateInput(false);
	   		  	if(!valid) {
	   		  		 return;
	   		  	}
	    		IPension pension = buildPension();
	    		String jsonString = JsonSerializer.serializeToJson(pension);
	    		new FileExportHandler().exportToJsonFile(shell, jsonString);
	    	}
	    });
	    
	    MenuItem fileJsonImport = new MenuItem(fileMenu, SWT.PUSH);
	    fileJsonImport.setText("Versorgungsauskunft öffnen");
	    fileJsonImport.addSelectionListener(new SelectionAdapter () {
	    	public void widgetSelected(SelectionEvent event) {
	    		try {
	    		clear();
	    		String jsonString = new FileImportHandler().importFromJsonFile(shell);
	    		IPension pension = JsonDeserializer.deserializeFromJson(jsonString);
	    		setData(pension);
	    		}
	    		catch(Exception ex) {
	    			
	    			ex.printStackTrace();
	    			StringWriter sw = new StringWriter();
	                ex.printStackTrace(new PrintWriter(sw));
	    			Status status = new Status(IStatus.ERROR, "JsonDeserializer",  ex.getLocalizedMessage(), new Exception(sw.toString()));
	    			ErrorDialog.openError(Display.getCurrent().getActiveShell(),
	    		            "Exception occured when reading json file!", ex.getMessage(), status);
	    		    
	    			
	    			/*
	    			StringWriter sw = new StringWriter();
	                ex.printStackTrace(new PrintWriter(sw));
	    			Status status = new Status(IStatus.ERROR, "JsonDeserializer",  ex.getLocalizedMessage(), new Exception(sw.toString()));
	  
	    			ErrorDialog errorDialog = new ErrorDialog(Display.getCurrent().getActiveShell(), "Json Deserializer.", "Fehler beim Einlesen des Json-Files. Weitere Informationen unter \"Details\"", status,  IStatus.ERROR);
	    			errorDialog.open();
	    			*/
	    		}
	    	}
	    });
	    
	    
	    shell.setMenuBar(menuBar);
	    
	}

	
	
	private void createContents(Shell shell) {
		// Create the containing tab folder
	    this.tabFolder = new TabFolder(shell, SWT.NONE);
	    
	    TabItem grunddaten = new TabItem(tabFolder, SWT.NONE);
	    grunddaten.setText("Grunddaten");
	    grunddaten.setToolTipText("Grunddaten");
	    grunddaten.setControl(getTabGrunddaten(tabFolder));
	    
	    TabItem zeiten = new TabItem(tabFolder, SWT.NONE);
	    zeiten.setText("Ruhegehaltfähige Zeiten");
	    zeiten.setToolTipText("Ruhegehaltfähige Zeiten");
	    zeiten.setControl(getTabZeiten(tabFolder));
	    
	    TabItem kindererziehung = new TabItem(tabFolder, SWT.NONE);
	    kindererziehung.setText("Kindererziehungszeiten");
	    kindererziehung.setToolTipText("Kindererziehungszeiten");
	    kindererziehung.setControl(getTabKindererziehung(tabFolder));
	    
	    TabItem besoldungstabelle = new TabItem(tabFolder, SWT.NONE);
	    besoldungstabelle.setText("Besoldungstabelle");
	    besoldungstabelle.setToolTipText("Besoldungstabelle");
	    besoldungstabelle.setControl(getBesoldungstabelle(tabFolder));
	    
	    TabItem berechnungTabItem = new TabItem(tabFolder, SWT.NONE);
	    berechnungTabItem.setText("Versorgungsauskunft");
	    berechnungTabItem.setToolTipText("Versorgungsauskunft");
	    berechnungTabItem.setControl(getRgBerechnung(tabFolder));
	}


	public GrunddatenPart getGrunddaten() {
		return grunddaten;
	}


	public ZeitenPart getZeiten() {
		return zeiten;
	}


	public KindererziehungszeitenPart getKez() {
		return kez;
	}


	/**
	   * Gets the control for tab one
	   * 
	   * @param tabFolder the parent tab folder
	   * @return Control
	   */
	  private Control getTabGrunddaten(TabFolder tabFolder) {
		  
		  this.grunddaten = new GrunddatenPart(tabFolder);
		  this.grunddaten.setPartListener(new GrunddatenPartListener(this, this.grunddaten));
		  return grunddaten.getSashForm();
		  
	  }
	    
		
	  private Control getTabZeiten(TabFolder tabFolder) {
		  this.zeiten = new ZeitenPart(tabFolder);
		  this.zeiten.setPartListener(new ZeitenPartListener(this, this.zeiten));
		  return zeiten.getSashForm();
      }
	  
	  

	  private Control getTabKindererziehung(TabFolder tabFolder) {
		  this.kez = new KindererziehungszeitenPart(tabFolder);
		  this.kez.setPartListener(new KindererziehungszeitenPartListener(this, this.kez));;
		  return kez.getSashForm();
	  }
	
	
	  private Control getBesoldungstabelle(TabFolder tabFolder) {
			BesoldungstabellePart besTab = new BesoldungstabellePart(tabFolder);
			besTab.initPart(tabFolder);
			return besTab.getPart();
	  }
	
	
	  private Control getRgBerechnung(TabFolder tabFolder) {
		    this.rgBerechnung = new RuhegehaltBerechnungPart(tabFolder, true);
		    return rgBerechnung.getPart();
	  }
	
	  
	  
	  
	  
	  public boolean validateInput(boolean showSuccess) {
		  
		  Shell[] shells = grunddaten.getSashForm().getDisplay().getShells();
		  boolean validInput = true;
		 
		  List<Status> validationStatuses = grunddaten.validateInput();
		  if(!validationStatuses.isEmpty()) {
			  validInput = false;
		  }
		  
		  if(validInput) {
			  //wenn alle Eingaben im richtigen Format (Datum, Nummerische Werte etc.)  eingeben wurden kann die inhaltliche Prüfung vorgenommen werden
			  IPension pension = buildPension();
			  ValidationResult validationResult = pension.validate();
			  
			 if(!validationResult.isSuccess()) {
				 validInput = false;
				 for (Iterator<ValidationItem> iterator = validationResult.getItems().iterator(); iterator.hasNext();) {
					 ValidationItem validationItem = iterator.next();
					 Status status = new Status(IStatus.ERROR, pension.getPensionTyp(), validationItem.getMessage());
					 validationStatuses.add(status);
				}
			 }
			  
			  if(validInput && showSuccess) {
				  MessageDialog.openInformation(shells[0], "Validierung erfolgreich", "Die Eingabedaten wurden erfolgreich validiert.");
			  }
		  }
		  
		  
		  if(!validInput) {
			  MultiStatus ms = new MultiStatus(this.getClass().getName(),
					                IStatus.ERROR, validationStatuses.toArray(new Status[] {}), 
					                "Validierung fehlgeschlagen", 
					                null);
			  
			  ErrorDialog validationErrorDialog = new ErrorDialog(shells[0], "Validierung der Eingabedaten.", "Validierungsfehler vorhanden. Weitere Informationen unter \"Details\"", ms,  IStatus.ERROR);
			  validationErrorDialog.open();
			  return false;
		  }
		  
		  return true;
	  }
	  
	  
	  public void calculateRuhegehalt() {
		  //immer sicherstellen, dass eine valide Basis für die Berechnung vorhanden ist.
		  //kein Dialog anzeigen, wenn alles ok ist.
		  boolean valid = validateInput(false);
		  if(!valid) {
			  return;
		  }
		  
		  rgBerechnung.setPension(buildPension());
		  rgBerechnung.initPart();
		    
		  this.tabFolder.setSelection(4);
		 
	  }
		
	
	  
      public IPension buildPension() {
    	Combo comboPensionType = grunddaten.getComboPensionType();
		Combo comboAnzuwendRecht = grunddaten.getComboAnzuwendRecht();
		String pensionTyp = comboPensionType.getItem(comboPensionType.getSelectionIndex());
		String anzuwendendesRecht = comboAnzuwendRecht.getItem(comboAnzuwendRecht.getSelectionIndex());
		
		IPension pension = null;
		Person person = this.grunddaten.getAntragsteller();
		
		
		switch (pensionTyp) {
			
			case SelectionConstants.ART_DER_PENSION_GESETZLICHE_ALTERESGRENZE:
				pension = new RegelaltersgrenzeRuhestand(person, anzuwendendesRecht);
				break;
			case SelectionConstants.ART_DER_PENSION_ANTRAGSRUHESTAND:
				pension = new AntragsRuhestand(person, anzuwendendesRecht);
				break;
			case SelectionConstants.ART_DER_PENSION_VORRUHESTAND:
				pension = new Vorruhestand(person, anzuwendendesRecht);
				break;
			case SelectionConstants.ART_DER_PENSION_ENGAGIERTER_RUHESTAND:
				pension = new EngagierterRuhestand(person, anzuwendendesRecht);
				break;
			case SelectionConstants.ART_DER_PENSION_DIENSTUNFAEHIGKEIT:
				pension = new Dienstunfaehigkeit(person, anzuwendendesRecht);
				break;
			case SelectionConstants.ART_DER_PENSION_DIENSTUNFALL:
				Dienstunfaehigkeit dienstunfall = new Dienstunfaehigkeit(person, anzuwendendesRecht);
				dienstunfall.setDienstunfall(true);
				pension = dienstunfall;
				break;
	
			default:
				break;
		}
		
		Date retirementDate = DateUtil.getDate(grunddaten.getTfRetirementDate().getText());
		IBesoldungstabelle besoldungstabelle = BesoldungstabelleFactory.getInstance().getBesoldungstabelle(SelectionConstants.ANZUWENDENDES_RECHT_BUND, retirementDate); 
		pension.setBesoldungstabelle(besoldungstabelle);
		pension.setTimePeriods( this.zeiten.getTimePeriods());
		
		float aktuellenRentenwertWest = pension.getBesoldungstabelle().getAktuellenRentenwertWestForKindererziehungszuschlag();
		pension.setKindererziehungsZuschlag(this.kez.getKindererziehungszuschlag(aktuellenRentenwertWest));
		
		pension.setVergleichsberechnungBeamtVG_PARA_85(grunddaten.getCbVergleichsberechnungErforderlich().getSelection());
		
		return pension;
	  }

      
      
     public void clear() {
    	 grunddaten.clear();
    	 zeiten.clear();
    	 kez.clear();
    	 rgBerechnung.clear();
     }
     
     
	 public void setData(IPension pension) {
		 grunddaten.setData(pension);
    	 zeiten.setData(pension.getTimePeriods());
    	 kez.setData(pension.getKindererziehungsZuschlag());
     }
		
		
	 
}
