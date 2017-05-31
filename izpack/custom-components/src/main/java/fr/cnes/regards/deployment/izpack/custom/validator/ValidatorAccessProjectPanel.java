/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.validator;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Class ValidatorAccessProjectPanel
 *
 * Validator for access-project panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorAccessProjectPanel extends InjectDataValidator {

    /**
     * Default constructor
     */
    public ValidatorAccessProjectPanel() {
        type = ComponentType.ACCESS_PROJECT;
        entryKey = type.getName();
    }
}
