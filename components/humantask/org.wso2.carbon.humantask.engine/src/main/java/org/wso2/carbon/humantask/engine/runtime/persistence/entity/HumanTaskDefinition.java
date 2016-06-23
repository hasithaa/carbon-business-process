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


package org.wso2.carbon.humantask.engine.runtime.persistence.entity;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.humantask.engine.runtime.HumanTaskRuntimeException;

/**
 * BaseEntity Class for HumanTaskDefinition.
 */
public class HumanTaskDefinition {

    private final static Logger logger = LoggerFactory.getLogger(HumanTaskDefinition.class);

    private String taskName;
    private String packageName;
    private String taskDefinitionName;
    private long taskDefinitionVersion;

    private String taskType;

    private String businessType;

    public HumanTaskDefinition(String taskName, String packageName, String taskDefinitionName, long
            taskDefinitionVersion, String businessType) {

        validateInputs(taskName, packageName, taskDefinitionName, taskDefinitionVersion);
        this.taskName = taskName;
        this.packageName = packageName;
        this.taskDefinitionName = taskDefinitionName;
        this.taskDefinitionVersion = taskDefinitionVersion;
        this.businessType = businessType;


    }

    private void validateInputs(String taskName, String packageName, String taskDefinitionName, long
            taskDefinitionVersion) {
        if (taskName == null || StringUtils.isEmpty(taskName.trim())) {
            throw new HumanTaskRuntimeException("Can't create Task. Invalid Task Name: " + taskName);
        }
        if (packageName == null || StringUtils.isEmpty(packageName.trim())) {
            throw new HumanTaskRuntimeException("Can't create Task. Invalid Package Name: " + packageName);
        }
        if (taskDefinitionName == null || StringUtils.isEmpty(taskDefinitionName.trim())) {
            throw new HumanTaskRuntimeException("Can't create Task. Invalid Task Definition Name: " +
                    taskDefinitionName);
        }
        if (taskDefinitionVersion <= 0) {
            throw new HumanTaskRuntimeException("Can't create Task. Invalid Task Definition Version: " +
                    taskDefinitionVersion);
        }
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTaskDefinitionName() {
        return taskDefinitionName;
    }

    public void setTaskDefinitionName(String taskDefinitionName) {
        this.taskDefinitionName = taskDefinitionName;
    }

    public long getTaskDefinitionVersion() {
        return taskDefinitionVersion;
    }

    public void setTaskDefinitionVersion(long taskDefinitionVersion) {
        this.taskDefinitionVersion = taskDefinitionVersion;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
