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

import java.util.ArrayList;
import java.util.List;

public class State {

    private final String stateName;
    private final boolean endState;
    private final List<Operation> statefulOperations;
    private final List<Operation> supportedOperations;

    public State(String stateName, boolean isEndingState) {
        if (stateName != null) {
            this.stateName = stateName;
        } else {
            this.stateName = "INVALID";
        }
        this.endState = isEndingState;
        this.statefulOperations = new ArrayList<>();
        this.supportedOperations = new ArrayList<>();
    }

    public State(String stateName) {
        if (stateName != null) {
            this.stateName = stateName;
        } else {
            this.stateName = "INVALID";
        }
        this.endState = false;
        this.statefulOperations = new ArrayList<>();
        this.supportedOperations = new ArrayList<>();
    }

    public String getStateName() {
        return stateName;
    }

    public boolean isEndState() {
        return endState;
    }

    protected void addStatefullOperation(Operation operation) {
        this.statefulOperations.add(operation);
    }

    protected void addSupportedOperation(Operation operation) {
        this.supportedOperations.add(operation);
    }

    /**
     * Get Next state for given operation.
     *
     * @param operation Operation name.
     * @return Next State. Return null if given operation is invalid or not supported by current state.
     */
    public State getNextState(Operation operation) {
        if (operation == null || !supportedOperations.contains(operation)) {
            return null;
        }
        if (!operation.isStateFullOperation() && endState) {
            // Operation is stateless and This is an end state. No state transitions.
            return this;
        }
        return operation.getNextState(this);
    }

    public List<Operation> getStatefulOperations() {
        return statefulOperations;
    }
}
