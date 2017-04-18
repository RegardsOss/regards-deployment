/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.installer.DataValidator;

import fr.cnes.regards.deployment.izpack.utils.ComponentConfigListAccessor;
import fr.cnes.regards.deployment.izpack.utils.model.ComponentConfig;
import fr.cnes.regards.deployment.izpack.utils.model.ComponentConfigList;

/**
 * This class  does not perform validation, but is a way to hack into the Izpack {@link InstallData}<br>
 * in order to manually add the hosts and ports of all instances of a component.
 *
 * @author Guillaume Barthe de Montmejan
 * @author Xavier-Alexandre Brochard
 */
public abstract class ValidatorInstanceList implements DataValidator {

    /**
     * Identifier concatenated to pattern string to identify one specific
     * component
     */
    protected String componentIdentifier;

    /**
     * Identifier concatenated to pattern string to identify specific count
     * variable for one component
     */
    protected String componentCountIdentifier;

    /**
     * First index of Izpack variable list is 1
     */
    private static final int IZPACK_FIRST_INDEX_OF_VARIABLE_LIST = 1;

    @Override
    public Status validateData(InstallData installData) {
        final String count = componentCountIdentifier + ".count";
        final String uriName = componentIdentifier + ".host";
        final String portName = componentIdentifier + ".port";
        final String microserviceListName = componentCountIdentifier + ".instanceList";

        String countAsString = installData.getVariable(count);
        int nbComponents = Integer.valueOf(countAsString) + IZPACK_FIRST_INDEX_OF_VARIABLE_LIST;

        ComponentConfigList componentConfigList = new ComponentConfigList();
        // Concatenate all values in single one formatted
        for (int i = IZPACK_FIRST_INDEX_OF_VARIABLE_LIST; i < nbComponents; i++) {
            ComponentConfig componentConfig = new ComponentConfig();
            String uri = installData.getVariable(uriName + "." + i);
            int port = Integer.parseInt(installData.getVariable(portName + "." + i));

            componentConfig.setHost(uri);
            componentConfig.setPort(port);
            componentConfig.setId(i - 1);

            componentConfigList.add(componentConfig);
        }
        // Set result in a new variable
        // Careful, if you use an existing one, it's doesn't work.
        installData.setVariable(microserviceListName,
                                ComponentConfigListAccessor.writeToString(componentConfigList));

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
