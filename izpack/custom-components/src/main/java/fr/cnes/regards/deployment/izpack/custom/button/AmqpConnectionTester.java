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

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.handler.Prompt;
import com.izforge.izpack.panels.userinput.action.ButtonAction;
import com.izforge.izpack.util.Console;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * When the corresponding button is clicked, it attemps to verify the connection to Elasticsearch
 *
 * @author Christophe Mertz
 */
public class AmqpConnectionTester extends ButtonAction {

    /**
     * Message
     */
    private static final String ERROR = "error";

    /**
     * The name of the HOST variable in the install data
     */
    public static final String HOST_VARIABLE = "regards.config.spring.rabbitmq.host";

    /**
     * The name of the PORT variable in the install data
     */
    public static final String PORT_VARIABLE = "regards.config.spring.rabbitmq.port";

    /**
     * The name of the USER_NAME variable in the install data
     */
    public static final String USERNAME_VARIABLE = "regards.config.spring.rabbitmq.username";

    /**
     * The name of the PASSWORD variable in the install data
     */
    public static final String PASSWORD_VARIABLE = "regards.config.spring.rabbitmq.password";

    /**
     * @param installData
     */
    public AmqpConnectionTester(InstallData installData) {
        super(installData);
    }

    @Override
    public boolean execute() {
        String hostName = installData.getVariable(HOST_VARIABLE);
        String portNumber = installData.getVariable(PORT_VARIABLE);
        String userName = installData.getVariable(USERNAME_VARIABLE);
        String password = installData.getVariable(PASSWORD_VARIABLE);

        Connection conn = null;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(userName);
        factory.setPassword(password);
        factory.setVirtualHost("/");
        factory.setHost(hostName);
        factory.setPort(Integer.valueOf(portNumber));
        try {
            conn = factory.newConnection();
            return (conn.isOpen());
        } catch (IOException | TimeoutException e) { // NOSONAR
            System.out
                    .println("Connection Failed to Amqp : " + hostName + "(" + portNumber + ") for user : " + userName);
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (IOException e) { // NOSONAR
                    e.printStackTrace();
                }
            }
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
