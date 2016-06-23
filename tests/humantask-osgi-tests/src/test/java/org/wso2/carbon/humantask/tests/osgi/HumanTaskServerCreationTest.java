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


package org.wso2.carbon.humantask.tests.osgi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.ops4j.pax.exam.testng.listener.PaxExam;
import org.osgi.framework.BundleContext;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.wso2.carbon.humantask.engine.EngineRuntimeException;
import org.wso2.carbon.humantask.engine.HumanTaskEngine;
import org.wso2.carbon.humantask.engine.HumanTaskEngineOSGIServiceImpl;
import org.wso2.carbon.humantask.tests.osgi.utils.BasicServerConfigurationUtil;
import org.wso2.carbon.kernel.utils.CarbonServerInfo;
import org.wso2.carbon.osgi.test.util.CarbonSysPropConfiguration;
import org.wso2.carbon.osgi.test.util.OSGiTestConfigurationUtils;

import java.nio.file.Path;
import java.util.List;
import javax.inject.Inject;

@Listeners(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class HumanTaskServerCreationTest {

    private static final Log log = LogFactory.getLog(HumanTaskServerCreationTest.class);

    @Inject
    private BundleContext bundleContext;

    @Inject
    private HumanTaskEngineOSGIServiceImpl humanTaskEngineOSGIService;

    @Inject
    private CarbonServerInfo carbonServerInfo;


    @Configuration
    public Option[] createConfiguration() {

        List<Option> optionList = BasicServerConfigurationUtil.createBasicConfiguration();
        Path carbonHome = BasicServerConfigurationUtil.getCarbonHome();

        CarbonSysPropConfiguration sysPropConfiguration = new CarbonSysPropConfiguration();
        sysPropConfiguration.setCarbonHome(carbonHome.toString());
        sysPropConfiguration.setServerKey("carbon-humantask");
        sysPropConfiguration.setServerName("WSO2 Carbon humantask Server");
        sysPropConfiguration.setServerVersion("1.0.0");

        optionList = OSGiTestConfigurationUtils.getConfiguration(optionList, sysPropConfiguration);
        Option[] options = optionList.toArray(new Option[optionList.size()]);
        return options;
    }

    @Test
    public void testTaskEngineCreation() throws EngineRuntimeException {
        log.info("Starting Test case. ");
        HumanTaskEngine humanTaskEngine = humanTaskEngineOSGIService.getHumanTaskEngine();

        Assert.assertNotNull(humanTaskEngine, "TaskEngine  is null.");
        humanTaskEngine.start();
        Assert.assertTrue(humanTaskEngine.isEngineRunning(), "Task engine is not running.");

    }
}
