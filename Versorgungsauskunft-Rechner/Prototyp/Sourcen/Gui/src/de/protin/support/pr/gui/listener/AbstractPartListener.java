package de.protin.support.pr.gui.listener;

import java.util.ResourceBundle;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;

import de.protin.support.pr.gui.DokumentionPart;
import de.protin.support.pr.gui.PensionGUI;

public abstract class AbstractPartListener implements IPartListener, FocusListener {

	protected DokumentionPart docPart;
	protected PensionGUI gui;
	protected ResourceBundle textResources;
	
	public AbstractPartListener(PensionGUI gui, DokumentionPart docPart) {
		this.gui = gui;
		this.docPart = docPart;
	}
	
	@Override
	public abstract void focusGained(FocusEvent arg0);
	
	@Override
	public abstract void focusLost(FocusEvent arg0);
	
	@Override
	public abstract void registerListener();
	
}
