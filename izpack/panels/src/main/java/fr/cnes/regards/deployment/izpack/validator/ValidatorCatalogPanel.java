/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 * Class ValidatorCatalogPanel
 *
 * Validator for catalog panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorCatalogPanel extends ValidatorMicroserviceList {

	public ValidatorCatalogPanel() {
		microServiceIdentifier = "microservice.catalog";
		microServiceCountIdentifier = microServiceIdentifier;
	}
}
