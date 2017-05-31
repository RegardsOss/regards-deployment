/*
 * LICENSE_PLACEHOLDER
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