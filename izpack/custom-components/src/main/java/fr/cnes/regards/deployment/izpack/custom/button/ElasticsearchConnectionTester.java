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

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

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

    /**
     * Message
     */
    private static final String ERROR = "error";

    /**
     * The name of the HOST variable in the install data
     */
    public final static String HOST_VARIABLE = "regards.config.elasticsearch.host";

    /**
     * The name of the PORT variable in the install data
     */
    public final static String PORT_VARIABLE = "regards.config.elasticsearch.tcp.port";

    /**
     * The name of the CLUSTER variable in the install data
     */
    public final static String CLUSTER_VARIABLE = "regards.config.elasticsearch.cluster.name";

    private String host;

    private String port;

    private String cluster;

    private TransportClient client;

    /**
     * @param pInstallData
     */
    public ElasticsearchConnectionTester(InstallData installData) {
        super(installData);
    }

    @SuppressWarnings("resource")
    private void createTransportClient() throws UnknownHostException {
        Settings settings = Settings.EMPTY;

        if (cluster != null) {
            settings = Settings.builder().put("cluster.name", cluster).build();
        }

        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), new Integer(port)));
    }

    @Override
    public boolean execute() {
        host = installData.getVariable(HOST_VARIABLE);
        port = installData.getVariable(PORT_VARIABLE);
        cluster = installData.getVariable(CLUSTER_VARIABLE);

        try {
            createTransportClient();
            return !client.connectedNodes().isEmpty();
        } catch (UnknownHostException e) {
            System.out.println("Connection Failed to Elasticsearch : " + host + "(" + port + ") for cluster : "
                    + cluster);
            e.printStackTrace();
            return false;
        } finally {
            if (client != null) {
                client.close();
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
