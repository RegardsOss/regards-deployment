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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Component configurations list.
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 */
@XmlRootElement
public class ComponentConfigList {

    /**
     * items field
     */
    @XmlElement(name = "componentConfig")
    private final List<ComponentConfig> items = new ArrayList<>();

    /**
     * Add microservice configuration to the list
     *
     * @param componentConfig
     *            configuration to add
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
