/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

import java.util.HashMap;
import java.util.Map;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.installer.DataValidator;

import fr.cnes.regards.deployment.izpack.utils.XmlAccessor;
import fr.cnes.regards.deployment.izpack.utils.model.ComponentConfigList;
import fr.cnes.regards.deployment.izpack.utils.model.WaitRule;
import fr.cnes.regards.deployment.izpack.utils.model.WaitRuleList;

/**
 * This class  does not perform validation, but is a way to hack into the Izpack {@link InstallData}<br>
 * in order to manually inject the waited components to each component configuration.
 *
 * @author Xavier-Alexandre Brochard
 */
public class InjectWaitRules implements DataValidator {

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
     * We want to tell "Componen catalog needs to wait for
     * - component config with 30s timeout
     * - and component registry with 90s timeout
     * to be sarted before starting itself
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
        registryWaitList.put(ComponentType.CONFIG, 90);
        WAIT_RULES.put(ComponentType.REGISTRY, registryWaitList);

        Map<ComponentType, Integer> gatewayWaitList = new HashMap<>();
        gatewayWaitList.put(ComponentType.CONFIG, 30);
        gatewayWaitList.put(ComponentType.REGISTRY, 90);
        gatewayWaitList.put(ComponentType.ADMIN, 330);
        WAIT_RULES.put(ComponentType.GATEWAY, gatewayWaitList);

        Map<ComponentType, Integer> adminWaitList = new HashMap<>();
        adminWaitList.put(ComponentType.CONFIG, 30);
        adminWaitList.put(ComponentType.REGISTRY, 90);
        WAIT_RULES.put(ComponentType.ADMIN, adminWaitList);

        Map<ComponentType, Integer> authenticationWaitList = new HashMap<>();
        authenticationWaitList.put(ComponentType.CONFIG, 30);
        authenticationWaitList.put(ComponentType.REGISTRY, 90);
        authenticationWaitList.put(ComponentType.ADMIN, 330);
        WAIT_RULES.put(ComponentType.AUTHENTICATION, authenticationWaitList);

        Map<ComponentType, Integer> damWaitList = new HashMap<>();
        damWaitList.put(ComponentType.CONFIG, 30);
        damWaitList.put(ComponentType.REGISTRY, 90);
        damWaitList.put(ComponentType.ADMIN, 330);
        WAIT_RULES.put(ComponentType.DAM, damWaitList);

        Map<ComponentType, Integer> catalogWaitList = new HashMap<>();
        catalogWaitList.put(ComponentType.CONFIG, 30);
        catalogWaitList.put(ComponentType.REGISTRY, 90);
        catalogWaitList.put(ComponentType.ADMIN, 330);
        WAIT_RULES.put(ComponentType.CATALOG, catalogWaitList);

        Map<ComponentType, Integer> accessInstanceWaitList = new HashMap<>();
        accessInstanceWaitList.put(ComponentType.CONFIG, 30);
        accessInstanceWaitList.put(ComponentType.REGISTRY, 90);
        accessInstanceWaitList.put(ComponentType.ADMIN, 330);
        WAIT_RULES.put(ComponentType.ACCESS_INSTANCE, accessInstanceWaitList);

        Map<ComponentType, Integer> accessProjectWaitList = new HashMap<>();
        accessProjectWaitList.put(ComponentType.CONFIG, 30);
        accessProjectWaitList.put(ComponentType.REGISTRY, 90);
        accessProjectWaitList.put(ComponentType.ADMIN, 330);
        WAIT_RULES.put(ComponentType.ACCESS_PROJECT, accessProjectWaitList);

        Map<ComponentType, Integer> frontendWaitList = new HashMap<>();
        WAIT_RULES.put(ComponentType.FRONTEND, frontendWaitList);
    }

    @Override
    public Status validateData(InstallData installData) {
        WAIT_RULES.forEach((currentType, waitedMap) -> {
            String currentTypeAsString = currentType.getType();
            WaitRuleList waitRuleList = new WaitRuleList();

            waitedMap.forEach((waitedType, timeout) -> {
                String waitedTypeAsString = waitedType.getType();
                ComponentConfigList waitedComponentConfigList = XmlAccessor
                        .readFromString(installData.getVariable(waitedTypeAsString + INSTANCE_LIST_SUFFIX),
                                        ComponentConfigList.class);
                waitedComponentConfigList.getItems().stream().map(item -> new WaitRule(item, timeout))
                        .forEach(waitRuleList::add);
            });

            installData.setVariable(currentTypeAsString + WAIT_RULE_LIST_SUFFIX,
                                    XmlAccessor.writeToString(waitRuleList));
        });

        return Status.OK;
    }

    @Override
    public String getErrorMessageId() {
        // Not necessary, status is always OK.
        return null;
    }

    @Override
    public String getWarningMessageId() {
        // Not necessary, status is always OK.
        return null;
    }

    @Override
    public boolean getDefaultAnswer() {
        // Not necessary, status is always OK.
        return false;
    }

}
