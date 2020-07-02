/*
 * Copyright 2017-2020 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
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
 * {@link List} of {@link WaitRule}
 *
 * @author Xavier-Alexandre Brochard
 * @author Christophe Mertz
 */
@XmlRootElement
public class WaitRuleList {

    /**
     * items field
     */
    @XmlElement(name = "waitRule")
    private final List<WaitRule> items = new ArrayList<>();

    /**
     * Add {@link WaitRule} to the {@link List}
     *
     * @param waitRule
     *            wait rule to add
     */
    public void add(WaitRule waitRule) {
        items.add(waitRule);
    }

    /**
     * @return the items
     */
    public List<WaitRule> getItems() {
        return items;
    }

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
