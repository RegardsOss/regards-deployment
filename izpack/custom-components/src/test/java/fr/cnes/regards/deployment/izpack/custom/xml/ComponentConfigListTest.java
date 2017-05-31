/*
 * LICENSE_PLACEHOLDER
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
