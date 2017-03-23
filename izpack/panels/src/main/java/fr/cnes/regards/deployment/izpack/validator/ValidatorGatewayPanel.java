/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 * Class ValidatorGatewayPanel
 *
 * Validator for gateway panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorGatewayPanel extends ValidatorMicroserviceList {

	public ValidatorGatewayPanel() {
		microServiceIdentifier = "microservice.gateway";
		microServiceCountIdentifier = microServiceIdentifier;
	}
}
