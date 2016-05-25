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

import org.wso2.carbon.humantask.engine.runtime.Constants;
import org.wso2.carbon.kernel.context.CarbonContext;

import java.util.Map;

public class State {

    private final String stateName;
    // Map<OperationName, State>
    private Map<String, State> nextStates;

    public State(String stateName) {
        this.stateName = stateName;
    }

    public void setNextStates(Map<String, State> nextStates) {
        this.nextStates = nextStates;
    }

    public String getStateName() {
        return stateName;
    }

    public State getNextState(Operation operation) {
        if (this.nextStates.containsKey(operation.getOperationName())) {
            return this.nextStates.get(operation.getOperationName());
        } else {
            CarbonContext context =
                    (CarbonContext) CarbonContext.getCurrentContext();
            Long taskId = (Long) context.getProperty(Constants.TASK_ID);
            throw new IllegalStateFault(taskId.longValue(), operation.getOperationName(), stateName);
        }
    }
}
