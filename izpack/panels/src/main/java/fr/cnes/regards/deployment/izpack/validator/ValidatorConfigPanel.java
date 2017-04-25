/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 * Validator for config panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
public class ValidatorConfigPanel extends ValidatorInstanceList {

    /**
     * Default constructor
     */
    public ValidatorConfigPanel() {
        componentIdentifier = "config";
        componentCountIdentifier = componentIdentifier;
    }
}
