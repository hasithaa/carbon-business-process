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

/**
 * WS-HumanTask 1.1 specific constants.
 */
public final class WSHumanTaskConstants {

    public static final String WS_HUMANTASK_NAMESPACE = "";
    public static final String WS_HUMANTASK_SPECIFICATION = "ws-humantask-1.0";

    private WSHumanTaskConstants(){
        throw new AssertionError("Can't create an instance from " + WSHumanTaskConstants.class);
    }

}
