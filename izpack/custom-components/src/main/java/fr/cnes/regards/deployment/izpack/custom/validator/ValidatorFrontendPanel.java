/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.validator;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Class ValidatorFrontendPanel
 *
 * Validator for frontend panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorFrontendPanel extends InjectDataValidator {

    /**
     * Default constructor
     */
    public ValidatorFrontendPanel() {
        type = ComponentType.FRONTEND;
        entryKey = type.getName();
    }
}
