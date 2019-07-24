/*
 * Copyright 2017-2019 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
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

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.installer.DataValidator;

import fr.cnes.regards.deployment.izpack.custom.injector.InstanceListInjector;
import fr.cnes.regards.deployment.izpack.custom.injector.WaitRulesInjector;
import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * This class  does not perform validation, but is a way to hack into the Izpack {@link InstallData}<br>
 * in order to manually inject additional data (like the hosts and ports of all instances of a component) into the install data.
 *
 * @author Xavier-Alexandre Brochard
 * @author Christophe Mertz
 */
public abstract class AbstractInjectDataValidator implements DataValidator {

    /**
     * Validated component type
     */
    protected ComponentType type;

    /**
     * Key used to identify the component's properties in the install data.<br>
     * Ideally matches the above type, but some exceptions exists.
     */
    protected String entryKey;

    @Override
    public Status validateData(InstallData installData) {
        new InstanceListInjector(type, entryKey).inject(installData);
        new WaitRulesInjector(type).inject(installData);

        return Status.OK;
    }

    @Override
    public String getErrorMessageId() {
        return "";
    }

    @Override
    public String getWarningMessageId() {
        return "";
    }

    @Override
    public boolean getDefaultAnswer() {
        return false;
    }

}
