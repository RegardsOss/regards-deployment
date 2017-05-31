/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.validator;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Class ValidatorRegistryPanel
 *
 * Validator for registry panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorRegistryPanel extends InjectDataValidator {

    /**
     * Default constructor
     */
    public ValidatorRegistryPanel() {
        type = ComponentType.REGISTRY;
        entryKey = "regards.config.cloud.registry";
    }
}
