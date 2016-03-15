package org.wso2.carbon.webservices;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.webservices.runtime.CarbonWSException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the CarbonWSRegistry interface.
 *
 * @since 5.0.0-SNAPSHOT
 */
public class CarbonWSRegistry {

    private static final Log log = LogFactory.getLog(CarbonWSRegistry.class);

    private Map<String, Service> services;

    public CarbonWSRegistry() {
        this.services = new HashMap<>();
    }

    public void registerService(String name, Service service, boolean replace) throws CarbonWSException {
        if (name != null && service != null) {
            if (replace == true) {
                registerService(name, service);
            } else {
                if (this.services.containsKey(name)) {
                    throw new CarbonWSException("Service name " + name + " already exists.");
                } else {
                    registerService(name, service);
                }
            }
        } else if (name == null) {
            log.error("Unable to registry Service " + name + ". Service name can't be null.");
        } else if (service == null) {
            log.error("Unable to register Service " + name + ". Service instance is null.");
        }
    }

    private void registerService(String name, Service service) throws CarbonWSException {
        this.services.put(name, service);
    }

    public Service getService(String serviceName) {
        if (this.services.containsKey(serviceName)) {
            return this.services.get(serviceName);
        } else {
            log.error("Unable to locate service :" + serviceName);
            return null;
        }

    }

    public boolean isServiceExist(String name) {
        // TODO: Validate Service.
        return this.services.containsKey(name);
    }


}
