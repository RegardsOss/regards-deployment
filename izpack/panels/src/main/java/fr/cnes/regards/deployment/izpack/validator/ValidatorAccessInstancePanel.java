/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 * Class ValidatorAccessInstancePanel
 *
 * Validator for access-instance panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorAccessInstancePanel extends ValidatorMicroserviceList {

	public ValidatorAccessInstancePanel() {
		microServiceIdentifier = "microservice.access-instance";
		microServiceCountIdentifier = microServiceIdentifier;
	}
}
