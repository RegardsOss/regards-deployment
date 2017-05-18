/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.validator;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.installer.DataValidator;

import fr.cnes.regards.deployment.izpack.custom.injector.InstanceListInjector;
import fr.cnes.regards.deployment.izpack.custom.injector.WaitRulesInjector;
import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;

/**
 * This class  does not perform validation, but is a way to hack into the Izpack {@link InstallData}<br>
 * in order to manually inject additional data (like the hosts and ports of all instances of a component) into the install data.
 *
 * @author Xavier-Alexandre Brochard
 */
public abstract class InjectDataValidator implements DataValidator {

    /**
     * Validated component type
     */
    protected ComponentType type;

    /**
     * Key identifiying the component's properties in the install data. Ideally matches the above type, but some exceptions exist.
     */
    protected String entryKey;

    @Override
    public Status validateData(InstallData installData) {
        new InstanceListInjector(type, entryKey).inject(installData);
        new WaitRulesInjector(type).inject(installData);

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
