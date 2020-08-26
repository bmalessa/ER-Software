package de.protin.support.pr.domain.besoldung;
/**
 * Utility class for Amtszulage. Align Fussnote to salary.
 * 
 * @author Bernd
 *
 */
public class Amtszulage {
	
	private String besGroup;
	private float zulage;
	private String fussnote;
	
	
	public Amtszulage () {}
	
	public Amtszulage (String besGroup, float zulage, String fussnote) {
		this.besGroup =  besGroup;
		this.zulage = zulage;
		this.fussnote = fussnote;
	}

	public float getZulage() {
		return zulage;
	}

	public void setZulage(float zulage) {
		this.zulage = zulage;
	}

	public String getFussnote() {
		return fussnote;
	}

	public void setFussnote(String fussnote) {
		this.fussnote = fussnote;
	}

	public String getBesGroup() {
		return besGroup;
	}

	public void setBesGroup(String besGroup) {
		this.besGroup = besGroup;
	}
	
	
}