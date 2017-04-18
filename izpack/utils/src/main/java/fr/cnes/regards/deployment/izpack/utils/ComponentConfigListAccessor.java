/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;

import javax.xml.bind.JAXB;

import fr.cnes.regards.deployment.izpack.utils.model.ComponentConfigList;

/**
 * Class ComponentConfigListAccessor
 *
 * Read or write ComponentConfigList POJO in different XML format (File, String, ...).
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
public final class ComponentConfigListAccessor {

    /**
     * Private contructor in order to hide the default one
     */
    private ComponentConfigListAccessor() {
        super();
    }

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
        ComponentConfigList componentConfigList = readFromString(xmlString);
        JAXB.marshal(componentConfigList, outputXmlFile.toFile());
    }

    /**
     * Write the POJO into a file
     *
     * @param componentConfigList
     *            input POJO
     * @param outputXmlFile
     *            output file path
     * @since 1.0.0
     */
    public static void writeToFile(ComponentConfigList componentConfigList, Path outputXmlFile) {
        JAXB.marshal(componentConfigList, outputXmlFile.toFile());
    }

    /**
     * Write the POJO to XML String
     *
     * @param componentConfigList
     *            input POJO
     * @return output XML String
     * @since 1.0.0
     */
    public static String writeToString(ComponentConfigList componentConfigList) {
        StringWriter sw = new StringWriter();
        JAXB.marshal(componentConfigList, sw);

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
    public static ComponentConfigList readFromString(String xmlString) {
        ComponentConfigList result;
        StringReader sr = new StringReader(xmlString);

        result = JAXB.unmarshal(sr, ComponentConfigList.class);
        return result;
    }

    /**
     * Read from XML File and return POJO
     *
     * @param inputXmlFile
     * @return output POJO
     * @since 1.0.0
     */
    public static ComponentConfigList readFromFile(Path inputXmlFile) {
        ComponentConfigList result;

        result = JAXB.unmarshal(inputXmlFile.toFile(), ComponentConfigList.class);
        return result;
    }
}
