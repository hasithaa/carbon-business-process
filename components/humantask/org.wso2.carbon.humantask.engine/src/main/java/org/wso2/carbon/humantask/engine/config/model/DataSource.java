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


package org.wso2.carbon.humantask.engine.config.model;

/**
 * DataSource bean.
 */
public class DataSource {

    private boolean runInMemory = false;

    /**
     * HumanTask engine DataSource
     */
    private String humanTaskDataSource = "HumanTask";
    /**
     * Audit and Event DataSource
     */
    private String auditDataSource = "HumanTaskAudit";
    /**
     * History DataSource
     */
    private String historyDataSource = "HumanTaskHistory";

    /***
     * Getter and Setters.
     ***/


    public boolean isRunInMemory() {
        return runInMemory;
    }

    public void setRunInMemory(boolean runInMemory) {
        this.runInMemory = runInMemory;
    }

    public String getHumanTaskDataSource() {
        return humanTaskDataSource;
    }

    public void setHumanTaskDataSource(String humanTaskDataSource) {
        this.humanTaskDataSource = humanTaskDataSource;
    }

    public String getAuditDataSource() {
        return auditDataSource;
    }

    public void setAuditDataSource(String auditDataSource) {
        this.auditDataSource = auditDataSource;
    }

    public String getHistoryDataSource() {
        return historyDataSource;
    }

    public void setHistoryDataSource(String historyDataSource) {
        this.historyDataSource = historyDataSource;
    }
}
