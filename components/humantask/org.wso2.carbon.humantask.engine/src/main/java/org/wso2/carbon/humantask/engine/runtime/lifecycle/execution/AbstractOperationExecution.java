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


package org.wso2.carbon.humantask.engine.runtime.lifecycle.execution;

import org.wso2.carbon.humantask.engine.runtime.Constants;
import org.wso2.carbon.humantask.engine.runtime.HumanTaskRuntimeException;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.Operation;
import org.wso2.carbon.humantask.engine.runtime.scheduler.command.Command;

public abstract class AbstractOperationExecution implements Command {

    private final Operation operation;

    private Object input;

    public AbstractOperationExecution(Operation operation) {
        this.operation = operation;
    }

    public Object getInput() {
        return input;
    }

    public void setInput(Object input) {
        this.input = input;
    }

    public Operation getOperation() {
        return operation;
    }

    public abstract boolean isBatchedInput();

    public abstract boolean isValidInput();

    public Long getTaskID(String id){
        try {
            return Long.parseLong(id);
        }catch (NumberFormatException e){
            throw new HumanTaskRuntimeException(Constants.MSG_INVALID_TASK_IDENTIFIER);
        }
    }
}
