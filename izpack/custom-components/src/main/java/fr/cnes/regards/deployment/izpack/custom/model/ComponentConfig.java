/*
 * Copyright 2017 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
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
 * @since 1.0.0
 */
public class ComponentConfig {

    /**
     * id field.
     *
     * @since 1.0.0
     */
    private int id;

    /**
     * host field.
     *
     * @since 1.0.0
     */
    private String host;

    /**
     * port field.
     *
     * @since 1.0.0
     */
    private int port;

    /**
     * Xmx field.
     *
     * @since 1.0.0
     */
    private String xmx;

    /**
     * Get method.
     *
     * @since 1.0.0
     */
    public int getPort() {
        return port;
    }

    /**
     * Get method.
     *
     * @since 1.0.0
     */
    public String getHost() {
        return host;
    }

    /**
     * Get method.
     *
     * @since 1.0.0
     */
    @XmlAttribute
    public int getId() {
        return id;
    }

    /**
     * Get xmx.
     *
     * @since 1.0.0
     */
    public String getXmx() {
        return xmx;
    }

    /**
     * Set method.
     *
     * @param port
     *            the port to set
     * @since 1.0.0
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Set method.
     *
     * @param id
     *            the id to set
     * @since 1.0.0
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set method.
     *
     * @param host
     *            the host to set
     * @since 1.0.0
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Set method.
     *
     * @param xmx
     *            the xmx to set
     * @since 1.0.0
     */
    public void setXmx(String xmx) {
        this.xmx = xmx;
    }

    /**
     * Methode surchargee
     *
     * @see java.lang.Object#toString()
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return "ComponentConfig [id=" + id + ", host=" + host + ", port=" + port + ", xmx=" + xmx + "]";
    }

}
