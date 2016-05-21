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

import org.wso2.carbon.humantask.engine.HumanTaskEngine;
import org.wso2.carbon.humantask.engine.exceptions.EngineRuntimeException;

/**
 * Content Holder for HumanTask component.
 *
 * @since 5.0.0
 */
public class ContentHolder {

    private static ContentHolder instance = null;

    private HumanTaskEngine taskEngine;

    private ContentHolder() {
    }

    public static ContentHolder getInstance() {
        if (instance == null) {
            instance = new ContentHolder();
        }
        return instance;
    }

    public HumanTaskEngine getTaskEngine() {
        return taskEngine;
    }


    public synchronized void setTaskEngine(HumanTaskEngine taskEngine) throws EngineRuntimeException {
        if (this.taskEngine != null && taskEngine.isEngineRunning()) {
            throw new EngineRuntimeException("Can't set a new task engine instance, since old instance is still " +
                    "running. Shutdown existing instance first.");
        }
        this.taskEngine = taskEngine;
    }
}
