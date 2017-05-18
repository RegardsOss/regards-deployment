/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.injector;

import org.junit.Assert;
import org.junit.Test;

import com.izforge.izpack.api.data.InstallData;

import fr.cnes.regards.deployment.izpack.custom.injector.InstanceListInjector;
import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * Test class for {@link InstanceListInjector}.
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 * @since 1.0.0
 */
public class InstanceListInjectorTest {

    @Test
    public void testInject() {
        // @formatter:off
        final String EXPECTED_VALUE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<componentConfigList>\n"
                + "    <componentConfig id=\"0\">\n"
                + "        <host>localhost</host>\n"
                + "        <port>3456</port>\n"
                + "    </componentConfig>\n"
                + "    <componentConfig id=\"1\">\n"
                + "        <host>127.0.0.1</host>\n"
                + "        <port>3457</port>\n"
                + "    </componentConfig>\n"
                + "</componentConfigList>\n";
        // @formatter:on
        ComponentType type = ComponentType.REGISTRY;
        String entryKey = "regards.config.cloud.registry";
        InstallData installData = InjectorTestUtils.buildDummyInstallData(type);

        InstanceListInjector injector = new InstanceListInjector(type, entryKey);
        injector.inject(installData);

        // Check transformation
        String valueOfComponentListName = installData.getVariable(type.getName() + ".instanceList");
        Assert.assertEquals(EXPECTED_VALUE, valueOfComponentListName);
    }

}
