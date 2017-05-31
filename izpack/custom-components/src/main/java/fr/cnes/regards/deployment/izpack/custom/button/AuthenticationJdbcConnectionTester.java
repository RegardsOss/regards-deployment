/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.button;

import com.izforge.izpack.api.data.InstallData;

public class AuthenticationJdbcConnectionTester extends JdbcConnectionTester {

    public AuthenticationJdbcConnectionTester(InstallData pInstallData) {
        super(pInstallData);
        urlVariable = "regards.config.gateway.regards.jpa.multitenant.tenants.url.1";
        userVariable = "regards.config.gateway.regards.jpa.multitenant.tenants.user.name.1";
        passwordVariable = "regards.config.gateway.regards.jpa.multitenant.tenants.password.1";
    }

}