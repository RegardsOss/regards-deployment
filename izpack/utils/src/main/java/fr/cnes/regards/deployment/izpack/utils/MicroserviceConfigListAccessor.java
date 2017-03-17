package fr.cnes.regards.deployment.izpack.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;

import javax.xml.bind.JAXB;

import fr.cnes.regards.deployment.izpack.utils.model.MicroserviceConfigList;

public final class MicroserviceConfigListAccessor {

    public static void writeToFile(String xmlString, Path outputXmlFile) {
        MicroserviceConfigList microserviceConfigList = readFromString(xmlString);
        JAXB.marshal(microserviceConfigList, outputXmlFile.toFile());
    }

    public static void writeToFile(MicroserviceConfigList microserviceConfigList, Path outputXmlFile) {
        JAXB.marshal(microserviceConfigList, outputXmlFile.toFile());
    }

    public static String writeToString(MicroserviceConfigList microserviceConfigList) {
        StringWriter sw = new StringWriter();
        JAXB.marshal(microserviceConfigList, sw);

        return sw.toString();
    }

    public static MicroserviceConfigList readFromString(String xmlString) {
        MicroserviceConfigList result;
        StringReader sr = new StringReader(xmlString);

        result = JAXB.unmarshal(sr, MicroserviceConfigList.class);
        return result;
    }

    public static MicroserviceConfigList readFromFile(Path inputXmlFile) {
        MicroserviceConfigList result;

        result = JAXB.unmarshal(inputXmlFile.toFile(), MicroserviceConfigList.class);
        return result;
    }
}
