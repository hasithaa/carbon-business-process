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


package org.wso2.carbon.humantask.engine.runtime.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log4j based auditor.
 */
public class HumanTaskLog4JAuditor implements HumanTaskAuditor {

    private static final Logger log = LoggerFactory.getLogger(HumanTaskLog4JAuditor.class);

    private final static String AUDIT = "AUDIT";
    private final static String TASKID = "TaskID";
    private final static String ORIGINATOR = "Originator";
    private final static String OPERATION = "Operation";
    private final static String MESSAGE = "Message";
    private final static String CHAR_OSB = " [";
    private final static String CHAR_CSB = "] ";
    private final static String CHAR_EQL = " = ";


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
        logParam(auditMsg, AUDIT);
        logParam(auditMsg, auditLevel.name());
        logParam(auditMsg, auditType.name());
        logParam(auditMsg, TASKID, taskID);
        logParam(auditMsg, ORIGINATOR, originator);
        logParam(auditMsg, OPERATION, operation);
        logParam(auditMsg, MESSAGE, message);
        log.info(auditMsg.toString());
    }

    private void logParam(StringBuilder auditMsg, String value) {
        auditMsg.append(CHAR_OSB)
                .append(value)
                .append(CHAR_CSB);
    }

    private void logParam(StringBuilder auditMsg, String key, String value) {
        auditMsg.append(CHAR_OSB)
                .append(key)
                .append(CHAR_EQL)
                .append(value)
                .append(CHAR_CSB);
    }
}
