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

import org.wso2.carbon.humantask.engine.runtime.persistence.BaseEntity;

import java.util.Date;
import java.util.Map;

public class DeploymentUnitEntity implements BaseEntity {

    protected long id;

    protected String name;

    protected long version;

    protected String namespace;

    protected String qualifiedName;

    protected String definition;

    protected Date deployOn;

    protected String checkSum;

    protected DeploymentUnitState status;

    protected Map<String, Resources> resources;

    protected int revision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Object getPersistentState() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Date getDeployOn() {
        return deployOn;
    }

    public void setDeployOn(Date deployOn) {
        this.deployOn = deployOn;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public DeploymentUnitState getStatus() {
        return status;
    }

    public void setStatus(DeploymentUnitState status) {
        this.status = status;
    }

    public Map<String, Resources> getResources() {
        return resources;
    }

    public void setResources(Map<String, Resources> resources) {
        this.resources = resources;
    }

    @Override
    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }
}
