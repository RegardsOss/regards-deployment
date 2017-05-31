/*
 * LICENSE_PLACEHOLDER
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

        InstallData installData = new com.izforge.izpack.installer.data.InstallData(variables, platform);
        variables.set(count, "2");
        variables.set(uriName + ".1", "localhost");
        variables.set(portName + ".1", "3456");
        variables.set(uriName + ".2", "127.0.0.1");
        variables.set(portName + ".2", "3457");

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
