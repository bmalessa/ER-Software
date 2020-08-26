package de.protin.support.pr.gui.listener;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.protin.support.pr.domain.IBesoldungstabelle;
import de.protin.support.pr.domain.besoldung.Amtszulage;
import de.protin.support.pr.domain.besoldung.tabelle.BesoldungstabelleFactory;
import de.protin.support.pr.domain.utils.DateUtil;
import de.protin.support.pr.domain.utils.SelectionConstants;
import de.protin.support.pr.gui.GrunddatenPart;
import de.protin.support.pr.gui.PensionGUI;

public class GrunddatenPartListener extends AbstractPartListener  {
	
	private GrunddatenPart grunddaten;
	
	
	public GrunddatenPartListener(PensionGUI gui, GrunddatenPart part) {
		super(gui, part.getDocPart());
		this.grunddaten = part;
		this.textResources = grunddaten.getTextResources();
		this.registerListener();
	}
	
	
	public void registerListener() {
		
		  grunddaten.getBtnRadioAltersgrenze().addFocusListener(this);
		  grunddaten.getBtnRadioBesondereAltersgrenze().addFocusListener(this);
		  grunddaten.getCbAnspruchStellenzulage().addFocusListener(this);
		  grunddaten.getCbVergleichsberechnungErforderlich().addFocusListener(this);
		  grunddaten.getComboAmtszulage().addFocusListener(this);
		  grunddaten.getComboAnzuwendRecht().addFocusListener(this);
		  grunddaten.getComboLaufbahn().addFocusListener(this);
		  grunddaten.getComboBesGruppe().addFocusListener(this);
		  grunddaten.getComboBesStufe().addFocusListener(this);
		 
		
		  grunddaten.getTfBirthDate().addListener (SWT.FocusIn, new Listener () {
		      public void handleEvent (Event e) {
		    	  String hintText = textResources.getString("DATE_FORMAT");
				  docPart.setPartInfoText("Das Datum für das Feld \n'Geburtsdatum'\n" + hintText);
		      }
		  });
		  
		  grunddaten.getTfRetirementDate().addListener (SWT.FocusIn, new Listener () {
		      public void handleEvent (Event e) {
		    	  
		    	  String hintDateFormat = textResources.getString("DATE_FORMAT");
		    	  String hintRetirementDate = textResources.getString("RETIREMENT_DATE");
		    	  StringBuffer sb = new StringBuffer();
		    	  sb.append(hintDateFormat);
		    	  sb.append("\n\n");
		    	  sb.append(hintRetirementDate);
		    	  
				  docPart.setPartInfoText("Das Datum für das Feld \n'Eintritt in den Ruhestand zum'\n " + sb.toString());
		      }
		  });
		  
		  grunddaten.getTfSubstantiationDate().addListener (SWT.FocusIn, new Listener () {
		      public void handleEvent (Event e) {
		    	  
		    	  String hintDateFormat = textResources.getString("DATE_FORMAT");
		    	  String hintSubstantiationDate = textResources.getString("SUBSTANTIATION_DATE");
		    	  StringBuffer sb = new StringBuffer();
		    	  sb.append(hintDateFormat);
		    	  sb.append("\n\n");
		    	  sb.append(hintSubstantiationDate);
		    	  
				  docPart.setPartInfoText("Das Datum für das Feld \n'Begründung des Beamtenverhältnis am'\n " + sb.toString());
		      }
		  });
		  
		  grunddaten.getTfSonstigeBezuege().addListener (SWT.FocusIn, new Listener () {
		      public void handleEvent (Event e) {
		    	  docPart.setPartInfoText("Die 'Sonstigen ruhegehaltsfähigen Bezüge'\n bitte als Dezimalzahl angeben \n(z.B. 256,90)");
		      }
		  });
		  
		  grunddaten.getTfName().addListener (SWT.FocusIn, new Listener () {
		      public void handleEvent (Event e) {
		    	  docPart.setPartInfoText("Das Feld 'Name' ist kein Pflichtfeld und kann, sofern gewünscht, leer bleiben");
		      }
		  });
		  
		  grunddaten.getTfVorname().addListener (SWT.FocusIn, new Listener () {
		      public void handleEvent (Event e) {
		    	  docPart.setPartInfoText("Das Feld 'Vorname' ist kein Pflichtfeld und kann, sofern gewünscht, leer bleiben");
		      }
		  });
		  
		  
		  
		
		  grunddaten.getComboPensionType().addListener(SWT.Selection, new Listener() {
			    public void handleEvent(Event e) {
			    	selectionPensionTypeChanged(grunddaten.getComboPensionType());
			    }
		  });
		  
		  grunddaten.getCbAnspruchStellenzulage().addSelectionListener(new SelectionAdapter() {
		        @Override
		        public void widgetSelected(SelectionEvent event) {
		            Button btn = (Button) event.getSource();
		            if (btn.getSelection()) {
				        grunddaten.getComboAmtszulage().setVisible(true);
				   	}
				   	else {
				   		grunddaten.getComboAmtszulage().setVisible(false);
				   	}
		        }
		  });
		  
		  
		  grunddaten.getComboBesGruppe().addSelectionListener(new SelectionAdapter() {
		        @Override
		        public void widgetSelected(SelectionEvent event) {
		        	//immer erst zurücksetzen
		        	grunddaten.getComboAmtszulage().setItems(new String[] {});
		        	
		        	Combo comboBesGroup = (Combo) event.getSource();
		        	String besGroup = comboBesGroup.getItem(comboBesGroup.getSelectionIndex());
		        	String laufbahn = grunddaten.getComboLaufbahn().getItem(grunddaten.getComboLaufbahn().getSelectionIndex());
		        	String besoldungsGruppe = laufbahn + " " + besGroup;
		        	
		        	Date retirementDate = new Date();
		        	if(grunddaten.getTfRetirementDate().getText() != null && grunddaten.getTfRetirementDate().getText().length() == 10) {
		        		retirementDate = DateUtil.getDate(grunddaten.getTfRetirementDate().getText());
		        	}
			        
		        	IBesoldungstabelle besoldungstabelle = BesoldungstabelleFactory.getInstance().getBesoldungstabelle(SelectionConstants.ANZUWENDENDES_RECHT_BUND, retirementDate); 
			        Amtszulage[] amtszulagenForBesoldungsgruppe = besoldungstabelle.getAmtszulagenForBesoldungsgruppe(besoldungsGruppe);
			        if(amtszulagenForBesoldungsgruppe != null) {
			        	String[] values = convertToString(amtszulagenForBesoldungsgruppe);
			        	grunddaten.getComboAmtszulage().setItems(values);
			        	grunddaten.getComboAmtszulage().select(0);
			        }
		        	
		        }
	
				private String[] convertToString(Amtszulage[] amtszulagenForBesoldungsgruppe) {
					String[] result = new String[amtszulagenForBesoldungsgruppe.length];
					for (int i = 0; i < amtszulagenForBesoldungsgruppe.length; i++) {
						result[i] = Float.toString(amtszulagenForBesoldungsgruppe[i].getZulage()) + 
								" (" + amtszulagenForBesoldungsgruppe[i].getFussnote() +")";
					}
							
					return result;
				}
		  });
		  
		  
		  grunddaten.getBtnValidate().addListener(SWT.Selection, new Listener() {
			    public void handleEvent(Event e) {
			    	 switch (e.type) {
				         case SWT.Selection:
				           gui.validateInput(true);
				           break;
				     }
			    }
	
				
		  });
		  
		  grunddaten.getBtnCalculate().addListener(SWT.Selection, new Listener() {
			    public void handleEvent(Event e) {
			    	switch (e.type) {
				         case SWT.Selection:
				           gui.validateInput(false);
				           gui.calculateRuhegehalt();
				           break;
			    	}
			    }
	
				
		  });
	 }
	  
	
	@Override
	public void focusGained(FocusEvent event) {
		String defaulText = textResources.getString("DEFAULT_TEXT");
		docPart.setPartDefaultText(defaulText);
	}

	@Override
	public void focusLost(FocusEvent event) {
		//docPart.setPartInfoText("");
	}
	
	
	public void selectionPensionTypeChanged(Combo comboPensionType) {
		int selectionIndex = comboPensionType.getSelectionIndex();
		String type = comboPensionType.getItem(selectionIndex);
		
		String hintText = new String();
		switch (type) {
	        case SelectionConstants.ART_DER_PENSION_GESETZLICHE_ALTERESGRENZE:
	        	hintText = textResources.getString("ART_DER_PENSION_GESETZLICHE_ALTERSGRENZE");
	        	break;
	        case SelectionConstants.ART_DER_PENSION_ANTRAGSRUHESTAND:
	        	hintText = textResources.getString("ART_DER_PENSION_ANTRAGSRUHESTAND"); 
	        	break;
	        case SelectionConstants.ART_DER_PENSION_ENGAGIERTER_RUHESTAND:
	        	hintText = textResources.getString("ART_DER_PENSION_ENGAGIERTER_RUHESTAND"); 
	        	break;
	        case SelectionConstants.ART_DER_PENSION_VORRUHESTAND:
	        	hintText = textResources.getString("ART_DER_PENSION_VORRUHESTAND"); 
	        	break;
	        case SelectionConstants.ART_DER_PENSION_DIENSTUNFAEHIGKEIT:
	        	hintText = textResources.getString("ART_DER_PENSION_DIENSTUNFAEHIGKEIT"); 
	        	break;
	        case SelectionConstants.ART_DER_PENSION_DIENSTUNFALL:
	        	hintText = textResources.getString("ART_DER_PENSION_DIENSTUNFALL"); 
	        	break;
	        
	        default:
	        	break;
		}
		
		this.docPart.setPartInfoText(hintText);
	}
	
	
}




