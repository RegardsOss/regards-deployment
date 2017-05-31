/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Component configurations list.
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
@XmlRootElement
public class ComponentConfigList {

    /**
     * items field
     *
     * @since 1.0.0
     */
    @XmlElement(name = "componentConfig")
    private final List<ComponentConfig> items = new ArrayList<>();

    /**
     * Add microservice configuration to the list
     *
     * @param componentConfig
     *            configuration to add
     * @since 1.0.0
     */
    public void add(ComponentConfig componentConfig) {
        items.add(componentConfig);
    }

    /**
     * Get a specific configuration from the list
     *
     * @param id
     *            of the configuration
     * @return configuration corresponded to the id, null if none exists
     * @since 1.0.0
     */
    public ComponentConfig get(int id) {
        for (ComponentConfig componentConfig : items) {
            if (componentConfig.getId() == id) {
                return componentConfig;
            }
        }
        // Return null if index is not found
        return null;
    }

    /**
     * @return the items
     */
    public List<ComponentConfig> getItems() {
        return items;
    }

    /**
     * Methode surchargee
     *
     * @see java.lang.Object#toString()
     * @since 1.0.0
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        boolean isNotFirst = false;
        for (ComponentConfig componentConfig : items) {
            if (isNotFirst) {
                builder.append("\n");
            } else {
                isNotFirst = true;
            }

            builder.append(componentConfig.toString());
        }
        return builder.toString();
    }

}
