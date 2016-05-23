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


package org.wso2.carbon.humantask.engine.runtime.lifecycle.ws.humantask11;

/**
 * This defines Participant Operations defined in HumanTask Specification 1.1 in section
 * 7.1.1 Participant Operations.
 *
 * @see <a href="http://docs.oasis-open.org/bpel4people/ws-humantask-1.1-spec-cs-01.html#_Toc261430337">7.1.1
 * Participant Operations</a>.
 */
public enum Operations {

    addAttachment,
    addComment,
    claim,
    complete,
    delegate,
    deleteAttachment,
    deleteComment,
    deleteFault,
    deleteOutput,
    fail,
    forward,
    getAttachment,
    getAttachmentInfos,
    getComments,
    getFault,
    getInput,
    getOutcome,
    getOutput,
    getParentTask,
    getParentTaskIdentifier,
    getRendering,
    getRenderingTypes,
    getSubtaskIdentifiers,
    getSubtasks,
    getTaskDescription,
    getTaskDetails,
    getTaskHistory,
    getTaskInstanceData,
    getTaskOperations,
    hasSubtasks,
    instantiateSubTask,
    isSubtask,
    release,
    remove,
    resume,
    setFault,
    setOutput,
    setPriority,
    setTaskCompletionDeadlineExpression,
    setTaskCompletionDurationExpression,
    setTaskStartDeadlineExpression,
    setTaskStartDurationExpression,
    skip,
    start,
    stop,
    suspend,
    suspendUntil,
    updateComment,
}
