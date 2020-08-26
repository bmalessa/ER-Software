package de.protin.support.pr.domain.service;

import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.validation.ValidationResult;

public interface IValidationService {
	public ValidationResult validate(IPension pension);
}
