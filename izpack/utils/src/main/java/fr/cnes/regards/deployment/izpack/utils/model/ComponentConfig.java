/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.utils.model;

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
     * waitRuleList field.
     *
     * @since 1.0.0
     */
    private WaitRuleList waitRuleList = new WaitRuleList();

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
     * @return the waitRules
     */
    public WaitRuleList getWaitRuleList() {
        return waitRuleList;
    }

    /**
     * @param pWaitRuleList the waitRules to set
     */
    public void setWaitRuleList(WaitRuleList pWaitRuleList) {
        waitRuleList = pWaitRuleList;
    }

    /**
     * Add wait rule
     * @param pWaitRule
     * @return
     * @see java.util.List#add(java.lang.Object)
     */
    public void addWaitRule(WaitRule pWaitRule) {
        waitRuleList.add(pWaitRule);
    }

    /**
     * Methode surchargee
     *
     * @see java.lang.Object#toString()
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return "ComponentConfig [id=" + id + ", host=" + host + ", port=" + port + ", waitRules=" + waitRuleList + "]";
    }

}
