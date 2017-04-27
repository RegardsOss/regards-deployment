/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.validator;

/**
 *
 * @author Xavier-Alexandre Brochard
 */
public enum ComponentType {
    CONFIG("config"),
    REGISTRY("registry"),
    GATEWAY("gateway"),
    ADMIN("admin"),
    AUTHENTICATION("authentication"),
    DAM("dam"),
    CATALOG("catalog"),
    ACCESS_INSTANCE("access-instance"),
    ACCESS_PROJECT("access-project"),
    FRONTEND("frontend");

    /**
     * Type
     */
    private final String type;

    /**
     * @param pType
     */
    private ComponentType(String pType) {
        type = pType;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

}
