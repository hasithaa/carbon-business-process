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


package org.wso2.carbon.humantask.engine.runtime.impl.ws.humantask11.lifecycle;

import org.wso2.carbon.humantask.engine.runtime.impl.ws.humantask11.Operations;
import org.wso2.carbon.humantask.engine.runtime.impl.ws.humantask11.States;
import org.wso2.carbon.humantask.engine.runtime.impl.ws.humantask11.TaskTypes;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.Operation;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.State;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.TaskLifeCycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WSHumanTask11LifeCycle implements TaskLifeCycle {

    private final static WSHumanTask11LifeCycle instance = new WSHumanTask11LifeCycle();

    private Map<String, State> wsHumanTaskStates;
    private Map<String, Operation> wsHumanTaskOperations;
    private List<String> supportedTasks;

    private State startingState;

    private WSHumanTask11LifeCycle() {

        long startTime = System.currentTimeMillis();

        // Defining Supported Task Types
        this.supportedTasks = new ArrayList<>();
        supportedTasks.add(TaskTypes.WS_HT11_TASK.name());
        supportedTasks.add(TaskTypes.WS_HT11_REMOTE_TASK.name());
        supportedTasks.add(TaskTypes.WS_HT11_LOCAL_TASK.name());
        supportedTasks.add(TaskTypes.WS_HT11_LEAN_TASK.name());

        // Defining Supported States.
        this.wsHumanTaskStates = new HashMap<>();


        // Created.
        final State created = new State(States.CREATED.name());
        wsHumanTaskStates.put(created.getStateName(), created);
        this.startingState = created;
        // Ready
        final State ready = new State(States.READY.name());
        wsHumanTaskStates.put(ready.getStateName(), ready);
        // Reserved
        final State reserved = new State(States.RESERVED.name());
        wsHumanTaskStates.put(reserved.getStateName(), reserved);
        //InProgress
        final State inProgress = new State(States.IN_PROGRESS.name());
        wsHumanTaskStates.put(inProgress.getStateName(), inProgress);
        // Suspend Ready
        final State suspendedReady = new State(States.SUSPENDED_READY.name());
        wsHumanTaskStates.put(suspendedReady.getStateName(), suspendedReady);
        // Suspend Reserved
        final State suspendedReserved = new State(States.SUSPENDED_RESERVED.name());
        wsHumanTaskStates.put(suspendedReserved.getStateName(), suspendedReserved);
        // Suspend In Progress
        final State suspendedInProgress = new State(States.SUSPENDED_IN_PROGRESS.name());
        wsHumanTaskStates.put(suspendedInProgress.getStateName(), suspendedInProgress);
        // Completed
        final State completed = new State(States.COMPLETED.name(), true);
        wsHumanTaskStates.put(completed.getStateName(), completed);
        // Failed
        final State failed = new State(States.FAILED.name(), true);
        wsHumanTaskStates.put(failed.getStateName(), failed);
        // Error
        final State error = new State(States.ERROR.name(), true);
        wsHumanTaskStates.put(error.getStateName(), error);
        // Existed
        final State exited = new State(States.EXITED.name(), true);
        wsHumanTaskStates.put(exited.getStateName(), exited);
        // Obsolete
        final State obsolete = new State(States.OBSOLETE.name(), true);
        wsHumanTaskStates.put(obsolete.getStateName(), obsolete);

        // Defining Operations.
        wsHumanTaskOperations = new HashMap<>();


        // Administrative Operations supported by WS-humanTask 1.1 specification.
        Operation activate = new Operation(Operations.activate.name(), true, true);
        activate.addNextState(created, ready);
        activate.addSupportedState(ready);
        wsHumanTaskOperations.put(activate.getOperationName(), activate);

        Operation nominate = new Operation(Operations.nominate.name(), true, true);
        nominate.addSupportedState(created);
        nominate.addNextState(created, ready);
        // nominate.addNextState(created, reserved); ( this will handled at the command execution.)
        wsHumanTaskOperations.put(nominate.getOperationName(), nominate);

        Operation setGenericHumanRole = new Operation(Operations.setGenericHumanRole.name(), true);
        setGenericHumanRole.addSupportedState(created);
        setGenericHumanRole.addSupportedState(reserved);
        setGenericHumanRole.addSupportedState(ready);
        setGenericHumanRole.addSupportedState(inProgress);
        setGenericHumanRole.addSupportedState(suspendedInProgress);
        setGenericHumanRole.addSupportedState(suspendedReady);
        setGenericHumanRole.addSupportedState(suspendedReserved);
        wsHumanTaskOperations.put(setGenericHumanRole.getOperationName(), setGenericHumanRole);

        // Operations supported by WS-humanTask 1.1 specification.

        Operation addAttachment = new Operation(Operations.addAttachment.name());
        addAttachment.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(addAttachment.getOperationName(), addAttachment);

        Operation addComment = new Operation(Operations.addComment.name());
        addComment.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(addComment.getOperationName(), addComment);

        Operation claim = new Operation(Operations.claim.name(), true, true);
        claim.addNextState(ready, reserved);
        claim.addSupportedState(ready);
        wsHumanTaskOperations.put(claim.getOperationName(), claim);

        Operation complete = new Operation(Operations.complete.name(), true, true);
        complete.addNextState(inProgress, completed);
        complete.addSupportedState(inProgress);
        wsHumanTaskOperations.put(complete.getOperationName(), complete);

        Operation delegate = new Operation(Operations.delegate.name(), true, true);
        delegate.addNextState(ready, reserved);
        delegate.addNextState(reserved, reserved);
        delegate.addNextState(inProgress, reserved);
        delegate.addSupportedState(ready);
        delegate.addSupportedState(reserved);
        delegate.addSupportedState(inProgress);
        wsHumanTaskOperations.put(delegate.getOperationName(), delegate);

        Operation deleteAttachment = new Operation(Operations.deleteAttachment.name());
        deleteAttachment.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(deleteAttachment.getOperationName(), deleteAttachment);

        Operation deleteComment = new Operation(Operations.deleteComment.name());
        deleteComment.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(deleteComment.getOperationName(), deleteComment);

        Operation deleteFault = new Operation(Operations.deleteFault.name());
        deleteFault.addSupportedState(inProgress);
        wsHumanTaskOperations.put(deleteFault.getOperationName(), deleteFault);

        Operation deleteOutput = new Operation(Operations.deleteOutput.name());
        deleteOutput.addSupportedState(inProgress);
        wsHumanTaskOperations.put(deleteOutput.getOperationName(), deleteOutput);

        Operation fail = new Operation(Operations.fail.name(), true, true);
        fail.addNextState(inProgress, failed);
        fail.addSupportedState(inProgress);
        wsHumanTaskOperations.put(fail.getOperationName(), fail);

        Operation forward = new Operation(Operations.forward.name(), true, true);
        forward.addNextState(ready, ready);
        forward.addNextState(reserved, ready);
        forward.addNextState(inProgress, ready);
        forward.addSupportedState(ready);
        forward.addSupportedState(reserved);
        forward.addSupportedState(inProgress);
        wsHumanTaskOperations.put(forward.getOperationName(), forward);

        Operation getAttachment = new Operation(Operations.getAttachment.name());
        getAttachment.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getAttachment.getOperationName(), getAttachment);

        Operation getAttachmentInfos = new Operation(Operations.getAttachmentInfos.name());
        getAttachmentInfos.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getAttachmentInfos.getOperationName(), getAttachmentInfos);

        Operation getComments = new Operation(Operations.getComments.name());
        getComments.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getComments.getOperationName(), getComments);

        Operation getFault = new Operation(Operations.getFault.name());
        getFault.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getFault.getOperationName(), getFault);

        Operation getInput = new Operation(Operations.getInput.name());
        getInput.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getInput.getOperationName(), getInput);

        Operation getOutcome = new Operation(Operations.getOutcome.name());
        getOutcome.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getOutcome.getOperationName(), getOutcome);

        Operation getOutput = new Operation(Operations.getOutput.name());
        getOutput.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getOutput.getOperationName(), getOutput);

        Operation getParentTask = new Operation(Operations.getParentTask.name());
        getParentTask.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getParentTask.getOperationName(), getParentTask);

        Operation getParentTaskIdentifier = new Operation(Operations.getParentTaskIdentifier.name());
        getParentTaskIdentifier.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getParentTaskIdentifier.getOperationName(), getParentTaskIdentifier);

        Operation getRendering = new Operation(Operations.getRendering.name());
        getRendering.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getRendering.getOperationName(), getRendering);

        Operation getRenderingTypes = new Operation(Operations.getRenderingTypes.name());
        getRenderingTypes.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getRenderingTypes.getOperationName(), getRenderingTypes);

        Operation getSubtaskIdentifiers = new Operation(Operations.getSubtaskIdentifiers.name());
        getSubtaskIdentifiers.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getSubtaskIdentifiers.getOperationName(), getSubtaskIdentifiers);

        Operation getSubtasks = new Operation(Operations.getSubtasks.name());
        getSubtasks.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getSubtasks.getOperationName(), getSubtasks);

        Operation getTaskDescription = new Operation(Operations.getTaskDescription.name());
        getTaskDescription.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getTaskDescription.getOperationName(), getTaskDescription);

        Operation getTaskDetails = new Operation(Operations.getTaskDetails.name());
        getTaskDetails.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getTaskDetails.getOperationName(), getTaskDetails);

        Operation getTaskHistory = new Operation(Operations.getTaskHistory.name());
        getTaskHistory.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getTaskHistory.getOperationName(), getTaskHistory);

        Operation getTaskInstanceData = new Operation(Operations.getTaskInstanceData.name());
        getTaskInstanceData.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getTaskInstanceData.getOperationName(), getTaskInstanceData);

        Operation getTaskOperations = new Operation(Operations.getTaskOperations.name());
        getTaskOperations.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(getTaskOperations.getOperationName(), getTaskOperations);

        Operation hasSubtasks = new Operation(Operations.hasSubtasks.name());
        hasSubtasks.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(hasSubtasks.getOperationName(), hasSubtasks);

        Operation instantiateSubTask = new Operation(Operations.instantiateSubTask.name());
        instantiateSubTask.addSupportedState(reserved);
        instantiateSubTask.addSupportedState(inProgress);
        wsHumanTaskOperations.put(instantiateSubTask.getOperationName(), instantiateSubTask);

        Operation isSubtask = new Operation(Operations.isSubtask.name());
        isSubtask.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(isSubtask.getOperationName(), isSubtask);

        Operation release = new Operation(Operations.release.name(), true, true);
        release.addNextState(inProgress, ready);
        release.addNextState(reserved, ready);
        release.addSupportedState(inProgress);
        release.addSupportedState(reserved);
        wsHumanTaskOperations.put(release.getOperationName(), release);

        Operation resume = new Operation(Operations.resume.name(), true, true);
        resume.addNextState(suspendedReady, ready);
        resume.addNextState(suspendedReserved, reserved);
        resume.addNextState(suspendedInProgress, inProgress);
        resume.addSupportedState(suspendedReady);
        resume.addSupportedState(suspendedReserved);
        resume.addSupportedState(suspendedInProgress);
        wsHumanTaskOperations.put(resume.getOperationName(), resume);

        Operation setFault = new Operation(Operations.setFault.name());
        setFault.addSupportedState(inProgress);
        wsHumanTaskOperations.put(setFault.getOperationName(), setFault);

        Operation setOutput = new Operation(Operations.setOutput.name());
        setOutput.addSupportedState(inProgress);
        wsHumanTaskOperations.put(setOutput.getOperationName(), setOutput);

        Operation setPriority = new Operation(Operations.setPriority.name(), true);
        setPriority.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(setPriority.getOperationName(), setPriority);

        Operation setTaskCompletionDeadlineExpression = new Operation(Operations.setTaskCompletionDeadlineExpression
                .name(), true);
        setTaskCompletionDeadlineExpression.addSupportedState(created);
        setTaskCompletionDeadlineExpression.addSupportedState(ready);
        setTaskCompletionDeadlineExpression.addSupportedState(reserved);
        setTaskCompletionDeadlineExpression.addSupportedState(inProgress);
        wsHumanTaskOperations.put(setTaskCompletionDeadlineExpression.getOperationName(),
                setTaskCompletionDeadlineExpression);

        Operation setTaskCompletionDurationExpression = new Operation(Operations.setTaskCompletionDurationExpression
                .name(), true);
        setTaskCompletionDurationExpression.addSupportedState(created);
        setTaskCompletionDurationExpression.addSupportedState(ready);
        setTaskCompletionDurationExpression.addSupportedState(reserved);
        setTaskCompletionDurationExpression.addSupportedState(inProgress);
        wsHumanTaskOperations.put(setTaskCompletionDurationExpression.getOperationName(),
                setTaskCompletionDurationExpression);

        Operation setTaskStartDeadlineExpression = new Operation(Operations.setTaskStartDeadlineExpression.name(),
                true);
        setTaskStartDeadlineExpression.addSupportedState(created);
        setTaskStartDeadlineExpression.addSupportedState(ready);
        setTaskStartDeadlineExpression.addSupportedState(reserved);
        setTaskStartDeadlineExpression.addSupportedState(inProgress);
        wsHumanTaskOperations.put(setTaskStartDeadlineExpression.getOperationName(), setTaskStartDeadlineExpression);

        Operation setTaskStartDurationExpression = new Operation(Operations.setTaskStartDurationExpression.name(),
                true);
        setTaskStartDurationExpression.addSupportedState(created);
        setTaskStartDurationExpression.addSupportedState(ready);
        setTaskStartDurationExpression.addSupportedState(reserved);
        setTaskStartDurationExpression.addSupportedState(inProgress);
        wsHumanTaskOperations.put(setTaskStartDurationExpression.getOperationName(), setTaskStartDurationExpression);

        Operation skip = new Operation(Operations.skip.name(), true, true);
        skip.addNextState(created, obsolete);
        skip.addNextState(ready, obsolete);
        skip.addNextState(reserved, obsolete);
        skip.addNextState(inProgress, obsolete);
        skip.addSupportedState(created);
        skip.addSupportedState(ready);
        skip.addSupportedState(reserved);
        skip.addSupportedState(inProgress);
        wsHumanTaskOperations.put(skip.getOperationName(), skip);

        Operation start = new Operation(Operations.start.name(), true, true);
        start.addNextState(ready, inProgress);
        start.addNextState(reserved, inProgress);
        start.addSupportedState(ready);
        start.addSupportedState(reserved);
        wsHumanTaskOperations.put(start.getOperationName(), start);

        Operation stop = new Operation(Operations.stop.name(), true, true);
        stop.addNextState(inProgress, reserved);
        stop.addSupportedState(inProgress);
        wsHumanTaskOperations.put(stop.getOperationName(), stop);

        Operation suspend = new Operation(Operations.suspend.name(), true, true);
        suspend.addNextState(ready, suspendedReady);
        suspend.addNextState(reserved, suspendedReserved);
        suspend.addNextState(inProgress, suspendedInProgress);
        suspend.addSupportedState(ready);
        suspend.addSupportedState(reserved);
        suspend.addSupportedState(inProgress);
        wsHumanTaskOperations.put(suspend.getOperationName(), suspend);

        Operation suspendUntil = new Operation(Operations.suspendUntil.name(), true, true);
        suspendUntil.addNextState(ready, suspendedReady);
        suspendUntil.addNextState(reserved, suspendedReserved);
        suspendUntil.addNextState(inProgress, suspendedInProgress);
        suspendUntil.addSupportedState(ready);
        suspendUntil.addSupportedState(reserved);
        suspendUntil.addSupportedState(inProgress);
        wsHumanTaskOperations.put(suspendUntil.getOperationName(), suspendUntil);

        Operation updateComment = new Operation(Operations.updateComment.name());
        updateComment.addSupportedStates(wsHumanTaskStates.values());
        wsHumanTaskOperations.put(updateComment.getOperationName(), updateComment);

    }

    public static WSHumanTask11LifeCycle getInstance() {
        return instance;
    }

    @Override
    public List<String> getSupportedTaskType() {
        return supportedTasks;
    }

    public State getStartingState() {
        return startingState;
    }
}
