/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

import fr.cnes.regards.deployment.izpack.utils.model.ComponentType;

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
