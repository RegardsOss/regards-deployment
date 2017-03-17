package fr.cnes.regards.deployment.izpack.utils.model;

import javax.xml.bind.annotation.XmlAttribute;

public class MicroserviceConfig {

    private int id;

    private String host;

    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
