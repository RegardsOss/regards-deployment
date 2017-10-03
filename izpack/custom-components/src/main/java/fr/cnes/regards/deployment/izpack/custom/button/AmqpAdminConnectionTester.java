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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.handler.Prompt;
import com.izforge.izpack.panels.userinput.action.ButtonAction;
import com.izforge.izpack.util.Console;

/**
 * When the corresponding button is clicked, it attemps to verify the connection to Elasticsearch
 *
 * @author Christophe Mertz
 */
public class AmqpAdminConnectionTester extends ButtonAction {

    /**
     * Message
     */
    private static final String ERROR = "error";

    private static final String PROTOCOL = "http://";

    private static final String SEPARATOR = ":";

    /**
     * The name of the ADMIN HOST variable in the install data
     */
    public static final String HOST_ADMIN_VARIABLE = "regards.config.regards.amqp.management.host";

    /**
     * The name of the ADMIN PORT variable in the install data
     */
    public static final String PORT_ADMIN_VARIABLE = "regards.config.regards.amqp.management.port";

    /**
     * @param installData
     */
    public AmqpAdminConnectionTester(InstallData installData) {
        super(installData);
    }

    @Override
    public boolean execute() {
        String hostNameAdmin = installData.getVariable(HOST_ADMIN_VARIABLE);
        String portNumberAdmin = installData.getVariable(PORT_ADMIN_VARIABLE);

        URL url;
        try {
            StringBuilder result = new StringBuilder();

            result.append(PROTOCOL);
            result.append(hostNameAdmin);
            result.append(SEPARATOR);
            result.append(portNumberAdmin);

            url = new URL(result.toString());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Process the HTTP request
            con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

        } catch (IOException e) { // NOSONAR
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return false;
        }
        return true;

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
