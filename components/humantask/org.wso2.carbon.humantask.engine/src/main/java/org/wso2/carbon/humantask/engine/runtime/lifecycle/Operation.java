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


package org.wso2.carbon.humantask.engine.runtime.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.execution.AbstractOperationExecution;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.execution.FaultyOperationExecution;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.execution.UnsupportedOperationExecution;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Operation {

    private static final Logger logger = LoggerFactory.getLogger(Operation.class);
    private final int id;
    private final boolean isBatchSupported;
    private final boolean stateFullOperation;
    private final String operationName;
    private Map<Integer, Integer> nextStates;
    private List<Integer> allowedHumanRole;
    private List<Integer> excludedHumanRole;
    private Class executor;

    public Operation(int id, String name) {
        this.id = id;
        this.operationName = name;
        this.isBatchSupported = false;
        this.stateFullOperation = false;
        this.nextStates = new HashMap<>();
        allowedHumanRole = new ArrayList<>();
        excludedHumanRole = new ArrayList<>();
        executor = UnsupportedOperationExecution.class;
    }

    public Operation(int id, String name, boolean isBatchSupported) {
        this.id = id;
        this.operationName = name;
        this.isBatchSupported = isBatchSupported;
        this.stateFullOperation = false;
        this.nextStates = new HashMap<>();
        allowedHumanRole = new ArrayList<>();
        excludedHumanRole = new ArrayList<>();
        executor = UnsupportedOperationExecution.class;
    }

    public Operation(int id, String name, boolean isBatchSupported, boolean stateFullOperation) {
        this.id = id;
        this.operationName = name;
        this.isBatchSupported = isBatchSupported;
        this.stateFullOperation = stateFullOperation;
        this.nextStates = new HashMap<>();
        allowedHumanRole = new ArrayList<>();
        excludedHumanRole = new ArrayList<>();
        executor = UnsupportedOperationExecution.class;
    }

    public void addNextState(State current, State next) {
//        current.addStatefullOperation(this);
        this.nextStates.put(current.getId(), next.getId());
        this.addSupportedStates(current);
    }

    public void addSupportedStates(Collection<State> states) {
        states.stream().forEach(aState -> aState.addSupportedOperation(this));
    }

    public void addSupportedStates(State... state) {
        Arrays.stream(state).forEach(aState -> aState.addSupportedOperation(this));
    }

    public void setAllowedHumanRole(HumanRole... role) {
        Arrays.stream(role).forEach(a -> allowedHumanRole.add(a.getId()));
    }

    public void setExcludedHumanRole(HumanRole... role) {
        Arrays.stream(role).forEach(a -> excludedHumanRole.add(a.getId()));
    }

    public boolean isBatchSupported() {
        return isBatchSupported;
    }

    public boolean isStateFullOperation() {
        return stateFullOperation;
    }

    public String getOperationName() {
        return operationName;
    }

    /**
     * Return next state, when this operation is applied on current state.
     *
     * @param currentState current state.
     * @return next state, return negative if current state is invalid for this operation.
     */
    public int getNextState(State currentState) {
        if (currentState == null) {
            return -1;
        }
        return stateFullOperation ? this.nextStates.get(currentState.getId()) :
                (currentState.isEndState() ? currentState.getId() : -1);
    }

    public int getId() {
        return id;
    }

    public AbstractOperationExecution getExecutor() {
        try {
            return (AbstractOperationExecution) executor.getConstructor(Operation.class)
                    .newInstance(this);
        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException e) {
            String errorMsg = "Unable to instantiate Execution for Operation : " + operationName + ". "
                    + e.getMessage();
            // TODO : Add to auditor.
            logger.warn(errorMsg, e);
            return new FaultyOperationExecution(this, "Error while creating execution.", errorMsg);
        }

    }

    public void setExecutor(Class executor) {
        if (executor.isAssignableFrom(AbstractOperationExecution.class)) {
            this.executor = executor;
        }
    }
}
