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


package org.wso2.carbon.humantask.engine.db.model;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.humantask.engine.exceptions.HumanTaskRuntimeException;

import java.util.Date;

/**
 * Entity Class for HumanTasks.
 */
public class HumanTask {

    private final static Logger logger = LoggerFactory.getLogger(HumanTask.class);

    /**
     * HumanTask Status as defined in HumanTask specifications.
     *
     * @See: https://docs.oasis-open.org/bpel4people/ws-humantask-1.1-spec-cs-01.html#_Ref193112184
     */
    public enum State {
        CREATED,
        READY,
        RESERVED,
        IN_PROGRESS,
        SUSPENDED,
        COMPLETED,
        FAILED,
        ERROR,
        EXITED,
        OBSOLETE,
        /**
         * Apply Only for Notifications.
         */
        REMOVED,
    }

    public enum TaskType {
        /**
         * <b>task</b> is used to define an inline task within the people activity.
         */
        task,
        /**
         * <b>localTask</b> is used to refer to a standalone task with no callable Web service interface.
         */
        localTask,
        /**
         * <b>remoteTask</b> is used to refer to a standalone task offering callable Web service interface.
         */
        remoteTask,
        /**
         * <b>notification</b> is used to define an inline notification within the people activity.
         */
        notification,
        /**
         * <b>localNotification</b> is used to refer to a standalone notification with no callable Web service
         * interface.
         */
        localNotification,
        /**
         * <b>remoteNotification</b> is used to refer to a standalone notification offering callable Web service
         * interface.
         */
        remoteNotification,
        /**
         * <b>leanTask</b> is used to define an leanTask task.
         */
        leanTask,
    }


    private long taskID;
    private String taskName;
    private String packageName;
    private String taskDefinitionName;
    private long taskDefinitionVersion;

    private int priority;
    private String actualOwner;

    private boolean skipable;
    private boolean escalated;

    private State state;
    private State stateBeforeSuspension;
    private TaskType taskType;

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
            taskDefinitionVersion, int priority, State state, boolean skipable, boolean escalated, TaskType taskType,
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
        }else {
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getStateBeforeSuspension() {
        return stateBeforeSuspension;
    }

    public void setStateBeforeSuspension(State stateBeforeSuspension) {
        this.stateBeforeSuspension = stateBeforeSuspension;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
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
