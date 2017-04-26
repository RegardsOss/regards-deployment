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
 * Test class for {@link InjectWaitRules}.
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
public class InjectWaitRulesTest {

    @Test
    public void testValidateData() {
        // @formatter:off
        final String EXPECTED_VALUE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<waitRuleList>\n"
                + "    <waitRule>\n"
                + "        <host>localhost</host>\n"
                + "        <port>3333</port>\n"
                + "        <timeout>90</timeout>\n"
                + "    </waitRule>\n"
                + "    <waitRule>\n"
                + "        <host>127.0.0.1</host>\n"
                + "        <port>3333</port>\n"
                + "        <timeout>90</timeout>\n"
                + "    </waitRule>\n"
                + "    <waitRule>\n"
                + "        <host>localhost</host>\n"
                + "        <port>1111</port>\n"
                + "        <timeout>30</timeout>\n"
                + "    </waitRule>\n"
                + "    <waitRule>\n"
                + "        <host>127.0.0.1</host>\n"
                + "        <port>2222</port>\n"
                + "        <timeout>30</timeout>\n"
                + "    </waitRule>\n"
                + "</waitRuleList>\n";
        // @formatter:on
        Variables variables = new DefaultVariables();
        Platform platform = new Platform(Name.LINUX, System.getProperty("os.version"));
        final String MICROSERVICE_IDENTIFIER = "admin";
        final String MICROSERVICE_COUNT_IDENTIFIER = MICROSERVICE_IDENTIFIER;

        // Set Test Values
        final String count = MICROSERVICE_COUNT_IDENTIFIER + ".count";
        final String uriName = MICROSERVICE_IDENTIFIER + ".host";
        final String portName = MICROSERVICE_IDENTIFIER + ".port";
        final String componentListName = MICROSERVICE_IDENTIFIER + InjectWaitRules.INSTANCE_LIST_SUFFIX;
        final String waitRuleListName = MICROSERVICE_IDENTIFIER + InjectWaitRules.WAIT_RULE_LIST_SUFFIX;

        InstallData installData = new InstallData(variables, platform);
        variables.set(count, "2");
        variables.set(uriName + ".1", "localhost");
        variables.set(portName + ".1", "3456");
        variables.set(uriName + ".2", "127.0.0.1");
        variables.set(portName + ".2", "3457");
        // @formatter:off
        variables.set(componentListName, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<componentConfigList>\n"
                + "    <componentConfig id=\"0\">\n"
                + "        <host>localhost</host>\n"
                + "        <port>3456</port>\n"
                + "    </componentConfig>\n"
                + "    <componentConfig id=\"1\">\n"
                + "        <host>127.0.0.1</host>\n"
                + "        <port>3457</port>\n"
                + "    </componentConfig>\n"
                + "</componentConfigList>\n");
        variables.set("config.instanceList", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<componentConfigList>\n"
                + "    <componentConfig id=\"0\">\n"
                + "        <host>localhost</host>\n"
                + "        <port>1111</port>\n"
                + "    </componentConfig>\n"
                + "    <componentConfig id=\"1\">\n"
                + "        <host>127.0.0.1</host>\n"
                + "        <port>2222</port>\n"
                + "    </componentConfig>\n"
                + "</componentConfigList>\n");
        variables.set("registry.instanceList", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<componentConfigList>\n"
                + "    <componentConfig id=\"0\">\n"
                + "        <host>localhost</host>\n"
                + "        <port>3333</port>\n"
                + "    </componentConfig>\n"
                + "    <componentConfig id=\"1\">\n"
                + "        <host>127.0.0.1</host>\n"
                + "        <port>3333</port>\n"
                + "    </componentConfig>\n"
                + "</componentConfigList>\n");
        // @formatter:on
        InjectWaitRules validator = new InjectWaitRules();

        Status status = validator.validateData(installData);
        Assert.assertEquals(status, Status.OK);

        // Check transformation
        String valueOfComponentListName = installData.getVariable(waitRuleListName);
        Assert.assertEquals(EXPECTED_VALUE, valueOfComponentListName);
    }
}
