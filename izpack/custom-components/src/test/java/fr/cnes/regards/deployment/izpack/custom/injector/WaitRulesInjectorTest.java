/*
 * Copyright 2017-2019 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
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
package fr.cnes.regards.deployment.izpack.custom.injector;

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

import fr.cnes.regards.deployment.izpack.custom.injector.WaitRulesInjector;
import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Test class for {@link WaitRulesInjector}.
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 * @author Christophe Mertz
 */
public class WaitRulesInjectorTest {

    @Test
    public void testInject() {
        // @formatter:off
        String EXPECTED_VALUE_1 = ""
                + "    <waitRule>\n"
                + "        <host>localhost</host>\n"
                + "        <port>3333</port>\n"
                + "        <timeout>180</timeout>\n"
                + "    </waitRule>\n";
        String EXPECTED_VALUE_3 = ""
                + "    <waitRule>\n"
                + "        <host>127.0.0.1</host>\n"
                + "        <port>3333</port>\n"
                + "        <timeout>180</timeout>\n"
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
        selectedPacks.add(new Pack(ComponentType.CONFIG.getName(), portName, uriName, null, null, true, true, false,
                waitRuleListName, false, 0));
        selectedPacks.add(new Pack(ComponentType.REGISTRY.getName(), portName, uriName, null, null, true, true, false,
                waitRuleListName, false, 0));
        selectedPacks.add(new Pack(ComponentType.ADMIN_INSTANCE.getName(), portName, uriName, null, null, true, true,
                false, waitRuleListName, false, 0));
        installData.setSelectedPacks(selectedPacks);

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
        variables.set("admin-instance.instanceList", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
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
        Assert.assertTrue(valueOfComponentListName.contains(EXPECTED_VALUE_1));
        Assert.assertTrue(valueOfComponentListName.contains(EXPECTED_VALUE_3));

    }
}
