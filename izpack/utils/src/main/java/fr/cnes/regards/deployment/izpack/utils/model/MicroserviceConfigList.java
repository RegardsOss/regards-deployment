/*
 * LICENSE_PLACEHOLDER
 */
package fr.cnes.regards.deployment.izpack.utils.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class MicroserviceConfigList
 *
 * Microservice configurations list.
 *
 * @author Guillaume Barthe de Montmejan
 * @since 1.0.0
 */
@XmlRootElement
public class MicroserviceConfigList {

    /**
     * microserviceConfigList field.
     *
     * @since 1.0.0
     */
    @XmlElement(name = "microserviceConfig")
    private List<MicroserviceConfig> microserviceConfigList = new ArrayList<>();

    /**
     * Add microservice configuration to the list
     *
     * @param microserviceConfig
     *            configuration to add
     * @since 1.0.0
     */
    public void add(MicroserviceConfig microserviceConfig) {
        microserviceConfigList.add(microserviceConfig);
    }

    /**
     * Get a specific configuration from the list
     *
     * @param id
     *            of the configuration
     * @return configuration corresponded to the id, null if none exists
     * @since 1.0.0
     */
    public MicroserviceConfig get(int id) {
        for (MicroserviceConfig microserviceConfig : microserviceConfigList) {
            if (microserviceConfig.getId() == id) {
                return microserviceConfig;
            }
        }
        // Return null if index is not found
        return null;
    }

    /**
     * Methode surchargee
     *
     * @see java.lang.Object#toString()
     * @since 1.0.0
     */
    @Override
    public String toString() {
        String result = "";
        boolean isNotFirst = false;
        for (MicroserviceConfig microserviceConfig : microserviceConfigList) {
            if (isNotFirst) {
                result += "\n";
            } else {
                isNotFirst = true;
            }

            result += microserviceConfig.toString();
        }
        return result;
    }
}
