package de.protin.support.pr.gui.berechnung;

import org.eclipse.swt.graphics.Image;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.gui.RuhegehaltBerechnungPart;

public interface IVersorgungsauskunftBerechnung {

	IPension getPension();

	void setPension(IPension pension);
	
	Image getImage(); 
	
	void buildHeader();

	void buildPensionSection();

	void buildBerechnungSection();

	void buildRuhegehaltsfaehigeZeitenSection();

	void buildBerechnungRuhegehaltssatzSection();

	boolean buildVergleichsberechnungPara85Section();

	void buildVergleichAbschlussSection();

	void buildVersorgungsabschlagSection();

	void buildZurechnungDDUSection();

	void buildKinderziehungszuschlagSection();
	
	void pageBreak();

}