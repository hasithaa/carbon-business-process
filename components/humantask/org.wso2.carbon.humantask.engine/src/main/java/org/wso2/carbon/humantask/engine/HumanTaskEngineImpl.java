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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.humantask.engine.config.model.HumanTaskConfiguration;
import org.wso2.carbon.humantask.engine.exceptions.EngineRuntimeException;
import org.wso2.carbon.humantask.engine.runtime.HumanTaskRuntime;
import org.wso2.carbon.humantask.engine.runtime.audit.HumanTaskAuditor;

/**
 * Human Task Engine implementation.
 *
 * @See org.wso2.carbon.humantask.engine.HumanTaskEngine
 */
public class HumanTaskEngineImpl implements HumanTaskEngine {

    private final static Logger logger = LoggerFactory.getLogger(HumanTaskEngineImpl.class);

    private HumanTaskAuditor humanTaskHumanTaskAuditor;
    private HumanTaskConfiguration humanTaskEngineConfiguration;
    private HumanTaskRuntime taskRuntime;

    private boolean isInitialized;
    private boolean isEngineRunning;

    public HumanTaskEngineImpl() {
        isInitialized = false;
        isEngineRunning = false;
    }

    /**
     * Initialize HumanTask Server. In case of re-initialization, engine should be shutdown first.
     *
     * @throws EngineRuntimeException if engine is running or couldn't initialized engine. See stack trace for
     *                                detailed message.
     */
    public synchronized void init() throws EngineRuntimeException {

        if (isEngineRunning) {
            throw new EngineRuntimeException("Unable to initialize. Engine is running, shutdown engine first.");
        }
        if (this.humanTaskEngineConfiguration == null) {
            throw new EngineRuntimeException("Found invalid humantask engine configuration.");
        }

        //TODO : Implement logic.

        this.isInitialized = true;
    }

    /**
     * Start HumanTask engine. Start processing requests. You have to initialize HumanTask Server before start.
     *
     * @throws EngineRuntimeException if unable to start engine. See stack trace for detailed message.
     */
    public synchronized void start() throws EngineRuntimeException {
        if (!isInitialized) {
            throw new EngineRuntimeException("Engine is not initialized yet for HumanTask engine configuration.");
        }

        //TODO : Implement logic.
        logger.info("HumanTask engine started.");
        this.isEngineRunning = true;
    }

    /**
     * Shutdown HumanTask engine. Stop processing requests.
     *
     * @throws EngineRuntimeException if unable to shutdown engine. See stack trace for detailed message.
     */
    public synchronized void shutdown() throws EngineRuntimeException {
        if (!isEngineRunning) {
            throw new EngineRuntimeException("Engine isn't started yet.");
        }

        //TODO : Implement logic.

        isEngineRunning = false;
    }

    /**
     * Shutdown and HumanTask engine and destroy engine.
     *
     * @throws EngineRuntimeException if unable to shutdown and destroy engine details. See stack trace for detailed
     *                                message.
     */
    public synchronized void destroy() throws EngineRuntimeException {
        shutdown();
        //TODO : Implement missing logic.
        humanTaskEngineConfiguration = null;
        humanTaskHumanTaskAuditor = null;
        taskRuntime = null;
    }

    /**
     * Is HumanTask engine initialized.
     *
     * @return true, if server initialized.
     */
    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Is HumanTask engine running.
     *
     * @return true, if server running.
     */
    public boolean isEngineRunning() {
        return isEngineRunning;
    }

    /**
     * Set HumanTask engine configuration. Once new configuration set, user has to run init method to apply changes.
     *
     * @param taskConfiguration HumanTaskConfiguration
     */
    public synchronized void setEngineConfiguration(final HumanTaskConfiguration taskConfiguration) {
        this.isInitialized = false;
        this.humanTaskEngineConfiguration = taskConfiguration;
    }

    /**
     * Get current HumanTask engine configuration.
     *
     * @return initialized HumanTaskConfiguration instance for current engine.
     */
    public HumanTaskConfiguration getEngineConfiguration() {
        return humanTaskEngineConfiguration;
    }

    /**
     * Get HumanTask runtime service.
     *
     * @return HumanTaskRuntime instance.
     */
    public HumanTaskRuntime getHumanTaskRuntime() {
        return this.taskRuntime;
    }

    /**
     * Is Audit Service enabled.
     *
     * @return
     */
    public boolean isAuditEnabled() {
        if (humanTaskHumanTaskAuditor == null) {
            return false;
        }
        return true;
    }

    /**
     * Set HumanTask HumanTaskAuditor.
     *
     * @param humanTaskAuditor
     */
    public void setAuditor(HumanTaskAuditor humanTaskAuditor) {
        this.humanTaskHumanTaskAuditor = humanTaskAuditor;
    }

    /**
     * Get HumanTask HumanTaskAuditor.
     *
     * @return
     */
    public HumanTaskAuditor getAuditor() {
        return humanTaskHumanTaskAuditor;
    }

}
