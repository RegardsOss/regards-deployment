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
import org.junit.Test;

import com.izforge.izpack.api.data.InstallData;

import fr.cnes.regards.deployment.izpack.custom.utils.DummyInstallDataFactory;

/**
 * Unit test for {@link AccessInstanceJdbcConnectionTester}.
 *
 * @author Xavier-Alexandre Brochard
 */
public class AccessInstanceJdbcConnectionTesterTest {

    /**
     * Test method for {@link fr.cnes.regards.deployment.izpack.custom.button.JdbcConnectionTester#execute()}.
     */
    @Test
    public final void testExecute() {
        // Prepare test
        InstallData installData = DummyInstallDataFactory.buildWithAccessInstance();
        AccessInstanceJdbcConnectionTester tester = new AccessInstanceJdbcConnectionTester(installData);

        // Perform test
        boolean result = tester.execute();

        // Check
        Assert.assertTrue(result);
    }

    /**
     * Test method for {@link fr.cnes.regards.deployment.izpack.custom.button.JdbcConnectionTester#execute()}.
     */
    @Test
    public final void testExecute_wrongUrl() {
        // Prepare test
        InstallData installData = DummyInstallDataFactory.buildWithAccessInstanceWrongUrl();
        AccessInstanceJdbcConnectionTester tester = new AccessInstanceJdbcConnectionTester(installData);

        // Perform test
        boolean result = tester.execute();

        // Check
        Assert.assertFalse(result);
    }

    /**
     * Test method for {@link fr.cnes.regards.deployment.izpack.custom.button.JdbcConnectionTester#execute()}.
     */
    @Test
    public final void testExecute_wrongUser() {
        // Prepare test
        InstallData installData = DummyInstallDataFactory.buildWithAccessInstanceWrongUser();
        AccessInstanceJdbcConnectionTester tester = new AccessInstanceJdbcConnectionTester(installData);

        // Perform test
        boolean result = tester.execute();

        // Check
        Assert.assertFalse(result);
    }

    /**
     * Test method for {@link fr.cnes.regards.deployment.izpack.custom.button.JdbcConnectionTester#execute()}.
     */
    @Test
    public final void testExecute_wrongPassword() {
        // Prepare test
        InstallData installData = DummyInstallDataFactory.buildWithAccessInstanceWrongPassword();
        AccessInstanceJdbcConnectionTester tester = new AccessInstanceJdbcConnectionTester(installData);

        // Perform test
        boolean result = tester.execute();

        // Check
        Assert.assertFalse(result);
    }

}
