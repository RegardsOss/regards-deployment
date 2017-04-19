/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 * Class ValidatorAdminPanel
 *
 * Validator for admin panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorAdminPanel extends ValidatorInstanceList {

    /**
     * Default constructor
     */
    public ValidatorAdminPanel() {
        componentIdentifier = "admin";
        componentCountIdentifier = componentIdentifier;
    }
}
