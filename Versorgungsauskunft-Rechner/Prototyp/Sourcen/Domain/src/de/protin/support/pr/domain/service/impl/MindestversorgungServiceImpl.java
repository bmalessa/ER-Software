package de.protin.support.pr.domain.service.impl;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;
import de.protin.support.pr.domain.besoldung.SonstigeZulage;
import de.protin.support.pr.domain.besoldung.Stellenzulage;
import de.protin.support.pr.domain.service.IMindestversorgungService;
import de.protin.support.pr.domain.utils.Constants;

public class MindestversorgungServiceImpl implements IMindestversorgungService {

	@Override
	public float calculateMindestversorgung(IPension pension) {
		float mindestversorgung = 0.0f;
		float amtsabhaengigeMindestversorgung = calculateAmtsabhaengigeMindestversorgung(pension);
		float amtsunabhaengigeMindestversorgung = calculateAmtsunabhaengigeMindestversorgung(pension);
		
		//der höhere Wert aus amtabhängige bzw. amtsunabhängiger Mindestversorgung wird genommen
		if(amtsabhaengigeMindestversorgung > amtsunabhaengigeMindestversorgung) {
			mindestversorgung = amtsabhaengigeMindestversorgung;
		}
		else  {
			mindestversorgung = amtsunabhaengigeMindestversorgung;
		}
		
		return mindestversorgung;
	}
	
	
	/**
	 * 
	 * @return Amtsunabhaengige Mindestversorgung
	 */
	public float calculateAmtsunabhaengigeMindestversorgung(IPension pension) {
		
		float amtsunabhaengigeMindestversorgung = 0.0f;

		//Amtsunabhängige Mindestversorgung
		amtsunabhaengigeMindestversorgung = pension.getBesoldungstabelle().getSalaryForAmtsunabhängigeMindestversorgung();
		amtsunabhaengigeMindestversorgung += pension.getBesoldungstabelle().getSalaryForFamiliezuschlag(pension.getPerson().getFamilienzuschlag());
		
		amtsunabhaengigeMindestversorgung *= Constants.AMTSUNABHAENGIGE_MINDESTVERSORGUNG_PROZENTSATZ_PARA_14_Abs_4_BeamtVG;
		amtsunabhaengigeMindestversorgung *= Constants.KORREKTURFAKTOR_PARA_5_Abs_1_BeamtVG;
		
		amtsunabhaengigeMindestversorgung += Constants.AMTSUNABHAENGIGE_MINDESTVERSORGUNG_FIXBETRAG_PARA_14_Abs_4_BeamtVG;
		return amtsunabhaengigeMindestversorgung;
	}


	/**
	 * 
	 * @return Amtsabhaengige Mindestversorgung
	 */
	public float calculateAmtsabhaengigeMindestversorgung(IPension pension) {

		float amtsabhaengigeMindestversorgung = 0.0f;
		
		Familienzuschlag familienzuschlag = pension.getPerson().getFamilienzuschlag();
		Stellenzulage stellenzulage = pension.getPerson().getStellenzulage();
		SonstigeZulage sonstigeZulage = pension.getPerson().getSonstigeZulage();
		//Amtsabhängige Mindestversogung
		Grundgehalt grundgehalt = pension.getPerson().getBesoldung();
		amtsabhaengigeMindestversorgung = pension.getBesoldungstabelle().getSalaryForGrundgehalt(grundgehalt);
		amtsabhaengigeMindestversorgung += pension.getBesoldungstabelle().getSalaryForFamiliezuschlag(familienzuschlag);
		if(stellenzulage != null) {
			amtsabhaengigeMindestversorgung += stellenzulage.getSalary();
		}
		if(sonstigeZulage != null) {
			amtsabhaengigeMindestversorgung += sonstigeZulage.getSalary();	
		}

		amtsabhaengigeMindestversorgung *= Constants.AMTSABHAENGIGE_MINDESTVERSORGUNG_PROZENTSATZ_PARA_14_Abs_4_BeamtVG;
		amtsabhaengigeMindestversorgung *= Constants.KORREKTURFAKTOR_PARA_5_Abs_1_BeamtVG;
		return amtsabhaengigeMindestversorgung;
	}
	
	

}
