/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 * Class ValidatorConfPanel
 *
 * Validator for conf panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorConfPanel extends ValidatorMicroserviceList {

	public ValidatorConfPanel() {
		microServiceIdentifier = "regards.config.cloud.conf";
		microServiceCountIdentifier = "microservice.conf";
	}
}
