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

import org.apache.ibatis.session.SqlSession;
import org.wso2.carbon.humantask.engine.runtime.persistence.entity.manager.PropertyEntityManager;

/**
 *
 */
public class Session {
    private SessionFactory sessionFactory;
    private SqlSession humanTaskSession;

    protected Session(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void open() {
        humanTaskSession = sessionFactory.getHumanTaskDBSession();
    }

    public void commit() {
        humanTaskSession.commit(true);
    }

    public void close() {
        humanTaskSession.close();
    }

    public void rollback(){
        humanTaskSession.rollback();
    }

    public PropertyEntityManager getPropertyEntityManager() {
        return this.humanTaskSession.getMapper(PropertyEntityManager.class);
    }

}
