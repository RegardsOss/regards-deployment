/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

import fr.cnes.regards.deployment.izpack.utils.model.ComponentType;

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
