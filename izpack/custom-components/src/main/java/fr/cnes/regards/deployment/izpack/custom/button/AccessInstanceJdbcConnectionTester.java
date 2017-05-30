/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.button;

import com.izforge.izpack.api.data.InstallData;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

public class AccessInstanceJdbcConnectionTester extends JdbcConnectionTester {

    public AccessInstanceJdbcConnectionTester(InstallData pInstallData) {
        super(ComponentType.ACCESS_INSTANCE, pInstallData);
        urlVariable = "regards.config.access.instance.regards.jpa.instance.datasource.url";
        userVariable = "regards.config.access.instance.regards.jpa.instance.datasource.user.name";
        passwordVariable = "regards.config.access.instance.regards.jpa.instance.datasource.password";
    }

}