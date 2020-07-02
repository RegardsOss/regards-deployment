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
package fr.cnes.regards.deployment.izpack.custom.xml;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentConfigList;
import fr.cnes.regards.deployment.izpack.custom.model.WaitRuleList;

/**
 * Tests for class {@link XmlAccessor}
 *
 * @author Christophe Mertz
 */
public class XmlAccessorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlAccessorTest.class);

    @Test
    public void testReadFromStringWriteToFile() {
        Path outputFile = Paths.get("src/test/resources/componentConfig.xml");
        Path expendedFile = Paths.get("src/test/resources/expectedTestReadFromStringWriteToFile.xml");
        // @formatter:off
        ComponentConfigList componentConfigList = XmlAccessor
                .readFromString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                        + "<componentConfigList>\n"
                        + "    <componentConfig id=\"0\">\n"
                        + "        <host>localhost</host>\n"
                        + "        <port>7776</port>\n"
                        + "        <xmx>128m</xmx>\n"
                        + "        <waitRuleList/>\n"
                        + "    </componentConfig>\n"
                        + "    <componentConfig id=\"1\">\n"
                        + "        <host>localhost1</host>\n"
                        + "        <port>7777</port>\n"
                        + "        <xmx>333m</xmx>\n"
                        + "       <waitRuleList/>\n"
                        + "    </componentConfig>\n"
                        + "</componentConfigList>\n", ComponentConfigList.class);
        // @formatter:on
        XmlAccessor.writeToFile(componentConfigList, outputFile);
        try {
            Assert.assertEquals("The files differ!", FileUtils.readFileToString(outputFile.toFile(), "utf-8"),
                                FileUtils.readFileToString(expendedFile.toFile(), "utf-8"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    public void testReadFromStringWriteToString() {
        // @formatter:off
        final String EXPECTED_VALUE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<componentConfigList>\n"
                + "    <componentConfig id=\"0\">\n"
                + "        <host>localhost3</host>\n"
                + "        <port>7778</port>\n"
                + "        <xmx>123m</xmx>\n"
                + "    </componentConfig>\n"
                + "    <componentConfig id=\"1\">\n"
                + "        <host>localhost4</host>\n"
                + "        <port>7779</port>\n"
                + "        <xmx>456m</xmx>\n"
                + "    </componentConfig>\n"
                + "</componentConfigList>\n";
        // @formatter:on

        // @formatter:off
        ComponentConfigList componentConfigList = XmlAccessor
                .readFromString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                        + "<componentConfigList>\n"
                        + "    <componentConfig id=\"0\">\n"
                        + "        <host>localhost3</host>\n"
                        + "        <port>7778</port>\n"
                        + "        <xmx>123m</xmx>\n"
                        + "    </componentConfig>\n"
                        + "    <componentConfig id=\"1\">\n"
                        + "        <host>localhost4</host>\n"
                        + "        <port>7779</port>\n"
                        + "        <xmx>456m</xmx>\n"
                        + "    </componentConfig>\n"
                        + "</componentConfigList>\n", ComponentConfigList.class);
        // @formatter:on

        String pojoString = XmlAccessor.writeToString(componentConfigList);
        Assert.assertEquals(EXPECTED_VALUE, pojoString);
    }

    @Test
    public void testReadFromFile_WithComponentConfig() {
        final String EXPECTED_VALUE = "ComponentConfig [id=55, host=localhost, port=7776, xmx=1234]\n"
                + "ComponentConfig [id=56, host=localhost1, port=7777, xmx=5678]";
        ComponentConfigList componentConfigList = XmlAccessor
                .readFromFile(Paths.get("src/test/resources/incomingComponentConfig.xml"), ComponentConfigList.class);

        String componentConfigListString = componentConfigList.toString();
        Assert.assertEquals(EXPECTED_VALUE, componentConfigListString);
    }

    @Test
    public void testReadFromFile_WithWaitRule() {
        final String EXPECTED_VALUE = "WaitRule [host=localhost, port=1111, timeout=30]\n"
                + "WaitRule [host=localhost, port=2222, timeout=90]";
        WaitRuleList waitRuleList = XmlAccessor.readFromFile(Paths.get("src/test/resources/incomingWaitRuleList.xml"),
                                                             WaitRuleList.class);

        String waitRuleListString = waitRuleList.toString();
        Assert.assertEquals(EXPECTED_VALUE, waitRuleListString);
    }

    @Test
    public void testWriteToFileFromString() {
        Path outputFile = Paths.get("src/test/resources/componentConfigFromString.xml");
        Path expendedFile = Paths.get("src/test/resources/expectedTestReadFromStringWriteToString.xml");
        // @formatter:off
        XmlAccessor.writeToFile("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<componentConfigList>\n"
                + "    <componentConfig id=\"0\">\n"
                + "        <host>localhost5</host>\n"
                + "        <port>7780</port>\n"
                + "        <xmx>512m</xmx>\n"
                + "    </componentConfig>\n"
                + "    <componentConfig id=\"1\">\n"
                + "        <host>localhost6</host>\n"
                + "        <port>7781</port>\n"
                + "        <xmx>733m</xmx>\n"
                + "    </componentConfig>\n"
                + "</componentConfigList>\n", outputFile, ComponentConfigList.class);
        // @formatter:on

        try {
            Assert.assertEquals("The files differ!", FileUtils.readFileToString(outputFile.toFile(), "utf-8"),
                                FileUtils.readFileToString(expendedFile.toFile(), "utf-8"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
