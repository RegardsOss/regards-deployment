/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.model;

/**
 * Generic modelisation of a connection to any type of database.
 *
 * @param <T> the JDBC driver class corresponding to the modeled database
 * @author Xavier-Alexandre Brochard
 */
public abstract class AbstractJdbcConnectionModel implements JdbcConnectionModel {

    /**
     * The db driver class name
     */
    private final String driverClassName;

    /**
     * The db type
     */
    private final String type;

    /**
     * The db url
     */
    private final String url;

    /**
     * The db user
     */
    private final String user;

    /**
     * The db password
     */
    private final String password;

    /**
     * @param pDriverClassName
     * @param pType
     * @param pUrl
     * @param pUser
     * @param pPassword
     */
    public AbstractJdbcConnectionModel(String pDriverClassName, String pType, String pUrl, String pUser,
            String pPassword) {
        super();
        driverClassName = pDriverClassName;
        type = pType;
        url = pUrl;
        user = pUser;
        password = pPassword;
    }

    /**
     * @return the driverClassName
     */
    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * @return the type
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * @return the url
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * @return the user
     */
    @Override
    public String getUser() {
        return user;
    }

    /**
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

}
