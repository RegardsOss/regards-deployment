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

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.handler.Prompt;
import com.izforge.izpack.panels.userinput.action.ButtonAction;
import com.izforge.izpack.util.Console;

/**
 * When the corresponding button is clicked, it attemps to verify the connection to Elasticsearch
 *
 * @author Christophe Mertz
 */
public class ElasticsearchConnectionTester extends ButtonAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConnectionTester.class);

    /**
     * Message
     */
    private static final String ERROR = "error";

    /**
     * The name of the HOST variable in the install data
     */
    public static final String HOST_VARIABLE = "regards.config.elasticsearch.host";

    /**
     * The name of the PORT variable in the install data
     */
    public static final String PORT_VARIABLE = "regards.config.elasticsearch.http.port";

    private String host;

    private String port;

    /**
     * @param installData
     */
    public ElasticsearchConnectionTester(InstallData installData) {
        super(installData);
    }

    @Override
    public boolean execute() {
        host = installData.getVariable(HOST_VARIABLE);
        port = installData.getVariable(PORT_VARIABLE);

        RestClient restClient = RestClient.builder(new HttpHost(host, Integer.parseInt(port))).build();
        RestHighLevelClient client = new RestHighLevelClient(restClient);

        boolean result = false;
        try {
            // Testing availability of ES
            if (!client.ping()) {
                LOGGER.error("Elasticsearch is down. ");
            } else {
                result = true;
            }
        } catch (Throwable t) {
            LOGGER.error("Error while pinging Elasticsearch", t);
        }

        return result;
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
