/*
 * LICENSE_PLACEHOLDER
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
