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
 * Unit test for {@link AmqpConnectionTester}
 *
 * @author Christophe Mertz
 */
public class AmqpConnectionTesterTest {

    private final static String HOST_VALUE = "172.26.47.52";

    private final static String PORT_VALUE = "5672";

    private final static String USERNAME = "guest";

    private final static String PASSWORD = "guest";

    private static final Variables variables = new DefaultVariables();

    private static final Platform platform = new Platform(Name.LINUX, System.getProperty("os.version"));

    private final static InstallData installData = new com.izforge.izpack.installer.data.InstallData(variables,
            platform);

    @Before
    public void init() {
        variables.refresh();
    }

    private boolean launchTest() {
        AmqpConnectionTester tester = new AmqpConnectionTester(installData);

        // Perform test
        return tester.execute();
    }

    @Test
    public final void testExecute() {
        variables.set(AmqpConnectionTester.HOST_VARIABLE, HOST_VALUE);
        variables.set(AmqpConnectionTester.PORT_VARIABLE, PORT_VALUE);
        variables.set(AmqpConnectionTester.USERNAME_VARIABLE, USERNAME);
        variables.set(AmqpConnectionTester.PASSWORD_VARIABLE, PASSWORD);

        Assert.assertTrue(launchTest());
    }

    @Test
    public final void testExecuteWrongHost() {
        variables.set(AmqpConnectionTester.HOST_VARIABLE, "10.11.1.10");
        variables.set(AmqpConnectionTester.PORT_VARIABLE, PORT_VALUE);
        variables.set(AmqpConnectionTester.USERNAME_VARIABLE, USERNAME);
        variables.set(AmqpConnectionTester.PASSWORD_VARIABLE, PASSWORD);

        Assert.assertFalse(launchTest());
    }

    @Test
    public final void testExecuteWrongPort() {
        variables.set(AmqpConnectionTester.HOST_VARIABLE, HOST_VALUE);
        variables.set(AmqpConnectionTester.PORT_VARIABLE, "9250");
        variables.set(AmqpConnectionTester.USERNAME_VARIABLE, USERNAME);
        variables.set(AmqpConnectionTester.PASSWORD_VARIABLE, PASSWORD);

        Assert.assertFalse(launchTest());
    }

    @Test
    public final void testExecuteWrongAuthentication() {
        variables.set(AmqpConnectionTester.HOST_VARIABLE, HOST_VALUE);
        variables.set(AmqpConnectionTester.PORT_VARIABLE, PORT_VALUE);
        variables.set(AmqpConnectionTester.USERNAME_VARIABLE, USERNAME);
        variables.set(AmqpConnectionTester.PASSWORD_VARIABLE, "hello");

        Assert.assertFalse(launchTest());
    }

}
