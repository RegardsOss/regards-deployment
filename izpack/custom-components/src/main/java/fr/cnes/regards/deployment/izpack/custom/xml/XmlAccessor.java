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

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;

import javax.xml.bind.JAXB;

import com.izforge.izpack.api.data.Info.Author;

/**
 * Class ComponentConfigListAccessor
 *
 * Read or write ComponentConfigList POJO in different XML format (File, String, ...).
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 * @author Christophe Mertz
 */
public final class XmlAccessor {

    /**
     * Private constructor in order to hide the default one
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
     */
    public static <T> T readFromString(String xmlString, Class<T> pType) {
        StringReader sr = new StringReader(xmlString);

        return  (T) JAXB.unmarshal(sr, pType);
    }

    /**
     * Read from XML File and return POJO
     *
     * @param inputXmlFile
     * @return output POJO
     */
    public static <T> T readFromFile(Path inputXmlFile, Class<T> pType) {
        return (T) JAXB.unmarshal(inputXmlFile.toFile(), pType);
    }
}
