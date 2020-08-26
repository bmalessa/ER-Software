package de.protin.support.pr.gui;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;

import de.protin.support.pr.gui.listener.IPartListener;

public abstract class TabPart  {
	
	
	protected SashForm sashForm;
	protected DokumentionPart docPart;
	protected IPartListener partListener;
	protected ResourceBundle textResources;
	
	
	public TabPart(Composite parent) {
		
		sashForm = new SashForm(parent, SWT.HORIZONTAL);
		
		initPart(sashForm, SWT.BORDER);
		loadTextResources();
		initDocumentationPart(sashForm, SWT.BORDER);
		
		sashForm.setWeights(new int[] { 5, 2 });
		sashForm.setSashWidth(10);
		    
	}
	
	
	public abstract void initPart(Composite parent, int style);
	
	public abstract void loadTextResources();
	
	public void initDocumentationPart(Composite parent, int style) {
		docPart = new DokumentionPart(parent, style);
		String text = textResources.getString("DEFAULT_TEXT");
		docPart.setPartDefaultText(text);
	}

	
	public void setPartInfoText(String infotext) {
		docPart.clearInfoText();
		docPart.setPartInfoText(infotext);
	}
	
	
	public void setPartListener(IPartListener partListener) {
		this.partListener = partListener;
	}
	
	public IPartListener getPartListener() {
		return this.partListener;
	}
	
	
	public SashForm getSashForm() {
		return sashForm;
	}
	public DokumentionPart getDocPart() {
		return docPart;
	}


	public ResourceBundle getTextResources() {
		return textResources;
	}
	
	
	
	
	
}
