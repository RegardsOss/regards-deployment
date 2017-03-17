package fr.cnes.regards.deployment.izpack.utils;

import java.nio.file.Paths;

import org.junit.Test;

import fr.cnes.regards.deployment.izpack.utils.model.MicroserviceConfigList;

public class MicroserviceConfigAccessorTest {

    @Test
    public void testReadFromStringWriteToFile() {
        MicroserviceConfigList microserviceConfigList = MicroserviceConfigListAccessor
                .readFromString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                        + "<microserviceConfigList>\n" + "<microserviceConfig id=\"0\">\n" + "<host>localhost</host>\n"
                        + "<port>7776</port>\n" + "</microserviceConfig>\n" + "<microserviceConfig id=\"1\">\n"
                        + "<host>localhost1</host>\n" + "<port>7777</port>\n" + "</microserviceConfig>\n"
                        + "</microserviceConfigList>\n");

        MicroserviceConfigListAccessor.writeToFile(microserviceConfigList, Paths.get("target/microserviceConfig.xml"));
    }

    @Test
    public void testReadFromStringWriteToString() {

        MicroserviceConfigList microserviceConfigList = MicroserviceConfigListAccessor
                .readFromString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                        + "<microserviceConfigList>\n" + "<microserviceConfig id=\"0\">\n"
                        + "<host>localhost3</host>\n" + "<port>7778</port>\n" + "</microserviceConfig>\n"
                        + "<microserviceConfig id=\"1\">\n" + "<host>localhost4</host>\n" + "<port>7779</port>\n"
                        + "</microserviceConfig>\n" + "</microserviceConfigList>\n");

        String pojoString = MicroserviceConfigListAccessor.writeToString(microserviceConfigList);
        System.out.println(pojoString);
    }

    @Test
    public void testReadFromFile() {

        MicroserviceConfigList microserviceConfigList = MicroserviceConfigListAccessor.readFromFile(Paths
                .get("test/context/incoming/microserviceConfig.xml"));

        System.out.println(microserviceConfigList.toString());
    }

    @Test
    public void testWriteToFileFromString() {
        MicroserviceConfigListAccessor.writeToFile("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<microserviceConfigList>\n" + "<microserviceConfig id=\"0\">\n" + "<host>localhost5</host>\n"
                + "<port>7780</port>\n" + "</microserviceConfig>\n" + "<microserviceConfig id=\"1\">\n"
                + "<host>localhost6</host>\n" + "<port>7781</port>\n" + "</microserviceConfig>\n"
                + "</microserviceConfigList>\n", Paths.get("target/microserviceConfigFromString.xml"));
    }
}
