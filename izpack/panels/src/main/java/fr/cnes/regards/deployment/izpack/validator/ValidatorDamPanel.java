/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 * Class ValidatorDamPanel
 *
 * Validator for dam panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorDamPanel extends ValidatorMicroserviceList {

	public ValidatorDamPanel() {
		microServiceIdentifier = "microservice.dam";
		microServiceCountIdentifier = microServiceIdentifier;
	}
}
