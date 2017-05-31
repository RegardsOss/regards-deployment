/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.validator;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Class ValidatorGatewayPanel
 *
 * Validator for gateway panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorGatewayPanel extends InjectDataValidator {

    /**
     * Default constructor
     */
    public ValidatorGatewayPanel() {
        type = ComponentType.GATEWAY;
        entryKey = type.getName();
    }
}
