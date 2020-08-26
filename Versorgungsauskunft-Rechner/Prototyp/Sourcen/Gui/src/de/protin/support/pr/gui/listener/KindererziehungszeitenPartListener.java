package de.protin.support.pr.gui.listener;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Table;

import de.protin.support.pr.gui.KindererziehungszeitenPart;
import de.protin.support.pr.gui.PensionGUI;

public class KindererziehungszeitenPartListener extends AbstractPartListener {

	private KindererziehungszeitenPart kezPart;
	
	public KindererziehungszeitenPartListener(PensionGUI gui, KindererziehungszeitenPart part) {
		super(gui, part.getDocPart());
		this.kezPart = part;
		this.textResources = part.getTextResources();
		this.registerListener();
	}
	
	
	public void registerListener() {
		kezPart.getTableAfter1991().addFocusListener(this);
		kezPart.getTableBefore1992().addFocusListener(this);
	}


	
	public void focusGained(FocusEvent event) {
		Object source = event.getSource();
		if (source instanceof Table) {
			Table table = (Table)source;
			String tooltip = table.getToolTipText();
			
			if(tooltip == null) {
				return;
			}
			
			String hintText = this.textResources.getString("DEFAULT_TEXT");
			if(tooltip.contains("geboren nach dem 31.12.1991")) {
				hintText = this.textResources.getString("KEZ_NACH_1991");
			}
			else if(tooltip.contains("geboren vor dem 01.01.1992")) {
				hintText = this.textResources.getString("KEZ_VOR_1992");
				
			}
			
			this.docPart.setPartInfoText(hintText);
		}
	}


	
	public void focusLost(FocusEvent arg0) {
		this.docPart.clearInfoText();
		kezPart.setPartInfoText(this.textResources.getString("DEFAULT_TEXT"));
	}
		 
}
