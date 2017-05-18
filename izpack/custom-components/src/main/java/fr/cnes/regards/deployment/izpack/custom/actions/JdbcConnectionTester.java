/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.actions;

import java.sql.DriverManager;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.handler.Prompt;
import com.izforge.izpack.panels.userinput.action.ButtonAction;
import com.izforge.izpack.util.Console;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;
import fr.cnes.regards.deployment.izpack.custom.model.JdbcConnectionModel;

/**
 * When the corresponding button is clicked, it attemps to verify the connection to the database.
 *
 * @author Xavier-Alexandre Brochard
 * @author m.gond
 */
public abstract class JdbcConnectionTester extends ButtonAction {

    /**
     * Message
     */
    private static final String FAIL_MESSAGE = "Could not connect to database";

    /**
     * The model of the connection model
     */
    protected JdbcConnectionModel jdbcModel;

    /**
     * @param pInstallData
     */
    public JdbcConnectionTester(ComponentType pType, InstallData pInstallData) {
        super(pInstallData);
    }

    /* (non-Javadoc)
     * @see com.izforge.izpack.panels.userinput.action.ButtonAction#execute()
     */
    @Override
    public boolean execute() {
        boolean valid = true;

        try {
            Class.forName(jdbcModel.getDriverClassName());
        } catch (Exception e) {
            valid = false;
        }

        try {
            DriverManager.getConnection(jdbcModel.getJdbcString(), jdbcModel.getUser(), jdbcModel.getPassword());
        } catch (Exception e) {
            valid = false;
        }

        return valid;
    }

    /* (non-Javadoc)
     * @see com.izforge.izpack.panels.userinput.action.ButtonAction#execute(com.izforge.izpack.util.Console)
     */
    @Override
    public boolean execute(Console pConsole) {
        if (!execute()) {
            pConsole.println(messages.get(FAIL_MESSAGE));
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see com.izforge.izpack.panels.userinput.action.ButtonAction#execute(com.izforge.izpack.api.handler.Prompt)
     */
    @Override
    public boolean execute(Prompt pPrompt) {
        if (!execute()) {
            pPrompt.warn(messages.get(FAIL_MESSAGE));
            return false;
        }
        return true;
    }

    /**
     * -----------------------------------------------------------------------------------------------
     * Here we define inheriting classes internally for simplicity. We have one per microservice type.
     * -----------------------------------------------------------------------------------------------
     */

}
