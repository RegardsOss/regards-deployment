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
package fr.cnes.regards.deployment.izpack.custom.xml;

import org.junit.Assert;
import org.junit.Test;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentConfig;
import fr.cnes.regards.deployment.izpack.custom.model.ComponentConfigList;

/**
 * Unit test for {@link ComponentConfigList}
 *
 * @author Xavier-Alexandre Brochard
 */
public class ComponentConfigListTest {

    /**
     * Test method for {@link fr.cnes.regards.deployment.izpack.custom.model.ComponentConfigList#get(int)}.
     */
    @Test
    public final void testGet() {
        ComponentConfigList list = new ComponentConfigList();

        Integer idOfFirst = 0;
        ComponentConfig first = new ComponentConfig();
        first.setId(idOfFirst);

        Integer idOfSecond = 1;
        ComponentConfig second = new ComponentConfig();
        second.setId(idOfSecond);

        list.add(first);
        list.add(second);

        Assert.assertEquals(list.get(idOfFirst), first);
        Assert.assertEquals(list.get(idOfSecond), second);
    }

}
