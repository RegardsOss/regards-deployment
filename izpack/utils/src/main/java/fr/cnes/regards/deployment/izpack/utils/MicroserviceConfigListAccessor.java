/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;

import javax.xml.bind.JAXB;

import fr.cnes.regards.deployment.izpack.utils.model.MicroserviceConfigList;

/**
 * Class MicroserviceConfigListAccessor
 *
 * Read or write MicroserviceConfigList POJO in different XML format (File, String, ...).
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public final class MicroserviceConfigListAccessor {

    /**
     * Read XML String and write into a file
     *
     * @param xmlString
     *            input XML String
     * @param outputXmlFile
     *            output file path
     * @since 1.0.0
     */
    public static void writeToFile(String xmlString, Path outputXmlFile) {
        MicroserviceConfigList microserviceConfigList = readFromString(xmlString);
        JAXB.marshal(microserviceConfigList, outputXmlFile.toFile());
    }

    /**
     * Write the POJO into a file
     *
     * @param microserviceConfigList
     *            input POJO
     * @param outputXmlFile
     *            output file path
     * @since 1.0.0
     */
    public static void writeToFile(MicroserviceConfigList microserviceConfigList, Path outputXmlFile) {
        JAXB.marshal(microserviceConfigList, outputXmlFile.toFile());
    }

    /**
     * Write the POJO to XML String
     *
     * @param microserviceConfigList
     *            input POJO
     * @return output XML String
     * @since 1.0.0
     */
    public static String writeToString(MicroserviceConfigList microserviceConfigList) {
        StringWriter sw = new StringWriter();
        JAXB.marshal(microserviceConfigList, sw);

        return sw.toString();
    }

    /**
     * Read from XML String and return POJO
     *
     * @param xmlString
     *            input XML String
     * @return output POJO
     * @since 1.0.0
     */
    public static MicroserviceConfigList readFromString(String xmlString) {
        MicroserviceConfigList result;
        StringReader sr = new StringReader(xmlString);

        result = JAXB.unmarshal(sr, MicroserviceConfigList.class);
        return result;
    }

    /**
     * Read from XML File and return POJO
     *
     * @param inputXmlFile
     * @return output POJO
     * @since 1.0.0
     */
    public static MicroserviceConfigList readFromFile(Path inputXmlFile) {
        MicroserviceConfigList result;

        result = JAXB.unmarshal(inputXmlFile.toFile(), MicroserviceConfigList.class);
        return result;
    }
}
