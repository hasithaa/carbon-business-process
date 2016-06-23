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


package org.wso2.carbon.humantask.engine.runtime.persistence;

public interface BaseEntity {

    Long getId();
    void setId(Long id);

    // Field for Optimistic Locking.
    int getRevision();
    void setRevision(int version);

    /**
     * Returns a representation of the object,
     *  as would be stored in the database.
     * Used when deciding if updates have
     *  occurred to the object or not since
     *  it was last loaded.
     */
    java.lang.Object getPersistentState();
}
