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


package org.wso2.carbon.humantask.engine.test.humantask11;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.wso2.carbon.humantask.engine.runtime.context.TaskRuntimeContext;
import org.wso2.carbon.humantask.engine.runtime.impl.ws.humantask11.TaskTypes;
import org.wso2.carbon.humantask.engine.runtime.impl.ws.humantask11.WSHumanTask11LifeCycle;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.Operation;
import org.wso2.carbon.humantask.engine.runtime.lifecycle.execution.AbstractOperationExecution;
import org.wso2.carbon.humantask.engine.runtime.people.TaskUser;
import org.wso2.carbon.humantask.engine.runtime.persistence.entity.HumanTask;

import java.util.Arrays;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class LifeCycleTest {

    private static final Logger logger = LoggerFactory.getLogger(LifeCycleTest.class);

    private static long lastTaskID = 0;

    @Test
    public void testValidateHumanTaskLifeCycle() throws Exception {
        long startTime = System.currentTimeMillis();
        WSHumanTask11LifeCycle instance = WSHumanTask11LifeCycle.getInstance();
        long endTime = System.currentTimeMillis();
        logger.info("Time Taken to initiate HumanTask Life Cycle : " + (endTime - startTime) + " ms.");
        instance.validateRuntimeModel();
    }

    @Test(dependsOnMethods = "testValidateHumanTaskLifeCycle")
    public void testLifeCycle() throws JsonProcessingException, JAXBException {
        ObjectMapper mapper = new ObjectMapper();
        HumanTask task = createDummyTask();
        WSHumanTask11LifeCycle task11LifeCycle = WSHumanTask11LifeCycle.getInstance();

        if (task11LifeCycle.isSupportedTask(task.getTaskType())) {
            // creating task
            task.setState(task11LifeCycle.getStartingState().getId());
            TaskUser admin = new TaskUser("admin", "admin@test.com", Arrays.asList(new String[]{"admin", "managers"}));
            TaskUser user1 = new TaskUser("user1", "user1@test.com", Arrays.asList(new String[]{"users", "clerk"}));

            TaskRuntimeContext.destroyCurrentContext();
            TaskRuntimeContext currentContext = TaskRuntimeContext.getCurrentContext();
            currentContext.setTaskUser(admin);
//            currentContext.setTaskId(lastTaskID);

            // invoke activate
            Long time0 = System.currentTimeMillis();
            Operation activate = task11LifeCycle.getOperation("activate");
            if (activate != null) {
                AbstractOperationExecution executor = activate.getExecutor();
                Object result = executor.execute();
                long time1 = System.currentTimeMillis();
                logger.info("Time taken " + (time1 -time0) + " ms");

                // Testing JSON
                String jsonInString = mapper.writeValueAsString(result);
                System.out.println(jsonInString);

                // Testing XML
                JAXBContext jaxbContext;
                Class t = result.getClass();
                jaxbContext = JAXBContext.newInstance(t);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                // output pretty printed
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbMarshaller.marshal(result, System.out);

            }


        }


    }


    private synchronized HumanTask createDummyTask() {
        return new HumanTask(++lastTaskID, "testTask", "testPackage", "testDefinition", 1, 5, TaskTypes
                .WS_HT11_LOCAL_TASK.name());
    }
}
