/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.actions;

import com.izforge.izpack.api.data.InstallData;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;
import fr.cnes.regards.deployment.izpack.custom.model.PostgreSqlJdbcConnectionModel;

public class AccessInstanceJdbcConnectionTester extends JdbcConnectionTester {

    public AccessInstanceJdbcConnectionTester(InstallData pInstallData) {
        super(ComponentType.ACCESS_INSTANCE, pInstallData);
        String url = pInstallData.getVariable("regards.config.admin.regards.jpa.instance.datasource.url");
        String user = pInstallData.getVariable("regards.config.admin.regards.jpa.instance.datasource.user.name");
        String password = pInstallData.getVariable("regards.config.admin.regards.jpa.instance.datasource.password");
        jdbcModel = new PostgreSqlJdbcConnectionModel(url, user, password);
    }

}