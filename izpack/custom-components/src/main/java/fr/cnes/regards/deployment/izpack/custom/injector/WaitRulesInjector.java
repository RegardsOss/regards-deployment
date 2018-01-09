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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.izforge.izpack.api.data.InstallData;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentConfigList;
import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;
import fr.cnes.regards.deployment.izpack.custom.model.WaitRule;
import fr.cnes.regards.deployment.izpack.custom.model.WaitRuleList;
import fr.cnes.regards.deployment.izpack.custom.xml.XmlAccessor;

/**
 * Injects the {@link List} of wait rules of a component into the installation data.
 * 
 * @author Xavier-Alexandre Brochard
 * @author Christophe Mertz
 */
public class WaitRulesInjector implements InstallDataInjector {

    /**
     * The current {@link ComponentType}
     */
    private final ComponentType type;

    /**
     * Suffix of variables ".instanceList" on the install data
     */
    public static final String INSTANCE_LIST_SUFFIX = ".instanceList";

    /**
     * Suffix of variables ".waitRuleList" on the install data
     */
    public static final String WAIT_RULE_LIST_SUFFIX = ".waitRuleList";

    /**
     * Define here which  component needs to wait for which other.
     * For example:
     * We want to tell "Component catalog needs to wait for
     * - component config with 30s timeout
     * - and component registry with 90s timeout
     * to be started before starting itself
     *
     * We will add an entry like this to the map: 'catalog' => {
     *                                                          'config' => 30,
     *                                                          'registry' => 30
     *                                                         }
     */
    private static final Map<ComponentType, Map<ComponentType, Integer>> WAIT_RULES;

    static {
        WAIT_RULES = new HashMap<ComponentType, Map<ComponentType, Integer>>();

        Map<ComponentType, Integer> configWaitList = new HashMap<>();
        WAIT_RULES.put(ComponentType.CONFIG, configWaitList);

        Map<ComponentType, Integer> registryWaitList = new HashMap<>();
        registryWaitList.put(ComponentType.CONFIG, 60);
        WAIT_RULES.put(ComponentType.REGISTRY, registryWaitList);

        Map<ComponentType, Integer> gatewayWaitList = new HashMap<>();
        gatewayWaitList.put(ComponentType.CONFIG, 60);
        gatewayWaitList.put(ComponentType.REGISTRY, 90);
        WAIT_RULES.put(ComponentType.GATEWAY, gatewayWaitList);

        Map<ComponentType, Integer> adminWaitList = new HashMap<>();
        adminWaitList.put(ComponentType.CONFIG, 60);
        adminWaitList.put(ComponentType.REGISTRY, 90);
        WAIT_RULES.put(ComponentType.ADMIN, adminWaitList);

        Map<ComponentType, Integer> authenticationWaitList = new HashMap<>();
        authenticationWaitList.put(ComponentType.CONFIG, 60);
        authenticationWaitList.put(ComponentType.REGISTRY, 90);
        authenticationWaitList.put(ComponentType.ADMIN, 330);
        WAIT_RULES.put(ComponentType.AUTHENTICATION, authenticationWaitList);

        Map<ComponentType, Integer> dataProviderWaitList = new HashMap<>();
        dataProviderWaitList.put(ComponentType.CONFIG, 60);
        dataProviderWaitList.put(ComponentType.REGISTRY, 90);
        dataProviderWaitList.put(ComponentType.ADMIN, 240);
        // TODO CMZ add ingest
        WAIT_RULES.put(ComponentType.DATAPROVIDER, dataProviderWaitList);

        Map<ComponentType, Integer> storageProviderWaitList = new HashMap<>();
        storageProviderWaitList.put(ComponentType.CONFIG, 60);
        storageProviderWaitList.put(ComponentType.REGISTRY, 90);
        storageProviderWaitList.put(ComponentType.ADMIN, 240);
        storageProviderWaitList.put(ComponentType.INGEST, 240);
        WAIT_RULES.put(ComponentType.STORAGE, storageProviderWaitList);

        Map<ComponentType, Integer> ingestProviderWaitList = new HashMap<>();
        storageProviderWaitList.put(ComponentType.CONFIG, 60);
        storageProviderWaitList.put(ComponentType.REGISTRY, 90);
        storageProviderWaitList.put(ComponentType.ADMIN, 240);
        ingestProviderWaitList.put(ComponentType.STORAGE, 240);
        WAIT_RULES.put(ComponentType.INGEST, ingestProviderWaitList);

        Map<ComponentType, Integer> damWaitList = new HashMap<>();
        damWaitList.put(ComponentType.CONFIG, 60);
        damWaitList.put(ComponentType.REGISTRY, 90);
        damWaitList.put(ComponentType.ADMIN, 240);
        WAIT_RULES.put(ComponentType.DAM, damWaitList);

        Map<ComponentType, Integer> orderWaitList = new HashMap<>();
        orderWaitList.put(ComponentType.CONFIG, 60);
        orderWaitList.put(ComponentType.REGISTRY, 90);
        orderWaitList.put(ComponentType.ADMIN, 240);
        WAIT_RULES.put(ComponentType.ORDER, orderWaitList);

        Map<ComponentType, Integer> catalogWaitList = new HashMap<>();
        catalogWaitList.put(ComponentType.DAM, 240);
        WAIT_RULES.put(ComponentType.CATALOG, catalogWaitList);

        Map<ComponentType, Integer> accessInstanceWaitList = new HashMap<>();
        accessInstanceWaitList.put(ComponentType.CONFIG, 60);
        accessInstanceWaitList.put(ComponentType.REGISTRY, 90);
        accessInstanceWaitList.put(ComponentType.ADMIN, 90);
        WAIT_RULES.put(ComponentType.ACCESS_INSTANCE, accessInstanceWaitList);

        Map<ComponentType, Integer> accessProjectWaitList = new HashMap<>();
        accessProjectWaitList.put(ComponentType.CONFIG, 30);
        accessProjectWaitList.put(ComponentType.REGISTRY, 90);
        accessProjectWaitList.put(ComponentType.ADMIN, 90);
        WAIT_RULES.put(ComponentType.ACCESS_PROJECT, accessProjectWaitList);

        Map<ComponentType, Integer> frontendWaitList = new HashMap<>();
        WAIT_RULES.put(ComponentType.FRONTEND, frontendWaitList);
    }

    /**
     * Constructor
     * @param newType a {@link ComponentType}
     */
    public WaitRulesInjector(ComponentType newType) {
        super();
        type = newType;
    }

    @Override
    public void inject(InstallData installData) {
        WaitRuleList waitRuleList = new WaitRuleList();
        WAIT_RULES.get(type).forEach((waitedType, timeout) -> {
            String waitedPack = waitedType.getName(); // The type of component we should be waiting for
            Boolean isWaitedPackBeingInstalled = installData.getSelectedPacks().stream()
                    .anyMatch(pack -> pack.getName().equals(waitedPack)); // Is the component/pack we are waiting for is being installed?

            // Only wait for a component/pack which is being installed
            if (isWaitedPackBeingInstalled) {
                ComponentConfigList waitedComponentConfigList = XmlAccessor
                        .readFromString(installData.getVariable(waitedPack + INSTANCE_LIST_SUFFIX),
                                        ComponentConfigList.class);
                waitedComponentConfigList.getItems().stream().map(item -> new WaitRule(item, timeout))
                        .forEach(waitRuleList::add);
            }
        });

        installData.setVariable(type.getName() + WAIT_RULE_LIST_SUFFIX, XmlAccessor.writeToString(waitRuleList));
    }

}
