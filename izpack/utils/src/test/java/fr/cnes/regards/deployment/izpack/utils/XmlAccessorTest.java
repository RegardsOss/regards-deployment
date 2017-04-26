/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import fr.cnes.regards.deployment.izpack.utils.model.ComponentConfigList;

/**
 * Tests for class {@link XmlAccessor}
 *
 * @author CS
 * @since 1.0.0
 */
public class XmlAccessorTest {

    @Test
    public void testReadFromStringWriteToFile() {
        Path outputFile = Paths.get("target/componentConfig.xml");
        Path expendedFile = Paths.get("test/context/expected/testReadFromStringWriteToFile.xml");
        // @formatter:off
        ComponentConfigList componentConfigList = XmlAccessor
                .readFromString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                        + "<componentConfigList>\n"
                        + "    <componentConfig id=\"0\">\n"
                        + "        <host>localhost</host>\n"
                        + "        <port>7776</port>\n"
                        + "        <waitRuleList/>\n"
                        + "    </componentConfig>\n"
                        + "    <componentConfig id=\"1\">\n"
                        + "        <host>localhost1</host>\n"
                        + "        <port>7777</port>\n"
                        + "       <waitRuleList/>\n"
                        + "    </componentConfig>\n"
                        + "</componentConfigList>\n", ComponentConfigList.class);
        // @formatter:on
        XmlAccessor.writeToFile(componentConfigList, outputFile);
        try {
            Assert.assertEquals("The files differ!", FileUtils.readFileToString(outputFile.toFile(), "utf-8"),
                                FileUtils.readFileToString(expendedFile.toFile(), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
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
                + "    </componentConfig>\n"
                + "    <componentConfig id=\"1\">\n"
                + "        <host>localhost4</host>\n"
                + "        <port>7779</port>\n"
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
                        + "    </componentConfig>\n"
                        + "    <componentConfig id=\"1\">\n"
                        + "        <host>localhost4</host>\n"
                        + "        <port>7779</port>\n"
                        + "    </componentConfig>\n"
                        + "</componentConfigList>\n", ComponentConfigList.class);
        // @formatter:on

        String pojoString = XmlAccessor.writeToString(componentConfigList);
        Assert.assertEquals(EXPECTED_VALUE, pojoString);
    }

    @Test
    public void testReadFromFile() {
        final String EXPECTED_VALUE = "ComponentConfig [id=55, host=localhost, port=7776]\n"
                + "ComponentConfig [id=56, host=localhost1, port=7777]";
        ComponentConfigList componentConfigList = XmlAccessor
                .readFromFile(Paths.get("test/context/incoming/componentConfig.xml"), ComponentConfigList.class);

        String componentConfigListString = componentConfigList.toString();
        Assert.assertEquals(EXPECTED_VALUE, componentConfigListString);
    }

    @Test
    public void testWriteToFileFromString() {
        Path outputFile = Paths.get("target/componentConfigFromString.xml");
        Path expendedFile = Paths.get("test/context/expected/testReadFromStringWriteToString.xml");
        // @formatter:off
        XmlAccessor.writeToFile("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<componentConfigList>\n"
                + "    <componentConfig id=\"0\">\n"
                + "        <host>localhost5</host>\n"
                + "        <port>7780</port>\n"
                + "    </componentConfig>\n"
                + "    <componentConfig id=\"1\">\n"
                + "        <host>localhost6</host>\n"
                + "        <port>7781</port>\n"
                + "    </componentConfig>\n"
                + "</componentConfigList>\n", outputFile, ComponentConfigList.class);
        // @formatter:on

        try {
            Assert.assertEquals("The files differ!", FileUtils.readFileToString(outputFile.toFile(), "utf-8"),
                                FileUtils.readFileToString(expendedFile.toFile(), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
