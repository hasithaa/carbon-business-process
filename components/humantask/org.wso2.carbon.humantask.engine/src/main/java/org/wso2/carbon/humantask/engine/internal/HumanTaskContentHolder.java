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


package org.wso2.carbon.humantask.engine.internal;

import org.wso2.carbon.humantask.engine.config.model.HumanTaskConfiguration;

/**
 * Content Holder for HumanTask component.
 *
 * @since 5.0.0
 */
public class HumanTaskContentHolder {

    private static HumanTaskContentHolder instance = null;

    private HumanTaskConfiguration humanTaskConfiguration;

    private HumanTaskContentHolder() {
    }

    public static HumanTaskContentHolder getInstance() {
        if (instance == null) {
            instance = new HumanTaskContentHolder();
        }
        return instance;
    }

    public HumanTaskConfiguration getHumanTaskConfiguration() {
        return humanTaskConfiguration;
    }

    protected void setHumanTaskConfiguration(HumanTaskConfiguration humanTaskConfiguration) {
        this.humanTaskConfiguration = humanTaskConfiguration;
    }
}
