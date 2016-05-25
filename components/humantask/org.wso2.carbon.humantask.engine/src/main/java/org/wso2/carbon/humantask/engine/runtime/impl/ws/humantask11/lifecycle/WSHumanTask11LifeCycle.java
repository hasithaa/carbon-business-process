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

import org.wso2.carbon.humantask.engine.runtime.impl.ws.humantask11.States;
import org.wso2.carbon.humantask.engine.runtime.impl.ws.humantask11.TaskTypes;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.Operation;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.State;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.TaskLifeCycle;
import org.wso2.carbon.humantask.engine.runtime.db.model.HumanTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WSHumanTask11LifeCycle implements TaskLifeCycle {

    private List<State> wsHumanTaskStatus;
    private List<String> supportedTasks;

    private static WSHumanTask11LifeCycle instance;

    private WSHumanTask11LifeCycle() {

        // Defining Supported States.
        this.wsHumanTaskStatus = new ArrayList<>();
        State created = new State(States.CREATED.name());
        wsHumanTaskStatus.add(created);

        State ready = new State(States.READY.name());
        wsHumanTaskStatus.add(ready);

        State reserved = new State(States.RESERVED.name());
        wsHumanTaskStatus.add(reserved);

        State inProgress = new State(States.IN_PROGRESS.name());
        wsHumanTaskStatus.add(inProgress);

        State suspendedReady = new State(States.SUSPENDED_READY.name());
        wsHumanTaskStatus.add(suspendedReady);

        State suspendedReserved = new State(States.SUSPENDED_RESERVED.name());
        wsHumanTaskStatus.add(suspendedReserved);

        State suspendedInProgress = new State(States.SUSPENDED_IN_PROGRESS.name());
        wsHumanTaskStatus.add(suspendedInProgress);

        State completed = new State(States.COMPLETED.name());
        wsHumanTaskStatus.add(completed);

        State failed = new State(States.FAILED.name());
        wsHumanTaskStatus.add(failed);

        State error = new State(States.ERROR.name());
        wsHumanTaskStatus.add(error);

        State exited = new State(States.EXITED.name());
        wsHumanTaskStatus.add(exited);

        State obsolete = new State(States.OBSOLETE.name());
        wsHumanTaskStatus.add(obsolete);


        Map<String, State> nextStatesForCreated = new HashMap<>();
        Map<String, State> nextStatesForReady = new HashMap<>();
        Map<String, State> nextStatesForReserved = new HashMap<>();
        Map<String, State> nextStatesForInProgress = new HashMap<>();
        Map<String, State> nextStatesForSuspendedReady = new HashMap<>();
        Map<String, State> nextStatesForSuspendedReserved = new HashMap<>();
        Map<String, State> nextStatesForSuspendedInProgress = new HashMap<>();
        Map<String, State> nextStatesForSuspendedCompleted = new HashMap<>();
        Map<String, State> nextStatesForSuspendedFailed = new HashMap<>();
        Map<String, State> nextStatesForSuspendedError = new HashMap<>();
        Map<String, State> nextStatesForSuspendedExited = new HashMap<>();
        Map<String, State> nextStatesForSuspendedObsolete = new HashMap<>();


        // Defining Supported Task Types
        this.supportedTasks = new ArrayList<>();
        supportedTasks.add(TaskTypes.TASK.name());
        supportedTasks.add(TaskTypes.REMOTE_TASK.name());
        supportedTasks.add(TaskTypes.LOCAL_TASK.name());
        supportedTasks.add(TaskTypes.LEAN_TASK.name());
    }

    public static WSHumanTask11LifeCycle getInstance() {
        if (instance == null) {
            instance = new WSHumanTask11LifeCycle();
        }
        return instance;
    }

    @Override
    public HumanTask performOperation(HumanTask task, Operation operation) {
        return null;
    }

    @Override
    public List<String> getSupportedTaskType() {
        return supportedTasks;
    }
}
