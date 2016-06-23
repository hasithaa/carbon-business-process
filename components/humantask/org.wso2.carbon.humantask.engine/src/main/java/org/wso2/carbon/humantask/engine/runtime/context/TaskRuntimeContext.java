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


package org.wso2.carbon.humantask.engine.runtime.context;

import org.wso2.carbon.humantask.engine.runtime.people.TaskUser;
import org.wso2.carbon.humantask.engine.runtime.persistence.Session;

import java.util.Map;

public class TaskRuntimeContext {

    private TaskRuntimeContextHolder contextHolder = null;

    private TaskRuntimeContext() {
    }


    TaskRuntimeContext(TaskRuntimeContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }

    public static TaskRuntimeContext getCurrentContext() {
        return new TaskRuntimeContext(TaskRuntimeContextHolder.getCurrentContextHolder());
    }

    public static void destroyCurrentContext() {
        getCurrentContext().getContextHolder().destroyCurrentContextHolder();
    }

    public TaskRuntimeContextHolder getContextHolder() {
        return contextHolder;
    }

    public Map<String, Object> getOverridingProperties() {
        return getCurrentContext().getContextHolder().getOverridingProperties();
    }

    public void setOverridingProperties(Map<String, Object> overridingProperties) {
        getCurrentContext().getContextHolder().setOverridingProperties(overridingProperties);
    }

    public Session getSqlSession() {
        return getCurrentContext().getContextHolder().getSqlSession();
    }

    public void setSqlSession(Session sqlSession) {
        getCurrentContext().getContextHolder().setSqlSession(sqlSession);
    }

    public TaskUser getTaskUser() {
        return getCurrentContext().getContextHolder().getTaskUser();
    }

    public void setTaskUser(TaskUser taskUser) {
        getCurrentContext().getContextHolder().setTaskUser(taskUser);
    }

}
