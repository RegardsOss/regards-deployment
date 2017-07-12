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
