package de.protin.support.pr.domain;

import java.util.Map;

import de.protin.support.pr.domain.besoldung.Amtszulage;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;

public interface IBesoldungstabelle {
	
	/**
	 * 
	 * @param ordnung   A / B
	 * @param gruppe	A = 3-16, B = 1-11  
	 * @param stufe		A 1-8
	 * @return Betrag in €
	 */
	
	public abstract float getSalaryForGrundgehalt(Grundgehalt grundgehalt);
	
	public abstract float getSalaryForFamiliezuschlag(Familienzuschlag familienzuschlag);
	
	public abstract Amtszulage[] getAmtszulagenForBesoldungsgruppe(String besoldunggruppe);

	public abstract float getSalaryForAmtsunabhängigeMindestversorgung();
	
	public abstract float getAktuellenRentenwertWestForKindererziehungszuschlag();
	
	public abstract float getAktuellenRentenwertOstForKindererziehungszuschlag();
	
	public abstract float getMaxAbzugPflegeleistung();
	
	public Map<Integer, float[]> getBesoldungsOrdnungA();
	
	public Map<Integer, Float> getBesoldungsOrdnungB();
	
	public Map<String, Amtszulage[]> getAmtszulagen();

	public abstract String print();
	
}
