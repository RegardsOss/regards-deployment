/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.button;

import com.izforge.izpack.api.data.InstallData;

public class AccessProjectJdbcConnectionTester extends JdbcConnectionTester {

    public AccessProjectJdbcConnectionTester(InstallData pInstallData) {
        super(pInstallData);
        urlVariable = "regards.config.access.project.regards.jpa.multitenant.tenants.url.1";
        userVariable = "regards.config.access.project.regards.jpa.multitenant.tenants.user.name.1";
        passwordVariable = "regards.config.access.project.regards.jpa.multitenant.tenants.password.1";
    }

}