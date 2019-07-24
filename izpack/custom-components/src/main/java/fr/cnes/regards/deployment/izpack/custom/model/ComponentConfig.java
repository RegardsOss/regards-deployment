/*
 * Copyright 2017-2019 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
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
package fr.cnes.regards.deployment.izpack.custom.model;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Contains a component configuration.
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 */
public class ComponentConfig {

    /**
     * id field.
     */
    private int id;

    /**
     * host field.
     */
    private String host;

    /**
     * port field.
     */
    private int port;

    /**
     * Xmx field.
     */
    private String xmx;

    /**
     * Get method.
     */
    public int getPort() {
        return port;
    }

    /**
     * Get method.
     */
    public String getHost() {
        return host;
    }

    /**
     * Get method.
     */
    @XmlAttribute
    public int getId() {
        return id;
    }

    /**
     * Get xmx.
     */
    public String getXmx() {
        return xmx;
    }

    /**
     * Set method.
     *
     * @param port
     *            the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Set method.
     *
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set method.
     *
     * @param host
     *            the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Set method.
     *
     * @param xmx
     *            the xmx to set
     */
    public void setXmx(String xmx) {
        this.xmx = xmx;
    }

    @Override
    public String toString() {
        return "ComponentConfig [id=" + id + ", host=" + host + ", port=" + port + ", xmx=" + xmx + "]";
    }

}
