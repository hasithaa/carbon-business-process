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

import org.wso2.carbon.humantask.engine.exceptions.HumanTaskRuntimeException;

/**
 * Human Task Engine which is responsible for initializing data sources, task store, schedulers and
 * other resources used by Task Engine.
 */
public interface HumanTaskEngine {

    /**
     * Initialize HumanTask Server.
     */
    void init();

    /**
     * Start HumanTask engine. Start processing requests.  You have to initialize HumanTask Server before start.
     */
    void start() throws HumanTaskRuntimeException;

    /**
     * Shutdown HumanTask engine. Stop processing requests.
     */
    void shutdown();

}
