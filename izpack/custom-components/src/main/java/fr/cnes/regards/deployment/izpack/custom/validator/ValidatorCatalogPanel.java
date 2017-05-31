/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.validator;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Class ValidatorCatalogPanel
 *
 * Validator for catalog panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorCatalogPanel extends InjectDataValidator {

    /**
     * Default constructor
     */
    public ValidatorCatalogPanel() {
        type = ComponentType.CATALOG;
        entryKey = type.getName();
    }
}
