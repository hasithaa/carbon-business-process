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


package org.wso2.carbon.humantask.engine.runtime.impl.people;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.humantask.engine.internal.ContentHolder;
import org.wso2.carbon.humantask.engine.runtime.Constants;
import org.wso2.carbon.humantask.engine.runtime.HumanTaskRuntimeException;
import org.wso2.carbon.humantask.engine.runtime.people.PeopleQueryEvaluator;
import org.wso2.carbon.kernel.context.CarbonContext;
import org.wso2.carbon.security.caas.user.core.bean.Group;
import org.wso2.carbon.security.caas.user.core.bean.User;
import org.wso2.carbon.security.caas.user.core.exception.GroupNotFoundException;
import org.wso2.carbon.security.caas.user.core.exception.IdentityStoreException;
import org.wso2.carbon.security.caas.user.core.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.cache.Cache;
import javax.cache.expiry.Duration;

public class CaasBasedPeopleQueryEvaluator implements PeopleQueryEvaluator {

    public static final String CHECK_USER_EXISTENCE = "CheckUserExistence";
    public static final String CHECK_GROUP_EXISTENCE = "CheckGroupExistence";
    public static final String ENABLE_CAHCE = "EnableCache";
    public static final String CACHE_EXPIRY_TIME = "CacheExpiryTime";

    private static final String CACHE_USER = "org.wso2.carbon.humantask.CaasBasedPeopleQueryEvaluator.user";
    private static final String CACHE_GROUP = "org.wso2.carbon.humantask.CaasBasedPeopleQueryEvaluator.group";
    private static final String CACHE_GROUPS_OF_USER = "org.wso2.carbon.humantask.CaasBasedPeopleQueryEvaluator" +
            ".groupOfUser";

    private static final Logger log = LoggerFactory.getLogger(CaasBasedPeopleQueryEvaluator.class);

    private Properties config;
    private boolean checkUserExistance;
    private boolean checkGroupExistance;
    private boolean enableCache;
    private Duration cacheExpiryTime;

    private Cache<String, Boolean> userExistanceCache;
    private Cache<String, Boolean> groupExistanceCache;
    private Cache<String, List> groupsOfUserCache;

    public CaasBasedPeopleQueryEvaluator() {
        this.config = new Properties();
        this.checkUserExistance = true;
        this.checkGroupExistance = true;
        this.enableCache = false;
        // 15 minutes of expiry time. 15*60
        this.cacheExpiryTime = new Duration(TimeUnit.SECONDS, 900);
    }

    @Override
    public Properties getProperties() {
        return config;
    }

    @Override
    public void setProperties(Properties properties) {
        config = properties;
        if (config.containsKey(CHECK_USER_EXISTENCE)
                && config.getProperty(CHECK_USER_EXISTENCE) != null
                && Boolean.parseBoolean(config.getProperty(CHECK_USER_EXISTENCE))) {
            this.checkUserExistance = true;
        } else {
            this.checkUserExistance = false;
        }

        if (config.containsKey(CHECK_USER_EXISTENCE)
                && config.getProperty(CHECK_USER_EXISTENCE) != null
                && Boolean.parseBoolean(config.getProperty(CHECK_USER_EXISTENCE))) {
            this.checkGroupExistance = true;
        } else {
            this.checkGroupExistance = false;
        }

        if (config.containsKey(CACHE_EXPIRY_TIME)
                && config.getProperty(CACHE_EXPIRY_TIME) != null) {
            String timeString = config.getProperty(CACHE_EXPIRY_TIME);
            try {
                long timeInSeconds = Long.parseLong(timeString);
                cacheExpiryTime = new Duration(TimeUnit.SECONDS, timeInSeconds);
            } catch (NumberFormatException e) {
                // Nothing to do. Using default values.
                log.warn(Constants.MSG_INVALID_CACHE_EXPIRY_TIME);
            }
        }

        if (config.containsKey(ENABLE_CAHCE)
                && config.getProperty(ENABLE_CAHCE) != null
                && Boolean.parseBoolean(config.getProperty(ENABLE_CAHCE))) {
            this.enableCache = true;
            this.userExistanceCache = ContentHolder.getInstance().getCache(CACHE_USER, String.class, Boolean.class,
                    cacheExpiryTime);
            this.groupExistanceCache = ContentHolder.getInstance().getCache(CACHE_GROUP, String.class, Boolean.class,
                    cacheExpiryTime);
            this.groupsOfUserCache = ContentHolder.getInstance().getCache(CACHE_GROUPS_OF_USER, String.class, List
                    .class, cacheExpiryTime);

        }
    }

    @Override
    public boolean isExistingUser(String userName) {
        if (!checkUserExistance) {
            return true;
        }
        if (enableCache && userExistanceCache != null) {
            // Cache hit.
            if (userExistanceCache.containsKey(userName)) {
                return userExistanceCache.get(userName);
            }
            // Cache missed.
        }
        // Check the realm service for user existence.
        if (ContentHolder.getInstance().getRealmService() != null
                && ContentHolder.getInstance().getRealmService().getIdentityStore() != null) {
            try {
                ContentHolder.getInstance().getRealmService().getIdentityStore().getUser(userName);
                if (enableCache && userExistanceCache != null) {
                    userExistanceCache.put(userName, Boolean.TRUE);
                }
                return true;
            } catch (IdentityStoreException | UserNotFoundException e) {
                if (log.isDebugEnabled()) {
                    // Logging this information for debugging purpose only.
                    CarbonContext currentContext = CarbonContext.getCurrentContext();
                    Long taskID = (Long) currentContext.getProperty(Constants.TASK_ID);
                    log.debug(Constants.getTaskIDFormatted(taskID) + Constants.MSG_USER_NOT_FOUND + userName, e);
                }
            }
        } else {
            throw new HumanTaskRuntimeException(Constants.MSG_REALM_NOT_FOUND);
        }
        return false;
    }

    @Override
    public boolean isExistingGroup(String groupName) {
        if (!this.checkGroupExistance) {
            return true;
        }
        if (enableCache && groupExistanceCache != null) {
            //Cache Hit.
            if (groupExistanceCache.containsKey(groupName)) {
                return groupExistanceCache.get(groupName);
            }
            // Cache missed.
        }
        // Check the realm service for group existence.
        if (ContentHolder.getInstance().getRealmService() != null
                && ContentHolder.getInstance().getRealmService().getIdentityStore() != null) {
            try {
                ContentHolder.getInstance().getRealmService().getIdentityStore().getGroup(groupName);
                if (enableCache && groupExistanceCache != null) {
                    groupExistanceCache.put(groupName, Boolean.TRUE);
                }
                return true;
            } catch (IdentityStoreException | GroupNotFoundException e) {
                if (log.isDebugEnabled()) {
                    // Logging this information for debugging purpose only.
                    CarbonContext currentContext = CarbonContext.getCurrentContext();
                    Long taskID = (Long) currentContext.getProperty(Constants.TASK_ID);
                    log.debug(Constants.getTaskIDFormatted(taskID) + Constants.MSG_GROUP_NOT_FOUND + groupName, e);
                }
            }
        } else {
            throw new HumanTaskRuntimeException(Constants.MSG_REALM_NOT_FOUND);
        }
        if (enableCache && groupExistanceCache != null) {
            groupExistanceCache.put(groupName, Boolean.FALSE);
        }
        return false;
    }

    @Override
    public List<String> getGroupsOfUser(String userName) {
        // Note : In CaaS based People Query evaluator, we could only retrieve groups of an users from a user store,
        // which belongs to current logged in user's user store. This is limitation in caas impl. TODO: In future, check
        // possibility of overcoming this issue.
        if (enableCache && groupsOfUserCache != null) {
            // Cache hit.
            if (groupsOfUserCache.containsKey(userName)) {
                return groupsOfUserCache.get(userName);
            }
            // Cache missed.
        }
        List<String> groupList = new ArrayList<>();
        if (ContentHolder.getInstance().getRealmService() != null
                && ContentHolder.getInstance().getRealmService().getIdentityStore() != null) {

            try {
                User user = ContentHolder.getInstance().getRealmService().getIdentityStore().getUser(userName);
                List<Group> groupsOfUser = ContentHolder.getInstance().getRealmService().getIdentityStore()
                        .getGroupsOfUser(user.getUserId(), user.getIdentityStoreId());
                groupsOfUser.forEach(group -> groupList.add(group.getName()));

            } catch (IdentityStoreException e) {
                throw new HumanTaskRuntimeException(Constants.MSG_IDENTITY_STORE_ERROR, e);
            } catch (UserNotFoundException e) {
                if (log.isDebugEnabled()) {
                    // Logging this information for debugging purpose only.
                    CarbonContext currentContext = CarbonContext.getCurrentContext();
                    Long taskID = (Long) currentContext.getProperty(Constants.TASK_ID);
                    log.debug(Constants.getTaskIDFormatted(taskID) + Constants.MSG_USER_NOT_FOUND + userName, e);
                }
                if (checkUserExistance) {
                    // Return an empty List.
                } else {
                    throw new HumanTaskRuntimeException(Constants.MSG_USER_NOT_FOUND + userName);
                }
            }
        } else {
            throw new HumanTaskRuntimeException(Constants.MSG_REALM_NOT_FOUND);
        }
        if (enableCache && groupsOfUserCache != null) {
            groupsOfUserCache.put(userName, groupList);
        }
        return groupList;
    }


}
