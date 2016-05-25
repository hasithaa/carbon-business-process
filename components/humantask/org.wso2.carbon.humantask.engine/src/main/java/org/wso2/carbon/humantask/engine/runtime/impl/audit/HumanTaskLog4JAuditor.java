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


package org.wso2.carbon.humantask.engine.runtime.impl.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.humantask.engine.runtime.Constants;
import org.wso2.carbon.humantask.engine.runtime.audit.HumanTaskAuditor;

/**
 * Log4j based auditor.
 */
public class HumanTaskLog4JAuditor implements HumanTaskAuditor {

    private static final Logger log = LoggerFactory.getLogger(HumanTaskLog4JAuditor.class);




    /**
     * Log audit data to logger.
     *
     * @param auditType  is the type of Audit data.
     * @param auditLevel is the level of Audit data
     * @param taskID     of the Audit data.
     * @param originator of the Audit data. ( Eg. User, Internal component.  )
     * @param operation  of the task operation.
     * @param message    of the Audit data.
     */
    @Override
    public void audit(AuditType auditType, AuditLevel auditLevel, String taskID, String originator, String operation,
                      String message) {
        StringBuilder auditMsg = new StringBuilder();
        logParam(auditMsg, Constants.AUDIT);
        logParam(auditMsg, auditLevel.name());
        logParam(auditMsg, auditType.name());
        logParam(auditMsg, Constants.TASK_ID, taskID);
        logParam(auditMsg, Constants.ORIGINATOR, originator);
        logParam(auditMsg, Constants.OPERATION, operation);
        logParam(auditMsg, Constants.MESSAGE, message);
        log.info(auditMsg.toString());
    }

    private void logParam(StringBuilder auditMsg, String value) {
        auditMsg.append(Constants.CHAR_OSB)
                .append(value)
                .append(Constants.CHAR_CSB);
    }

    private void logParam(StringBuilder auditMsg, String key, String value) {
        auditMsg.append(Constants.CHAR_OSB)
                .append(key)
                .append(Constants.CHAR_EQL)
                .append(value)
                .append(Constants.CHAR_CSB);
    }
}
