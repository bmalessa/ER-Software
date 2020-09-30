package de.protin.support.pr.domain.besoldung.tabelle;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

import de.protin.support.pr.domain.IBesoldungstabelle;
import de.protin.support.pr.domain.besoldung.Amtszulage;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.zuschlag.KindererziehungszeitenZuschlag;
import de.protin.support.pr.domain.service.IKindererziehungszeitenService;
import de.protin.support.pr.domain.service.impl.ServiceRegistry;
import de.protin.support.pr.domain.utils.Constants;

public abstract class AbstractBesoldungstabelle implements IBesoldungstabelle {
	
	protected Map<Integer, float[]> besoldungsordnungA;
	protected SortedMap<Integer, Float> besoldungsordnungB;
	protected SortedMap<String, Amtszulage[]> amtszulage;
	
	protected float famZuschlagStufe1;
	protected float famZuschlagStufe2;
	protected float famZuschlagZweitesKind;
	protected float famZuschlagWeitereKinder;
	protected float aktuellerRentenwertWest;
	protected float aktuellerRentenwertOst;
	protected float maxAbzugPflegeleistung;
	
	
	protected String titel;
	protected String subTitel;
	protected String quelle;
	protected Date validFrom;
	protected Date validTo;
	
	@Override
	public float getSalaryForGrundgehalt(Grundgehalt grundgehalt) {
		float result = 0.0f;

		String laufbahnOrdnung = grundgehalt.getLaufbahnOrdnung();
		if(laufbahnOrdnung == null || "A".equalsIgnoreCase(laufbahnOrdnung)) {
			float[] stufen = besoldungsordnungA.get(grundgehalt.getBesoldungsGruppe());
			int besoldungsStufe = grundgehalt.getBesoldungsStufe();
			result = stufen[--besoldungsStufe];
		}
		else if( "B".equalsIgnoreCase(laufbahnOrdnung)) {
			result =  besoldungsordnungB.get(grundgehalt.getBesoldungsGruppe());
		}
		return result;
	}


	@Override
	public Amtszulage[] getAmtszulagenForBesoldungsgruppe(String besoldungsgruppe) {
		Amtszulage result[] = {};
		result = amtszulage.get(besoldungsgruppe);
		return result;
	}

	
	
	/**
	 * Bei der Ermittlung der ruhegehaltfähigen Dienstbezüge bleiben z. B. unberücksichtigt:
		.... kinderbezogene Anteile des Familienzuschlags, ...
	 */
	@Override
	public float getSalaryForFamiliezuschlag(Familienzuschlag familienzuschlag) {
		if(familienzuschlag.getStufe() == 1 ) {
			return this.famZuschlagStufe1;
		}
		else if(familienzuschlag.getStufe() == 2 ) {
			return this.famZuschlagStufe1 / 2;
		}
		
		/*
		else if(familienzuschlag.getStufe() == 2) {
			if(familienzuschlag.getAnzahlKinder() < 2) {
				return this.famZuschlagStufe2;
			}
			else if(familienzuschlag.getAnzahlKinder() == 2) {
				return this.famZuschlagStufe2 + this.famZuschlagZweitesKind;
			}
			else if(familienzuschlag.getAnzahlKinder() > 2) {
				int weitereKinder = familienzuschlag.getAnzahlKinder() - 2;
				return this.famZuschlagStufe2 + this.famZuschlagZweitesKind + (weitereKinder * this.famZuschlagWeitereKinder);
			}
		}
		*/
		//Default Stufe 1
		return 0.0f;
	}


	@Override
	public float getSalaryForAmtsunabhängigeMindestversorgung() {
		float mindestversorgung = 0.0f;
		float[] stufen = besoldungsordnungA.get(Constants.AMTSUNABHAENGIGE_MINDESTVERSORGUNG_BESGRUPPE_PARA_14_Abs_4_BeamtVG);
		int besoldungsStufe = Constants.AMTSUNABHAENGIGE_MINDESTVERSORGUNG_BESSTUFE_PARA_14_Abs_4_BeamtVG;
		mindestversorgung = stufen[--besoldungsStufe];

		return mindestversorgung;
	}
	
	public void initKindererziehungszuschlag(Date dateOfRetirement) {
		IKindererziehungszeitenService kindererziehungszeitenService = ServiceRegistry.getInstance().getKindererziehungszeitenService();
		if(dateOfRetirement == null) {
			dateOfRetirement = new Date();
		}
		this.aktuellerRentenwertWest = kindererziehungszeitenService.getAktuellerRentenwert(dateOfRetirement, KindererziehungszeitenZuschlag.RENTENWERT_WEST);
		this.aktuellerRentenwertOst =  kindererziehungszeitenService.getAktuellerRentenwert(dateOfRetirement, KindererziehungszeitenZuschlag.RENTENWERT_OST);
	}
	
	
	@Override
	public float getAktuellenRentenwertWestForKindererziehungszuschlag() {
		return aktuellerRentenwertWest;
	}
	
	@Override
	public float getAktuellenRentenwertOstForKindererziehungszuschlag() {
		return aktuellerRentenwertOst;
	}
	
	@Override
	public  Map<Integer, float[]> getBesoldungsOrdnungA() {
		return this.besoldungsordnungA;
	}

	@Override
	public Map<Integer, Float> getBesoldungsOrdnungB() {
		return this.besoldungsordnungB;
	}

	@Override
	public Map<String, Amtszulage[]> getAmtszulagen() {
		return this.amtszulage;
	}


	public Date getValidFrom() {
		return validFrom;
	}


	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}


	public Date getValidTo() {
		return validTo;
	}


	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	
	

	public String getTitel() {
		return titel;
	}


	public void setTitel(String titel) {
		this.titel = titel;
	}


	public String getSubTitel() {
		return subTitel;
	}
	

	public void setSubTitel(String subTitel) {
		this.subTitel = subTitel;
	}

	
}
