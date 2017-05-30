/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.handler.Prompt;
import com.izforge.izpack.panels.userinput.action.ButtonAction;
import com.izforge.izpack.util.Console;

import fr.cnes.regards.deployment.izpack.custom.model.ComponentType;
import fr.cnes.regards.deployment.izpack.custom.model.JdbcConnectionModel;
import fr.cnes.regards.deployment.izpack.custom.model.PostgreSqlJdbcConnectionModel;

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
    private static final String ERROR = "error";

    /**
     * The name of the url variable in the install data
     */
    protected String urlVariable;

    /**
     * The name of the user variable in the install data
     */
    protected String userVariable;

    /**
     * The name of the password variable in the install data
     */
    protected String passwordVariable;

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
        String url = installData.getVariable(urlVariable);
        String user = installData.getVariable(userVariable);
        String password = installData.getVariable(passwordVariable);
        JdbcConnectionModel jdbcModel = new PostgreSqlJdbcConnectionModel(url, user, password);

        try {
            Class.forName(jdbcModel.getDriverClassName());
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? " + "You may need to include in a library");
            e.printStackTrace();
            return false;
        }

        System.out.println("PostgreSQL JDBC Driver Registered!");

        try (Connection connection = DriverManager.getConnection(jdbcModel.getJdbcString(), jdbcModel.getUser(),
                                                                 jdbcModel.getPassword())) {
            if (connection != null) {
                System.out.println("Successfully connected to the database");
                return true;
            } else {
                System.out.println("Failed to make connection");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return false;
        }

    }

    /* (non-Javadoc)
     * @see com.izforge.izpack.panels.userinput.action.ButtonAction#execute(com.izforge.izpack.util.Console)
     */
    @Override
    public boolean execute(Console pConsole) {
        if (!execute()) {
            pConsole.println(messages.get(ERROR));
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
            pPrompt.warn(messages.get(ERROR));
            return false;
        }
        return true;
    }

}
