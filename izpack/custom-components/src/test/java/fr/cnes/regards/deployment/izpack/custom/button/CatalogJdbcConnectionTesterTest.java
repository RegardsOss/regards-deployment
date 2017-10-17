/*
 * Copyright 2017 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
 *
 * This file is part of REGARDS.
 *
 * REGARDS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * REGARDS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with REGARDS. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.cnes.regards.deployment.izpack.custom.button;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.data.Variables;
import com.izforge.izpack.core.data.DefaultVariables;
import com.izforge.izpack.util.Platform;
import com.izforge.izpack.util.Platform.Name;

/**
 * Unit test for {@link CatalogJdbcConnectionTester}
 *
 * @author Christophe Mertz
 */
public class CatalogJdbcConnectionTesterTest {
    
    private static final String URL = "172.26.47.52:5432/rs_catalog_tenant0";

    private static final String USER = "azertyuiop123456789";
    
    private static final String PASSWORD = "azertyuiop123456789";

    private static final Variables variables = new DefaultVariables();

    private static final Platform platform = new Platform(Name.LINUX, System.getProperty("os.version"));

    private final static InstallData installData = new com.izforge.izpack.installer.data.InstallData(variables,
            platform);

    @Before
    public void init() {
        variables.refresh();
    }

    private boolean launchTest() {
        CatalogJdbcConnectionTester tester = new CatalogJdbcConnectionTester(installData);

        // Perform test
        return tester.execute();
    }

    @Test
    public final void testExecute() {
        variables.set(CatalogJdbcConnectionTester.URL_DATASOURCE_VARIABLE, URL);
        variables.set(CatalogJdbcConnectionTester.USERNAME_DATASOURCE_VARIABLE, USER);
        variables.set(CatalogJdbcConnectionTester.PASSWORD_DATASOURCE_VARIABLE, PASSWORD);

        Assert.assertTrue(launchTest());
    }

    @Test
    public final void testExecuteWrongHost() {
        variables.set(CatalogJdbcConnectionTester.URL_DATASOURCE_VARIABLE, URL+"/hello");
        variables.set(CatalogJdbcConnectionTester.USERNAME_DATASOURCE_VARIABLE, USER);
        variables.set(CatalogJdbcConnectionTester.PASSWORD_DATASOURCE_VARIABLE, PASSWORD);

        Assert.assertFalse(launchTest());
    }

    @Test
    public final void testExecuteWrongPassword() {
        variables.set(CatalogJdbcConnectionTester.URL_DATASOURCE_VARIABLE, URL);
        variables.set(CatalogJdbcConnectionTester.USERNAME_DATASOURCE_VARIABLE, USER);
        variables.set(CatalogJdbcConnectionTester.PASSWORD_DATASOURCE_VARIABLE, "dummy");

        Assert.assertFalse(launchTest());
    }

    @Test
    public final void testExecuteWrongAuthentication() {
        variables.set(CatalogJdbcConnectionTester.URL_DATASOURCE_VARIABLE, URL);
        variables.set(CatalogJdbcConnectionTester.USERNAME_DATASOURCE_VARIABLE, "unknown");
        variables.set(CatalogJdbcConnectionTester.PASSWORD_DATASOURCE_VARIABLE, PASSWORD);

        Assert.assertFalse(launchTest());
    }

}
