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

    private final int id;
    private final String stateName;
    private final boolean endState;
//    private final List<Integer> statefulOperations;
    private final List<Integer> supportedOperations;

    public State(int id, String stateName, boolean isEndingState) {
        this.id = id;
        if (stateName != null) {
            this.stateName = stateName;
        } else {
            this.stateName = "INVALID";
        }
        this.endState = isEndingState;
//        this.statefulOperations = new ArrayList<>();
        this.supportedOperations = new ArrayList<>();
    }

    public State(int id, String stateName) {
        this.id = id;
        if (stateName != null) {
            this.stateName = stateName;
        } else {
            this.stateName = "INVALID";
        }
        this.endState = false;
        //this.statefulOperations = new ArrayList<>();
        this.supportedOperations = new ArrayList<>();
    }

    public String getStateName() {
        return stateName;
    }

    public boolean isEndState() {
        return endState;
    }

//    protected void addStatefullOperation(Operation operation) {
//        this.statefulOperations.add(operation.getId());
//    }

    protected void addSupportedOperation(Operation operation) {
        this.supportedOperations.add(operation.getId());
    }

    /**
     * Get Next state for given operation.
     *
     * @param operation Operation name.
     * @return Next State. Return negative value if given operation is invalid or not supported by current state.
     */
    public int getNextState(Operation operation) {
        if (operation == null || !supportedOperations.contains(operation)) {
            return -1;
        }
        if (!operation.isStateFullOperation() && endState) {
            // Operation is stateless and This is an end state. No state transitions.
            return -1;
        }
        return operation.getNextState(this);
    }

//    public List<Integer> getStatefulOperations() {
//        return statefulOperations;
//    }

    public int getId() {
        return id;
    }
}
