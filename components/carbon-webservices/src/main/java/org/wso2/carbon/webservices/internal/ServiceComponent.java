package org.wso2.carbon.webservices.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.*;
import org.wso2.carbon.kernel.CarbonRuntime;
import org.wso2.carbon.kernel.transports.CarbonTransport;
import org.wso2.carbon.webservices.CarbonWSRegistry;

/**
 * Service webservices to consume CarbonRuntime instance which has been registered as an OSGi service
 * by Carbon Kernel.
 *
 * @since 5.0.0-SNAPSHOT
 */
@Component(
        name = "org.wso2.carbon.webservices.internal.ServiceComponent",
        immediate = true
)
public class ServiceComponent {

    private static final Log logger = LogFactory.getLog(ServiceComponent.class);
    private ServiceRegistration serviceRegistration;

    /**
     * This is the activation method of ServiceComponent. This will be called when its references are
     * satisfied.
     *
     * @param bundleContext the bundle context instance of this bundle.
     * @throws Exception this will be thrown if an issue occurs while executing the activate method
     */
    @Activate
    protected void start(BundleContext bundleContext) throws Exception {
        logger.info("Service Component is activated");
        // Register CarbonWSRegistry instance as an OSGi service.
        DataHolder.getInstance().setCarbonWSRegistry(new CarbonWSRegistry());
        serviceRegistration = bundleContext.registerService(CarbonWSRegistry.class.getName(), DataHolder.getInstance().getCarbonWSRegistry(), null);
    }

    /**
     * This is the deactivation method of ServiceComponent. This will be called when this webservices
     * is being stopped or references are satisfied during runtime.
     *
     * @throws Exception this will be thrown if an issue occurs while executing the de-activate method
     */
    @Deactivate
    protected void stop() throws Exception {
        logger.info("Service Component is deactivated");

        // Unregister CarbonWSRegistry OSGi service
        serviceRegistration.unregister();
    }

    /**
     * This bind method will be called when CarbonRuntime OSGi service is registered.
     *
     * @param carbonRuntime The CarbonRuntime instance registered by Carbon Kernel as an OSGi service
     */
    @Reference(
            name = "carbon.runtime.service",
            service = CarbonRuntime.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unsetCarbonRuntime"
    )
    protected void setCarbonRuntime(CarbonRuntime carbonRuntime) {
        DataHolder.getInstance().setCarbonRuntime(carbonRuntime);
    }

    /**
     * This is the unbind method which gets called at the un-registration of CarbonRuntime OSGi service.
     *
     * @param carbonRuntime The CarbonRuntime instance registered by Carbon Kernel as an OSGi service
     */
    protected void unsetCarbonRuntime(CarbonRuntime carbonRuntime) {
        DataHolder.getInstance().setCarbonRuntime(null);
    }

    /**
     * This bind method will be called when Carbon-Transport OSGi service is registered.
     *
     * @param carbonTransport the CarbonTransport instance registered by Carbon-transport component.
     */
    @Reference(
            name = "carbon-transport",
            service = CarbonTransport.class,
            cardinality = ReferenceCardinality.AT_LEAST_ONE,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "removeCarbonTransport"
    )
    protected void addCarbonTransport(CarbonTransport carbonTransport) {
        // TODO: Implement me
    }

    /**
     * This is the unbind method which gets called at the un-registration of CarbonTransport OSGi service.
     *
     * @param carbonTransport the CarbonTransport instance registered by Carbon-transport component.
     */
    protected void removeCarbonTransport(CarbonTransport carbonTransport) {
        // TODO: Implement me.
    }
}
