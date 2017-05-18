/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.validator;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Validator for config panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
public class ValidatorConfigPanel extends InjectDataValidator {

    /**
     * Default constructor
     */
    public ValidatorConfigPanel() {
        type = ComponentType.CONFIG;
        entryKey = type.getName();
    }
}
