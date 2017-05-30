/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.button;

import com.izforge.izpack.api.data.InstallData;

public class AdminProjectJdbcConnectionTester extends JdbcConnectionTester {

    public AdminProjectJdbcConnectionTester(InstallData pInstallData) {
        super(pInstallData);
        urlVariable = "regards.config.admin.regards.jpa.multitenant.tenants.url.1";
        userVariable = "regards.config.admin.regards.jpa.multitenant.tenants.user.name.1";
        passwordVariable = "regards.config.admin.regards.jpa.multitenant.tenants.password.1";
    }

}