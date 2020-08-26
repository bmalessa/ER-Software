package de.protin.support.pr.gui.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.timeperiod.AufbauhilfeOst;
import de.protin.support.pr.domain.timeperiod.Para_10_VordienstzeitenAlsArbeitnehmerImOeffentlichenDienst;
import de.protin.support.pr.domain.timeperiod.Para_11_SonstigeZeiten;
import de.protin.support.pr.domain.timeperiod.Para_12_FoerderlichePraktischeAusbildung;
import de.protin.support.pr.domain.timeperiod.Para_12_FoerderlichePraktischeHauptberuflicheTaetigkeit;
import de.protin.support.pr.domain.timeperiod.Para_12_VorgeschriebeneFachschulzeiten;
import de.protin.support.pr.domain.timeperiod.Para_12_VorgeschriebeneHochschulstudienzeiten;
import de.protin.support.pr.domain.timeperiod.Para_12_VorgeschriebenePraktischeAusbildung;
import de.protin.support.pr.domain.timeperiod.Para_12_VorgeschriebenePraktischeHauptberuflicheTaetigkeit;
import de.protin.support.pr.domain.timeperiod.Para_13_VerwendungInLaendernMitGesundheitsschaedlichenKlima;
import de.protin.support.pr.domain.timeperiod.Para_6_AltersteilzeitBlockmodel;
import de.protin.support.pr.domain.timeperiod.Para_6_AltersteilzeitTeilzeitmodel;
import de.protin.support.pr.domain.timeperiod.Para_6_AusbildungBeamterAufWiderruf;
import de.protin.support.pr.domain.timeperiod.Para_6_Beamterdienstzeit;
import de.protin.support.pr.domain.timeperiod.Para_8_9_WehrdienstOderZivildienst;
import de.protin.support.pr.domain.timeperiod.UOB_Ruhegehaltsfaehig;
import de.protin.support.pr.gui.PensionGUI;
import de.protin.support.pr.gui.ZeitenPart;
import de.protin.support.pr.gui.timeperiod.TimePeriodWrapper;

public class ZeitenPartListener extends AbstractPartListener {
	
	private ZeitenPart zeitenPart;
	
	public ZeitenPartListener(PensionGUI gui, ZeitenPart part) {
		super(gui, part.getDocPart());
		this.zeitenPart = part;
		this.textResources = part.getTextResources();
		this.registerListener();
	}
	
	
	public void registerListener() {
		zeitenPart.getTableViewer().getTable().addFocusListener(this);
		
		zeitenPart.getTableViewer().getTable().addListener(SWT.Selection, new Listener() {
			    public void handleEvent(Event e) {
			    	int selectionIndex = zeitenPart.getTableViewer().getTable().getSelectionIndex();
			    	TableItem tableItem = zeitenPart.getTableViewer().getTable().getItem(selectionIndex);
			    	TimePeriodWrapper tableData = (TimePeriodWrapper)tableItem.getData();
			    	int type = tableData.getType();
			   		String rgfZeitTyp = tableData.getLabel();
			   		if(type > 0 && type < 20) {
			   			StringBuffer sb = new StringBuffer();
			   			sb.append(rgfZeitTyp + "\n\n");
			   			sb.append(getHintText(type));
			   			docPart.setPartInfoText(sb.toString());
			   		}
			   	}
				
		  });
	}
  



	@Override
	public void focusGained(FocusEvent event) {
		Object source = event.getSource();
		if (source instanceof Table) {
			Table table = (Table)source;
			String tooltip = table.getToolTipText();
			
			if(tooltip == null) {
				return;
			}
			
			String hintText = this.textResources.getString("DEFAULT_TEXT");
			this.docPart.setPartInfoText(hintText);
		}
		
	}


	@Override
	public void focusLost(FocusEvent arg0) {
		this.docPart.clearInfoText();
		String hintText = this.textResources.getString("HINT_TEXT");
		zeitenPart.setPartInfoText(this.textResources.getString("HINT_TEXT"));
	}

	
	
	protected String getHintText(int rgfZeitTyp) {
		String hintText = new String();
		switch(rgfZeitTyp) {
	    	case ITimePeriod.BEAMTEN_DIENSTZEIT_RUHEGEHALTSFAEHIG_PARA_6:
	    		hintText = this.textResources.getString("BEAMTVG_§_6");
	    		break;
	    	case ITimePeriod.AUSBILDUNG_BEAMTER_WIDERRUF_PARA_6:
	    		hintText = this.textResources.getString("BEAMTVG_§_6");
	    		break;
	    	case ITimePeriod.BUNDESWEHR_ODER_ZIVILDIENST_PARA_8_9:
	    		hintText = this.textResources.getString("BEAMTVG_§_8") + "\n\n\n" +  this.textResources.getString("BEAMTVG_§_9");
	    		break;
	    	case ITimePeriod.ANGESTELLTENVERHAELTNIS_IM_OEFFENTLICHEN_DIENST_PARA_10:
	    		hintText = this.textResources.getString("BEAMTVG_§_10");
	    		break;
	    	case ITimePeriod.UOB_RUHEGEHALTSFAEHIG:
	    		hintText = this.textResources.getString("UOB_RUHEGEHALTSFAEHIG");
	    		break;
	    	case ITimePeriod.ALTERSTEILZEIT_BLOCKMODELL:
	    		hintText = this.textResources.getString("ALTERSTEILZEIT");
	    		break;
	    	case ITimePeriod.ALTERSTEILZEIT_TEILZEITMODELL:
	    		hintText = this.textResources.getString("ALTERSTEILZEIT");
	    		break;
	    	case ITimePeriod.AUFBAUHILFE_NEUE_BUNDESLAENDER:
	    		hintText = this.textResources.getString("AUFBAUHILFE_OST");
	    		break;
	    	case ITimePeriod.VERWENDUNG_IN_LAENDERN_MIT_GESUNDHEITSSCHAEDLICHEM_KLIMA_PARA_13:
	    		hintText = this.textResources.getString("BEAMTVG_§_13");
	    		break;
	    	case ITimePeriod.SONSTIGE_ZEITEN_PARA_11:
	    		hintText = this.textResources.getString("BEAMTVG_§_11");
	    		break;
	    	case ITimePeriod.VORGESCHR_FACHSCHULZEITEN_PARA_12_1_1:
	    		hintText = this.textResources.getString("BEAMTVG_§_12");
	    		break;
	    	case ITimePeriod.VORGESCHR_HOCHSCHULSTUDIEN_ZEITEN_PARA_12_1_1:
	    		hintText = this.textResources.getString("BEAMTVG_§_12");
	    		break;
	    	case ITimePeriod.VORGESCHR_PRAKTISCHE_AUSBILDUNG_PARA_12_1_1:
	    		hintText = this.textResources.getString("BEAMTVG_§_12");
	    		break;
	    	case ITimePeriod.VORGESCHR_PRAKTISCHE_HAUPTBERUFLICHE_TAETIGKEIT_PARA_12_1_2:
	    		hintText = this.textResources.getString("BEAMTVG_§_12");
	    		break;
	    	case ITimePeriod.FOERDERLICHE_PRAKTISCHE_AUSBILDUNG_PARA_12_2:
	    		hintText = this.textResources.getString("BEAMTVG_§_12");
	    		break;
	    	case ITimePeriod.FOERDERLICHE_PRAKTISCHE_HAUPTBERUFLICHE_TAETIGKEIT_PARA_12_2:
	    		hintText = this.textResources.getString("BEAMTVG_§_12");
	    		break;
	    	
	    	default:
	    		break;
		}
  
		return hintText;
	}

}
