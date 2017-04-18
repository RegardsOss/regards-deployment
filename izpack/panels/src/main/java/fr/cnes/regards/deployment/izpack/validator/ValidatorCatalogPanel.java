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
public class ValidatorCatalogPanel extends ValidatorInstanceList {

    /**
     * Default constructor
     */
    public ValidatorCatalogPanel() {
        componentIdentifier = "catalog";
        componentCountIdentifier = componentIdentifier;
    }
}
