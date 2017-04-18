/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 * Validator for rs-authentication configuration panel.
 *
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
public class ValidatorAuthenticationPanel extends ValidatorInstanceList {

    /**
     * Default constructor
     */
    public ValidatorAuthenticationPanel() {
        componentIdentifier = "authentication";
        componentCountIdentifier = componentIdentifier;
    }
}
