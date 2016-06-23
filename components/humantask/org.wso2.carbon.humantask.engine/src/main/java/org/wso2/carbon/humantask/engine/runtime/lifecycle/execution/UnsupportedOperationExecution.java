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

import org.wso2.carbon.humantask.engine.runtime.lifecycle.Operation;

public class UnsupportedOperationExecution extends AbstractOperationExecution {

    public UnsupportedOperationExecution(Operation operation) {
        super(operation);
    }

    @Override
    public boolean isBatchedInput() {
        return false;
    }

    @Override
    public boolean isValidInput() {
        return false;
    }

    @Override
    public ErrorMessage execute() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorName("Unsupported Operation " + getOperation().getOperationName() + " is invoked.");
        errorMessage.setErrorDetails("Invoked operation " + getOperation().getOperationName() + " is not supported in" +
                " this version of HumanTask engine.");
        return errorMessage;
    }
}
