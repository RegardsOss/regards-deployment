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

/**
 * Represents a waited component
 * @author Xavier-Alexandre Brochard
 */
public class WaitRule {

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
     * timeout in seconds
     *
     * @since 1.0.0
     */
    private int timeout;

    /**
     * Default constructor
     */
    public WaitRule() {
        super();
    }

    /**
     * Constructor
     * @param pComponentConfig
     * @param pTimeout
     */
    public WaitRule(ComponentConfig pComponentConfig, int pTimeout) {
        super();
        host = pComponentConfig.getHost();
        port = pComponentConfig.getPort();
        timeout = pTimeout;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * @param pHost the host to set
     */
    public void setHost(String pHost) {
        host = pHost;
    }

    /**
     * @param pPort the port to set
     */
    public void setPort(int pPort) {
        port = pPort;
    }

    /**
     * @param pTimeout the timeout to set
     */
    public void setTimeout(int pTimeout) {
        timeout = pTimeout;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "WaitRule [host=" + host + ", port=" + port + ", timeout=" + timeout + "]";
    }

}
