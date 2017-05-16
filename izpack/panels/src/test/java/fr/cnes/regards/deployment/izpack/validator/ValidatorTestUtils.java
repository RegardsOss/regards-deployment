/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.data.Variables;
import com.izforge.izpack.core.data.DefaultVariables;
import com.izforge.izpack.util.Platform;
import com.izforge.izpack.util.Platform.Name;

import fr.cnes.regards.deployment.izpack.utils.model.ComponentType;

/**
 *
 * @author Xavier-Alexandre Brochard
 */
public class ValidatorTestUtils {

    public static InstallData buildDummyInstallData(ComponentType pType) {
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
}
