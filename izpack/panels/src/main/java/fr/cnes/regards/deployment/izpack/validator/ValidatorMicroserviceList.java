/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.installer.DataValidator;

import fr.cnes.regards.deployment.izpack.utils.MicroserviceConfigListAccessor;
import fr.cnes.regards.deployment.izpack.utils.model.MicroserviceConfig;
import fr.cnes.regards.deployment.izpack.utils.model.MicroserviceConfigList;

/**
 *
 * This class contains validators used to concatenate microservice properties to
 * formatted List.
 *
 * @author Guillaume Barthe de Montmejan
 */
public abstract class ValidatorMicroserviceList implements DataValidator {

    /**
     * Identifier concatenated to pattern string to identify one specific
     * microservice
     */
    protected String microServiceIdentifier;

    /**
     * Identifier concatenated to pattern string to identify specific count
     * variable for one microservice
     */
    protected String microServiceCountIdentifier;

    /**
     * First index of Izpack variable list is 1
     */
    private static final int IZPACK_FIRST_INDEX_OF_VARIABLE_LIST = 1;

    @Override
    public Status validateData(InstallData installData) {
        final String count = microServiceCountIdentifier + ".count";
        final String uriName = microServiceIdentifier + ".uri";
        final String portName = microServiceIdentifier + ".port";
        final String microserviceListName = microServiceCountIdentifier + ".microserviceList";

        String countAsString = installData.getVariable(count);
        int nbMicroservices = Integer.valueOf(countAsString) + IZPACK_FIRST_INDEX_OF_VARIABLE_LIST;

        MicroserviceConfigList microserviceConfigList = new MicroserviceConfigList();
        // Concatenate all values in single one formatted
        for (int i = IZPACK_FIRST_INDEX_OF_VARIABLE_LIST; i < nbMicroservices; i++) {
            MicroserviceConfig microserviceConfig = new MicroserviceConfig();
            String uri = installData.getVariable(uriName + "." + i);
            int port = Integer.parseInt(installData.getVariable(portName + "." + i));

            microserviceConfig.setHost(uri);
            microserviceConfig.setPort(port);
            microserviceConfig.setId(i - 1);

            microserviceConfigList.add(microserviceConfig);
        }
        // Set result in a new variable
        // Careful, if you use an existing one, it's doesn't work.
        installData.setVariable(microserviceListName,
                                MicroserviceConfigListAccessor.writeToString(microserviceConfigList));

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
