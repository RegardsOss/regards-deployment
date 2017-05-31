/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.custom.injector;

import com.izforge.izpack.api.data.InstallData;

/**
 * Injects some data in Izpack {@link InstallData}.
 * @author Xavier-Alexandre Brochard
 */
@FunctionalInterface
public interface InstallDataInjector {

    /**
     * Inject some data in the install data, and return the result
     * @param pInstallData the install data
     */
    void inject(InstallData pInstallData);

}
