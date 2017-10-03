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
package fr.cnes.regards.deployment.izpack.custom.button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.handler.Prompt;
import com.izforge.izpack.panels.userinput.action.ButtonAction;
import com.izforge.izpack.util.Console;

import fr.cnes.regards.deployment.izpack.custom.model.JdbcConnectionModel;
import fr.cnes.regards.deployment.izpack.custom.model.PostgreSqlJdbcConnectionModel;

/**
 * When the corresponding button is clicked, it attempts to verify the connection to the database.
 *
 * @author Xavier-Alexandre Brochard
 * @author Christophe Mertz
 */
public abstract class AbstractJdbcConnectionTester extends ButtonAction {

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
     * @param installData
     */
    public AbstractJdbcConnectionTester(InstallData installData) {
        super(installData);
    }

    @Override
    public boolean execute() {
        String url = installData.getVariable(urlVariable);
        String user = installData.getVariable(userVariable);
        String password = installData.getVariable(passwordVariable);
        JdbcConnectionModel jdbcModel = new PostgreSqlJdbcConnectionModel(url, user, password);

        try {
            Class.forName(jdbcModel.getDriverClassName());
        } catch (ClassNotFoundException e) { // NOSONAR
            System.out.println("Where is your PostgreSQL JDBC Driver? " + "You may need to include in a library");
            e.printStackTrace();
            return false;
        }

        System.out.println("PostgreSQL JDBC Driver Registered!");

        try (Connection connection = DriverManager.getConnection(jdbcModel.getJdbcString(), jdbcModel.getUser(),
                                                                 jdbcModel.getPassword())) {
            System.out.println("Successfully connected to the database");
            return true;
        } catch (SQLException e) { // NOSONAR
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return false;
        }

    }

    public boolean execute(Console console) {
        if (!execute()) {
            console.println(messages.get(ERROR));
            return false;
        }
        return true;
    }

    public boolean execute(Prompt prompt) {
        if (!execute()) {
            prompt.warn(messages.get(ERROR));
            return false;
        }
        return true;
    }

}
