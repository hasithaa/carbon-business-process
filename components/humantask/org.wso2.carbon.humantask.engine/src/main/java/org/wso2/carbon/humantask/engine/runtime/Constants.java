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
    public final static String MSG_USER_NOT_FOUND = " User not found : ";
    public final static String MSG_GROUP_NOT_FOUND = " Group not found : ";

    public final static String MSG_IDENTITY_STORE_ERROR = "Unable to retrieve details from Identity Store.";
    public final static String MSG_USER_PRINCIPLE_NOT_FOUND = "User Principle not found in current context.";
    public final static String MSG_REALM_NOT_FOUND = "Engine initialization error. Realm Service isn't initialized " +
            "properly.";
    public final static String MSG_INVALID_CACHE_EXPIRY_TIME = "Found invalid Cache expiry time. Using Default value " +
            "900 Seconds (15 minutes) of cache expiry time.";


    public static String getTaskIDFormatted(long id) {
        StringBuilder br = new StringBuilder();
        br.append(CHAR_OSB).append(TASK_ID).append(CHAR_EQL).append(id).append(CHAR_CSB);
        return br.toString();
    }

}
