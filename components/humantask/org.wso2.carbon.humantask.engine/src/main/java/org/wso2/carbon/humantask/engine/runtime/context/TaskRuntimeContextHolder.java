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

public final class TaskRuntimeContextHolder {

    private static ThreadLocal<TaskRuntimeContextHolder> currentContextHolder = new
            ThreadLocal<TaskRuntimeContextHolder>() {
                protected TaskRuntimeContextHolder initialValue() {
                    return new TaskRuntimeContextHolder();
                }
            };

    private Map<String, Object> overridingProperties;
    private Session sqlSession;
    private TaskUser taskUser;

    private TaskRuntimeContextHolder() {

    }

    public static TaskRuntimeContextHolder getCurrentContextHolder() {
        return currentContextHolder.get();
    }

    public void destroyCurrentContextHolder() {
        currentContextHolder.remove();
    }


    public Map<String, Object> getOverridingProperties() {
        return overridingProperties;
    }

    public void setOverridingProperties(Map<String, Object> overridingProperties) {
        this.overridingProperties = overridingProperties;
    }

    public Session getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(Session sqlSession) {
        this.sqlSession = sqlSession;
    }

    public TaskUser getTaskUser() {
        return taskUser;
    }

    public void setTaskUser(TaskUser taskUser) {
        this.taskUser = taskUser;
    }
}
