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


package org.wso2.carbon.humantask.engine.runtime.lifecycle.execution;

import org.wso2.carbon.humantask.engine.runtime.Constants;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>Java class for ErrorMessage complex type.
 */
@XmlRootElement(name = "ErrorMessage" , namespace = Constants.NS_ENGINE)
public class ErrorMessage {


    private String errorName;

    private Object errorDetails;

    public ErrorMessage() {
    }

    public ErrorMessage(String errorName, Object errorDetails) {
        this.errorName = errorName;
        this.errorDetails = errorDetails;
    }

    /**
     * Gets the value of the faultName property.
     *
     * @return possible object is
     * {@link String }
     */
    @XmlElement(name = "errorName" ,  namespace = Constants.NS_ENGINE )
    public String getErrorName() {
        return this.errorName;
    }

    /**
     * Sets the value of the faultName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setErrorName(String value) {
        this.errorName = value;
    }

    /**
     * Gets the value of the errorDetails property.
     *
     * @return possible object is
     * {@link Object }
     */
    @XmlElement(name = "errorDetails" , namespace = Constants.NS_ENGINE)
    public Object getErrorDetails() {
        return this.errorDetails;
    }

    /**
     * Sets the value of the errorDetails property.
     *
     * @param value allowed object is
     *              {@link Object }
     */
    public void setErrorDetails(Object value) {
        this.errorDetails = value;
    }



}