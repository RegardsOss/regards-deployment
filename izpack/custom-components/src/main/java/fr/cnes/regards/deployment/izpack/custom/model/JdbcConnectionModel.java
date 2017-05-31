/*
 * LICENSE_PLACEHOLDER
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
