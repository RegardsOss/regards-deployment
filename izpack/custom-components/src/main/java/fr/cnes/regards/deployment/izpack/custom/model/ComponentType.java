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
package fr.cnes.regards.deployment.izpack.custom.model;

/**
 * Enumerates possible values for type of instance
 * 
 * @author Xavier-Alexandre Brochard
 * @author Christophe Mertz
 */
public enum ComponentType {
    CONFIG("config"),
    REGISTRY("registry"),
    GATEWAY("gateway"),
    ADMIN("admin"),
    ADMIN_INSTANCE("admin-instance"),
    AUTHENTICATION("authentication"),
    DAM("dam"),
    CATALOG("catalog"),
    ACCESS_INSTANCE("access-instance"),
    ACCESS_PROJECT("access-project"),
    DATAPROVIDER("dataprovider"),
    STORAGE("storage"),
    ORDER("order"),
    INGEST("ingest"),
    FRONTEND("frontend");

    /**
     * Type
     */
    private final String name;

    /**
     * Default constructor
     * 
     * @param newName the new instance's name
     */
    private ComponentType(String newName) {
        name = newName;
    }

    /**
     * Get the instance's name
     * @return the name
     */
    public String getName() {
        return name;
    }

}
