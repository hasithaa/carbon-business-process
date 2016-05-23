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


package org.wso2.carbon.humantask.engine.runtime;

public class Constants {

    private Constants() {
        throw new AssertionError("Can't create an instance from " + Constants.class);
    }

    //Properties.



    public final static String AUDIT = "AUDIT";
    public final static String TASK_ID = "TaskID";
    public final static String ORIGINATOR = "Originator";
    public final static String OPERATION = "Operation";
    public final static String MESSAGE = "Message";

    // Various Characters used in engine.
    public final static String CHAR_OSB = " [";
    public final static String CHAR_CSB = "] ";
    public final static String CHAR_EQL = " = ";


    // Various Messages.
    public final static String MSG_NOT_SUPPORTED_AT_STATE = " is not supported at state : ";

}
