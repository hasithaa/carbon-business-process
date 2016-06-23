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

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.humantask.engine.internal.ContentHolder;
import org.wso2.carbon.humantask.engine.runtime.Constants;
import org.wso2.carbon.humantask.engine.runtime.HumanTaskRuntimeException;
import org.wso2.carbon.humantask.engine.runtime.persistence.entity.Property;
import org.wso2.carbon.humantask.engine.runtime.persistence.entity.manager.PropertyEntityManager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

public class SessionFactory {

    private static final Logger logger = LoggerFactory.getLogger(SessionFactory.class);

    private static SessionFactory instance;

    private String databaseType;

    private SqlSessionFactory humanTaskSqlSessionFactory;

    private SessionFactory() {
        initHumanTaskSessionFactory();
    }

    public static SessionFactory getDefaultSessionFactory() {
        if (instance == null) {
            instance = new SessionFactory();
        }
        return instance;
    }


    private Properties getDefaultDatabaseTypeMappings() {
        Properties databaseTypeMappings = new Properties();
        databaseTypeMappings.setProperty("H2", Constants.DATABASE_TYPE_H2);
        databaseTypeMappings.setProperty("HSQL Database Engine", Constants.DATABASE_TYPE_HSQL);
        databaseTypeMappings.setProperty("MySQL", Constants.DATABASE_TYPE_MYSQL);
        databaseTypeMappings.setProperty("Oracle", Constants.DATABASE_TYPE_ORACLE);
        databaseTypeMappings.setProperty("PostgreSQL", Constants.DATABASE_TYPE_POSTGRES);
        databaseTypeMappings.setProperty("Microsoft SQL Server", Constants.DATABASE_TYPE_MSSQL);
        databaseTypeMappings.setProperty(Constants.DATABASE_TYPE_DB2, Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/NT", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/NT64", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2 UDP", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/LINUX", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/LINUX390", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/LINUXX8664", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/LINUXZ64", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/LINUXPPC64", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/LINUXPPC64LE", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/400 SQL", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/6000", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2 UDB iSeries", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/AIX64", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/HPUX", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/HP64", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/SUN", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/SUN64", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/PTX", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2/2", Constants.DATABASE_TYPE_DB2);
        databaseTypeMappings.setProperty("DB2 UDB AS400", Constants.DATABASE_TYPE_DB2);
        return databaseTypeMappings;
    }

    private void initHumanTaskSessionFactory() {

        DataSource dataSource = ContentHolder.getInstance().getTaskEngine().getHumanTaskRuntime()
                .getHumantaskDataSource();
        // Deciding which databases is used.
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            logger.info("database product name: '{}'", databaseProductName);
            databaseType = getDefaultDatabaseTypeMappings().getProperty(databaseProductName);
            if (databaseType == null) {
                throw new HumanTaskRuntimeException("couldn't deduct database type from database product name '" +
                        databaseProductName + "'");
            }
            logger.info("Using database type: {}", databaseType);
        } catch (SQLException e) {
            throw new HumanTaskRuntimeException(Constants.MSG_SESSION_FACTORY_NOT_INIT, e);
        }
        // Building MyBatis Configuration.
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("humantask", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);

        configuration.getTypeAliasRegistry().registerAlias("property", Property.class);

        // Externalize followings
        //configuration.setAggressiveLazyLoading();
        //configuration.setLazyLoadingEnabled();
        //configuration.setCacheEnabled();

        configuration.addMapper(PropertyEntityManager.class);
        humanTaskSqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    protected SqlSession getHumanTaskDBSession() {
        if (this.humanTaskSqlSessionFactory != null) {

            return this.humanTaskSqlSessionFactory.openSession();
        }
        throw new HumanTaskRuntimeException(Constants.MSG_SESSION_FACTORY_NOT_INIT);
    }

    public Session newSession(){
        return new Session(this);
    }


    public String getDatabaseType() {
        return databaseType;
    }
}
