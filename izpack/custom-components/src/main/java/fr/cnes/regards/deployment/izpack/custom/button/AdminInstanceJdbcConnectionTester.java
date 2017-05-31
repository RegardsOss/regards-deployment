/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.button;

import com.izforge.izpack.api.data.InstallData;

public class AdminInstanceJdbcConnectionTester extends JdbcConnectionTester {

    public AdminInstanceJdbcConnectionTester(InstallData pInstallData) {
        super(pInstallData);
        urlVariable = "regards.config.admin.regards.jpa.instance.datasource.url";
        userVariable = "regards.config.admin.regards.jpa.instance.datasource.user.name";
        passwordVariable = "regards.config.admin.regards.jpa.instance.datasource.password";
    }

}