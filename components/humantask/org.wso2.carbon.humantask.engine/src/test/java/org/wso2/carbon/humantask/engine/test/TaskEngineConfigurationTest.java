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


package org.wso2.carbon.humantask.engine.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.wso2.carbon.humantask.engine.EngineConstants;
import org.wso2.carbon.humantask.engine.EngineRuntimeException;
import org.wso2.carbon.humantask.engine.config.HumanTaskConfigurationFactory;
import org.wso2.carbon.humantask.engine.config.model.DataSource;
import org.wso2.carbon.humantask.engine.config.model.HumanTaskConfiguration;
import org.wso2.carbon.humantask.engine.config.model.PeopleQueryConfiguration;
import org.wso2.carbon.humantask.engine.runtime.impl.people.CaasBasedPeopleQueryEvaluator;

import java.nio.file.Paths;
import java.util.HashMap;

public class TaskEngineConfigurationTest {

    public static HumanTaskConfiguration buildTestServerConfig() {
        HumanTaskConfiguration conf = new HumanTaskConfiguration();
        conf.setEngineId("test-engine");
        conf.setName("test engine");
        conf.setPeopleQueryConfiguration(new PeopleQueryConfiguration());
        conf.getPeopleQueryConfiguration().setPeopleQueryEvaluator(InMemoryPeopleQueryEvaluator.class
                .getCanonicalName());
        conf.getPeopleQueryConfiguration().setProperties(new HashMap<>());
        conf.setDataSource(new DataSource());
        conf.getDataSource().setRunInMemory(true);
        conf.getDataSource().setHumanTaskDataSource("NotRequired.");
        conf.getDataSource().setAuditDataSource("NotRequired.");
        conf.getDataSource().setHistoryDataSource("NotRequired.");
        return conf;
    }

    @Test
    public void readConfigDefault() throws EngineRuntimeException {
        HumanTaskConfigurationFactory configurationFactory = new HumanTaskConfigurationFactory();
        HumanTaskConfiguration conf = configurationFactory.build();

        Assert.assertEquals(conf.getEngineId(), "default-engine", "getEngineId()");
        Assert.assertEquals(conf.getName(), "WS-HumanTask 1.1 engine", "getName()");

        Assert.assertEquals(conf.getPeopleQueryConfiguration().getPeopleQueryEvaluator(),
                CaasBasedPeopleQueryEvaluator.class.getCanonicalName(), "getPeopleQueryConfiguration()" +
                        ".getPeopleQueryEvaluator()");

        Assert.assertEquals(conf.getPeopleQueryConfiguration().getProperties().size(), 0,
                "getPeopleQueryConfiguration().getProperties()");
        Assert.assertFalse(conf.getDataSource().isRunInMemory(), "getDataSource().isRunInMemory()");
        Assert.assertEquals(conf.getDataSource().getHumanTaskDataSource(), "HumanTask", "getDataSource()" +
                ".getHumanTaskDataSource()");
        Assert.assertEquals(conf.getDataSource().getAuditDataSource(), "HumanTaskAudit", "getDataSource()" +
                ".getAuditDataSource()");
        Assert.assertEquals(conf.getDataSource().getHistoryDataSource(), "HumanTaskHistory", "getDataSource()" +
                ".getHistoryDataSource()");
    }

    @Test
    public void readConfigYaml() throws EngineRuntimeException {

        String basedir = System.getProperty("basedir");
        if (basedir == null) {
            basedir = Paths.get(".").toString();
        }
        String configYmlPath = Paths.get(basedir, "src", "main", "resources", EngineConstants.HT_CONFIG_FILE).
                toString();
        HumanTaskConfigurationFactory configurationFactory = new HumanTaskConfigurationFactory(configYmlPath);
        HumanTaskConfiguration conf = configurationFactory.build();
        Assert.assertEquals(conf.getEngineId(), "default-engine", "getEngineId()");
        Assert.assertEquals(conf.getName(), "WSO2 WS-HumanTask 1.1 engine", "getName()");

        Assert.assertEquals(conf.getPeopleQueryConfiguration().getPeopleQueryEvaluator(),
                CaasBasedPeopleQueryEvaluator.class.getCanonicalName(), "getPeopleQueryConfiguration()" +
                        ".getPeopleQueryEvaluator()");

        Assert.assertTrue(conf.getPeopleQueryConfiguration().getProperties().containsKey
                (CaasBasedPeopleQueryEvaluator.CHECK_USER_EXISTENCE), "getPeopleQueryConfiguration().getProperties()" +
                ".containsKey");
        Assert.assertEquals(conf.getPeopleQueryConfiguration().getProperties().get(
                CaasBasedPeopleQueryEvaluator.CHECK_USER_EXISTENCE), "true", "getPeopleQueryConfiguration()" +
                ".getProperties().get");

        Assert.assertTrue(conf.getPeopleQueryConfiguration().getProperties().containsKey
                (CaasBasedPeopleQueryEvaluator.CHECK_GROUP_EXISTENCE), "getPeopleQueryConfiguration().getProperties()" +
                ".containsKey");
        Assert.assertEquals(conf.getPeopleQueryConfiguration().getProperties().get(
                CaasBasedPeopleQueryEvaluator.CHECK_GROUP_EXISTENCE), "true", "getPeopleQueryConfiguration()" +
                ".getProperties().get");

        Assert.assertTrue(conf.getPeopleQueryConfiguration().getProperties().containsKey
                (CaasBasedPeopleQueryEvaluator.ENABLE_CAHCE), "getPeopleQueryConfiguration().getProperties()" +
                ".containsKey");
        Assert.assertEquals(conf.getPeopleQueryConfiguration().getProperties().get(
                CaasBasedPeopleQueryEvaluator.ENABLE_CAHCE), "false", "getPeopleQueryConfiguration().getProperties()" +
                ".get");

        Assert.assertTrue(conf.getPeopleQueryConfiguration().getProperties().containsKey
                (CaasBasedPeopleQueryEvaluator.CACHE_EXPIRY_TIME), "getPeopleQueryConfiguration().getProperties()" +
                ".containsKey");
        Assert.assertEquals(conf.getPeopleQueryConfiguration().getProperties().get(
                CaasBasedPeopleQueryEvaluator.CACHE_EXPIRY_TIME), "900", "getPeopleQueryConfiguration().getProperties" +
                "().get");

        Assert.assertFalse(conf.getDataSource().isRunInMemory(), "getDataSource().isRunInMemory()");
        Assert.assertEquals(conf.getDataSource().getHumanTaskDataSource(), "HumanTask", "getDataSource()" +
                ".getHumanTaskDataSource()");
        Assert.assertEquals(conf.getDataSource().getAuditDataSource(), "HumanTaskAudit", "getDataSource()" +
                ".getAuditDataSource()");
        Assert.assertEquals(conf.getDataSource().getHistoryDataSource(), "HumanTaskHistory", "getDataSource()" +
                ".getHistoryDataSource()");
    }

    @Test(expectedExceptions = EngineRuntimeException.class)
    public void readConfigYamlInvalid() throws EngineRuntimeException {
        String basedir = System.getProperty("basedir");
        if (basedir == null) {
            basedir = Paths.get(".").toString();
        }
        String configYmlPath = Paths.get(basedir, "src", "main", "resources", "humantask-Invalid.yml").
                toString();
        HumanTaskConfigurationFactory configurationFactory = new HumanTaskConfigurationFactory(configYmlPath);
        HumanTaskConfiguration conf = configurationFactory.build();

    }

    @Test
    public void manualConfigCreationTest() {
        HumanTaskConfiguration conf = buildTestServerConfig();
        Assert.assertNotNull(conf);
    }


}
