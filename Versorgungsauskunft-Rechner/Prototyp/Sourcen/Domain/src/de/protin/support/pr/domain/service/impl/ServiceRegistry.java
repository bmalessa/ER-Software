package de.protin.support.pr.domain.service.impl;

import de.protin.support.pr.domain.service.IKindererziehungszeitenService;
import de.protin.support.pr.domain.service.IMindestversorgungService;
import de.protin.support.pr.domain.service.IRuhegehaltsabschlagService;
import de.protin.support.pr.domain.service.IRuhegehaltsberechnungAktuellesRechtService;
import de.protin.support.pr.domain.service.IRuhegehaltsberechnungAltesRechtService;
import de.protin.support.pr.domain.service.IRuhegehaltsfaehigeZeitenService;
import de.protin.support.pr.domain.service.IValidationService;

public class ServiceRegistry {

	private static ServiceRegistry instance;

	private ServiceRegistry() {
	}

	public static ServiceRegistry getInstance() {
		if (null == instance) {
			instance = new ServiceRegistry();
		}
		return instance;
	}
	
	public IValidationService getValidationService() {
		return new ValidationServiceImpl();
	}
	
	public IMindestversorgungService getMindestversorgungService() {
		return new MindestversorgungServiceImpl();
	}
	
	public IRuhegehaltsberechnungAktuellesRechtService getRuhegehaltsberechnungAktuellesRechtService() {
		return new RuhegehaltsberechnungAktuellesRechtServiceImpl();
	}
	
	public IRuhegehaltsberechnungAltesRechtService getRuhegehaltsberechnungAltesRechtService() {
		return new RuhegehaltsberechnungAltesRechtServiceImpl();
	}
	
	public IRuhegehaltsfaehigeZeitenService getRuhegehaltsfaehigeZeitenService() {
		return new RuhegehaltsfaehigeZeitenServiceImpl();
	}
	
	public IRuhegehaltsabschlagService getRuhegehaltsabschlagService() {
		return new RuhegehaltsabschlagServiceImpl();
	}

	public IKindererziehungszeitenService getKindererziehungszeitenService() {
		return new KindererziehungszeitenServiceImpl();
	}
}
