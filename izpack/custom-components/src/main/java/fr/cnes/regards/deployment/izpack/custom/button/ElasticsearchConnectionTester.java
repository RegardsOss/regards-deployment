/*
 * Copyright 2017-2020 CNES - CENTRE NATIONAL d'ETUDES SPATIALES
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

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.izforge.izpack.api.data.InstallData;
import com.izforge.izpack.api.handler.Prompt;
import com.izforge.izpack.panels.userinput.action.ButtonAction;
import com.izforge.izpack.util.Console;

/**
 * Checks the Elasticsearch connection
 *
 * @author Christophe Mertz
 */
public class ElasticsearchConnectionTester extends ButtonAction {

    /**
     * Class logger
     */
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

    /**
     * Constructor
     *
     * @param installData {@link InstallData} used throughout the installation
     */
    public ElasticsearchConnectionTester(InstallData installData) {
        super(installData);
    }

    @Override
    public boolean execute() {
        String host = installData.getVariable(HOST_VARIABLE);
        String port = installData.getVariable(PORT_VARIABLE);

        RestClientBuilder builder = RestClient.builder(new HttpHost(host, Integer.parseInt(port)));
        RestClient restClient = builder.build();
        RestHighLevelClient client = new RestHighLevelClient(builder);

        boolean result = false;
        try {
            // Testing availability of ES
            if (client.ping(RequestOptions.DEFAULT)) {
                result = true;
            } else {
                LOGGER.error("Elasticsearch is down. ");
            }
        } catch (IOException t) {
            LOGGER.error("Error while pinging Elasticsearch", t);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
            try {
                restClient.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return result;
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
