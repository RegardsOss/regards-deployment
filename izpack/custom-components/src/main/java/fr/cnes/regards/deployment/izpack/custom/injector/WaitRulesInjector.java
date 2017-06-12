/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.injector;

import java.util.HashMap;
import java.util.Map;

import com.izforge.izpack.api.data.InstallData;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentConfigList;
import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;
import fr.cnes.regards.deployment.izpack.custom.model.WaitRule;
import fr.cnes.regards.deployment.izpack.custom.model.WaitRuleList;
import fr.cnes.regards.deployment.izpack.custom.xml.XmlAccessor;

/**
 * Injects the list of wait rules of a component into the installation data.
 * @author Xavier-Alexandre Brochard
 */
public class WaitRulesInjector implements InstallDataInjector {

    /**
     * Type
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

        Map<ComponentType, Integer> damWaitList = new HashMap<>();
        damWaitList.put(ComponentType.CONFIG, 60);
        damWaitList.put(ComponentType.REGISTRY, 90);
        damWaitList.put(ComponentType.ADMIN, 90);
        WAIT_RULES.put(ComponentType.DAM, damWaitList);

        Map<ComponentType, Integer> catalogWaitList = new HashMap<>();
        catalogWaitList.put(ComponentType.DAM, 90);
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
     * @param pType
     */
    public WaitRulesInjector(ComponentType pType) {
        super();
        type = pType;
    }

    /* (non-Javadoc)
     * @see fr.cnes.regards.deployment.izpack.custom.InstallDataInjector#inject(com.izforge.izpack.api.data.InstallData)
     */
    @Override
    public void inject(InstallData pInstallData) {
        WaitRuleList waitRuleList = new WaitRuleList();
        WAIT_RULES.get(type).forEach((waitedType, timeout) -> {
            String waitedPack = waitedType.getName(); // The type of component we should be waiting for
            Boolean isWaitedPackBeingInstalled = pInstallData.getSelectedPacks().stream()
                    .anyMatch(pack -> pack.getName().equals(waitedPack)); // Is the component/pack we are waiting for is being installed?
            // Only wait for a component/pack which is being installed
            if (isWaitedPackBeingInstalled) {
                ComponentConfigList waitedComponentConfigList = XmlAccessor
                        .readFromString(pInstallData.getVariable(waitedPack + INSTANCE_LIST_SUFFIX),
                                        ComponentConfigList.class);
                waitedComponentConfigList.getItems().stream().map(item -> new WaitRule(item, timeout))
                        .forEach(waitRuleList::add);
            }
        });

        pInstallData.setVariable(type.getName() + WAIT_RULE_LIST_SUFFIX, XmlAccessor.writeToString(waitRuleList));
    }

}
