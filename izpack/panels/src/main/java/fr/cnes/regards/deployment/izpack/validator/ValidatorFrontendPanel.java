/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 * Class ValidatorFrontendPanel
 *
 * Validator for frontend panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorFrontendPanel extends ValidatorMicroserviceList {

	public ValidatorFrontendPanel() {
		microServiceIdentifier = "microservice.frontend";
		microServiceCountIdentifier = microServiceIdentifier;
	}
}
