package de.protin.support.pr.gui.text;

import java.util.Locale;
import java.util.ResourceBundle;

public class TextResources {

	
	public static ResourceBundle getKindererziehungszuschlagDocs() {
		ResourceBundle kezDocs = ResourceBundle.getBundle("de.protin.support.pr.gui.text.kindererziehungszuschlag", Locale.getDefault());
		return kezDocs;
	}
	
	public static ResourceBundle getGrunddatenDocs() {
		ResourceBundle kezDocs = ResourceBundle.getBundle("de.protin.support.pr.gui.text.grunddaten", Locale.getDefault());
		return kezDocs;
		
	}
	
	public static ResourceBundle getRuhegehaltsfaehigeZeitenDocs() {
		ResourceBundle kezDocs = ResourceBundle.getBundle("de.protin.support.pr.gui.text.rgfZeiten", Locale.getDefault());
		return kezDocs;
	}
	
	
}
