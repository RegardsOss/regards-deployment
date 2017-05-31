/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.validator;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Class ValidatorDamPanel
 *
 * Validator for dam panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorDamPanel extends InjectDataValidator {

    /**
     * Default constructor
     */
    public ValidatorDamPanel() {
        type = ComponentType.DAM;
        entryKey = type.getName();
    }
}
