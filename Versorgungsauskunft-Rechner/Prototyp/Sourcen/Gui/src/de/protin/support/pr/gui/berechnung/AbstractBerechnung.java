package de.protin.support.pr.gui.berechnung;

import java.util.Date;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.service.IRuhegehaltsberechnungAltesRechtService;
import de.protin.support.pr.domain.service.impl.ServiceRegistry;
import de.protin.support.pr.domain.timeperiod.Para_13_Zurechnungszeit_DDU;
import de.protin.support.pr.domain.utils.TimePeriodCalculator;
import de.protin.support.pr.domain.utils.TimePeriodDetails;
import de.protin.support.pr.gui.RuhegehaltBerechnungPart;
import de.protin.support.swt.custom.StyleRange;
import de.protin.support.swt.custom.StyledText;


public class AbstractBerechnung  {
	
	protected RuhegehaltBerechnungPart part;
	protected IPension pension;
	protected StyledText styledText;
	protected Image image;
	//private Display display;
	
	protected float ruheghaltsfaehigerDienstbezug;
	protected float korrigierterRuhegehaltsbezug;
	protected float ruhegehaltssatz_Para_14_1;
	
	protected float ruhegehaltssatz_Para_85_1;
	protected float ruhegehaltssatz_Para_85_1_bis_1991;
	protected float ruhegehaltssatz_Para_85_1_ab_1992;
	
	protected float erdientesRuhegehaltVorVersorgungsabschlag;
	protected float erdientesRuhegehaltNachVersorgungsabschlag;
	//Prozentsatz des Versorgungsabschlag
	protected float abschlag_Para_14_BeamtVG_in_Prozent;
	//Betrag des Versorgungsabschlag
	protected float versorgungsabschlag;
	//Datum bei Ablauf des Monats der Vollendung des sechzigsten Lebensjahres
	protected Date dduZurechnungDatum;
	
	protected IRuhegehaltsberechnungAltesRechtService vergleichsberechnungService;
	
	public AbstractBerechnung(RuhegehaltBerechnungPart part) {
		this.part = part;
		this.styledText = part.getStyledText();
		this.vergleichsberechnungService = ServiceRegistry.getInstance().getRuhegehaltsberechnungAltesRechtService();
	}
	

	public Image getImage() {
		return image;
	}

	
	protected void appendStyleRange(String text, int fontStyle, int fontSize, int newlines) {
		styledText.append(text);
		StyleRange style = new StyleRange();
		style.start = styledText.getOffsetAtLine(styledText.getLineCount()-1);
		style.length = text.length();
		style.fontStyle = fontStyle;
		
		FontData[] fD = styledText.getFont().getFontData();
		fD[0].setHeight(fontSize);
		style.font = new Font(styledText.getDisplay(),fD[0]);
		
		styledText.setStyleRange(style);
		
		appendLine(newlines);
	}
	
	
	protected void appendColoredStyleRange(String text, int fontStyle, int color, int fontSize, int newlines) {
		styledText.append(text);
		StyleRange style = new StyleRange();
		style.start = styledText.getOffsetAtLine(styledText.getLineCount()-1);
		style.length = text.length();
		style.fontStyle = fontStyle;
		style.foreground = styledText.getDisplay().getSystemColor(color);
		
		FontData[] fD = styledText.getFont().getFontData();
		fD[0].setHeight(fontSize);
		style.font = new Font(styledText.getDisplay(),fD[0]);
		
		styledText.setStyleRange(style);
		
		appendLine(newlines);
	}
	
	protected void appendText(String text) {
		styledText.append(text);
	}
	
	protected void appendLine(int newlines) {
		for(int i = 0; i < newlines; i++) {
			styledText.append("\n");
		}
	}
	
	
	protected String addTabs(int anz) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < anz; i++) {
			sb.append("\t");
		}
		return sb.toString();
	}

	protected String addWhitespace(int anz) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < anz; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}


	protected String printFloat(float number) {
		StringBuilder sb = new StringBuilder();
		String printString = String.format("%.2f", number);
		if(printString.length() < 8) {
		
			while (sb.length() < 8 - printString.length()) {
				sb.append("  ");
			}
			if(printString.length() < 4) {
				sb.append("      ");
			}
			else if(printString.length() < 5) {
				sb.append("    ");
			}
			else if(printString.length() < 6) {
				sb.append("  ");
			}
			else if(printString.length() < 7) {
				sb.append("  ");
			}
			sb.append(printString);
		}
		
		return sb.toString();
	}




	protected String getTimePeriodAsString(ITimePeriod timePeriod) {
		
		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		if(timePeriod instanceof Para_13_Zurechnungszeit_DDU) {
			Dienstunfaehigkeit dduPension = (Dienstunfaehigkeit)this.pension;
			TimePeriodDetails zurechnungszeit = dduPension.calculateZurechnungzeit_Para_13_BeamtVG();
			sb.append("(");
			sb.append("" + zurechnungszeit.getYears() + " Jahre");
			sb.append(" / ");
			sb.append("" + zurechnungszeit.getDays() + " Tage");
			sb.append(")");
		}
		else if(timePeriod.getRuhegehaltsfaehigeTage() > 0) {
			TimePeriodDetails tpd = TimePeriodCalculator.calculateYearsAndDaysForTimePeriod(timePeriod.getStartDate(), timePeriod.getEndDate());
			sb.append("(");
			sb.append("" + tpd.getYears() + " Jahre");
			sb.append(" / ");
			sb.append("" + tpd.getDays() + " Tage");
			sb.append(")");
		}
		else if(timePeriod.getRuhegehaltsfaehigeTage() == 0){
			sb.append("(");
			sb.append("0 Jahre");
			sb.append(" / ");
			sb.append("0 Tage");
			sb.append(")");
		}
		
		return sb.toString();
	}




	protected int calculateWhitspaces(int ruhegehaltsfaehigeTage) {
		if(ruhegehaltsfaehigeTage < 10)
			return 8;
		else if(ruhegehaltsfaehigeTage < 100)
			return 7;
		else if(ruhegehaltsfaehigeTage < 1000)
			return 4;
		else if(ruhegehaltsfaehigeTage < 10000)
			return 4;
		else if(ruhegehaltsfaehigeTage < 100000)
			return 3;
		else
			return 1;
	}


	public RuhegehaltBerechnungPart getPart() {
		return part;
	}


	public void setPart(RuhegehaltBerechnungPart part) {
		this.part = part;
	}


	public IPension getPension() {
		return pension;
	}


	public void setPension(IPension pension) {
		this.pension = pension;
	}


	

}
