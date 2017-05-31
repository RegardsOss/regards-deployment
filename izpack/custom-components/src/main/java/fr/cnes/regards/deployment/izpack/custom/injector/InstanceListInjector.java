/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.injector;

import com.izforge.izpack.api.data.InstallData;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentConfig;
import fr.cnes.regards.deployment.izpack.custom.model.ComponentConfigList;
import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;
import fr.cnes.regards.deployment.izpack.custom.xml.XmlAccessor;

/**
 * Injects the list of instances of a component into the installation data.
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 */
public class InstanceListInjector implements InstallDataInjector {

    /**
     * Component type
     */
    protected final ComponentType type;

    /**
     * Identifier concatenated to pattern string to identify one specific
     * component
     */
    protected final String entryKey;

    /**
     * First index of Izpack variable list is 1
     */
    private static final int IZPACK_FIRST_INDEX_OF_VARIABLE_LIST = 1;

    /**
     * @param pType
     * @param pEntryKey
     */
    public InstanceListInjector(ComponentType pType, String pEntryKey) {
        super();
        type = pType;
        entryKey = pEntryKey;
    }

    /* (non-Javadoc)
     * @see fr.cnes.regards.deployment.izpack.custom.InstallDataInjector#inject(com.izforge.izpack.api.data.InstallData)
     */
    @Override
    public void inject(InstallData pInstallData) {
        final String count = type.getName() + ".count";
        final String uriName = entryKey + ".host";
        final String portName = entryKey + ".port";
        final String microserviceListName = type.getName() + ".instanceList";

        String countAsString = pInstallData.getVariable(count);
        int nbComponents = Integer.valueOf(countAsString) + IZPACK_FIRST_INDEX_OF_VARIABLE_LIST;

        ComponentConfigList componentConfigList = new ComponentConfigList();
        // Concatenate all values in single one formatted
        for (int i = IZPACK_FIRST_INDEX_OF_VARIABLE_LIST; i < nbComponents; i++) {
            ComponentConfig componentConfig = new ComponentConfig();
            String uri = pInstallData.getVariable(uriName + "." + i);
            int port = Integer.parseInt(pInstallData.getVariable(portName + "." + i));

            componentConfig.setHost(uri);
            componentConfig.setPort(port);
            componentConfig.setId(i - 1);

            componentConfigList.add(componentConfig);
        }
        // Set result in a new variable
        // Careful, if you use an existing one, it doesn't work.
        pInstallData.setVariable(microserviceListName, XmlAccessor.writeToString(componentConfigList));
    }

}
