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
 * Test class for {@link ValidatorGatewayPanel}.
 *
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
public class ValidatorGatewayPanelTest {

    @Test
    public void testValidateData() {
        final String EXPECTED_VALUE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<componentConfigList>\n" + "    <componentConfig id=\"0\">\n" + "        <host>localhost</host>\n"
                + "        <port>3456</port>\n" + "    </componentConfig>\n" + "    <componentConfig id=\"1\">\n"
                + "        <host>127.0.0.1</host>\n" + "        <port>3457</port>\n" + "    </componentConfig>\n"
                + "</componentConfigList>\n";

        Variables variables = new DefaultVariables();
        Platform platform = new Platform(Name.LINUX, System.getProperty("os.version"));
        final String MICROSERVICE_IDENTIFIER = "gateway";
        final String MICROSERVICE_COUNT_IDENTIFIER = MICROSERVICE_IDENTIFIER;

        // Set Test Values
        final String count = MICROSERVICE_COUNT_IDENTIFIER + ".count";
        final String uriName = MICROSERVICE_IDENTIFIER + ".host";
        final String portName = MICROSERVICE_IDENTIFIER + ".port";
        final String componentListName = MICROSERVICE_COUNT_IDENTIFIER + ".instanceList";

        InstallData installData = new InstallData(variables, platform);
        variables.set(count, "2");
        variables.set(uriName + ".1", "localhost");
        variables.set(portName + ".1", "3456");
        variables.set(uriName + ".2", "127.0.0.1");
        variables.set(portName + ".2", "3457");

        ValidatorGatewayPanel validatorGatewayPanel = new ValidatorGatewayPanel();

        Status status = validatorGatewayPanel.validateData(installData);
        Assert.assertEquals(status, Status.OK);

        // Check transformation
        String valueOfComponentListName = installData.getVariable(componentListName);
        Assert.assertEquals(EXPECTED_VALUE, valueOfComponentListName);
    }
}
