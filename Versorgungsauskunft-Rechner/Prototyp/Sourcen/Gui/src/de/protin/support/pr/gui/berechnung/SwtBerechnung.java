package de.protin.support.pr.gui.berechnung;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.zuschlag.KindererziehungszeitenZuschlag;
import de.protin.support.pr.domain.besoldung.zuschlag.Para_50_Kindererziehungszeit;
import de.protin.support.pr.domain.pension.AbstractPension;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.service.impl.ServiceRegistry;
import de.protin.support.pr.domain.utils.Constants;
import de.protin.support.pr.domain.utils.PrintUtils;
import de.protin.support.pr.domain.utils.TimePeriodCalculator;
import de.protin.support.pr.domain.utils.TimePeriodDetails;
import de.protin.support.pr.gui.RuhegehaltBerechnungPart;
import de.protin.support.swt.custom.PaintObjectEvent;
import de.protin.support.swt.custom.PaintObjectListener;
import de.protin.support.swt.custom.StyleRange;

public class SwtBerechnung extends AbstractBerechnung implements IVersorgungsauskunftBerechnung {
	
	
	public SwtBerechnung(RuhegehaltBerechnungPart part) {
		super(part);
		styledText.setTabStops(new int[] {70, 140, 210, 300, 350, 400, 450});
	}
	
	@Override
	public void buildHeader() {
		styledText.addPaintObjectListener(new PaintObjectListener() {
		      public void paintObject(PaintObjectEvent event) {
		        GC gc = event.gc;
		        StyleRange style = event.style;
		        //int start = style.start;
	            int x = event.x;
	            int y = event.y + event.ascent - style.metrics.ascent;
	            gc.drawImage(image, x, y);
		      }
		});
		
		try {
            Image orgImage = new Image(styledText.getDisplay(), AbstractPension.class.getResourceAsStream("/prot-in-logo.jpg"));
            this.image = new Image(styledText.getDisplay(), orgImage.getImageData().scaledTo(900,120));
            
            StyleRange style = new StyleRange();
            style.start = 0;
            style.length = 1;
            Rectangle rect = image.getBounds();
            style.metrics = new GlyphMetrics(rect.height, 0, rect.width);
            styledText.setStyleRange(style);
        } 
        catch (Exception e1) {
        	e1.printStackTrace();
        }
	}
	
	  
	@Override
	public void buildPensionSection() {
		appendLine(1);
		appendColoredStyleRange(new String(part.getTextResources().getString("INFO_TEXT_1")), SWT.BOLD, SWT.COLOR_RED ,10, 1);
		appendColoredStyleRange(new String(part.getTextResources().getString("INFO_TEXT_2")), SWT.BOLD, SWT.COLOR_RED ,10, 1);
		appendColoredStyleRange(new String(part.getTextResources().getString("INFO_TEXT_3")), SWT.BOLD, SWT.COLOR_RED ,10, 1);
		appendLine(1);
		appendColoredStyleRange(new String(addWhitespace(55) + part.getTextResources().getString("INFO_TEXT_4")), SWT.BOLD, SWT.COLOR_RED ,10, 1);
		appendLine(1);
		appendColoredStyleRange(new String(part.getTextResources().getString("INFO_TEXT_5")), SWT.BOLD, SWT.COLOR_RED ,10, 1);
		appendColoredStyleRange(new String(part.getTextResources().getString("INFO_TEXT_6")), SWT.BOLD, SWT.COLOR_RED ,10, 1);
		appendColoredStyleRange(new String(part.getTextResources().getString("INFO_TEXT_7")), SWT.BOLD, SWT.COLOR_RED ,10, 1);
		appendLine(1);
		appendColoredStyleRange(new String(part.getTextResources().getString("INFO_TEXT_8")), SWT.BOLD, SWT.COLOR_RED ,10, 1);
		appendLine(2);
		appendText("-----------------------------------------------------------------------------------------------------------------------------------------\n");
		appendStyleRange(new String("Grundlagen der Berechnung für die Versorgungsauskunft"), SWT.BOLD, 14, 2);
		appendText("Art des Ruhestand: " + addTabs(1)  + this.pension.getPensionTyp());
		appendLine(1);
		appendText("Besoldung: " + addTabs(2) + this.pension.getBesoldungstabelle().print());
		appendLine(1);
		appendText("Anz. Recht: " + addTabs(2) + this.pension.getAnzwRecht());
		appendLine(1);
		appendText("-----------------------------------------------------------------------------------------------------------------------------------------\n");
		appendLine(2);
		appendStyleRange(new String("Versorgungsauskunft nach dem Beamtenversorgungsgesetz"), SWT.BOLD, 18, 1);
		appendLine(3);
		appendText("Versorgungsauskunft für: " + addTabs(3) + this.pension.getPerson().getVorname() + " " + this.pension.getPerson().getName());
		appendLine(1);
		appendText("geb.am: " + addTabs(4) + this.pension.getPerson().getDateOfBirthString());
		appendLine(2);
		if( this.pension.getPerson().isSchwerbehindert()) {
			styledText.append("Schwerbehinderung vorhanden: " + addTabs(2) + "Ja");
			appendLine(2);
		}
		appendText("Besoldungsgruppe: " + addTabs(3) + this.pension.getPerson().getBesoldung().print());
		appendLine(2);
		
		appendText("Begründung des Beamtenverhältnisses,\n");
		appendText("aus dem die Versorgung gewährt wird: " + addTabs(2) + this.pension.getPerson().getDateOfSubstantiationString());
		appendLine(1);
		appendText("Ruhestand beantragt zum: " + addTabs(2) + this.pension.getPerson().getDateOfRetirementString());
		appendLine(2);
		appendText("Gesetzl. Regelaltersgrenze: " + addTabs(2) + this.pension.getPerson().getPrintableDateOfLegalRetirement());
		appendLine(1);
		appendText("Gesetzl. Antragsaltersgrenze: " + addTabs(2) + this.pension.getPerson().getPrintableDateOfRequestRetirement());
		appendLine(1);
		if(this.pension.getPerson().isBesondereAltersgrenze()) {
			appendText("Besondere Regelaltersgrenze: " + addTabs(2) + this.pension.getPerson().getPrintableDateOfSpecialLegalRetirement());
			appendLine(1);
		}
		appendLine(2);
	
	}




	
	@Override
	public void buildBerechnungSection() {
		appendStyleRange(new String("Berechnung der ruhegehaltsfähigen Dienstbezüge"), SWT.BOLD, 14, 1);
		appendLine(2);
		appendText("Grundgehalt, Versorgung: " + addTabs(5) + printFloat(this.pension.getBesoldungstabelle().
				getSalaryForGrundgehalt(this.pension.getPerson().getBesoldung())));
		appendLine(1);
		Familienzuschlag famZuschlag = this.pension.getPerson().getFamilienzuschlag();
		String tabs = new String();
		if(famZuschlag.getStufe() == 0) {
			tabs = new String("(" + "ledig/geschieden" + "\t\t\t\t");
		}
		else if(famZuschlag.getStufe() == 1) {
			tabs =  new String("(" + "verheiratet/verwitwert"+ ") :  " + "\t\t\t");
		}
		else if(famZuschlag.getStufe() == 2) {
			tabs =  new String("(" + "Ehegatte im öD mit Familienzuschlag"+ ")\t\t");
		}
		appendText("Familienzuschlag " + tabs +	printFloat(this.pension.getBesoldungstabelle().getSalaryForFamiliezuschlag(this.pension.getPerson().getFamilienzuschlag())));
		appendLine(1);
		
		if(this.pension.getPerson().getAmtszulage() != null) {
			appendText("Amtszulage: "  + addTabs(6) +   printFloat(this.pension.getPerson().getAmtszulage().getZulage()));
			appendLine(1);
		}
		else {
			appendText("Amtszulage: "  + addTabs(6) +  printFloat(0.0f));
			appendLine(1);
		}
		if(this.pension.getPerson().getSonstigeZulage() != null) {
			appendText("Sonstige ruhrgehaltsf. Zulage: " + addTabs(4) + printFloat(this.pension.getPerson().getSonstigeZulage().getSalary()));
			appendLine(2);
		}
		appendLine(2);
		
		this.ruheghaltsfaehigerDienstbezug = this.pension.calculateRuhegehaltsfaehigenDienstbezug();
		appendText("  Summe der ruhegehaltsfähigen Dienstbezüge: " + addTabs(3) +  printFloat(ruheghaltsfaehigerDienstbezug));
		appendLine(1);
		
		this.korrigierterRuhegehaltsbezug = ruheghaltsfaehigerDienstbezug * Constants.KORREKTURFAKTOR_PARA_5_Abs_1_BeamtVG;
		
		appendText("x " + String.format("%.4f", Constants.KORREKTURFAKTOR_PARA_5_Abs_1_BeamtVG) +" (§5 Abs. 1 BeamtVG)" +  addTabs(4) +  
				printFloat(korrigierterRuhegehaltsbezug));
		appendLine(1);
		
		
		this.ruhegehaltssatz_Para_14_1 = this.pension.calculateRuhegehaltssatz_Para_14_1_Neue_Fassung();
		this.erdientesRuhegehaltVorVersorgungsabschlag  = korrigierterRuhegehaltsbezug * ruhegehaltssatz_Para_14_1 / 100;;
		
		appendText("x " + "Ruhegehaltssatz " + printFloat(ruhegehaltssatz_Para_14_1) + " v.H. " +  addTabs(4)  + 
				printFloat(erdientesRuhegehaltVorVersorgungsabschlag));
		appendLine(1);
		appendText("= " + "Ruhegehalt"  + addTabs(5)  +   printFloat(erdientesRuhegehaltVorVersorgungsabschlag));
		appendLine(2);
		
		//Prozentsatz des Versorgungsabschlag
		this.abschlag_Para_14_BeamtVG_in_Prozent = this.pension.calculateAbschlag_Para_14_BeamtVG();
		//Betrag des Versorgungsabschlag
		this.versorgungsabschlag = erdientesRuhegehaltVorVersorgungsabschlag * abschlag_Para_14_BeamtVG_in_Prozent / 100;
		this.erdientesRuhegehaltNachVersorgungsabschlag =  erdientesRuhegehaltVorVersorgungsabschlag - versorgungsabschlag;
	
		
		if(versorgungsabschlag > 0) {
			appendStyleRange(new String("Versorgungsabschlag gem. (§14 BeamtVG)"), SWT.BOLD, 12, 1);
			appendText("Ruhegehalt"  + addTabs(6)  +   printFloat(erdientesRuhegehaltVorVersorgungsabschlag));
			appendLine(1);
			appendText("- " + "Versorgungsabschlag " + printFloat(abschlag_Para_14_BeamtVG_in_Prozent) + " v.H. " +  addTabs(4)  + 
					printFloat(versorgungsabschlag));
			appendLine(1);
			appendText("= " + "Erdientes Ruhegehalt"  + addTabs(5)  +   printFloat(erdientesRuhegehaltNachVersorgungsabschlag));
			appendLine(2);
		}
		
		appendStyleRange(new String("Erdienter Versorgungsbezug"), SWT.BOLD, 12, 1);
		appendText("  " + "Erdientes Ruhegehalt"  + addTabs(5)  +   printFloat(erdientesRuhegehaltNachVersorgungsabschlag));
		appendLine(1);
		appendText("= " + "Erdienter Versorgungsbezug"  + addTabs(4)  +   printFloat(erdientesRuhegehaltNachVersorgungsabschlag));
		appendLine(2);
		
		appendStyleRange(new String("Berechnung Mindesversorgung"), SWT.BOLD, 12, 1);
		float mindestversorgung = this.pension.calculateMindestversorgung();
		appendText("  " + "Amtsabhängige Mindestversorgung"  + addTabs(4)  +   printFloat(this.pension.getAmtsabhaengigeMindestversorgung()));
		appendLine(1);
		appendText("  " + "Amtsunabhängige Mindestversorgung"  + addTabs(3)  +   printFloat(this.pension.getAmtsunabhaengigeMindestversorgung()));
		appendLine(2);
		appendText("  " + "Maximum (amtsabh./-unabh. Mindestversorgung)"  + addTabs(3)  +   printFloat(mindestversorgung));
		appendLine(1);
		appendText("= " + "Mindestversorgung"  + addTabs(5)  +   printFloat(mindestversorgung));
		appendLine(2);
		
		appendStyleRange(new String("Ergebnis Berechnung Versorgungsbezug"), SWT.BOLD, 12, 1);
		float versorgungsbezug = 0.0f;
		if(erdientesRuhegehaltNachVersorgungsabschlag > mindestversorgung) {
			versorgungsbezug = erdientesRuhegehaltNachVersorgungsabschlag;
			appendText("  " + "Erdientes Ruhegehalt"  + addTabs(5)  +   printFloat(erdientesRuhegehaltNachVersorgungsabschlag));
		}
		else {
			versorgungsbezug = mindestversorgung;
			appendText("  " + "Mindestversorgung"  + addTabs(5)  +   printFloat(mindestversorgung));
		}
		appendLine(1);
		appendText("= " + "Versorgungsbezug"  + addTabs(5)  +   printFloat(versorgungsbezug));
		appendLine(2);
		
		
		appendStyleRange(new String("Abzug für Pflegeleistung nach §50f BeamtVG"), SWT.BOLD, 12, 1);
		appendText("  " + "Bemessungsfgrundlage Abzug Pflegeleistung "  + addTabs(3)  +   printFloat(versorgungsbezug));
		appendLine(1);
		appendText("x " + String.format("%.4f", Constants.FAKTOR_PARA_50f_BeamtVG) + " v.H." + addTabs(6)  +   printFloat(versorgungsbezug));
		appendLine(1);
		float abzugPflegeleistung = (versorgungsbezug/100) * Constants.FAKTOR_PARA_50f_BeamtVG;
		appendText("= " + "Abzug für Pflegeleistung § 50f BeamtVG"  + addTabs(3)  +  printFloat(abzugPflegeleistung));
		appendLine(2);
		
		appendStyleRange(new String("Zahlbetrag (brutto)"), SWT.BOLD, 12, 1);
		appendText("  " + "Versorgungsbezug ohne Anrechnungen"  + addTabs(3)  +   printFloat(versorgungsbezug));
		appendLine(1);
		appendText("- " + "Abzug für Pflegeleistung § 50f BeamtVG"  + addTabs(3)  +   printFloat(abzugPflegeleistung));
		appendLine(2);
		float zahlbetragBrutto = versorgungsbezug - abzugPflegeleistung;
		appendStyleRange(new String("Zahlbetrag Versorgungsbezug (brutto)"  + addTabs(2) + addWhitespace(9) + String.format("%.2f", zahlbetragBrutto)), SWT.BOLD, 12, 1);
		appendLine(6);
		
	
	}
	
	
	
	@Override
	public void buildRuhegehaltsfaehigeZeitenSection() {
		
		appendStyleRange(new String("Zusammenstellung der ruhegehaltsfähigen Dienstzeiten"), SWT.BOLD, 14, 1);
		appendLine(1);
		appendStyleRange(new String("vom" + addTabs(1) +  "bis"  + addTabs(1) + "Faktor" + 	addTabs(1) + "ruheg. Tage" + addTabs(3) + "Rechtsgrundlage/Erläuterung"), SWT.BOLD, 12, 1);
		
		appendText("-----------------------------------------------------------------------------------------------------------------------------------------\n");
		appendLine(1);
		for (Iterator<ITimePeriod> iterator = pension.getTimePeriods().iterator(); iterator.hasNext();) {
			ITimePeriod timePeriod = (ITimePeriod) iterator.next();
			String rgfTage = Integer.toString(timePeriod.getRuhegehaltsfaehigeTage()) + addWhitespace(calculateWhitspaces(timePeriod.getRuhegehaltsfaehigeTage()))+ StringUtils.leftPad(getTimePeriodAsString(timePeriod),22);
			int tabs = 2;
			
			//in der SWT Anzeige nehmen Spaces weniger Platz ein als normale Zeichen, deshalb ist das hier erforderlich 
			//damit das in der Anzeige richtig formatiert wird
			if(rgfTage.length() > 29 && StringUtils.countMatches(rgfTage, " ") < 12) {
				tabs = 1;
			}
			
			appendText(PrintUtils.formatValue(timePeriod.getStartDate()) + addTabs(1) +  PrintUtils.formatValue(timePeriod.getEndDate()) + addTabs(1) +  
					String.format("%.2f", timePeriod.getFactor())  + addTabs(1) + rgfTage  + addTabs(tabs) + timePeriod.getZeitart() );
			appendLine(1);
		}
		appendLine(1);
		appendText("-----------------------------------------------------------------------------------------------------------------------------------------\n");
		appendLine(2);

	}




	@Override
	public void buildBerechnungRuhegehaltssatzSection() {
		float ruhegehaltssatz = pension.calculateRuhegehaltssatz_Para_14_1_Neue_Fassung(); 
		
		appendStyleRange(new String("Berechnung des Ruhegehaltssatzes nach § 14 Abs. 1 BeamtVG neue Fassung"), SWT.BOLD, 14, 1);
		appendLine(1);
		TimePeriodDetails rgfDienstzeiten = pension.calculateRuhegehaltsfaehigeDienstzeiten_Para_14();
		
		appendText(addTabs(1) + "insgesamt: " + addTabs(2) + rgfDienstzeiten.getYears() + " Jahre / " + rgfDienstzeiten.getDays() + " Tage");
		appendLine(1);
		appendText(addTabs(1) + "oder: "  + addTabs(2) +  String.format("%.2f", ((float)rgfDienstzeiten.getAmountOfDays())/365) + " ruhegehaltsfähige Dienstjahre");
		appendLine(1);
		
		appendText(addTabs(1) + "Ruhegehaltssatz:  " + addTabs(1) + String.format("%.2f", ((float)rgfDienstzeiten.getAmountOfDays())/365) + " Jahre x " + String.format("%.4f", Constants.VERSORGUNGSSATZ_JAHR_PARA_14_Abs_1_BeamtVG) +" v.H. = "+
							String.format("%.2f", ruhegehaltssatz) + " v.H.");

		appendLine(2);
		appendStyleRange(new String("Das ergibt gem. §14 Abs. 1 BeamtVG neue Fassung einen Ruhegehaltssatz von: " + 
				String.format("%.2f", ruhegehaltssatz)) + " v. H.", SWT.BOLD, 14, 1);
		
		appendLine(2);
	}
	
	
	
	@Override
	public boolean buildVergleichsberechnungPara85Section() {
		TimePeriodDetails timePeriodBeforeDate19920101 = TimePeriodCalculator.getTimePeriodBeforeDate19920101(pension);
		
		//wenn weniger als 10 jahre vor dem 01.01.1992 vorhanden sind, mach die Vergleichsberechnug sowieso keinen Sinn
		if (timePeriodBeforeDate19920101.getYears() < 10) {
			return false;
		}
		
		appendStyleRange(new String("Berechnung des Ruhegehaltssatzes nach § 85 Abs. 1 BeamtVG"), SWT.BOLD, 14, 1);
		appendLine(1);
		appendStyleRange(addWhitespace(5) + new String("Ruhegehaltfähige Zeit: "), SWT.BOLD, 10, 1);
		appendText(addTabs(1)  + "bis 31.12.1991: " + addTabs(1) + timePeriodBeforeDate19920101.getYears() + " Jahre / " + timePeriodBeforeDate19920101.getDays() + " Tage");
		appendLine(1);
		TimePeriodDetails timePeriodAfterDate199112231 = TimePeriodCalculator.getTimePeriodAfterDate199112231(pension);
		appendText(addTabs(1) + "ab 01.01.1992: " + addTabs(1) + timePeriodAfterDate199112231.getYears() + " Jahre / " + timePeriodAfterDate199112231.getDays()  + " Tage");
		appendLine(2);
		appendStyleRange(addWhitespace(5) + new String("Ruhegehaltssatz: "), SWT.BOLD, 10, 1);
		this.ruhegehaltssatz_Para_85_1_bis_1991 = vergleichsberechnungService.calculateRuhegehaltssatz_Bis_1991(this.pension);
		this.ruhegehaltssatz_Para_85_1_ab_1992 = vergleichsberechnungService.calculateRuhegehaltssatz_Ab_1992(this.pension);
		appendText(addTabs(1) + "Besitzstand zum 31.12.1991: " + addTabs(1) +  String.format("%.2f", ruhegehaltssatz_Para_85_1_bis_1991) + " v.H." +
				addTabs(1) + "(Die ersten 10 Jahre = 35 v.H , die Jahre 11-20 zusätzlich jeweils 2.0 v.H)") ;
		appendLine(1);
		appendText(addTabs(1) + "Erhöhung ab 01.01.1992: " + addTabs(2) +  String.format("%.2f", ruhegehaltssatz_Para_85_1_ab_1992) + " v.H." + 
					addTabs(1) + "(" + String.format("%.2f", ruhegehaltssatz_Para_85_1_ab_1992) + " Jahre * 1.0 v.H)"  );
		appendLine(1);
		appendText(addTabs(1) + "zusammen: " + addTabs(3) + String.format("%.2f", ruhegehaltssatz_Para_85_1_bis_1991 + ruhegehaltssatz_Para_85_1_ab_1992) + " v.H.");
		appendLine(2);
		
		
		appendText(addTabs(1) + "Nach § 69e(4), § 85(11) BeamtVG Kürzung um " + String.format("%.4f", Constants.KORREKTURFAKTOR_PARA_69e_Abs_4_BeamtVG) + ": " + 
				String.format("%.2f", (ruhegehaltssatz_Para_85_1_bis_1991 + ruhegehaltssatz_Para_85_1_ab_1992) * Constants.KORREKTURFAKTOR_PARA_69e_Abs_4_BeamtVG) + " v.H.");
		
		appendLine(2);
		/*
		Ruhegehaltfähige Zeit:  
			bis 31.12.1991: 10 Jahre 122.00 Tage  
			ab  01.01.1992: 26 Jahre 212.00 Tage 
			Ruhegehaltssatz:  
				Besitzstand zum 31.12.1991: 35.00 %  
				Erhöhung ab 01.01.1992: 26.58 x 1 % = 26.58 %  
				zusammen: 61.58 %  
				Nach § 69e(4), § 85(11) BeamtVG Kürzung um 0.95667: 58.91 %
		 */
		
		this.ruhegehaltssatz_Para_85_1 = pension.calculateRuhegehaltssatz_Para_85_Vergleichsberechnung(); 
		appendStyleRange(new String("Das ergibt gem. §85 Abs. 1 BeamtVG einen Ruhegehaltssatz von: " + 
				String.format("%.2f", ruhegehaltssatz_Para_85_1)) + " v. H.", SWT.BOLD, 14, 1);
		appendLine(2);
		
		return true;
	}
	
	
	
	@Override
	public void buildVergleichAbschlussSection() {
		appendStyleRange(new String("Festsetzung des Ruhegehaltssatzes nach dem BeamtVG"), SWT.BOLD, 14, 1);
		appendLine(1);
		if(this.ruhegehaltssatz_Para_14_1 > this.ruhegehaltssatz_Para_85_1) {
			appendText("Da die Berechnung nach § 85 Abs. 1 BeamtVG zu keinem günstigeren Ergebnis führt, \nerübrigt sich die Berechnung der Begrenzung nach § 85 Abs. 4 S. 2 BeamtVG)");
			appendLine(2);
			appendStyleRange(new String("Der Ruhegehaltssatz wird nach neuem Recht berechnet und beträgt: " + 
					String.format("%.2f", ruhegehaltssatz_Para_14_1)) + " v. H.", SWT.BOLD, 14, 1);
		}
		else {
			appendText("Berechnung der Begrenzung nach §85 Abs. 4 S.2 BeamtVG");
			appendLine(2);
			appendStyleRange(new String("Der Ruhegehaltssatz wird nach §85 Abs. 1 BeamtVG (Besitzstand) berechnet und beträgt: " + 
					String.format("%.2f", ruhegehaltssatz_Para_85_1)) + " v. H.", SWT.BOLD, 14, 1);
		}
		
		appendLine(2);
	}
	
	

	@Override
	public void buildVersorgungsabschlagSection() {
		
		
		TimePeriodDetails tpd = ServiceRegistry.getInstance().getRuhegehaltsabschlagService().calculateTimePeriodForVersorgungsabschlag(this.pension);
		
		appendStyleRange(new String("Berechnung des Versorgungsabschlag nach § 14 Abs. 3 BeamtVG"), SWT.BOLD, 14, 1);
		appendLine(1);
		appendText("Ruhestand beantragt zum: " + addTabs(2) + this.pension.getPerson().getDateOfRetirementString());
		appendLine(1);
		appendText("Gesetzliche Regelaltersgrenze: " + addTabs(2) + this.pension.getPerson().getPrintableDateOfLegalRetirement());
		
		if(this.pension.getPerson().isSchwerbehindert()) {
			appendLine(1);
			appendText("Antragsaltersgrenze (schwerbehindert): " + addTabs(2) + this.pension.getPerson().getPrintableDateOfRequestRetirement());
			appendLine(1);
			appendText("Abschlagsfrei ab dem: " + addTabs(3) + this.pension.getPerson().getPrintableDateOfLegalRetirementForSchwerbehinderung());
		}
		else {
			appendLine(1);
			appendText("Gesetzliche Antragsaltersgrenze: " + addTabs(2) + this.pension.getPerson().getPrintableDateOfRequestRetirement());
		}
		appendLine(1);
		if(this.pension.getPerson().isBesondereAltersgrenze()) {
			appendText("Besondere Regelaltersgrenze: " + addTabs(2) + this.pension.getPerson().getPrintableDateOfSpecialLegalRetirement());
		}
		appendLine(1);
		appendStyleRange(new String("vom" + addTabs(1) +  "bis"  + addTabs(1) + "Jahre"  + addTabs(1) + "Tage"), SWT.BOLD, 12, 1);
		
		
		appendText(PrintUtils.formatValue(tpd.getStartDate()) + addTabs(1) +  PrintUtils.formatValue(tpd.getEndDate()) + addTabs(1) +  
				 + tpd.getYears() + addTabs(1)  +  tpd.getDays() );
		appendLine(2);
		appendText("Das Ruhegehalt vermindert sich um " + String.format("%.1f", Constants.MINDERUNG_JAHR_PARA_14_Abs_3_BeamtVG)  + " Prozent für jedes Jahr.");
		appendLine(1);
		
		float maxAbschlag = this.pension.calculateMaxAbschlag_Para_14_BeamtVG();
		String schwerbehinderungText = this.pension.getPerson().isSchwerbehindert() ? " + Schwerbehinderung" : new String("");
		
		appendText("Die maximale Minderung beträgt insgesamt " + String.format("%.1f", maxAbschlag)  + " Prozent. ( " + 
				this.pension.getPensionTyp() +  " " + schwerbehinderungText + ")"); 
		
		appendLine(2);
		
		if(this.abschlag_Para_14_BeamtVG_in_Prozent < maxAbschlag) {
			appendText("Der errechnete Versorgungsabschlag für " + tpd.getYears() +" Jahre und " + tpd.getDays() + " Tage beträgt: " + String.format("%.2f", this.abschlag_Para_14_BeamtVG_in_Prozent) + " v.H.");
		}
		else {
			appendText("Der errechnete Versorgungsabschlag wird durch den maximal zulässigen Wert in Höhe von " +  String.format("%.2f", maxAbschlag) + " v.H. ersetzt.");
			this.abschlag_Para_14_BeamtVG_in_Prozent = maxAbschlag;
		}
		appendLine(2);
		appendStyleRange(new String("Das ergibt gem. §14 Abs. 3 BeamtVG einen Versorgungsabschlag von: " + 
				String.format("%.2f", this.abschlag_Para_14_BeamtVG_in_Prozent)) + " v. H.", SWT.BOLD, 14, 1);
		appendLine(6);
	}
	
	

	@Override
	public void buildZurechnungDDUSection() {
		if(!(this.pension instanceof Dienstunfaehigkeit)) {
			return;
		}
		
		Dienstunfaehigkeit dduPension = (Dienstunfaehigkeit)this.pension;
		
		appendStyleRange(new String("Berechnung des Zurechnungszeit bei Dienstunfähigkeit nach § 13 BeamtVG"), SWT.BOLD, 14, 1);
		appendLine(1);
		appendText("Geburtstag: " + addTabs(5) + this.pension.getPerson().getDateOfBirthString());
		appendLine(1);
		appendText("Ruhestand beantragt zum: " + addTabs(3) + this.pension.getPerson().getDateOfRetirementString());
		
		TimePeriodDetails zurechnungszeit = dduPension.calculateZurechnungzeit_Para_13_BeamtVG();
		appendLine(1);
		appendText("Ablauf des Monats der Vollendung des 60 Lebensjahres: " + addTabs(1) + PrintUtils.formatValue(dduPension.getZurechungszeitEndDate()));
		appendLine(2);
		appendText("Die Zeit vom Beginn des Ruhestandes bis zum Ablauf des Monats der Vollendung des sechzigsten Lebensjahres" + "\n"
				+ "wird, für die Berechnung des Ruhegehalts der ruhegehaltfähigen Dienstzeit zu zwei Dritteln hinzugerechnet," + "\n"
				+ "soweit diese nicht nach anderen Vorschriften als ruhegehaltfähig berücksichtigt wird");
		appendLine(2);
		
		appendStyleRange(new String("Das ergibt gem. §13 BeamtVG eine Zurechnungszeit von " + 
				zurechnungszeit.getYears() + " Jahren und " + zurechnungszeit.getDays() + " Tagen"), SWT.BOLD, 14, 1);
		
		appendLine(6);  
	}

	
	@Override
	public void buildKinderziehungszuschlagSection() {
		KindererziehungszeitenZuschlag kindererziehungsZuschlag = this.pension.getKindererziehungsZuschlag();
		float kez = kindererziehungsZuschlag.calculateKindererziehungszuschlag();
		float rentenWertWest = this.pension.getBesoldungstabelle().getAktuellenRentenwertWestForKindererziehungszuschlag();
		float rentenWertOst = this.pension.getBesoldungstabelle().getAktuellenRentenwertOstForKindererziehungszuschlag();
		
		appendLine(4);
		
		StringBuffer sb = new StringBuffer();
		sb.append("Das um den Kindererziehungszuschlag erhöhte Ruhegehalt darf nicht höher sein als das Ruhegehalt, das sich unter Berücksichtigung ");
		sb.append("des Höchstruhegehaltssatzes und der ruhegehaltfähigen Dienstbezüge aus der Endstufe der Besoldungsgruppe, aus der sich das Ruhegehalt berechnet, ergeben würde.");
	
		
		appendStyleRange(new String("Berechnung des ungekürzten Zuschlag für Kindererziehungszeiten nach § 50 BeamtVG"), SWT.BOLD, 14, 1);
		appendLine(1);
		appendStyleRange(new String("Es wird hier lediglich der ungekürzte Kindererziehungszuschlag berechnet. "), SWT.BOLD, 10, 1);
		appendStyleRange(new String("Teilzeitbeschäftigung im Beamtenverhältnis während der Kindererziehungszeiten sowie Anwartschaften aus der gesetzlichen Rentenversicherung können zu Abschläge beim Kinderziehungszuschlag führen."), SWT.BOLD, 10, 1);
		appendStyleRange(sb.toString(), SWT.BOLD, 10, 1);
		appendLine(2);
		appendStyleRange(new String("Berechnungsparameter:"), SWT.BOLD, 10, 1);
		appendLine(1);
		appendText(addTabs(1) + "Aktueller Rentenwert (West): " + addTabs(2) + String.format("%.2f", rentenWertWest) + " €");
		appendLine(1);
		appendText(addTabs(1) + "Aktueller Rentenwert (Ost): " + addTabs(2) + String.format("%.2f", rentenWertOst) + " €");
		appendLine(1);
		appendText(addTabs(1) + "Mtl. Entgeltpunkte gem § 70 Abs. 2 SGB 6: " + addTabs(1) +  String.format("%.4f", Constants.KINDERERZIEHUNGSZUSCHLAG_ENTGELTPUNKTE_MONAT_PARA_70_Abs_2_SGB_6 ) + " v.H.");
		appendLine(2);
		appendText(addTabs(1) + "Kindererziehungszuschlag/Kind = <Anzahl zulageber. Monate> x <aktueller Rentenwert> x <mtl. Entgeltpunkte gem. §70 (2) SGB 6>");
		appendLine(3);
		 
		if (!kindererziehungsZuschlag.getKindererziehungszeitenVor1992().isEmpty()) {
			appendStyleRange(new String("Zusammenstellung des Kindererziehungszuschlag für vor dem 01.01.1992 geborene Kinder"), SWT.BOLD, 10, 2);
			
			appendText(new String("Kind" + addTabs(1) +  addWhitespace(10) + "Geb-Datum"  + addTabs(1) + "von" + addTabs(1) + "bis" + addTabs(1) + "Monate" + addTabs(1) + "Kindererz.-Zuschlag (mtl.)"));
			appendLine(1);
			appendText("------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			appendLine(1);
			for (Iterator<Para_50_Kindererziehungszeit> iterator = kindererziehungsZuschlag.getKindererziehungszeitenVor1992().iterator(); iterator.hasNext();) {
				Para_50_Kindererziehungszeit childKez = (Para_50_Kindererziehungszeit) iterator.next();
				
				float zulageChild = childKez.getZulageMonths() * Constants.KINDERERZIEHUNGSZUSCHLAG_ENTGELTPUNKTE_MONAT_PARA_70_Abs_2_SGB_6 * rentenWertWest;
				
				appendText(childKez.getChildName() + addTabs(1) +  addWhitespace(10) + PrintUtils.formatValue(childKez.getBirthDate()) + addTabs(1) +  
						PrintUtils.formatValue(childKez.getStartDate())  + addTabs(1) +  PrintUtils.formatValue(childKez.getEndDate()) + 
						addTabs(1) + childKez.getZulageMonths() + addTabs(1) +  String.format("%.2f", zulageChild));
				
				appendLine(1);
			}
			appendLine(1);
			appendText("------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			appendLine(2);
		}
		
		if (!kindererziehungsZuschlag.getKindererziehungszeitenNach1991().isEmpty()) {
			appendStyleRange(new String("Zusammenstellung des Kindererziehungszuschlag für nach dem 31.12.1991 geborene Kinder"), SWT.BOLD, 10, 2);
			
			appendText(new String("Kind" + addTabs(1) +  addWhitespace(10) + "Geb-Datum"  + addTabs(1) + "von" + addTabs(1) + "bis" + addTabs(1) + "Monate" + addTabs(1) + "Kindererz.-Zuschlag (mtl.)"));
			appendLine(1);
			appendText("------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			appendLine(1);
			for (Iterator<Para_50_Kindererziehungszeit> iterator = kindererziehungsZuschlag.getKindererziehungszeitenNach1991().iterator(); iterator.hasNext();) {
				Para_50_Kindererziehungszeit childKez = (Para_50_Kindererziehungszeit) iterator.next();
				
				float zulageChild = childKez.getZulageMonths() * Constants.KINDERERZIEHUNGSZUSCHLAG_ENTGELTPUNKTE_MONAT_PARA_70_Abs_2_SGB_6 * rentenWertWest;
				
				appendText(childKez.getChildName() + addTabs(1) +  addWhitespace(10) + PrintUtils.formatValue(childKez.getBirthDate()) + addTabs(1) +  
						PrintUtils.formatValue(childKez.getStartDate())  + addTabs(1) +  PrintUtils.formatValue(childKez.getEndDate()) + 
						addTabs(1) + childKez.getZulageMonths() + addTabs(1) +  String.format("%.2f", zulageChild));
				
				appendLine(1);
			}
			appendLine(1);
			appendText("------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			appendLine(2);
		}
		
		
		appendStyleRange(new String("Der ungekürzte Kindererziehungszuschlag beträgt monatlich insgesamt: " + String.format("%.2f", kez) + " € "	), SWT.BOLD, 14, 1);
	}



	@Override
	public void pageBreak() {
		// no page break required in SWT-Display - only Printer relevant
	}
	

}
