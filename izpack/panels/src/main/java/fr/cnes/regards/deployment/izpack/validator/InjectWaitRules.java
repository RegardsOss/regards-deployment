/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableTable;
import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.installer.DataValidator;

import fr.cnes.regards.deployment.izpack.utils.ComponentConfigListAccessor;
import fr.cnes.regards.deployment.izpack.utils.model.ComponentConfigList;
import fr.cnes.regards.deployment.izpack.utils.model.WaitRule;

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
    private static final String INSTANCE_LIST_SUFFIX = ".instanceList";

    /**
     * Logger
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(InjectWaitRules.class);

    /**
     * Store the wait rules here.
     * First entry of the table is the current component<br>
     * Second entry is a component to which the first is supposed to wait before booting<br>
     * Thrid
     */
    // @formatter:off
    private static final ImmutableTable<ComponentType, ComponentType, Integer> WAIT_RULES = new ImmutableTable.Builder<ComponentType, ComponentType, Integer>()
        .put(ComponentType.REGISTRY, ComponentType.CONFIG, 90)
        .put(ComponentType.ADMIN, ComponentType.CONFIG, 30)
        .put(ComponentType.ADMIN, ComponentType.REGISTRY, 90)
        .put(ComponentType.DAM, ComponentType.CONFIG, 30)
        .put(ComponentType.DAM, ComponentType.REGISTRY, 90)
        .put(ComponentType.DAM, ComponentType.ADMIN, 330)
        .put(ComponentType.CATALOG, ComponentType.CONFIG, 30)
        .put(ComponentType.CATALOG, ComponentType.REGISTRY, 90)
        .put(ComponentType.CATALOG, ComponentType.ADMIN, 330)
        .put(ComponentType.ACCESS_INSTANCE, ComponentType.CONFIG, 30)
        .put(ComponentType.ACCESS_INSTANCE, ComponentType.REGISTRY, 90)
        .put(ComponentType.ACCESS_INSTANCE, ComponentType.ADMIN, 330)
        .put(ComponentType.ACCESS_PROJECT, ComponentType.CONFIG, 30)
        .put(ComponentType.ACCESS_PROJECT, ComponentType.REGISTRY, 90)
        .put(ComponentType.ACCESS_PROJECT, ComponentType.ADMIN, 330)
        .build();
    // @formatter:on

    @Override
    public Status validateData(InstallData installData) {

        WAIT_RULES.cellSet().forEach(cell -> {
            try {
                ComponentConfigList currentComponentConfigList = ComponentConfigListAccessor
                        .readFromString(installData.getVariable(cell.getRowKey().getType() + INSTANCE_LIST_SUFFIX));
                ComponentConfigList waitedComponentConfigList = ComponentConfigListAccessor
                        .readFromString(installData.getVariable(cell.getColumnKey().getType() + INSTANCE_LIST_SUFFIX));

                waitedComponentConfigList.getItems().stream().map(item -> new WaitRule(item, cell.getValue()))
                        .forEach(currentComponentConfigList::addWaitRule);

                installData.setVariable(cell.getRowKey().getType() + INSTANCE_LIST_SUFFIX,
                                        ComponentConfigListAccessor.writeToString(currentComponentConfigList));
            } catch (Exception e) {
                LOGGER.info("Cell not found, ok, silently failing", e);
            }
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
