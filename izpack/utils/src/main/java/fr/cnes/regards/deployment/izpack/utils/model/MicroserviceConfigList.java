package fr.cnes.regards.deployment.izpack.utils.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MicroserviceConfigList {

    @XmlElement(name = "microserviceConfig")
    private List<MicroserviceConfig> microserviceConfigList = new ArrayList<>();

    public void add(MicroserviceConfig microserviceConfig) {
        microserviceConfigList.add(microserviceConfig);
    }

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
