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


package org.wso2.carbon.humantask.engine.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.humantask.engine.config.model.HumanTaskConfiguration;
import org.wso2.carbon.humantask.engine.exceptions.EngineRuntimeException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HumanTaskConfigurationFactory {

    private static final Logger logger = LoggerFactory.getLogger(HumanTaskConfigurationFactory.class);

    private String yamlConfigName;

    /**
     * Default HumanTask Configuration Factory.
     */
    public HumanTaskConfigurationFactory() {
        yamlConfigName = null;
    }

    /**
     * HumanTask Configuration Factory.
     *
     * @param yamlConfigName is humantask config yaml file name
     */
    public HumanTaskConfigurationFactory(String yamlConfigName) {
        this.yamlConfigName = yamlConfigName;
    }

    public HumanTaskConfiguration build() throws EngineRuntimeException {
        if (yamlConfigName == null) {
            return new HumanTaskConfiguration();
        }
        org.wso2.carbon.kernel.utils.Utils.checkSecurity();
        String configFileLocation = org.wso2.carbon.kernel.utils.Utils.getCarbonConfigHome().resolve(yamlConfigName)
                .toString();
        try (InputStream inputStream = new FileInputStream(configFileLocation)) {

            String yamlFileString;
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
                yamlFileString = scanner.useDelimiter("\\A").next();
                yamlFileString = org.wso2.carbon.kernel.utils.Utils.substituteVariables(yamlFileString);
            }

            Yaml yaml = new Yaml();
            yaml.setBeanAccess(BeanAccess.FIELD);
            return yaml.loadAs(yamlFileString, HumanTaskConfiguration.class);
        } catch (IOException e) {
            String errorMessage = "Failed populate HumanTaskConfiguration from " + configFileLocation;
            throw new EngineRuntimeException(errorMessage, e);
        }
    }

}
