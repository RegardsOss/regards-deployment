/*
 * Copyright 2017-2018 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.handler.Prompt;
import com.izforge.izpack.panels.userinput.action.ButtonAction;
import com.izforge.izpack.util.Console;

import fr.cnes.regards.deployment.izpack.custom.model.JdbcConnectionModel;
import fr.cnes.regards.deployment.izpack.custom.model.PostgreSqlJdbcConnectionModel;

/**
 * Abstract class used to checks the database connection
 *
 * @author Xavier-Alexandre Brochard
 * @author Christophe Mertz
 */
public abstract class AbstractJdbcConnectionTester extends ButtonAction {

    /**
     * Class logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJdbcConnectionTester.class);

    /**
     * The name of the URL datasource variable in the install data
     */
    protected String urlDatasourceVariable;

    /**
     * The name of the username datasource variable in the install data
     */
    protected String usernameDatasourceVariable;

    /**
     * The name of the password datasource variable in the install data
     */
    protected String passwordDatasourceVariable;

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
     * Default constructor
     * 
     * @param installData {@link InstallData} used throughout the installation
     */
    public AbstractJdbcConnectionTester(InstallData installData) {
        super(installData);
        urlVariable = urlDatasourceVariable;
        userVariable = usernameDatasourceVariable;
        passwordVariable = passwordDatasourceVariable;
    }

    @Override
    public boolean execute() {
        String url = installData.getVariable(urlVariable);
        String user = installData.getVariable(userVariable);
        String password = installData.getVariable(passwordVariable);
        JdbcConnectionModel jdbcModel = new PostgreSqlJdbcConnectionModel(url, user, password);

        try {
            Class.forName(jdbcModel.getDriverClassName());
        } catch (ClassNotFoundException e) {
            LOGGER.error("Where is your PostgreSQL JDBC Driver? " + "You may need to include in a library", e);
            return false;
        }

        LOGGER.info("PostgreSQL JDBC Driver Registered!");

        try (Connection connection = DriverManager.getConnection(jdbcModel.getJdbcString(), jdbcModel.getUser(),
                                                                 jdbcModel.getPassword())) {
            LOGGER.info("Successfully connected to the database");
            return true;
        } catch (SQLException e) {
            LOGGER.error("Connection Failed! Check output console", e);
            return false;
        }

    }

    @Override
    public boolean execute(Console console) {
        if (!execute()) {
            console.println(messages.get(ERROR));
            return false;
        }
        return true;
    }

    @Override
    public boolean execute(Prompt prompt) {
        if (!execute()) {
            prompt.warn(messages.get(ERROR));
            return false;
        }
        return true;
    }

}
