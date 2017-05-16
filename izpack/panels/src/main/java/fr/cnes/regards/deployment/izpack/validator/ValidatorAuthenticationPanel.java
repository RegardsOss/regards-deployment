/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

import fr.cnes.regards.deployment.izpack.utils.model.ComponentType;

/**
 * Validator for rs-authentication configuration panel.
 *
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
public class ValidatorAuthenticationPanel extends InjectDataValidator {

    /**
     * Default constructor
     */
    public ValidatorAuthenticationPanel() {
        type = ComponentType.AUTHENTICATION;
        entryKey = type.getName();
    }
}
