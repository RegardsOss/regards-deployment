/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.utils.model;

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
    private final String name;

    /**
     * @param pName
     */
    private ComponentType(String pName) {
        name = pName;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

}
