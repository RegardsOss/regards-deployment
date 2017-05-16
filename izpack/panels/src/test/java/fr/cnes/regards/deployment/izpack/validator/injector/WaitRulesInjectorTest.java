/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator.injector;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.data.Pack;
import com.izforge.izpack.api.data.Variables;
import com.izforge.izpack.core.data.DefaultVariables;
import com.izforge.izpack.util.Platform;
import com.izforge.izpack.util.Platform.Name;

import fr.cnes.regards.deployment.izpack.utils.model.ComponentType;

/**
 * Test class for {@link WaitRulesInjector}.
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
public class WaitRulesInjectorTest {

    @Test
    public void testInject() {
        // @formatter:off
        String EXPECTED_VALUE_0 = ""
                + "    <waitRule>\n"
                + "        <host>localhost</host>\n"
                + "        <port>1111</port>\n"
                + "        <timeout>30</timeout>\n"
                + "    </waitRule>\n";
        String EXPECTED_VALUE_1 = ""
                + "    <waitRule>\n"
                + "        <host>127.0.0.1</host>\n"
                + "        <port>2222</port>\n"
                + "        <timeout>30</timeout>\n"
                + "    </waitRule>\n";
        String EXPECTED_VALUE_2 = ""
                + "    <waitRule>\n"
                + "        <host>localhost</host>\n"
                + "        <port>3333</port>\n"
                + "        <timeout>90</timeout>\n"
                + "    </waitRule>\n";
        String EXPECTED_VALUE_3 = ""
                + "    <waitRule>\n"
                + "        <host>127.0.0.1</host>\n"
                + "        <port>3333</port>\n"
                + "        <timeout>90</timeout>\n"
                + "    </waitRule>\n";

        // @formatter:on
        Variables variables = new DefaultVariables();
        Platform platform = new Platform(Name.LINUX, System.getProperty("os.version"));
        ComponentType type = ComponentType.ADMIN;

        // Set Test Values
        final String count = type.getName() + ".count";
        final String uriName = type.getName() + ".host";
        final String portName = type.getName() + ".port";
        final String componentListName = type.getName() + WaitRulesInjector.INSTANCE_LIST_SUFFIX;
        final String waitRuleListName = type.getName() + WaitRulesInjector.WAIT_RULE_LIST_SUFFIX;

        InstallData installData = new com.izforge.izpack.installer.data.InstallData(variables, platform);
        variables.set(count, "2");
        variables.set(uriName + ".1", "localhost");
        variables.set(portName + ".1", "3456");
        variables.set(uriName + ".2", "127.0.0.1");
        variables.set(portName + ".2", "3457");

        List<Pack> selectedPacks = new ArrayList<>();
        selectedPacks
                .add(new Pack("config", portName, uriName, null, null, true, true, false, waitRuleListName, false, 0));
        selectedPacks.add(new Pack("registry", portName, uriName, null, null, true, true, false, waitRuleListName,
                false, 0));
        installData.setSelectedPacks(selectedPacks);

        //        variables.set("izpack.selected.config", "true");
        //        variables.set("izpack.selected.registry", "true");
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

        WaitRulesInjector injector = new WaitRulesInjector(ComponentType.ADMIN);
        injector.inject(installData);

        // Check transformation
        String valueOfComponentListName = installData.getVariable(waitRuleListName);
        Assert.assertTrue(valueOfComponentListName.contains(EXPECTED_VALUE_0));
        Assert.assertTrue(valueOfComponentListName.contains(EXPECTED_VALUE_1));
        Assert.assertTrue(valueOfComponentListName.contains(EXPECTED_VALUE_2));
        Assert.assertTrue(valueOfComponentListName.contains(EXPECTED_VALUE_3));

    }
}
