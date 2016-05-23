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

public class IllegalStateFault extends RuntimeException {

    public IllegalStateFault(Long taskID, String operation, String state) {
        super(getMessage(taskID, operation, state));
    }

    private static String getMessage(Long taskID, String operation, String state) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.CHAR_OSB)
                .append(Constants.TASK_ID)
                .append(Constants.CHAR_EQL)
                .append(taskID)
                .append(Constants.CHAR_CSB)
                .append(operation)
                .append(Constants.MSG_NOT_SUPPORTED_AT_STATE)
                .append(state);
        return sb.toString();
    }
}
