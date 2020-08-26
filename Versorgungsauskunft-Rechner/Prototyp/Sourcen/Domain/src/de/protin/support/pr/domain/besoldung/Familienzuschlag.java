package de.protin.support.pr.domain.besoldung;

public class Familienzuschlag {

	
	private int stufe;  // 1 oder 2
	private int anzahlKinder;	
	
	public Familienzuschlag(int familienstand) {
		this.stufe = familienstand;
	}
	
	public Familienzuschlag(int familienstand, int anzahlKinder) {
		this(familienstand);
		this.anzahlKinder = anzahlKinder;
	}

	public int getStufe() {
		return stufe;
	}

	public int getAnzahlKinder() {
		return anzahlKinder;
	}

	public String print() {
		String result = "";
		if(this.stufe == 0) {
			result = new String("Familienzuschlag (" + "ledig/geschieden" + "\t\t\t\t");
		}
		else if(this.stufe == 1) {
			result =  new String("Familienzuschlag (" + "verheiratet/verwitwert"+ ") :  " + "\t\t\t" + " " );
		}
		else if(this.stufe == 2) {
			result =  new String("Familienzuschlag (" + "Ehegatte im öffentl. Dient mit Familienzuschlag"+ "\t\t");
		}
		return result;
	}
	
}
