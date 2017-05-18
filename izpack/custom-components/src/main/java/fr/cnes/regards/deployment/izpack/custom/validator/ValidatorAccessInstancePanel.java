/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.validator;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Class ValidatorAccessInstancePanel
 *
 * Validator for access-instance panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorAccessInstancePanel extends InjectDataValidator {

    /**
     * Default constructor
     */
    public ValidatorAccessInstancePanel() {
        type = ComponentType.ACCESS_INSTANCE;
        entryKey = type.getName();
    }
}
