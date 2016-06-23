/**
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 **/


package org.wso2.carbon.humantask.engine.internal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.caching.CarbonCachingService;
import org.wso2.carbon.datasource.core.api.DataSourceService;
import org.wso2.carbon.humantask.engine.EngineConstants;
import org.wso2.carbon.humantask.engine.EngineRuntimeException;
import org.wso2.carbon.humantask.engine.HumanTaskEngine;
import org.wso2.carbon.humantask.engine.HumanTaskEngineImpl;
import org.wso2.carbon.humantask.engine.HumanTaskEngineOSGIServiceImpl;
import org.wso2.carbon.humantask.engine.config.HumanTaskConfigurationFactory;
import org.wso2.carbon.humantask.engine.config.model.HumanTaskConfiguration;
import org.wso2.carbon.security.caas.user.core.service.RealmService;

import java.util.Map;

/**
 * HumanTask Component.
 */
@Component(
        name = "org.wso2.carbon.humantask.engine.internal.HumanTaskComponent",
        service = HumanTaskEngineOSGIServiceImpl.class,
        immediate = true)
public class HumanTaskComponent {

    private static final Logger log = LoggerFactory.getLogger(HumanTaskComponent.class);
    private BundleContext bundleContext;


    @Activate
    protected void activate(ComponentContext ctxt) {
        log.info("HumanTask core component activator...");
        try {
            this.bundleContext = ctxt.getBundleContext();
            ContentHolder holder = ContentHolder.getInstance();
            String configFileLocation = org.wso2.carbon.kernel.utils.Utils.getCarbonConfigHome().resolve
                    (EngineConstants.HT_CONFIG_FILE)
                    .toString();
            if (log.isDebugEnabled()) {
                log.debug("Reading HumanTask engine configuration from : " + configFileLocation);
            }
            HumanTaskConfigurationFactory configurationFactory = new HumanTaskConfigurationFactory(configFileLocation);
            HumanTaskConfiguration humanTaskConfiguration = configurationFactory.build();

            HumanTaskEngine engine = new HumanTaskEngineImpl();
            engine.setEngineConfiguration(humanTaskConfiguration);
            engine.init();

            holder.setTaskEngine(engine);

        } catch (Throwable t) {
            log.error("Error initializing HumanTask component " + t);
        }
    }

    @Deactivate
    protected void deactivate(ComponentContext ctxt) {
        log.info("Deactivating the HumanTask component...");
        if (ContentHolder.getInstance().getTaskEngine().isEngineRunning()) {
            try {
                ContentHolder.getInstance().getTaskEngine().destroy();
            } catch (EngineRuntimeException e) {
                log.error("Unable to shutdown taskEngine : " + e.getLocalizedMessage(), e);
            }
        }
        log.info("HumanTask component is deactivated...");
    }

    //  Set CarbonRealmService
    @Reference(
            name = "org.wso2.carbon.security.CarbonRealmServiceImpl",
            service = RealmService.class,
            cardinality = ReferenceCardinality.AT_LEAST_ONE,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unregisterCarbonRealm"
    )
    public void registerCarbonRealm(RealmService carbonRealmService) {
        ContentHolder.getInstance().setRealmService(carbonRealmService);
    }

    public void unregisterCarbonRealm(RealmService carbonRealmService) {
        log.info("Unregister CarbonRealmService...");
    }

    @Reference(
            name = "carbon-caching.service",
            service = CarbonCachingService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "removeCachingService"
    )
    protected void addCachingService(CarbonCachingService cachingService, Map<String, ?> properties) {
        ContentHolder.getInstance().setCachingService(cachingService);
    }

    protected void removeCachingService(CarbonCachingService cachingService, Map<String, ?> properties) {
        ContentHolder.getInstance().setCachingService(null);
    }

    @Reference(
            name = "org.wso2.carbon.datasource.core.api.DataSourceService",
            service = DataSourceService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unRegisterDataSourceService")
    public void registerDataSourceService(DataSourceService dataSourceService) {
        ContentHolder.getInstance().setDataSourceService(dataSourceService);
    }

    public void unRegisterDataSourceService(DataSourceService dataSourceService) {
        ContentHolder.getInstance().setDataSourceService(null);
    }



}
