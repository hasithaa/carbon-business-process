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


package org.wso2.carbon.humantask.engine.runtime.api;

import org.wso2.carbon.humantask.engine.internal.ContentHolder;
import org.wso2.carbon.humantask.engine.runtime.people.PeopleQueryEvaluator;
import org.wso2.carbon.kernel.context.CarbonContext;

import java.security.Principal;

public abstract class UserAPI {

    private void checkUser() {
        CarbonContext context = CarbonContext.getCurrentContext();
        if (context != null) {
            Principal userPrincipal = context.getUserPrincipal();
            if (userPrincipal != null && userPrincipal.getName() != null) {
                ContentHolder holder = ContentHolder.getInstance();
                if (holder.getTaskEngine() != null
                        && holder.getTaskEngine().getHumanTaskRuntime() != null
                        && holder.getTaskEngine().getHumanTaskRuntime().getPeopleQueryEvaluator() != null) {
                    PeopleQueryEvaluator pqe = holder.getTaskEngine()
                            .getHumanTaskRuntime().getPeopleQueryEvaluator();
                    if (pqe.isExistingUser(userPrincipal.getName())) {
                        return;
                    }
                    throw new UnauthorizedFault("Unauthorized access: Found user principle, but engine can't retrieve " +
                            "user from People Query Evaluator. Please Check People Query Configuration.");
                }
            }
            throw new UnauthorizedFault("Unauthorized access: Can't find user principle for operation : " +
                    getName());
        }
        throw new UnauthorizedFault("Unauthorized access: Carbon context can't be null by implementation.?");
    }

    public abstract String getName();

}
