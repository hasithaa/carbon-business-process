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


package org.wso2.carbon.humantask.engine.runtime.impl.ws.humantask11;

public enum  TaskTypes {
    /**
     * <b>WS_HT11_TASK</b> is used to define an inline task within the people activity.
     */
    WS_HT11_TASK,
    /**
     * <b>WS_HT11_LOCAL_TASK</b> is used to refer to a standalone task with no callable Web service interface.
     */
    WS_HT11_LOCAL_TASK,
    /**
     * <b>WS_HT11_REMOTE_TASK</b> is used to refer to a standalone task offering callable Web service interface.
     */
    WS_HT11_REMOTE_TASK,
    /**
     * <b>WS_HT11_NOTIFICATION</b> is used to define an inline notification within the people activity.
     */
    WS_HT11_NOTIFICATION,
    /**
     * <b>WS_HT11_LOCAL_NOTIFICATION</b> is used to refer to a standalone notification with no callable Web service
     * interface.
     */
    WS_HT11_LOCAL_NOTIFICATION,
    /**
     * <b>WS_HT11_REMOTE_NOTIFICATION</b> is used to refer to a standalone notification offering callable Web service
     * interface.
     */
    WS_HT11_REMOTE_NOTIFICATION,
    /**
     * <b>WS_HT11_LEAN_TASK</b> is used to define an leanTask task.
     */
    WS_HT11_LEAN_TASK,
}
