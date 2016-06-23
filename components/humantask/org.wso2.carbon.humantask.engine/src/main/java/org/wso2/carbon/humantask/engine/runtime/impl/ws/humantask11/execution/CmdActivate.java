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


package org.wso2.carbon.humantask.engine.runtime.impl.ws.humantask11.execution;

import org.wso2.carbon.humantask.engine.runtime.context.TaskRuntimeContext;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.Operation;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.execution.AbstractOperationExecution;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.execution.ErrorMessage;
import org.wso2.carbon.humantask.model.api.Activate;
import org.wso2.carbon.humantask.model.api.BatchActivate;

public class CmdActivate extends AbstractOperationExecution {


    public CmdActivate(Operation operation) {
        super(operation);
    }

    @Override
    public Object execute() {
        if (isValidInput()) {
            if (isBatchedInput()) {

            } else {
                TaskRuntimeContext currentContext = TaskRuntimeContext.getCurrentContext();
                Activate input = (Activate) getInput();
                Long taskID = getTaskID(input.getIdentifier());
                currentContext.getSqlSession()
                ;


            }
        } else {
            return new ErrorMessage("Invalid input", getOperation().getOperationName() + " supports only " +
                    Activate.class + " or " + BatchActivate.class + " only.");
        }
        return null;
    }

    @Override
    public boolean isBatchedInput() {
        if (getOperation().isBatchSupported()) {
            Object input = getInput();
            if (input instanceof Activate) {
                return false;
            } else if (input instanceof BatchActivate) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    @Override
    public boolean isValidInput() {
        return (getInput() != null && (getInput() instanceof Activate || getInput() instanceof BatchActivate));
    }
}
