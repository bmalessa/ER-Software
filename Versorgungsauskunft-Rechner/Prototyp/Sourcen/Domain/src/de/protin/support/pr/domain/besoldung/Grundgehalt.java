package de.protin.support.pr.domain.besoldung;


public class Grundgehalt{
	
	private String laufbahnOrdnung;
	private int besoldungsGruppe;
	private int besoldungsStufe;
	
	public Grundgehalt(String ordnung, int gruppe, int stufe) {
		this.laufbahnOrdnung = ordnung;
		this.besoldungsGruppe = gruppe;
		this.besoldungsStufe = stufe;
	}

	public String getLaufbahnOrdnung() {
		return laufbahnOrdnung;
	}

	public int getBesoldungsGruppe() {
		return besoldungsGruppe;
	}

	public int getBesoldungsStufe() {
		return besoldungsStufe;
	}

	public String print() {
		return new String(laufbahnOrdnung + besoldungsGruppe + "    Stufe " + besoldungsStufe);
	}
	
	
	
	
	
	
}
