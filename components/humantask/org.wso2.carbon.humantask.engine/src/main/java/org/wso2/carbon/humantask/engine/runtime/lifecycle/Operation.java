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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Operation {

    private boolean isBatchSupported;
    private boolean stateFullOperation;
    private String operationName;
    private Map<State, State> nextStates;

    public Operation(String name) {
        this.operationName = name;
        this.isBatchSupported = false;
        this.stateFullOperation = false;
        this.nextStates = new HashMap<>();
    }

    public Operation(String name, boolean isBatchSupported) {
        this.operationName = name;
        this.isBatchSupported = isBatchSupported;
        this.stateFullOperation = false;
        this.nextStates = new HashMap<>();
    }

    public Operation(String name, boolean isBatchSupported, boolean stateFullOperation) {
        this.operationName = name;
        this.isBatchSupported = isBatchSupported;
        this.stateFullOperation = stateFullOperation;
        this.nextStates = new HashMap<>();
    }

    public void addNextState(State current, State next) {
        current.addStatefullOperation(this);
        this.nextStates.put(current, next);
    }

    public void addSupportedStates(Collection<State> states) {
        for (State state : states) {
           addSupportedState(state);
        }
    }

    public void addSupportedState(State state){
        state.addSupportedOperation(this);
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
     * @return next state, return null if current state is invalid for this operation.
     */
    public State getNextState(State currentState) {
        if (currentState == null) {
            return null;
        }
        return stateFullOperation ? this.nextStates.get(currentState) :
                (currentState.isEndState() ? currentState : null);
    }

}
