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
package fr.cnes.regards.deployment.izpack.custom.model;

/**
 * Model to store informations about a JDBC connection<br>
 * jdbc:[type]://[host]:[port]/[name]?schema=[schema]
 *
 * @author m.gond
 */
public interface JdbcConnectionModel {

    /**
     * Gets the db driverClassName
     *
     * @return the driverClassName
     */
    String getDriverClassName();

    /**
    * Gets the db type
    *
    * @return the type
    */
    String getType();

    /**
     * Gets the db url
     *
     * @return the url
     */
    String getUrl();

    /**
     * Gets the user
     *
     * @return the user
     */
    String getUser();

    /**
     * Gets the password
     *
     * @return the password
     */
    String getPassword();

    /**
     * Gets the complete jdbc string
     * @return
     */
    default String getJdbcString() {
        return "jdbc:" + getType() + "://" + getUrl();
    }

}
