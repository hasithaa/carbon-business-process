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
import org.osgi.service.jndi.JNDIContextManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.datasource.core.api.DataSourceManagementService;
import org.wso2.carbon.datasource.core.api.DataSourceService;
import org.wso2.carbon.humantask.engine.HumanTaskEngine;
import org.wso2.carbon.humantask.engine.HumanTaskEngineImpl;
import org.wso2.carbon.humantask.engine.HumanTaskEngineOSGIService;
import org.wso2.carbon.humantask.engine.config.HumanTaskConfigurationFactory;
import org.wso2.carbon.humantask.engine.config.model.HumanTaskConfiguration;
import org.wso2.carbon.humantask.engine.exceptions.EngineRuntimeException;

/**
 * HumanTask Component.
 */
@Component(
        name = "org.wso2.carbon.humantask.engine.internal.HumanTaskComponent",
        service = HumanTaskEngineOSGIService.class,
        immediate = true)
public class HumanTaskComponent {

    private static final Logger log = LoggerFactory.getLogger(HumanTaskComponent.class);
    private DataSourceService datasourceService;
    private DataSourceManagementService datasourceManagementService;
    private JNDIContextManager jndiContextManager;
    private BundleContext bundleContext;

    @Activate
    protected void activate(ComponentContext ctxt) {
        log.info("BPMN core component activator...");
        try {
            this.bundleContext = ctxt.getBundleContext();
            ContentHolder holder = ContentHolder.getInstance();

            // TODO : Read config from yaml.
            HumanTaskConfigurationFactory configurationFactory = new HumanTaskConfigurationFactory();
            HumanTaskConfiguration humanTaskConfiguration = configurationFactory.build();

            HumanTaskEngine engine = new HumanTaskEngineImpl();

            engine.setEngineConfiguration(humanTaskConfiguration);
            engine.init();

            holder.setTaskEngine(engine);

//            // Building HumanTask engine,
//            holder.setEngine(ActivitiEngineBuilder.getInstance().buildEngine());
//
//            HumanTaskEngineOSGIService humanTaskEngineOSGIService = new HumanTaskEngineOSGIServiceImpl();
//            BPMNEngineServiceImpl bpmnEngineService = new BPMNEngineServiceImpl();
//            bpmnEngineService
//                    .setProcessEngine(ActivitiEngineBuilder.getInstance().getProcessEngine());
//            bpmnEngineService.setCarbonRealmService(IdentityDataHolder.getInstance().getCarbonRealmService());
//
//            bundleContext.registerService(HumanTaskEngineOSGIService.class.getName(), humanTaskEngineOSGIService,
// null);

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

    @Reference(
            name = "org.wso2.carbon.datasource.jndi.JNDIContextManager",
            service = JNDIContextManager.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unRegisterJNDIContext")

    public void registerJNDIContext(JNDIContextManager contextManager) {
        log.info("register JNDI Context");
        this.jndiContextManager = contextManager;
    }

    public void unRegisterJNDIContext(JNDIContextManager contextManager) {
        log.info("Unregister JNDI Context");
    }

    @Reference(
            name = "org.wso2.carbon.datasource.core.api.DataSourceService",
            service = DataSourceService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unRegisterDataSourceService")
    public void registerDataSourceService(DataSourceService datasource) {
        log.info("register DataSource service");
        this.datasourceService = datasource;
    }

    public void unRegisterDataSourceService(DataSourceService datasource) {
        log.info("unregister datasource service");
    }

    @Reference(
            name = "org.wso2.carbon.datasource.core.api.DataSourceManagementService",
            service = DataSourceManagementService.class,
            cardinality = ReferenceCardinality.MANDATORY,
            policy = ReferencePolicy.DYNAMIC,
            unbind = "unRegisterDataSourceManagementService")

    public void registerDataSourceManagementService(
            DataSourceManagementService datasourceMgtService) {
        log.info("register DataSource Management service");
        this.datasourceManagementService = datasourceMgtService;
    }

    public void unRegisterDataSourceManagementService(DataSourceManagementService datasource) {
        log.info("unregister datasource service");
    }
}
