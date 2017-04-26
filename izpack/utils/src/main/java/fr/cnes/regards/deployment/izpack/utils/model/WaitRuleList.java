/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.utils.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * List of {@link WaitRule}s.
 *
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
@XmlRootElement
public class WaitRuleList {

    /**
     * items field
     *
     * @since 1.0.0
     */
    @XmlElement(name = "waitRule")
    private final List<WaitRule> items = new ArrayList<>();

    /**
     * Add wait rule to the list
     *
     * @param pWaitRule
     *            wait rule to add
     * @since 1.0.0
     */
    public void add(WaitRule pWaitRule) {
        items.add(pWaitRule);
    }

    /**
     * @return the items
     */
    public List<WaitRule> getItems() {
        return items;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "WaitRuleList [items=" + items + "]";
    }

}
