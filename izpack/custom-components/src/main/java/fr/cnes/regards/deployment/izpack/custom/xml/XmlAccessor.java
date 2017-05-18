/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.xml;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;

import javax.xml.bind.JAXB;

/**
 * Class ComponentConfigListAccessor
 *
 * Read or write ComponentConfigList POJO in different XML format (File, String, ...).
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
public final class XmlAccessor {

    /**
     * Private contructor in order to hide the default one
     */
    private XmlAccessor() {
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
    public static <T> void writeToFile(String xmlString, Path outputXmlFile, Class<T> pType) {
        T pObject = readFromString(xmlString, pType);
        JAXB.marshal(pObject, outputXmlFile.toFile());
    }

    /**
     * Write the POJO into a file
     * @param <T>
     *
     * @param pObject
     *            input POJO
     * @param outputXmlFile
     *            output file path
     * @since 1.0.0
     */
    public static <T> void writeToFile(T pObject, Path outputXmlFile) {
        JAXB.marshal(pObject, outputXmlFile.toFile());
    }

    /**
     * Write the POJO to XML String
     * @param <T>
     *
     * @param pObject
     *            input POJO
     * @return output XML String
     * @since 1.0.0
     */
    public static <T> String writeToString(T pObject) {
        StringWriter sw = new StringWriter();
        JAXB.marshal(pObject, sw);

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
    public static <T> T readFromString(String xmlString, Class<T> pType) {
        T result;
        StringReader sr = new StringReader(xmlString);

        result = JAXB.unmarshal(sr, pType);
        return result;
    }

    /**
     * Read from XML File and return POJO
     *
     * @param inputXmlFile
     * @return output POJO
     * @since 1.0.0
     */
    public static <T> T readFromFile(Path inputXmlFile, Class<T> pType) {
        T result;

        result = JAXB.unmarshal(inputXmlFile.toFile(), pType);
        return result;
    }
}
