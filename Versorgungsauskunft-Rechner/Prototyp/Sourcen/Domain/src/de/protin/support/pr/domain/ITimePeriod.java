package de.protin.support.pr.domain;

import java.util.Date;
import java.util.Set;

public interface ITimePeriod {

	//siehe auch http://www.beamtenversorgung.nrw.de/fsiframe.wrkexec
	public static final int BEAMTEN_DIENSTZEIT_RUHEGEHALTSFAEHIG_PARA_6 = 1;
	public static final int AUSBILDUNG_BEAMTER_WIDERRUF_PARA_6 = 2;
	public static final int BUNDESWEHR_ODER_ZIVILDIENST_PARA_8_9 = 3;
	public static final int ANGESTELLTENVERHAELTNIS_IM_OEFFENTLICHEN_DIENST_PARA_10 = 4;
	public static final int UOB_RUHEGEHALTSFAEHIG = 5; 
	public static final int KINDERERZIEHUNGUNGSZEITEN_PARA_50 = 6;  // wird in GUI eigenständig erfasst und zugerechnet
	public static final int ALTERSTEILZEIT_BLOCKMODELL = 7;
	public static final int ALTERSTEILZEIT_TEILZEITMODELL = 8;
	public static final int AUFBAUHILFE_NEUE_BUNDESLAENDER = 9;
	public static final int VERWENDUNG_IN_LAENDERN_MIT_GESUNDHEITSSCHAEDLICHEM_KLIMA_PARA_13 = 10;
	
	//siehe auch Prot-In Rechner
	public static final int SONSTIGE_ZEITEN_PARA_11 = 11;
	public static final int VORGESCHR_FACHSCHULZEITEN_PARA_12_1_1 = 12;
	public static final int VORGESCHR_HOCHSCHULSTUDIEN_ZEITEN_PARA_12_1_1 = 13;
	public static final int VORGESCHR_PRAKTISCHE_AUSBILDUNG_PARA_12_1_1 = 14;
	public static final int VORGESCHR_PRAKTISCHE_HAUPTBERUFLICHE_TAETIGKEIT_PARA_12_1_2 = 15;
	public static final int FOERDERLICHE_PRAKTISCHE_AUSBILDUNG_PARA_12_2 = 16;
	public static final int FOERDERLICHE_PRAKTISCHE_HAUPTBERUFLICHE_TAETIGKEIT_PARA_12_2 = 17;
	public static final int STUDIENZEIT = 18;
	
	
	public abstract String getZeitart(); 
	
	public void addComment(Comment comment);
	public boolean removeComment(Comment comment);
	public void resetComments();
	public Set<Comment> getComments();
	public void setComments(Set<Comment> comments);

	public int getType();
	public void setType(int type);

	public Date getStartDate();
	public void setStartDate(Date startDate);

	public Date getEndDate();
	public void setEndDate(Date endDate);

	public float getFactor();
	public void setFactor(float factor);
	
	public int getAnzahlTage();
	
	public int getRuhegehaltsfaehigeTage();
	public void setRuhegehaltsfaehigeTage(int anzTage);
	
	

}
