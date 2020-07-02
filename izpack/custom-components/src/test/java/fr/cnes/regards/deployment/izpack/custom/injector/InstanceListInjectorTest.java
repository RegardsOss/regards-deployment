/*
 * Copyright 2017-2020 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
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

import org.junit.Assert;
import org.junit.Test;

import com.izforge.izpack.api.data.InstallData;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;
import fr.cnes.regards.deployment.izpack.custom.utils.DummyInstallDataFactory;

/**
 * Test class for {@link InstanceListInjector}.
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
public class InstanceListInjectorTest {

    @Test
    public void testInject() {
        // @formatter:off
        final String EXPECTED_VALUE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<componentConfigList>\n"
                + "    <componentConfig id=\"0\">\n"
                + "        <host>localhost</host>\n"
                + "        <port>3456</port>\n"
                + "        <xmx>5432m</xmx>\n"
                + "    </componentConfig>\n"
                + "    <componentConfig id=\"1\">\n"
                + "        <host>127.0.0.1</host>\n"
                + "        <port>3457</port>\n"
                + "        <xmx>5432m</xmx>\n"
                + "    </componentConfig>\n"
                + "</componentConfigList>\n";
        // @formatter:on
        ComponentType type = ComponentType.REGISTRY;
        String entryKey = "regards.config.cloud.registry";
        InstallData installData = DummyInstallDataFactory.buildWithRegistry(type);

        InstanceListInjector injector = new InstanceListInjector(type, entryKey);
        injector.inject(installData);

        // Check transformation
        String valueOfComponentListName = installData.getVariable(type.getName() + ".instanceList");
        Assert.assertEquals(EXPECTED_VALUE, valueOfComponentListName);
    }

}
