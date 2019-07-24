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

/**
 * Represents a waited component
 * @author Xavier-Alexandre Brochard
 */
public class WaitRule {

    /**
     * host field.
     */
    private String host;

    /**
     * port field.
     */
    private int port;

    /**
     * timeout in seconds
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
     * @param componentConfig the {@link ComponentType} to wait
     * @param timeout the time delay to wait 
     */
    public WaitRule(ComponentConfig componentConfig, int tOut) {
        super();
        host = componentConfig.getHost();
        port = componentConfig.getPort();
        timeout = tOut;
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
     * @param newHost the host to set
     */
    public void setHost(String newHost) {
        host = newHost;
    }

    /**
     * @param newPort the port to set
     */
    public void setPort(int newPort) {
        port = newPort;
    }

    /**
     * @param tOut the timeout to set
     */
    public void setTimeout(int tOut) {
        timeout = tOut;
    }

    @Override
    public String toString() {
        return "WaitRule [host=" + host + ", port=" + port + ", timeout=" + timeout + "]";
    }

}
