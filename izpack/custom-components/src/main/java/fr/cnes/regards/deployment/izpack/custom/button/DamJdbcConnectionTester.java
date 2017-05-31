/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.button;

import com.izforge.izpack.api.data.InstallData;

public class DamJdbcConnectionTester extends JdbcConnectionTester {

    public DamJdbcConnectionTester(InstallData pInstallData) {
        super(pInstallData);
        urlVariable = "regards.config.dam.regards.jpa.multitenant.tenants.url.1";
        userVariable = "regards.config.dam.regards.jpa.multitenant.tenants.user.name.1";
        passwordVariable = "regards.config.dam.regards.jpa.multitenant.tenants.password.1";
    }

}