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
import org.apache.ibatis.type.Alias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.humantask.engine.runtime.HumanTaskRuntimeException;

import java.util.Date;

/**
 * BaseEntity Class for HumanTasks.
 */
@Alias("HumanTask")
public class HumanTask {

    private final static Logger logger = LoggerFactory.getLogger(HumanTask.class);

    private long taskID;
    private String taskName;
    private String packageName;
    private String taskDefinitionName;
    private long taskDefinitionVersion;

    private int priority;
    private String actualOwner;

    private boolean skipable;
    private boolean escalated;

    // Following variables are not defined as enum, to support other task implementations.
    private int state;
    private String taskType;

    private Date activationTime;
    private Date completeByTime;
    private Date expirationTime;
    private Date createdOnTime;
    private Date startedTime;
    private Date updateOnTime;
    private Date completedTime;

    private long inputMessage;
    private long outputMessage;
    private long faultMessage;

    private String businessKey;
    private String businessType;

    public HumanTask(long taskID, String taskName, String packageName, String taskDefinitionName, long
            taskDefinitionVersion, int priority , String taskType){
        validateInputs(taskID, taskName, packageName, taskDefinitionName, taskDefinitionVersion);
        this.taskID = taskID;
        this.taskName = taskName;
        this.packageName = packageName;
        this.taskDefinitionName = taskDefinitionName;
        this.taskDefinitionVersion = taskDefinitionVersion;
        if (priority >= 0 && priority <= 10) {
            this.priority = priority;
        } else {
            logger.warn("Task instance " + taskID + " has invalid priority version. Setting to default");
            this.priority = 5;
        }
        this.state = -1;
        this.skipable = false;
        this.escalated = false;
        this.taskType = taskType;
        this.activationTime = new Date();
        this.completeByTime = null;
        this.expirationTime = null;
        this.createdOnTime = new Date();
        this.inputMessage = -1;
        this.outputMessage = -1;
        this.faultMessage = -1;
        this.businessKey = null;
        this.businessType = null;
    }

    public HumanTask(long taskID, String taskName, String packageName, String taskDefinitionName, long
            taskDefinitionVersion, int priority, int state, boolean skipable, boolean escalated, String taskType,
                     Date activationTime, Date completeByTime, Date expirationTime, Date createdOnTime, long
                             inputMessage, String businessKey, String businessType) {

        validateInputs(taskID, taskName, packageName, taskDefinitionName, taskDefinitionVersion);
        this.taskID = taskID;
        this.taskName = taskName;
        this.packageName = packageName;
        this.taskDefinitionName = taskDefinitionName;
        this.taskDefinitionVersion = taskDefinitionVersion;
        if (priority >= 0 && priority <= 10) {
            this.priority = priority;
        } else {
            logger.warn("Task instance " + taskID + " has invalid priority version. Setting to default");
            this.priority = 5;
        }
        this.state = state;
        this.skipable = skipable;
        this.escalated = escalated;
        this.taskType = taskType;
        this.activationTime = activationTime;
        this.completeByTime = completeByTime;
        this.expirationTime = expirationTime;
        this.createdOnTime = createdOnTime;
        this.inputMessage = inputMessage;
        this.outputMessage = -1;
        this.faultMessage = -1;
        this.businessKey = businessKey;
        this.businessType = businessType;
    }

    private void validateInputs(long taskID, String taskName, String packageName, String taskDefinitionName, long
            taskDefinitionVersion) {
        if (taskID <= 0) {
            throw new HumanTaskRuntimeException("Can't create Task. Invalid Task ID: " + taskID);
        }
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

    public long getTaskID() {
        return taskID;
    }

    public void setTaskID(long taskID) {
        this.taskID = taskID;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getActualOwner() {
        return actualOwner;
    }

    public void setActualOwner(String actualOwner) {
        this.actualOwner = actualOwner;
    }

    public boolean isSkipable() {
        return skipable;
    }

    public void setSkipable(boolean skipable) {
        this.skipable = skipable;
    }

    public boolean isEscalated() {
        return escalated;
    }

    public void setEscalated(boolean escalated) {
        this.escalated = escalated;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Date getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
    }

    public Date getCompleteByTime() {
        return completeByTime;
    }

    public void setCompleteByTime(Date completeByTime) {
        this.completeByTime = completeByTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Date getCreatedOnTime() {
        return createdOnTime;
    }

    public void setCreatedOnTime(Date createdOnTime) {
        this.createdOnTime = createdOnTime;
    }

    public Date getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
    }

    public Date getUpdateOnTime() {
        return updateOnTime;
    }

    public void setUpdateOnTime(Date updateOnTime) {
        this.updateOnTime = updateOnTime;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public long getInputMessage() {
        return inputMessage;
    }

    public void setInputMessage(long inputMessage) {
        this.inputMessage = inputMessage;
    }

    public long getOutputMessage() {
        return outputMessage;
    }

    public void setOutputMessage(long outputMessage) {
        this.outputMessage = outputMessage;
    }

    public long getFaultMessage() {
        return faultMessage;
    }

    public void setFaultMessage(long faultMessage) {
        this.faultMessage = faultMessage;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
