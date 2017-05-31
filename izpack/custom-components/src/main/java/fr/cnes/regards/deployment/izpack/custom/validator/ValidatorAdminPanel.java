/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.validator;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Class ValidatorAdminPanel
 *
 * Validator for admin panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorAdminPanel extends InjectDataValidator {

    /**
     * Default constructor
     */
    public ValidatorAdminPanel() {
        type = ComponentType.ADMIN;
        entryKey = type.getName();
    }
}
