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
package fr.cnes.regards.deployment.izpack.custom.injector;

import com.izforge.izpack.api.data.InstallData;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentConfig;
import fr.cnes.regards.deployment.izpack.custom.model.ComponentConfigList;
import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;
import fr.cnes.regards.deployment.izpack.custom.xml.XmlAccessor;

/**
 * Injects the list of instances of a component into the installation data.
 * 
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 * @author Christophe Mertz
 */
public class InstanceListInjector implements InstallDataInjector {

    /**
     * The current {@link ComponentType}
     */
    protected final ComponentType type;

    /**
     * Identifier concatenated to pattern string to identify one specific component
     */
    protected final String entryKey;

    /**
     * First index of Izpack variable list is 1
     */
    private static final int IZPACK_FIRST_INDEX_OF_VARIABLE_LIST = 1;

    /**
     * Add a new instance
     * @param newType the instance's {@link ComponentType}
     * @param newEntryKey the an entry key for this new instance. Ideally matches the above type.
     */
    public InstanceListInjector(ComponentType newType, String newEntryKey) {
        super();
        type = newType;
        entryKey = newEntryKey;
    }

    @Override
    public void inject(InstallData installData) {
        final String count = type.getName() + ".count";
        final String uriName = entryKey + ".host";
        final String portName = entryKey + ".port";
        final String xmxName = entryKey + ".xmx";
        final String microserviceListName = type.getName() + ".instanceList";

        String countAsString = installData.getVariable(count);
        int nbComponents = Integer.valueOf(countAsString) + IZPACK_FIRST_INDEX_OF_VARIABLE_LIST;

        ComponentConfigList componentConfigList = new ComponentConfigList();
        // Concatenate all values in single one formatted
        for (int i = IZPACK_FIRST_INDEX_OF_VARIABLE_LIST; i < nbComponents; i++) {

            String uri = installData.getVariable(uriName + "." + i);
            int port = Integer.parseInt(installData.getVariable(portName + "." + i));
            String xmx = installData.getVariable(xmxName);

            ComponentConfig componentConfig = new ComponentConfig();
            componentConfig.setHost(uri);
            componentConfig.setPort(port);
            componentConfig.setId(i - 1);
            componentConfig.setXmx(xmx);

            componentConfigList.add(componentConfig);
        }
        // Set result in a new variable
        // Careful, if you use an existing one, it doesn't work.
        installData.setVariable(microserviceListName, XmlAccessor.writeToString(componentConfigList));
    }

}
