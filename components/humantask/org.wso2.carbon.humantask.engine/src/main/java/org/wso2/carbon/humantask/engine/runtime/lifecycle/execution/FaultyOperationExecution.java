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

public class FaultyOperationExecution extends AbstractOperationExecution {

    private String error;
    private String errorMessage;

    public FaultyOperationExecution(Operation operation, String error, String errorMessage) {
        super(operation);
        this.error = error;
        this.errorMessage = errorMessage;
    }

    @Override
    public ErrorMessage execute() {
        ErrorMessage errorMsg = new ErrorMessage();
        errorMsg.setErrorName("Faulty Operation " + getOperation().getOperationName() + " is invoked. Reason :" +
                error);
        errorMsg.setErrorDetails(this.errorMessage);
        return errorMsg;
    }

    @Override
    public boolean isBatchedInput() {
        return false;
    }

    @Override
    public boolean isValidInput() {
        return false;
    }
}
