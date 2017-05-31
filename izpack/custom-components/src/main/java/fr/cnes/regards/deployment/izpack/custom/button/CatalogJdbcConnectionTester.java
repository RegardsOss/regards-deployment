/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.button;

import com.izforge.izpack.api.data.InstallData;

public class CatalogJdbcConnectionTester extends JdbcConnectionTester {

    public CatalogJdbcConnectionTester(InstallData pInstallData) {
        super(pInstallData);
        urlVariable = "regards.config.catalog.regards.jpa.multitenant.tenants.url.1";
        userVariable = "regards.config.catalog.regards.jpa.multitenant.tenants.user.name.1";
        passwordVariable = "regards.config.catalog.regards.jpa.multitenant.tenants.password.1";
    }

}