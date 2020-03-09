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

import com.izforge.izpack.api.data.InstallData;

/**
 * Checks the Admin instance's database connection
 * 
 * @author Christophe Mertz
 *
 */
public class AdminInstanceJdbcConnectionTester extends AbstractJdbcConnectionTester {
    
    /**
     * The name of the URL datasource variable in the install data
     */
    public static final String URL_DATASOURCE_VARIABLE = "regards.config.admin.regards.jpa.instance.datasource.url";

    /**
     * The name of the username datasource variable in the install data
     */
    public static final String USERNAME_DATASOURCE_VARIABLE = "regards.config.admin.regards.jpa.instance.datasource.user.name";

    /**
     * The name of the password datasource variable in the install data
     */
    public static final String PASSWORD_DATASOURCE_VARIABLE = "regards.config.admin.regards.jpa.instance.datasource.password";

    /**
     * Constructor
     * 
     * @param installData {@link InstallData} used throughout the installation
     */
    public AdminInstanceJdbcConnectionTester(InstallData installData) {
        super(installData);
        urlVariable = URL_DATASOURCE_VARIABLE;
        userVariable = USERNAME_DATASOURCE_VARIABLE;
        passwordVariable = PASSWORD_DATASOURCE_VARIABLE;
    }

}