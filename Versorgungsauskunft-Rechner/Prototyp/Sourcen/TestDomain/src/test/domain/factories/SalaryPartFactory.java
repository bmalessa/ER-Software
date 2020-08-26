package test.domain.factories;

import de.protin.support.pr.domain.besoldung.Familienzuschlag;
import de.protin.support.pr.domain.besoldung.Grundgehalt;

public class SalaryPartFactory {

	private static SalaryPartFactory instance;

	private SalaryPartFactory() {
	}

	public static SalaryPartFactory getInstance() {
		if (null == instance) {
			instance = new SalaryPartFactory();
		}
		return instance;
	}
	
	
	public Grundgehalt setupGrundgehalt() {
		Grundgehalt gerundgehalt = new Grundgehalt("A", 12, 8);
		return gerundgehalt;
	}
	
	
	public Familienzuschlag setupFamilienzuschlag() {
		Familienzuschlag familienzuschlag = new Familienzuschlag(2, 4);
		return familienzuschlag;
	}
	
}
