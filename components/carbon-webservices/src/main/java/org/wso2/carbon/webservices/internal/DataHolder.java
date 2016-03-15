package org.wso2.carbon.webservices.internal;

import org.wso2.carbon.kernel.CarbonRuntime;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.wso2.carbon.webservices.CarbonWSRegistry;


/**
 * DataHolder to hold TODO: COMPLETE this.
 *
 * @since 5.0.0-SNAPSHOT
 */
public class DataHolder {

    private static final Log log = LogFactory.getLog(DataHolder.class);

    private static DataHolder instance = new DataHolder();
    private CarbonRuntime carbonRuntime;
    private CarbonWSRegistry carbonWSRegistry;

    private DataHolder() {

    }

    /**
     * This returns the DataHolder instance.
     *
     * @return The DataHolder instance of this singleton class
     */
    public static DataHolder getInstance() {
        return instance;
    }

    /**
     * Returns the CarbonRuntime service which gets set through a service webservices.
     *
     * @return CarbonRuntime Service
     */
    public CarbonRuntime getCarbonRuntime() {
        return carbonRuntime;
    }

    /**
     * This method is for setting the CarbonRuntime service. This method is used by
     * ServiceComponent.
     *
     * @param carbonRuntime The reference being passed through ServiceComponent
     */
    protected void setCarbonRuntime(CarbonRuntime carbonRuntime) {
        this.carbonRuntime = carbonRuntime;
    }

    public CarbonWSRegistry getCarbonWSRegistry() {
        if (carbonWSRegistry != null) {
            return carbonWSRegistry;
        } else {
            log.error("CarbonWSService is not initialed yet.");
            return null;
        }
    }

    protected void setCarbonWSRegistry(CarbonWSRegistry wsRegistry) {
        this.carbonWSRegistry = wsRegistry;
    }
}
