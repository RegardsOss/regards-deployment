/*
 * Copyright 2017 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
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
package fr.cnes.regards.deployment.izpack.custom.utils;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.data.Variables;
import com.izforge.izpack.core.data.DefaultVariables;
import com.izforge.izpack.util.Platform;
import com.izforge.izpack.util.Platform.Name;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Utils class building dummy install data.
 * @author Xavier-Alexandre Brochard
 */
public class DummyInstallDataFactory {

    private static final String ACCESS_INSTANCE_DB_URL_VARIABLE_NAME = "regards.config.access.instance.regards.jpa.instance.datasource.url";

    private static final String ACCESS_INSTANCE_DB_URL_VARIABLE_VALUE = "172.26.47.52:5432/rs_access_instance";

    private static final String ACCESS_INSTANCE_DB_USER_VARIABLE_VALUE = "azertyuiop123456789";

    private static final String ACCESS_INSTANCE_DB_USER_VARIABLE_NAME = "regards.config.access.instance.regards.jpa.instance.datasource.user.name";

    private static final String ACCESS_INSTANCE_DB_PASSWORD_VARIABLE_VALUE = "azertyuiop123456789";

    private static final String ACCESS_INSTANCE_DB_PASSWORD_VARIABLE_NAME = "regards.config.access.instance.regards.jpa.instance.datasource.password";

    public static InstallData buildWithRegistry(ComponentType pType) {
        Variables variables = new DefaultVariables();
        Platform platform = new Platform(Name.LINUX, System.getProperty("os.version"));
        final String entryKey = "regards.config.cloud.registry";

        // Set Test Values
        final String count = pType.getName() + ".count";
        final String uriName = entryKey + ".host";
        final String portName = entryKey + ".port";
        final String xmxName = entryKey + ".xmx";

        InstallData installData = new com.izforge.izpack.installer.data.InstallData(variables, platform);
        variables.set(count, "2");
        variables.set(uriName + ".1", "localhost");
        variables.set(portName + ".1", "3456");
        variables.set(uriName + ".2", "127.0.0.1");
        variables.set(portName + ".2", "3457");
        
        variables.set(xmxName, "5432m");

        return installData;
    }

    public static InstallData buildWithAccessInstance() {
        Variables variables = new DefaultVariables();
        Platform platform = new Platform(Name.LINUX, System.getProperty("os.version"));

        InstallData installData = new com.izforge.izpack.installer.data.InstallData(variables, platform);
        variables.set(ACCESS_INSTANCE_DB_URL_VARIABLE_NAME, ACCESS_INSTANCE_DB_URL_VARIABLE_VALUE);
        variables.set(ACCESS_INSTANCE_DB_USER_VARIABLE_NAME, ACCESS_INSTANCE_DB_USER_VARIABLE_VALUE);
        variables.set(ACCESS_INSTANCE_DB_PASSWORD_VARIABLE_NAME, ACCESS_INSTANCE_DB_PASSWORD_VARIABLE_VALUE);

        return installData;
    }

    public static InstallData buildWithAccessInstanceWrongUrl() {
        Variables variables = new DefaultVariables();
        Platform platform = new Platform(Name.LINUX, System.getProperty("os.version"));

        InstallData installData = new com.izforge.izpack.installer.data.InstallData(variables, platform);
        variables.set(ACCESS_INSTANCE_DB_URL_VARIABLE_NAME, "this:is/awrongurl");
        variables.set(ACCESS_INSTANCE_DB_USER_VARIABLE_NAME, ACCESS_INSTANCE_DB_USER_VARIABLE_VALUE);
        variables.set(ACCESS_INSTANCE_DB_PASSWORD_VARIABLE_NAME, ACCESS_INSTANCE_DB_PASSWORD_VARIABLE_VALUE);

        return installData;
    }

    public static InstallData buildWithAccessInstanceWrongUser() {
        Variables variables = new DefaultVariables();
        Platform platform = new Platform(Name.LINUX, System.getProperty("os.version"));

        InstallData installData = new com.izforge.izpack.installer.data.InstallData(variables, platform);
        variables.set(ACCESS_INSTANCE_DB_URL_VARIABLE_NAME, ACCESS_INSTANCE_DB_URL_VARIABLE_VALUE);
        variables.set(ACCESS_INSTANCE_DB_USER_VARIABLE_NAME, "wrongname");
        variables.set(ACCESS_INSTANCE_DB_PASSWORD_VARIABLE_NAME, ACCESS_INSTANCE_DB_PASSWORD_VARIABLE_VALUE);

        return installData;
    }

    public static InstallData buildWithAccessInstanceWrongPassword() {
        Variables variables = new DefaultVariables();
        Platform platform = new Platform(Name.LINUX, System.getProperty("os.version"));

        InstallData installData = new com.izforge.izpack.installer.data.InstallData(variables, platform);
        variables.set(ACCESS_INSTANCE_DB_URL_VARIABLE_NAME, ACCESS_INSTANCE_DB_URL_VARIABLE_VALUE);
        variables.set(ACCESS_INSTANCE_DB_USER_VARIABLE_NAME, ACCESS_INSTANCE_DB_USER_VARIABLE_VALUE);
        variables.set(ACCESS_INSTANCE_DB_PASSWORD_VARIABLE_NAME, "wrongpassword");

        return installData;
    }

}
