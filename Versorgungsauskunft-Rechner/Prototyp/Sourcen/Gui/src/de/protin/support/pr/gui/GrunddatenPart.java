package de.protin.support.pr.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.besoldung.Amtszulage;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.SonstigeZulage;
import de.protin.support.pr.domain.pension.AbstractPension;
import de.protin.support.pr.domain.utils.SelectionConstants;
import de.protin.support.pr.gui.text.TextResources;
import de.protin.support.pr.gui.util.ControlConstants;

public class GrunddatenPart extends TabPart {
	

	private Text tfName;
	private Text tfVorname;
	private Text tfBirthDate;
	private Text tfBesondereAltersgrenze;
	private Text tfRetirementDate;
	private Text tfSubstantiationDate;
	private Text tfSonstigeBezuege;
	private Combo comboPensionType;
	private Combo comboAnzuwendRecht;
	private Combo comboAmtszulage;
	private Combo comboBesGruppe;
	private Combo comboBesStufe;
	private Combo comboLaufbahn;
	private Combo comboFamZuschlag;
	private Button btnRadioAltersgrenze;
	private Button btnRadioBesondereAltersgrenze;
	private Button cbVergleichsberechnungErforderlich;
	private Button cbAnspruchAmtszulage;
	private Button cbSchwerbehindert;
	private Button cbVergleichSchwerbehinderung;
	private Button btnValidate;
	private Button btnCalculate;
	
	
	public GrunddatenPart(Composite parent) {
		super(parent);
	}

		
	public void initPart(Composite parent, int style) {
	
		
		Composite partComposite = new Composite(parent, SWT.BORDER);
		partComposite.setLayout(new GridLayout(2, false));
		
		Label lblSeparator_1 = new Label(partComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparator_1.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 10, SWT.BOLD));
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		data.horizontalSpan = 2;
		lblSeparator_1.setLayoutData(data);
		new Label(partComposite, SWT.NONE);
		new Label(partComposite, SWT.NONE);
		
		Label lblGrundDesEintritts = new Label(partComposite, SWT.NONE);
		lblGrundDesEintritts.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblGrundDesEintritts.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblGrundDesEintritts.setText(ControlConstants.LABEL_GRUND_DES_EINTRITTS);
		
		comboPensionType = new Combo(partComposite, SWT.NONE);
		

		comboPensionType.setItems(new String[] {SelectionConstants.ART_DER_PENSION_GESETZLICHE_ALTERESGRENZE, SelectionConstants.ART_DER_PENSION_ANTRAGSRUHESTAND, 
				SelectionConstants.ART_DER_PENSION_ENGAGIERTER_RUHESTAND, SelectionConstants.ART_DER_PENSION_VORRUHESTAND, 
				SelectionConstants.ART_DER_PENSION_DIENSTUNFAEHIGKEIT, SelectionConstants.ART_DER_PENSION_DIENSTUNFALL});
		

		GridData gd_comboPensionType = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_comboPensionType.widthHint = 218;
		comboPensionType.setLayoutData(gd_comboPensionType);
		
		
		Label lblEintrittBeaV = new Label(partComposite, SWT.NONE);
		lblEintrittBeaV.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblEintrittBeaV.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEintrittBeaV.setText(ControlConstants.LABEL_EINTRITT_IN_DAS_BEAMTENVERH_AM);
		
		tfSubstantiationDate = new Text(partComposite, SWT.BORDER);
		tfSubstantiationDate.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		
		Label lblEintrittInDen = new Label(partComposite, SWT.NONE);
		lblEintrittInDen.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblEintrittInDen.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEintrittInDen.setText(ControlConstants.LABEL_EINTRITT_IN_DEN_RUHESTAND_ZUM);
		
		tfRetirementDate = new Text(partComposite, SWT.BORDER);
		tfRetirementDate.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Label lblAnzuwendendesRecht = new Label(partComposite, SWT.NONE);
		lblAnzuwendendesRecht.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblAnzuwendendesRecht.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAnzuwendendesRecht.setText(ControlConstants.LABEL_ANZUWENDENDES_RECHT);
		
		comboAnzuwendRecht = new Combo(partComposite, SWT.NONE);
		comboAnzuwendRecht.setItems(new String[] {SelectionConstants.ANZUWENDENDES_RECHT_BUND});
		GridData gd_comboAnzuwendRecht = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_comboAnzuwendRecht.widthHint = 160;
		comboAnzuwendRecht.setLayoutData(gd_comboAnzuwendRecht);
		
		Label lblAnzuwendendeAltersgrenze = new Label(partComposite, SWT.NONE);
		lblAnzuwendendeAltersgrenze.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblAnzuwendendeAltersgrenze.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAnzuwendendeAltersgrenze.setText(ControlConstants.LABEL_ANZUWENDENDE_ALTERSGRENZE);
		
		Composite composite = new Composite(partComposite, SWT.NONE);
		GridLayout gl_composite = new GridLayout(4, false);
		gl_composite.marginWidth = 0;
		gl_composite.marginRight = 5;
		gl_composite.horizontalSpacing = 0;
		composite.setLayout(gl_composite);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		btnRadioAltersgrenze = new Button(composite, SWT.RADIO);
		btnRadioAltersgrenze.setText(ControlConstants.BTN_RADIO_GESETZLICHE_ALTERSGRENZE);
		
		btnRadioBesondereAltersgrenze = new Button(composite, SWT.RADIO);
		btnRadioBesondereAltersgrenze.setText(ControlConstants.BTN_RADIO_BESONDERE_ALTERSGRENZE);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("in Jahren:");
		
		tfBesondereAltersgrenze = new Text(composite, SWT.BORDER);
		GridData gd_tfBesondereAltersgrenze = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_tfBesondereAltersgrenze.horizontalIndent = 5;
		gd_tfBesondereAltersgrenze.widthHint = 12;
		tfBesondereAltersgrenze.setLayoutData(gd_tfBesondereAltersgrenze);
		tfBesondereAltersgrenze.setSize(23, 21);
		
		new Label(partComposite, SWT.NONE);
		
		this.cbVergleichsberechnungErforderlich = new Button(partComposite, SWT.CHECK);
		cbVergleichsberechnungErforderlich.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		cbVergleichsberechnungErforderlich.setText(ControlConstants.BTN_CB_BEAMTV_BESTAND_AM_31_12_1991);
		
		Label lblSeparator_2 = new Label(partComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparator_2.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 10, SWT.BOLD));
		data = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		data.horizontalSpan = 2;
		lblSeparator_2.setLayoutData(data);
		
		new Label(partComposite, SWT.NONE);
		new Label(partComposite, SWT.NONE);
		
		Label lblName = new Label(partComposite, SWT.NONE);
		lblName.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText(ControlConstants.LABEL_NAME);
		
		tfName = new Text(partComposite, SWT.BORDER);
		GridData gd_tfName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_tfName.widthHint = 186;
		tfName.setLayoutData(gd_tfName);
		
		Label lblVorname = new Label(partComposite, SWT.NONE);
		lblVorname.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblVorname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVorname.setText(ControlConstants.LABEL_VORNAME);
		
		tfVorname = new Text(partComposite, SWT.BORDER);
		GridData gd_tfVorname = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_tfVorname.widthHint = 186;
		tfVorname.setLayoutData(gd_tfVorname);
		
		Label lblGeburtsdatum = new Label(partComposite, SWT.NONE);
		lblGeburtsdatum.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblGeburtsdatum.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblGeburtsdatum.setText(ControlConstants.LABEL_GEBURTSDATUM);
		
		tfBirthDate = new Text(partComposite, SWT.BORDER);
		tfBirthDate.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		
		new Label(partComposite, SWT.NONE);
		this.cbSchwerbehindert = new Button(partComposite, SWT.CHECK);
		cbSchwerbehindert.setText(ControlConstants.BTN_CB_SCHWERBEHINDERT);
		new Label(partComposite, SWT.NONE);
		
		this.cbVergleichSchwerbehinderung = new Button(partComposite, SWT.CHECK);
		cbVergleichSchwerbehinderung.setText(ControlConstants.BTN_CB_SCHWERBEHINDERT_VOR_17_11_2000);
		
		
		Label lblSeparator_3 = new Label(partComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparator_3.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 10, SWT.BOLD));
		data = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		data.horizontalSpan = 2;
		lblSeparator_3.setLayoutData(data);
		
		new Label(partComposite, SWT.NONE);
		new Label(partComposite, SWT.NONE);
		
		Label lblLaufbahn = new Label(partComposite, SWT.NONE);
		lblLaufbahn.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblLaufbahn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLaufbahn.setText(ControlConstants.LABEL_LAUFBAHN);
		
		comboLaufbahn = new Combo(partComposite, SWT.NONE);
		comboLaufbahn.setItems(SelectionConstants.LAUFBAHN);
		comboLaufbahn.setVisibleItemCount(20);
		GridData gd_comboLaufbahn = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_comboLaufbahn.widthHint = 16;
		comboLaufbahn.setLayoutData(gd_comboLaufbahn);
		
		Label lblBesoldungsgruppe = new Label(partComposite, SWT.NONE);
		lblBesoldungsgruppe.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblBesoldungsgruppe.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBesoldungsgruppe.setText(ControlConstants.LABEL_BESOLDUNGSGRUPPE);
		
		comboBesGruppe = new Combo(partComposite, SWT.NONE);
		comboBesGruppe.setItems(SelectionConstants.BESOLDUNGSGRUPPE);
		comboBesGruppe.setVisibleItemCount(20);
		GridData gd_comboBesGruppe = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_comboBesGruppe.widthHint = 16;
		comboBesGruppe.setLayoutData(gd_comboBesGruppe);
		
		Label lblBesoldungsstufe = new Label(partComposite, SWT.NONE);
		lblBesoldungsstufe.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblBesoldungsstufe.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBesoldungsstufe.setText(ControlConstants.LABEL_BESOLDUNGSSTUFE);
		
		this.comboBesStufe = new Combo(partComposite, SWT.NONE);
		comboBesStufe.setItems(SelectionConstants.BESOLDUNGSSTUFE);
		comboBesStufe.setVisibleItemCount(20);
		GridData gd_comboBesStufe = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_comboBesStufe.widthHint = 17;
		comboBesStufe.setLayoutData(gd_comboBesStufe);
		new Label(partComposite, SWT.NONE);
		
		Composite compositeAmtszulage = new Composite(partComposite, SWT.NONE);
		GridLayout gl_compositeAmtszulage = new GridLayout(3, false);
		gl_compositeAmtszulage.marginWidth = 0;
		compositeAmtszulage.setLayout(gl_compositeAmtszulage);
		
		cbAnspruchAmtszulage = new Button(compositeAmtszulage, SWT.CHECK);
		cbAnspruchAmtszulage.setSize(260, 16);
		cbAnspruchAmtszulage.setText(ControlConstants.BTN_CB_ANSPRUCH_AMTSZULAGE);
		
		comboAmtszulage = new Combo(compositeAmtszulage, SWT.NONE);
		GridData gd_comboAmtszulage = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_comboAmtszulage.widthHint = 50;
		comboAmtszulage.setLayoutData(gd_comboAmtszulage);
		comboAmtszulage.setVisible(false);
		new Label(compositeAmtszulage, SWT.NONE);
		
		Label lblSonstigeRuhegehaltsfhigeBezge = new Label(partComposite, SWT.NONE);
		lblSonstigeRuhegehaltsfhigeBezge.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblSonstigeRuhegehaltsfhigeBezge.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSonstigeRuhegehaltsfhigeBezge.setText(ControlConstants.LABEL_SONSTIGE_RUHEGEHALT_BEZUEGE);
		
		tfSonstigeBezuege = new Text(partComposite, SWT.BORDER);
		GridData gd_tfSonstigeBezuege = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_tfSonstigeBezuege.widthHint = 68;
		tfSonstigeBezuege.setLayoutData(gd_tfSonstigeBezuege);
		
		Label lblFamilienzuschlag = new Label(partComposite, SWT.NONE);
		lblFamilienzuschlag.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 9, SWT.BOLD));
		lblFamilienzuschlag.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFamilienzuschlag.setText(ControlConstants.LABEL_FAMILIENZUSCHLAG);
		
		Composite compositeFamZuschlag = new Composite(partComposite, SWT.NONE);
		compositeFamZuschlag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_compositeFamZuschlag = new GridLayout(3, false);
		gl_compositeFamZuschlag.marginWidth = 0;
		compositeFamZuschlag.setLayout(gl_compositeFamZuschlag);
		
		this.comboFamZuschlag = new Combo(compositeFamZuschlag, SWT.NONE);
		GridData gd_comboFamZuschlag = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_comboFamZuschlag.widthHint = 236;
		comboFamZuschlag.setLayoutData(gd_comboFamZuschlag);
		comboFamZuschlag.setSize(210, 23);
		comboFamZuschlag.setItems(new String[] {SelectionConstants.FAMILIENZUSCHLAG_STUFE_0, 
				SelectionConstants.FAMILIENZUSCHLAG_STUFE_1, SelectionConstants.FAMILIENZUSCHLAG_STUFE_2});
		
		/*
		Label lblAnzahlKinderFr = new Label(compositeFamZuschlag, SWT.NONE);
		lblAnzahlKinderFr.setSize(188, 15);
		lblAnzahlKinderFr.setText(ControlConstants.LABEL_ANZAHL_KINDER);
		
		tfFamZuschlagAnzKinder = new Text(compositeFamZuschlag, SWT.BORDER);
		GridData gd_tfFamZuschlagAnzKinder = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_tfFamZuschlagAnzKinder.widthHint = 11;
		tfFamZuschlagAnzKinder.setLayoutData(gd_tfFamZuschlagAnzKinder);
		tfFamZuschlagAnzKinder.setSize(23, 21);
		*/
		//die folgenden beiden sind die Platzhalter für die kinderbezogenen Controls, welche hier eigentlich nicht benötigt werden
		new Label(partComposite, SWT.NONE);
		new Label(partComposite, SWT.NONE);
		
		
		new Label(partComposite, SWT.NONE);
		new Label(partComposite, SWT.NONE);
		
		Label lblSeparator_4 = new Label(partComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparator_4.setFont(SWTResourceManager.getFont(ControlConstants.FONT_LABEL, 10, SWT.BOLD));
		data = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		data.horizontalSpan = 2;
		lblSeparator_4.setLayoutData(data);
		
		
		new Label(partComposite, SWT.NONE);
		
		Composite btnComposite = new Composite(partComposite, SWT.NONE);
		btnComposite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		btnComposite.setLayout(new GridLayout(3, false));
		
		this.btnValidate = new Button(btnComposite, SWT.NONE);
		btnValidate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnValidate.setText("Validiere Eingaben");
		
		this.btnCalculate = new Button(btnComposite, SWT.NONE);
		btnCalculate.setText("Berechne Ruhegehalt");
		new Label(btnComposite, SWT.NONE);
		
		new Label(partComposite, SWT.NONE);
		
		
		comboPensionType.select(0);
		comboAnzuwendRecht.select(0);
		comboFamZuschlag.select(0);
		comboLaufbahn.select(0);
		btnRadioAltersgrenze.setSelection(true);
		new Label(partComposite, SWT.NONE);
		
	}
	
	
	/**
	 * 
	 */
	public void loadTextResources() {
		this.textResources = TextResources.getGrunddatenDocs();
	}


	
	/**
	 * Validierung der Eingaben gegen die jeweils für das betreffendes Eingabefeld erwarteten Datentypen, z.B. valides Datumsformat oder Integer. 
	 * 
	 * @return List<Status>
	 */
	public List<Status> validateInput() {
		
		 List<Status> validationStatuses = new ArrayList<>();
		 SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		 String decimalPattern = "([0-9]*)\\,([0-9][0-9])";  
		
		  try {
			sdf.parse(getTfBirthDate().getText());
		  } catch (ParseException e) {
			  Status status = new Status(IStatus.ERROR,"Geburtstag", "Geburtstag: Datum im bitte in folgendem Fomat eingeben: \"TT.MM.JJJJ\"");
			  validationStatuses.add(status);
		  }
		  try {
			sdf.parse(getTfRetirementDate().getText());
		  } catch (ParseException e) {
			  Status status = new Status(IStatus.ERROR,"Eintritt in den Ruhestand", "Eintritt in den Ruhestand: Datum im bitte in folgendem Fomat eingeben: \"TT.MM.JJJJ\"");
			  validationStatuses.add(status);
		  }
		  
		  
		  try {
			  String text = getTfBesondereAltersgrenze().getText();
			  if(text != null && text.length() > 0 ) {
				  Integer.parseInt(getTfBesondereAltersgrenze().getText());
			  }
			  
		  } catch (NumberFormatException e) {
			  Status status = new Status(IStatus.ERROR,"Besondere Altersgrenze", "Besondere Altersgrenze bitte als gültige Zahl eingeben");
			  validationStatuses.add(status);
		  }
		

		  String text = getTfSonstigeBezuege().getText();
		  if(text != null && text.length() > 0) {
			  boolean match = Pattern.matches(decimalPattern, text);
			  if(!match) {
				  Status status = new Status(IStatus.ERROR,"Sonstige Bezüge", "'Sonstige ruhegehaltsfähigen Bezüge' bitte in folgendem Format (nnn,nn) eingeben.");
				  validationStatuses.add(status);
			  }
		  }
	
		  int besGruppeIndex = getComboBesGruppe().getSelectionIndex();
		  int besStufeIndex = getComboBesStufe().getSelectionIndex();
		  if(besGruppeIndex < 0 || besStufeIndex < 0 ) {
			  Status status = new Status(IStatus.ERROR,"Besoldungsgruppe/Besoldungsstufe", "Bitte Besoldungsgruppe und Besoldungsstufe auswählen.");
			  validationStatuses.add(status);
		  }
		  
		  
		  return validationStatuses;
		
	}


	
	
	public Combo getComboPensionType() {
		return comboPensionType;
	}
	

	public Button getBtnRadioAltersgrenze() {
		return btnRadioAltersgrenze;
	}


	public Button getBtnRadioBesondereAltersgrenze() {
		return btnRadioBesondereAltersgrenze;
	}


	public Combo getComboAmtszulage() {
		return comboAmtszulage;
	}
	
	
	public Combo getComboAnzuwendRecht() {
		return comboAnzuwendRecht;
	}


	public Combo getComboBesGruppe() {
		return comboBesGruppe;
	}


	public Combo getComboBesStufe() {
		return comboBesStufe;
	}


	public Combo getComboLaufbahn() {
		return comboLaufbahn;
	}


	public void setComboAmtszulage(Combo comboAmtszulage) {
		this.comboAmtszulage = comboAmtszulage;
	}


	public Button getCbAnspruchStellenzulage() {
		return cbAnspruchAmtszulage;
	}


	public Button getBtnValidate() {
		return btnValidate;
	}


	public Button getBtnCalculate() {
		return btnCalculate;
	}

	public Text getTfBirthDate() {
		return tfBirthDate;
	}


	public Text getTfBesondereAltersgrenze() {
		return tfBesondereAltersgrenze;
	}


	public Text getTfRetirementDate() {
		return tfRetirementDate;
	}


	public Text getTfSonstigeBezuege() {
		return tfSonstigeBezuege;
	}
	
	
	public Text getTfName() {
		return tfName;
	}


	public Text getTfVorname() {
		return tfVorname;
	}


	public Text getTfSubstantiationDate() {
		return tfSubstantiationDate;
	}


	public Button getCbVergleichsberechnungErforderlich() {
		return cbVergleichsberechnungErforderlich;
	}


	public Person getAntragsteller() {
		
		if(this.tfName.getText().length() < 2) {
			this.tfName.setText("<Nachname>");
		}
		if(this.tfVorname.getText().length() < 2) {
			this.tfVorname.setText("<Vorname>");
		}
		
		
		Person person = new Person(this.tfName.getText(), this.tfVorname.getText());
		
		person.setDateOfBirthString(this.tfBirthDate.getText());
		person.setDateOfRetirementString(this.tfRetirementDate.getText());
		person.setDateOfSubstantiationString(this.tfSubstantiationDate.getText());
		
		if(this.btnRadioBesondereAltersgrenze.getSelection()) {
			person.setBesondereAltersgrenze(true);
			person.setBesondereAltersgrenzeJahre(Integer.parseInt(this.tfBesondereAltersgrenze.getText()));
		}
		
		if(this.cbSchwerbehindert.getSelection()) {
			person.setSchwerbehindert(true);
			if(this.cbVergleichSchwerbehinderung.getSelection()) {
				person.setSchwerbehindertBefore20001117(true);
			}
		}
		
		if(this.cbVergleichsberechnungErforderlich.getSelection()) {
			//muss das überhaupt explizit angeklickt werden oder wird das über die ruheghaltsfähigen Zeiten ermittelt
		}
		
	
		String laufbahnOrdnung = this.comboLaufbahn.getItem(comboLaufbahn.getSelectionIndex());
		String besGruppe = this.comboBesGruppe.getItem(comboBesGruppe.getSelectionIndex());
		String besStufe = this.comboBesStufe.getItem(comboBesStufe.getSelectionIndex());
		person.setBesoldung(new Grundgehalt(laufbahnOrdnung, Integer.parseInt(besGruppe), Integer.parseInt(besStufe)));
		
		String selectedItemFamilienzuschlag = this.comboFamZuschlag.getItem(comboFamZuschlag.getSelectionIndex());
		String[] splitFamZuschlag = selectedItemFamilienzuschlag.split(" ");
		person.setFamilienzuschlag(new Familienzuschlag(Integer.parseInt(splitFamZuschlag[0])));
		
		
		if(this.tfSonstigeBezuege.getText() != null && this.tfSonstigeBezuege.getText().length() > 0) {
		
			String valueStr = this.tfSonstigeBezuege.getText();
			valueStr = valueStr.replace(',', '.');
			person.setSonstigeZulage(new SonstigeZulage("", 0, Float.parseFloat(valueStr)));
		}
		
		if(this.cbAnspruchAmtszulage.getSelection()) {
			String selectedItemAmtszulage = this.comboAmtszulage.getItem(comboAmtszulage.getSelectionIndex());
			Amtszulage amtszulage = new Amtszulage();
			amtszulage.setBesGroup(laufbahnOrdnung + " " + besGruppe);
			String[] splitAmtszulage = selectedItemAmtszulage.split(" ");
			amtszulage.setZulage(Float.parseFloat(splitAmtszulage[0]));
			amtszulage.setFussnote(splitAmtszulage[1]);
			person.setAmtszulage(amtszulage);
		}
		
		
		return person;
	}


	public void clear() {
		tfName.setText("");
		tfVorname.setText("");
		tfBirthDate.setText("");
		tfBesondereAltersgrenze.setText("");
		tfRetirementDate.setText("");
		tfSubstantiationDate.setText("");
		tfSonstigeBezuege.setText("");
		comboPensionType.clearSelection();
		comboAmtszulage.deselectAll();
		comboBesGruppe.deselectAll();
		comboBesStufe.deselectAll();
		comboLaufbahn.clearSelection();
		comboFamZuschlag.clearSelection();
		btnRadioAltersgrenze.setSelection(true);
		btnRadioBesondereAltersgrenze.setSelection(false);
		cbVergleichsberechnungErforderlich.setSelection(false);
		cbAnspruchAmtszulage.setSelection(false);
		cbSchwerbehindert.setSelection(false);
		cbVergleichSchwerbehinderung.setSelection(false);
	}


	public void setData(IPension pension) {
		Person person = pension.getPerson();
		
		tfName.setText(person.getName());
		tfVorname.setText(person.getVorname());
		tfBirthDate.setText(person.getDateOfBirthString());
		tfRetirementDate.setText(person.getDateOfRetirementString());
		tfSubstantiationDate.setText(person.getDateOfSubstantiationString());
		
		if(person.getSonstigeZulage() != null) {
			tfSonstigeBezuege.setText(Float.toString(person.getSonstigeZulage().getSalary()));
		}
		
		comboAnzuwendRecht.setText(pension.getAnzwRecht());
		
		//todo - das ist ein bisschen "brittle".
		comboPensionType.select(((AbstractPension)pension).getTyp() - 1);
		
		comboLaufbahn.setText(person.getBesoldung().getLaufbahnOrdnung());
		if("A".equalsIgnoreCase(person.getBesoldung().getLaufbahnOrdnung())) {
			comboBesGruppe.select(person.getBesoldung().getBesoldungsGruppe()-2);
		}
		else {
			comboBesGruppe.select(person.getBesoldung().getBesoldungsGruppe()-1);
		}
		
		
		comboBesStufe.select(person.getBesoldung().getBesoldungsStufe()-1);
		
		
		
		comboFamZuschlag.select(person.getFamilienzuschlag().getStufe());
		
	
		if(person.isBesondereAltersgrenze()) {
			btnRadioBesondereAltersgrenze.setSelection(true);	
			tfBesondereAltersgrenze.setText(Integer.toString(person.getBesondereAltersgrenzeJahre()));
			btnRadioAltersgrenze.setSelection(false);
		}
		else {
			btnRadioAltersgrenze.setSelection(true);
			btnRadioBesondereAltersgrenze.setSelection(false);	
		}
		
	
		
		cbSchwerbehindert.setSelection(person.isSchwerbehindert());
		cbVergleichSchwerbehinderung.setSelection(person.isSchwerbehindertBefore20001117());
		
		
		if(person.getAmtszulage() != null) {
			cbAnspruchAmtszulage.setSelection(true);
			String amtszulage = Float.toString(person.getAmtszulage().getZulage()) + person.getAmtszulage().getFussnote();
			comboAmtszulage.setText(amtszulage);
			comboAmtszulage.setVisible(true);
		}
		else {
			cbAnspruchAmtszulage.setSelection(false);
		}
		
		
		cbVergleichsberechnungErforderlich.setSelection(pension.isVergleichsberechnungBeamtVG_PARA_85());
	}  

	
}
