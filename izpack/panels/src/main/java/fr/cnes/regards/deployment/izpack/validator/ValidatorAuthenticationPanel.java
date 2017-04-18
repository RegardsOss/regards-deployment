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
public class ValidatorAuthenticationPanel extends ValidatorMicroserviceList {

    public ValidatorAuthenticationPanel() {
        microServiceIdentifier = "authentication";
        microServiceCountIdentifier = microServiceIdentifier;
    }
}
