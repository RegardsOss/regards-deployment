/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.utils.model;

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
