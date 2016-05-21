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


package org.wso2.carbon.humantask.engine;

import org.wso2.carbon.humantask.engine.config.model.HumanTaskConfiguration;
import org.wso2.carbon.humantask.engine.exceptions.EngineRuntimeException;
import org.wso2.carbon.humantask.engine.runtime.HumanTaskRuntime;
import org.wso2.carbon.humantask.engine.runtime.audit.HumanTaskAuditor;

/**
 * Human Task Engine which is responsible for initializing data sources, task store, schedulers and
 * other resources used by Task Engine.
 */
public interface HumanTaskEngine {

    /**
     * Initialize HumanTask Server. In case of re-initialization, engine should be shutdown first.
     *
     * @throws EngineRuntimeException if engine is running or couldn't initialized engine. See stack trace for
     *                                detailed message.
     */
    void init() throws EngineRuntimeException;

    /**
     * Start HumanTask engine. Start processing requests. You have to initialize HumanTask Server before start.
     *
     * @throws EngineRuntimeException if unable to start engine. See stack trace for detailed message.
     */
    void start() throws EngineRuntimeException;

    /**
     * Shutdown HumanTask engine. Stop processing requests.
     *
     * @throws EngineRuntimeException if unable to shutdown engine. See stack trace for detailed message.
     */
    void shutdown() throws EngineRuntimeException;

    /**
     * Shutdown and HumanTask engine and destroy engine.
     *
     * @throws EngineRuntimeException if unable to shutdown and destroy engine details. See stack trace for detailed
     *                                message.
     */
    void destroy() throws EngineRuntimeException;

    /**
     * Is HumanTask engine initialized.
     *
     * @return true, if server initialized.
     */
    boolean isInitialized();

    /**
     * Is HumanTask engine running.
     *
     * @return true, if server running.
     */
    boolean isEngineRunning();

    /**
     * Set HumanTask engine configuration. Once new configuration set, user has to run init method to apply changes.
     *
     * @param taskConfiguration HumanTaskConfiguration
     */
    void setEngineConfiguration(final HumanTaskConfiguration taskConfiguration);

    /**
     * Get current HumanTask engine configuration.
     *
     * @return initialized HumanTaskConfiguration instance for current engine.
     */
    HumanTaskConfiguration getEngineConfiguration();

    /**
     * Get HumanTask runtime service.
     *
     * @return HumanTaskRuntime instance.
     */
    HumanTaskRuntime getHumanTaskRuntime();

    /**
     * Is Audit Service enabled.
     *
     * @return
     */
    boolean isAuditEnabled();

    /**
     * Set HumanTask HumanTaskAuditor.
     *
     * @param humanTaskAuditor
     */
    void setAuditor(HumanTaskAuditor humanTaskAuditor);

    /**
     * Get HumanTask HumanTaskAuditor.
     *
     * @return
     */
    HumanTaskAuditor getAuditor();

}
