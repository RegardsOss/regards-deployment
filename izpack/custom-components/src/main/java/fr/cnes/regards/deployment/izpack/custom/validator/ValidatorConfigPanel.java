/*
 * Copyright 2017-2020 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
 *
 * This file is part of REGARDS.
 *
 * REGARDS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * REGARDS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with REGARDS. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.cnes.regards.deployment.izpack.custom.validator;

import java.util.UUID;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.installer.DataValidator;
import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Validator for config panel.
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 */
public class ValidatorConfigPanel extends AbstractInjectDataValidator {

    /**
     * Default constructor
     */
    public ValidatorConfigPanel() {
        super();
        type = ComponentType.CONFIG;
        entryKey = type.getName();
    }

    @Override
    public Status validateData(InstallData installData) {
        installData.setVariable("jwt.secret", UUID.randomUUID().toString());
        return super.validateData(installData);
    }
}
