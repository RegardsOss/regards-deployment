/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 * Class ValidatorAccessProjectPanel
 *
 * Validator for access-project panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorAccessProjectPanel extends ValidatorMicroserviceList {

	public ValidatorAccessProjectPanel() {
		microServiceIdentifier = "microservice.access-project";
		microServiceCountIdentifier = microServiceIdentifier;
	}
}
