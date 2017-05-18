/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.model;

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

    /**
     * @see java.lang.Object#toString()
     * @since 1.0.0
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        boolean isNotFirst = false;
        for (WaitRule waitRule : items) {
            if (isNotFirst) {
                builder.append("\n");
            } else {
                isNotFirst = true;
            }

            builder.append(waitRule.toString());
        }
        return builder.toString();
    }

}
