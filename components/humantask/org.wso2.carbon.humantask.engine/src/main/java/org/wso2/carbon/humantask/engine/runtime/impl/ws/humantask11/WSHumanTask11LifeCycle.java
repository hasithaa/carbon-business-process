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

import org.wso2.carbon.humantask.engine.runtime.impl.ws.humantask11.execution.CmdActivate;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.HumanRole;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.Operation;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.State;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.TaskLifeCycle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WSHumanTask11LifeCycle implements TaskLifeCycle {

    private static WSHumanTask11LifeCycle instance;

    private final String id;
    private Map<String, State> wsHumanTaskStates;
    private Map<String, Operation> wsHumanTaskOperations;
    private Set<String> supportedTasks;
    private Map<String, HumanRole> supportedHumanRoles;

    private State startingState;

    private WSHumanTask11LifeCycle() {

        this.id = "ws-humantask-11-tasks";

        // Defining Supported Task Types
        this.supportedTasks = new HashSet<>();
        supportedTasks.add(TaskTypes.WS_HT11_TASK.name());
        supportedTasks.add(TaskTypes.WS_HT11_REMOTE_TASK.name());
        supportedTasks.add(TaskTypes.WS_HT11_LOCAL_TASK.name());
        supportedTasks.add(TaskTypes.WS_HT11_LEAN_TASK.name());


        // Defining Supported People Roles.
        this.supportedHumanRoles = new HashMap<>();
        HumanRole taskInitiator = new HumanRole(1, People.TaskInitiator.name());
        HumanRole taskStakeholders = new HumanRole(2, People.TaskStakeholders.name());
        HumanRole potentialOwners = new HumanRole(3, People.PotentialOwners.name());
        HumanRole actualOwner = new HumanRole(4, People.ActualOwner.name());
        HumanRole businessAdministrator = new HumanRole(5, People.BusinessAdministrator.name());
        HumanRole excludedOwner = new HumanRole(7, People.ExcludedOwner.name());

        this.supportedHumanRoles.put(taskInitiator.getName(), taskInitiator);
        this.supportedHumanRoles.put(taskStakeholders.getName(), taskStakeholders);
        this.supportedHumanRoles.put(potentialOwners.getName(), potentialOwners);
        this.supportedHumanRoles.put(actualOwner.getName(), actualOwner);
        this.supportedHumanRoles.put(businessAdministrator.getName(), businessAdministrator);
        this.supportedHumanRoles.put(excludedOwner.getName(), excludedOwner);


        // Defining Supported States.
        this.wsHumanTaskStates = new HashMap<>();

        // Created.
        final State created = new State(10, States.CREATED.name());
        wsHumanTaskStates.put(created.getStateName(), created);
        this.startingState = created;
        // Ready
        final State ready = new State(20, States.READY.name());
        wsHumanTaskStates.put(ready.getStateName(), ready);
        // Reserved
        final State reserved = new State(30, States.RESERVED.name());
        wsHumanTaskStates.put(reserved.getStateName(), reserved);
        //InProgress
        final State inProgress = new State(40, States.IN_PROGRESS.name());
        wsHumanTaskStates.put(inProgress.getStateName(), inProgress);
        // Suspend Ready
        final State suspendedReady = new State(25, States.SUSPENDED_READY.name());
        wsHumanTaskStates.put(suspendedReady.getStateName(), suspendedReady);
        // Suspend Reserved
        final State suspendedReserved = new State(35, States.SUSPENDED_RESERVED.name());
        wsHumanTaskStates.put(suspendedReserved.getStateName(), suspendedReserved);
        // Suspend In Progress
        final State suspendedInProgress = new State(45, States.SUSPENDED_IN_PROGRESS.name());
        wsHumanTaskStates.put(suspendedInProgress.getStateName(), suspendedInProgress);
        // Completed
        final State completed = new State(50, States.COMPLETED.name(), true);
        wsHumanTaskStates.put(completed.getStateName(), completed);
        // Failed
        final State failed = new State(60, States.FAILED.name(), true);
        wsHumanTaskStates.put(failed.getStateName(), failed);
        // Error
        final State error = new State(70, States.ERROR.name(), true);
        wsHumanTaskStates.put(error.getStateName(), error);
        // Existed
        final State exited = new State(80, States.EXITED.name(), true);
        wsHumanTaskStates.put(exited.getStateName(), exited);
        // Obsolete
        final State obsolete = new State(90, States.OBSOLETE.name(), true);
        wsHumanTaskStates.put(obsolete.getStateName(), obsolete);


        // Defining Operations.
        // Operations supported by WS-humanTask 1.1 specification.
        wsHumanTaskOperations = new HashMap<>();

        // activate
        Operation activate = new Operation(1, Operations.activate.name(), true, true);
        activate.addNextState(created, ready);
        activate.setAllowedHumanRole(taskInitiator, taskStakeholders, businessAdministrator);
        activate.setExcludedHumanRole(potentialOwners, actualOwner);
        activate.setExecutor(CmdActivate.class);
        wsHumanTaskOperations.put(activate.getOperationName(), activate);

        // addAttachment
        Operation addAttachment = new Operation(2, Operations.addAttachment.name());
        addAttachment.addSupportedStates(wsHumanTaskStates.values());
        addAttachment.setAllowedHumanRole(taskStakeholders, potentialOwners, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(addAttachment.getOperationName(), addAttachment);

        // addComment
        Operation addComment = new Operation(3, Operations.addComment.name());
        addComment.addSupportedStates(wsHumanTaskStates.values());
        addComment.setAllowedHumanRole(taskStakeholders, potentialOwners, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(addComment.getOperationName(), addComment);

        // claim
        Operation claim = new Operation(4, Operations.claim.name(), true, true);
        claim.addNextState(ready, reserved);
        claim.setAllowedHumanRole(potentialOwners);
        claim.setExcludedHumanRole(actualOwner, taskInitiator);
        wsHumanTaskOperations.put(claim.getOperationName(), claim);

        // complete
        Operation complete = new Operation(5, Operations.complete.name(), true, true);
        complete.addNextState(inProgress, completed);
        complete.setAllowedHumanRole(actualOwner);
        complete.setExcludedHumanRole(taskInitiator, potentialOwners);
        wsHumanTaskOperations.put(complete.getOperationName(), complete);

        // delegate
        Operation delegate = new Operation(6, Operations.delegate.name(), true, true);
        delegate.addNextState(ready, reserved);
        delegate.addNextState(reserved, reserved);
        delegate.addNextState(inProgress, reserved);
        delegate.setAllowedHumanRole(taskStakeholders, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(delegate.getOperationName(), delegate);

        // deleteAttachment
        Operation deleteAttachment = new Operation(7, Operations.deleteAttachment.name());
        deleteAttachment.addSupportedStates(wsHumanTaskStates.values());
        deleteAttachment.setAllowedHumanRole(taskStakeholders, potentialOwners, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(deleteAttachment.getOperationName(), deleteAttachment);


        // deleteComment
        Operation deleteComment = new Operation(8, Operations.deleteComment.name());
        deleteComment.addSupportedStates(wsHumanTaskStates.values());
        deleteComment.setAllowedHumanRole(taskStakeholders, potentialOwners, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(deleteComment.getOperationName(), deleteComment);

        // deleteFault
        Operation deleteFault = new Operation(9, Operations.deleteFault.name());
        deleteFault.addSupportedStates(inProgress);
        deleteFault.setAllowedHumanRole(actualOwner);
        deleteFault.setExcludedHumanRole(taskInitiator, potentialOwners);
        wsHumanTaskOperations.put(deleteFault.getOperationName(), deleteFault);

        // deleteOutput
        Operation deleteOutput = new Operation(10, Operations.deleteOutput.name());
        deleteOutput.addSupportedStates(inProgress);
        deleteOutput.setAllowedHumanRole(actualOwner);
        deleteOutput.setExcludedHumanRole(taskInitiator, potentialOwners);
        wsHumanTaskOperations.put(deleteOutput.getOperationName(), deleteOutput);

        // fail
        Operation fail = new Operation(11, Operations.fail.name(), true, true);
        fail.addNextState(inProgress, failed);
        fail.setAllowedHumanRole(actualOwner);
        fail.setExcludedHumanRole(taskInitiator, potentialOwners);
        wsHumanTaskOperations.put(fail.getOperationName(), fail);

        // forward
        Operation forward = new Operation(12, Operations.forward.name(), true, true);
        forward.addNextState(ready, ready);
        forward.addNextState(reserved, ready);
        forward.addNextState(inProgress, ready);
        forward.setAllowedHumanRole(taskStakeholders, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(forward.getOperationName(), forward);

        // getAttachment
        Operation getAttachment = new Operation(13, Operations.getAttachment.name());
        getAttachment.addSupportedStates(wsHumanTaskStates.values());
        getAttachment.setAllowedHumanRole(taskStakeholders, potentialOwners, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(getAttachment.getOperationName(), getAttachment);

        // getAttachmentInfos
        Operation getAttachmentInfos = new Operation(14, Operations.getAttachmentInfos.name());
        getAttachmentInfos.addSupportedStates(wsHumanTaskStates.values());
        getAttachmentInfos.setAllowedHumanRole(taskStakeholders, potentialOwners, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(getAttachmentInfos.getOperationName(), getAttachmentInfos);

        // getComments
        Operation getComments = new Operation(15, Operations.getComments.name());
        getComments.addSupportedStates(wsHumanTaskStates.values());
        getComments.setAllowedHumanRole(taskStakeholders, potentialOwners, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(getComments.getOperationName(), getComments);

        // getFault
        Operation getFault = new Operation(16, Operations.getFault.name());
        getFault.addSupportedStates(wsHumanTaskStates.values());
        getFault.setAllowedHumanRole(taskInitiator, taskStakeholders, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(getFault.getOperationName(), getFault);

        // getInput
        Operation getInput = new Operation(17, Operations.getInput.name());
        getInput.addSupportedStates(wsHumanTaskStates.values());
        getInput.setAllowedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(getInput.getOperationName(), getInput);

        // getMyTaskAbstracts
        Operation getMyTaskAbstracts = new Operation(18, Operations.getMyTaskAbstracts.name());
        getMyTaskAbstracts.addSupportedStates(wsHumanTaskStates.values());
        getMyTaskAbstracts.setAllowedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(getMyTaskAbstracts.getOperationName(), getMyTaskAbstracts);

        // getMyTaskDetails
        Operation getMyTaskDetails = new Operation(19, Operations.getMyTaskDetails.name());
        getMyTaskDetails.addSupportedStates(wsHumanTaskStates.values());
        getMyTaskDetails.setAllowedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(getMyTaskDetails.getOperationName(), getMyTaskDetails);

        // getOutcome
        Operation getOutcome = new Operation(20, Operations.getOutcome.name());
        getOutcome.addSupportedStates(wsHumanTaskStates.values());
        getOutcome.setAllowedHumanRole(taskInitiator, taskStakeholders, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(getOutcome.getOperationName(), getOutcome);

        // getOutput
        Operation getOutput = new Operation(21, Operations.getOutput.name());
        getOutput.addSupportedStates(wsHumanTaskStates.values());
        getOutput.setAllowedHumanRole(taskInitiator, taskStakeholders, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(getOutput.getOperationName(), getOutput);

        // getParentTask
        Operation getParentTask = new Operation(22, Operations.getParentTask.name());
        getParentTask.addSupportedStates(wsHumanTaskStates.values());
        getParentTask.setAllowedHumanRole(taskInitiator, taskStakeholders, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(getParentTask.getOperationName(), getParentTask);

        // getParentTaskIdentifier
        Operation getParentTaskIdentifier = new Operation(23, Operations.getParentTaskIdentifier.name());
        getParentTaskIdentifier.addSupportedStates(wsHumanTaskStates.values());
        getParentTaskIdentifier.setAllowedHumanRole(taskInitiator, taskStakeholders, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(getParentTaskIdentifier.getOperationName(), getParentTaskIdentifier);

        // getRendering
        Operation getRendering = new Operation(24, Operations.getRendering.name());
        getRendering.addSupportedStates(wsHumanTaskStates.values());
        getRendering.setAllowedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(getRendering.getOperationName(), getRendering);

        // getRenderingTypes
        Operation getRenderingTypes = new Operation(25, Operations.getRenderingTypes.name());
        getRenderingTypes.addSupportedStates(wsHumanTaskStates.values());
        getRenderingTypes.setAllowedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(getRenderingTypes.getOperationName(), getRenderingTypes);

        // getSubtaskIdentifiers
        Operation getSubtaskIdentifiers = new Operation(26, Operations.getSubtaskIdentifiers.name());
        getSubtaskIdentifiers.addSupportedStates(wsHumanTaskStates.values());
        getSubtaskIdentifiers.setAllowedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(getSubtaskIdentifiers.getOperationName(), getSubtaskIdentifiers);

        // getSubtasks
        Operation getSubtasks = new Operation(27, Operations.getSubtasks.name());
        getSubtasks.addSupportedStates(wsHumanTaskStates.values());
        getSubtasks.setAllowedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(getSubtasks.getOperationName(), getSubtasks);

        // getTaskDescription
        Operation getTaskDescription = new Operation(28, Operations.getTaskDescription.name());
        getTaskDescription.addSupportedStates(wsHumanTaskStates.values());
        getTaskDescription.setAllowedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(getTaskDescription.getOperationName(), getTaskDescription);

        // getTaskDetails
        Operation getTaskDetails = new Operation(29, Operations.getTaskDetails.name());
        getTaskDetails.addSupportedStates(wsHumanTaskStates.values());
        getTaskDetails.setAllowedHumanRole(taskStakeholders, potentialOwners, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(getTaskDetails.getOperationName(), getTaskDetails);

        // getTaskHistory
        Operation getTaskHistory = new Operation(30, Operations.getTaskHistory.name());
        getTaskHistory.addSupportedStates(wsHumanTaskStates.values());
        getTaskHistory.setAllowedHumanRole(taskInitiator, taskStakeholders, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(getTaskHistory.getOperationName(), getTaskHistory);

        // getTaskInstanceData
        Operation getTaskInstanceData = new Operation(31, Operations.getTaskInstanceData.name());
        getTaskInstanceData.addSupportedStates(wsHumanTaskStates.values());
        getTaskInstanceData.setAllowedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(getTaskInstanceData.getOperationName(), getTaskInstanceData);

        // getTaskOperations
        Operation getTaskOperations = new Operation(32, Operations.getTaskOperations.name());
        getTaskOperations.addSupportedStates(wsHumanTaskStates.values());
        getTaskOperations.setAllowedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(getTaskOperations.getOperationName(), getTaskOperations);

        // hasSubtasks
        Operation hasSubtasks = new Operation(33, Operations.hasSubtasks.name());
        hasSubtasks.addSupportedStates(wsHumanTaskStates.values());
        hasSubtasks.setAllowedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(hasSubtasks.getOperationName(), hasSubtasks);

        // instantiateSubTask
        Operation instantiateSubTask = new Operation(34, Operations.instantiateSubTask.name());
        instantiateSubTask.addSupportedStates(reserved, inProgress);
        instantiateSubTask.setAllowedHumanRole(actualOwner);
        instantiateSubTask.setExcludedHumanRole(taskInitiator, taskStakeholders, potentialOwners,
                businessAdministrator);
        wsHumanTaskOperations.put(instantiateSubTask.getOperationName(), instantiateSubTask);

        // isSubtask
        Operation isSubtask = new Operation(35, Operations.isSubtask.name());
        isSubtask.addSupportedStates(wsHumanTaskStates.values());
        isSubtask.setAllowedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner,
                businessAdministrator);
        wsHumanTaskOperations.put(isSubtask.getOperationName(), isSubtask);

        // nominate
        Operation nominate = new Operation(36, Operations.nominate.name(), true, true);
        nominate.addNextState(created, ready);
        // nominate.addNextState(created, reserved); ( this will handled at the command execution.)
        nominate.setAllowedHumanRole(businessAdministrator);
        nominate.setExcludedHumanRole(taskStakeholders, potentialOwners, actualOwner);
        wsHumanTaskOperations.put(nominate.getOperationName(), nominate);

        // release
        Operation release = new Operation(37, Operations.release.name(), true, true);
        release.addNextState(inProgress, ready);
        release.addNextState(reserved, ready);
        release.setAllowedHumanRole(actualOwner);
        release.setExcludedHumanRole(taskInitiator, potentialOwners);
        wsHumanTaskOperations.put(release.getOperationName(), release);

        // resume
        Operation resume = new Operation(38, Operations.resume.name(), true, true);
        resume.addNextState(suspendedReady, ready);
        resume.addNextState(suspendedReserved, reserved);
        resume.addNextState(suspendedInProgress, inProgress);
        resume.setAllowedHumanRole(taskStakeholders, businessAdministrator);
        wsHumanTaskOperations.put(resume.getOperationName(), resume);

        // setFault
        Operation setFault = new Operation(39, Operations.setFault.name());
        setFault.addSupportedStates(inProgress);
        setFault.setAllowedHumanRole(actualOwner);
        setFault.setExcludedHumanRole(taskInitiator, potentialOwners);
        wsHumanTaskOperations.put(setFault.getOperationName(), setFault);

        // setGenericHumanRole
        Operation setGenericHumanRole = new Operation(40, Operations.setGenericHumanRole.name(), true);
        setGenericHumanRole.addSupportedStates(created, reserved, ready, inProgress, suspendedInProgress,
                suspendedReady, suspendedReserved);
        setGenericHumanRole.setAllowedHumanRole(businessAdministrator);
        setGenericHumanRole.setExcludedHumanRole(taskInitiator, taskStakeholders, potentialOwners, actualOwner);
        wsHumanTaskOperations.put(setGenericHumanRole.getOperationName(), setGenericHumanRole);

        // setOutput
        Operation setOutput = new Operation(41, Operations.setOutput.name());
        setOutput.addSupportedStates(inProgress);
        setOutput.setAllowedHumanRole(actualOwner);
        setOutput.setExcludedHumanRole(taskInitiator, potentialOwners);
        wsHumanTaskOperations.put(setOutput.getOperationName(), setOutput);

        // setPriority
        Operation setPriority = new Operation(42, Operations.setPriority.name(), true);
        setPriority.addSupportedStates(wsHumanTaskStates.values());
        setPriority.setAllowedHumanRole(taskStakeholders, businessAdministrator);
        wsHumanTaskOperations.put(setPriority.getOperationName(), setPriority);

        // setTaskCompletionDeadlineExpression
        Operation setTaskCompletionDeadlineExpression = new Operation(43, Operations.setTaskCompletionDeadlineExpression
                .name(), true);
        setTaskCompletionDeadlineExpression.addSupportedStates(created, ready, reserved, inProgress);
        setTaskCompletionDeadlineExpression.setAllowedHumanRole(taskStakeholders, businessAdministrator);
        setTaskCompletionDeadlineExpression.setExcludedHumanRole(potentialOwners, actualOwner);
        wsHumanTaskOperations.put(setTaskCompletionDeadlineExpression.getOperationName(),
                setTaskCompletionDeadlineExpression);

        // setTaskCompletionDurationExpression
        Operation setTaskCompletionDurationExpression = new Operation(44, Operations.setTaskCompletionDurationExpression
                .name(), true);
        setTaskCompletionDurationExpression.addSupportedStates(created, ready, reserved, inProgress);
        setTaskCompletionDurationExpression.setAllowedHumanRole(taskStakeholders, businessAdministrator);
        setTaskCompletionDurationExpression.setExcludedHumanRole(potentialOwners, actualOwner);
        wsHumanTaskOperations.put(setTaskCompletionDurationExpression.getOperationName(),
                setTaskCompletionDurationExpression);

        // setTaskStartDeadlineExpression
        Operation setTaskStartDeadlineExpression = new Operation(45, Operations.setTaskStartDeadlineExpression.name(),
                true);
        setTaskStartDeadlineExpression.addSupportedStates(created, ready, reserved, inProgress);
        setTaskStartDeadlineExpression.setAllowedHumanRole(taskStakeholders, businessAdministrator);
        setTaskStartDeadlineExpression.setExcludedHumanRole(potentialOwners, actualOwner);
        wsHumanTaskOperations.put(setTaskStartDeadlineExpression.getOperationName(), setTaskStartDeadlineExpression);

        // setTaskStartDurationExpression
        Operation setTaskStartDurationExpression = new Operation(46, Operations.setTaskStartDurationExpression.name(),
                true);
        setTaskStartDurationExpression.addSupportedStates(created, ready, reserved, inProgress);
        setTaskStartDurationExpression.setAllowedHumanRole(taskStakeholders, businessAdministrator);
        setTaskStartDurationExpression.setExcludedHumanRole(potentialOwners, actualOwner);
        wsHumanTaskOperations.put(setTaskStartDurationExpression.getOperationName(), setTaskStartDurationExpression);

        // skip
        Operation skip = new Operation(47, Operations.skip.name(), true, true);
        skip.addNextState(created, obsolete);
        skip.addNextState(ready, obsolete);
        skip.addNextState(reserved, obsolete);
        skip.addNextState(inProgress, obsolete);
        skip.setAllowedHumanRole(taskInitiator, taskStakeholders, businessAdministrator);
        wsHumanTaskOperations.put(skip.getOperationName(), skip);

        // start
        Operation start = new Operation(48, Operations.start.name(), true, true);
        start.addNextState(ready, inProgress);
        start.addNextState(reserved, inProgress);
        start.setAllowedHumanRole(potentialOwners, actualOwner);
        start.setExcludedHumanRole(taskInitiator);
        wsHumanTaskOperations.put(start.getOperationName(), start);

        // stop
        Operation stop = new Operation(49, Operations.stop.name(), true, true);
        stop.addNextState(inProgress, reserved);
        stop.setAllowedHumanRole(actualOwner);
        stop.setExcludedHumanRole(taskInitiator, potentialOwners);
        wsHumanTaskOperations.put(stop.getOperationName(), stop);

        // suspend
        Operation suspend = new Operation(50, Operations.suspend.name(), true, true);
        suspend.addNextState(ready, suspendedReady);
        suspend.addNextState(reserved, suspendedReserved);
        suspend.addNextState(inProgress, suspendedInProgress);
        suspend.setAllowedHumanRole(taskStakeholders, businessAdministrator);
        wsHumanTaskOperations.put(suspend.getOperationName(), suspend);

        // suspendUntil
        Operation suspendUntil = new Operation(51, Operations.suspendUntil.name(), true, true);
        suspendUntil.addNextState(ready, suspendedReady);
        suspendUntil.addNextState(reserved, suspendedReserved);
        suspendUntil.addNextState(inProgress, suspendedInProgress);
        suspendUntil.setAllowedHumanRole(taskStakeholders, businessAdministrator);
        wsHumanTaskOperations.put(suspendUntil.getOperationName(), suspendUntil);

        // updateComment
        Operation updateComment = new Operation(52, Operations.updateComment.name());
        updateComment.addSupportedStates(wsHumanTaskStates.values());
        updateComment.setAllowedHumanRole(taskStakeholders, potentialOwners, actualOwner, businessAdministrator);
        wsHumanTaskOperations.put(updateComment.getOperationName(), updateComment);


        // Excluding excludedOwner from all operations.
        wsHumanTaskOperations.values().stream().forEach(operation -> operation.setExcludedHumanRole(excludedOwner));


    }

    public static WSHumanTask11LifeCycle getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new WSHumanTask11LifeCycle();
        }
        return instance;
    }


    public void validateRuntimeModel() {
        // This method only for Validation at unit testing.

        // Validate model for unique ID.
        Set<Integer> stateValues = new HashSet<>();
        for (State state : wsHumanTaskStates.values()) {
            if (stateValues.contains(state.getId())) {
                throw new RuntimeException("There are duplicate states defined : " + state.getId() + " for Life cycle" +
                        " " + this.getClass().getName());
            } else {
                stateValues.add(state.getId());
            }
        }
        Set<Integer> operationValues = new HashSet<>();
        for (Operation operation : wsHumanTaskOperations.values()) {
            if (operationValues.contains(operation.getId())) {
                throw new RuntimeException("There are duplicate Operations defined : " + operation.getId() + " for " +
                        "Life cycle " + this.getClass().getName());
            } else {
                operationValues.add(operation.getId());
            }
        }
        Set<Integer> roleValues = new HashSet<>();
        for (HumanRole role : supportedHumanRoles.values()) {
            if (roleValues.contains(role.getId())) {
                throw new RuntimeException("There are duplicate HumanRoles defined : " + role.getId() + " for Life " +
                        "cycle " + this.getClass().getName());
            } else {
                roleValues.add(role.getId());
            }
        }
    }


    public Set<String> getSupportedTaskType() {
        return supportedTasks;
    }

    public State getStartingState() {
        return startingState;
    }

    public Set<String> getSupportedTaskStates() {
        return wsHumanTaskStates.keySet();
    }

    public Set<String> getSupportedTaskOperations() {
        return wsHumanTaskOperations.keySet();
    }

    public Set<String> getSupportedHumanRoles() {
        return supportedHumanRoles.keySet();
    }

    public boolean isSupportedTask(String taskType) {
       return this.supportedTasks.contains(taskType);
    }

    public Operation getOperation(String operation){
        return this.wsHumanTaskOperations.get(operation);
    }
}
