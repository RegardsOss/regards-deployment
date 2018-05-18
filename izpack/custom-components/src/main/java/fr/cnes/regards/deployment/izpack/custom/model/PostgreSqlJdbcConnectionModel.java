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
 * Models a jdbc connection to a PostreSql database
 * @author Xavier-Alexandre Brochard
 */
public class PostgreSqlJdbcConnectionModel extends AbstractJdbcConnectionModel {

    /**
     * The driver class name
     */
    private static final String POSTGRESQL_DRIVER_CLASSNAME = "org.postgresql.Driver";

    /**
     * The type
     */
    private static final String POSTGRESQL_TYPE = "postgresql";

    /**
     * Constructor
     * @param pUrl the db url
     * @param pUser the db user
     * @param pPassword the db password
     */
    public PostgreSqlJdbcConnectionModel(String pUrl, String pUser, String pPassword) {
        super(POSTGRESQL_DRIVER_CLASSNAME, POSTGRESQL_TYPE, pUrl, pUser, pPassword);
    }

}
