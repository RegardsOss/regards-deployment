/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 * Class ValidatorAccessPanel
 *
 * Validator for access panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorAccessPanel extends ValidatorMicroserviceList {

	public ValidatorAccessPanel() {
		microServiceIdentifier = "microservice.access";
		microServiceCountIdentifier = microServiceIdentifier;
	}
}
