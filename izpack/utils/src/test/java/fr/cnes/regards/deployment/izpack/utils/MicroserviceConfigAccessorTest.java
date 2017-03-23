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

import fr.cnes.regards.deployment.izpack.utils.model.MicroserviceConfigList;

/**
 * Class MicroserviceConfigAccessorTest
 *
 * Tests for class MicroserviceConfigAccessor.
 *
 * @author CS
 * @since 1.0.0
 */
public class MicroserviceConfigAccessorTest {

    @Test
    public void testReadFromStringWriteToFile() {
        Path outputFile = Paths.get("target/microserviceConfig.xml");
        Path expendedFile = Paths.get("test/context/expected/testReadFromStringWriteToFile.xml");

        MicroserviceConfigList microserviceConfigList = MicroserviceConfigListAccessor
                .readFromString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                        + "<microserviceConfigList>\n" + "<microserviceConfig id=\"0\">\n" + "<host>localhost</host>\n"
                        + "<port>7776</port>\n" + "</microserviceConfig>\n" + "<microserviceConfig id=\"1\">\n"
                        + "<host>localhost1</host>\n" + "<port>7777</port>\n" + "</microserviceConfig>\n"
                        + "</microserviceConfigList>\n");

        MicroserviceConfigListAccessor.writeToFile(microserviceConfigList, outputFile);
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
        final String EXPECTED_VALUE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<microserviceConfigList>\n" + "    <microserviceConfig id=\"0\">\n"
                + "        <host>localhost3</host>\n" + "        <port>7778</port>\n" + "    </microserviceConfig>\n"
                + "    <microserviceConfig id=\"1\">\n" + "        <host>localhost4</host>\n"
                + "        <port>7779</port>\n" + "    </microserviceConfig>\n" + "</microserviceConfigList>\n";

        MicroserviceConfigList microserviceConfigList = MicroserviceConfigListAccessor
                .readFromString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                        + "<microserviceConfigList>\n" + "<microserviceConfig id=\"0\">\n"
                        + "<host>localhost3</host>\n" + "<port>7778</port>\n" + "</microserviceConfig>\n"
                        + "<microserviceConfig id=\"1\">\n" + "<host>localhost4</host>\n" + "<port>7779</port>\n"
                        + "</microserviceConfig>\n" + "</microserviceConfigList>\n");

        String pojoString = MicroserviceConfigListAccessor.writeToString(microserviceConfigList);
        Assert.assertEquals(EXPECTED_VALUE, pojoString);
    }

    @Test
    public void testReadFromFile() {
        final String EXPECTED_VALUE = "MicroserviceConfig [id=55, host=localhost, port=7776]\n"
                + "MicroserviceConfig [id=56, host=localhost1, port=7777]";
        MicroserviceConfigList microserviceConfigList = MicroserviceConfigListAccessor.readFromFile(Paths
                                                                                                    .get("test/context/incoming/microserviceConfig.xml"));

        String microserviceConfigListString = microserviceConfigList.toString();
        Assert.assertEquals(EXPECTED_VALUE, microserviceConfigListString);
    }

    @Test
    public void testWriteToFileFromString() {
        Path outputFile = Paths.get("target/microserviceConfigFromString.xml");
        Path expendedFile = Paths.get("test/context/expected/testReadFromStringWriteToString.xml");
        MicroserviceConfigListAccessor.writeToFile("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<microserviceConfigList>\n" + "<microserviceConfig id=\"0\">\n" + "<host>localhost5</host>\n"
                + "<port>7780</port>\n" + "</microserviceConfig>\n" + "<microserviceConfig id=\"1\">\n"
                + "<host>localhost6</host>\n" + "<port>7781</port>\n" + "</microserviceConfig>\n"
                + "</microserviceConfigList>\n", outputFile);

        try {
            Assert.assertEquals("The files differ!", FileUtils.readFileToString(outputFile.toFile(), "utf-8"),
                                FileUtils.readFileToString(expendedFile.toFile(), "utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
