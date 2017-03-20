/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.utils.model;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Class MicroserviceConfig
 *
 * Contains a microservice configuration.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
public class MicroserviceConfig {

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
     * Methode surchargee
     *
     * @see java.lang.Object#toString()
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return "MicroserviceConfig [id=" + id + ", host=" + host + ", port=" + port + "]";
    }
}
