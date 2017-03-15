/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.installer.DataValidator;

/**
 *
 * This class contains validators used to concatenate microservice properties to formatted List.
 *
 * @author Guillaume Barthe de Montmejan
 */
public abstract class ValidatorMicroserviceList implements DataValidator {

    /**
     * Identifier concatenated to pattern string to identify one specific microservice
     */
    protected String microServiceIdentifier;

    /**
     * First index of Izpack variable list is 1
     */
    private static final int IZPACK_FIRST_INDEX_OF_VARIABLE_LIST = 1;

    @Override
    public Status validateData(InstallData installData) {
        final String count = "microservice." + microServiceIdentifier + ".count";
        final String uriName = "microservice." + microServiceIdentifier + ".uri";
        final String portName = "microservice." + microServiceIdentifier + ".port";
        final String microserviceListName = "microservice." + microServiceIdentifier + ".microserviceList";

        int nbMicroservices = Integer.valueOf(installData.getVariable(count)) + IZPACK_FIRST_INDEX_OF_VARIABLE_LIST;

        String value = "";
        // Concatenate all values in single one formatted
        for (int i = IZPACK_FIRST_INDEX_OF_VARIABLE_LIST; i < nbMicroservices; i++) {
            if (!value.isEmpty()) {
                value += ",";
            }
            value += "{{" + installData.getVariable(uriName + "." + i) + "},{"
                    + installData.getVariable(portName + "." + i) + "}}";
        }
        // Set result in a new variable
        // Careful, if you use an existing one, it's doesn't work.
        installData.setVariable(microserviceListName, value);

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
