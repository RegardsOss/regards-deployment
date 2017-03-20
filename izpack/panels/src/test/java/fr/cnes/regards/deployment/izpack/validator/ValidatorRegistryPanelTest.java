/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

import org.junit.Assert;
import org.junit.Test;

import com.izforge.izpack.api.data.Variables;
import com.izforge.izpack.api.installer.DataValidator.Status;
import com.izforge.izpack.core.data.DefaultVariables;
import com.izforge.izpack.installer.data.InstallData;
import com.izforge.izpack.util.Platform;
import com.izforge.izpack.util.Platform.Name;

/**
 * Class ValidatorRegistryPanelTest
 *
 * Test class for ValidatorRegistryPanel.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class ValidatorRegistryPanelTest {

    @Test
    public void testValidateData() {
        final String EXPECTED_VALUE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<microserviceConfigList>\n" + "    <microserviceConfig id=\"0\">\n"
                + "        <host>localhost</host>\n" + "        <port>3456</port>\n" + "    </microserviceConfig>\n"
                + "    <microserviceConfig id=\"1\">\n" + "        <host>127.0.0.1</host>\n"
                + "        <port>3457</port>\n" + "    </microserviceConfig>\n" + "</microserviceConfigList>\n";

        Variables variables = new DefaultVariables();
        Platform platform = new Platform(Name.LINUX, System.getProperty("os.version"));
        final String MICROSERVICE_IDENTIFIER = "registry";

        // Set Test Values
        final String count = "microservice." + MICROSERVICE_IDENTIFIER + ".count";
        final String uriName = "microservice." + MICROSERVICE_IDENTIFIER + ".uri";
        final String portName = "microservice." + MICROSERVICE_IDENTIFIER + ".port";
        final String microserviceListName = "microservice." + MICROSERVICE_IDENTIFIER + ".microserviceList";

        InstallData installData = new InstallData(variables, platform);
        variables.set(count, "2");
        variables.set(uriName + ".1", "localhost");
        variables.set(portName + ".1", "3456");
        variables.set(uriName + ".2", "127.0.0.1");
        variables.set(portName + ".2", "3457");

        ValidatorRegistryPanel validatorRegistryPanel = new ValidatorRegistryPanel();

        Status status = validatorRegistryPanel.validateData(installData);
        Assert.assertEquals(status, Status.OK);

        // Check transformation
        String valueOfMicroserviceListName = installData.getVariable(microserviceListName);
        Assert.assertEquals(EXPECTED_VALUE, valueOfMicroserviceListName);
    }
}
