/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 * Class ValidatorRegistryPanel
 *
 * Validator for registry panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorRegistryPanel extends ValidatorInstanceList {

    /**
     * Default constructor
     */
    public ValidatorRegistryPanel() {
        componentIdentifier = "registry";
        componentCountIdentifier = componentIdentifier;
    }
}
