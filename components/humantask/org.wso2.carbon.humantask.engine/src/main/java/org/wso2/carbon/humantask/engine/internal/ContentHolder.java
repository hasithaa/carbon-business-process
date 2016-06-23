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


package org.wso2.carbon.humantask.engine.internal;

import org.wso2.carbon.caching.CarbonCachingService;
import org.wso2.carbon.datasource.core.api.DataSourceService;
import org.wso2.carbon.humantask.engine.EngineRuntimeException;
import org.wso2.carbon.humantask.engine.HumanTaskEngine;
import org.wso2.carbon.security.caas.user.core.service.RealmService;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

/**
 * Content Holder for HumanTask component.
 *
 * @since 5.0.0
 */
public class ContentHolder {

    private static ContentHolder instance = null;

    private HumanTaskEngine taskEngine;

    private RealmService realmService;

    private CarbonCachingService cachingService;

    private DataSourceService dataSourceService;

    private ContentHolder() {
    }

    public static ContentHolder getInstance() {
        if (instance == null) {
            instance = new ContentHolder();
        }
        return instance;
    }

    public HumanTaskEngine getTaskEngine() {
        return taskEngine;
    }


    public synchronized void setTaskEngine(HumanTaskEngine taskEngine) throws EngineRuntimeException {
        if (this.taskEngine != null && this.taskEngine.isEngineRunning()) {
            throw new EngineRuntimeException("Can't set a new task engine instance, since old instance is still " +
                    "running. Shutdown existing instance first.");
        }
        this.taskEngine = taskEngine;
    }

    public void setRealmService(RealmService carbonRealmService) {
        this.realmService = carbonRealmService;
    }

    public RealmService getRealmService() {
        return realmService;
    }

    public CarbonCachingService getCachingService() {
        return cachingService;
    }

    public void setCachingService(CarbonCachingService cachingService) {
        this.cachingService = cachingService;
    }

    public <K, V> Cache<K, V> getCache(String cacheName, Class<K> key, Class<V> value, Duration expiry) {
        CachingProvider provider = cachingService.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();
        Cache<K, V> cache = cacheManager.getCache(cacheName, key, value);
        if (cache == null) {
            cache = initCache(cacheName, cacheManager, key, value, expiry);
        }
        return cache;
    }

    private <K, V> Cache<K, V> initCache(String name, CacheManager cacheManager, Class<K> key, Class<V> value,
                                         Duration expiry) {

        //configure the cache
        MutableConfiguration<K, V> config = new MutableConfiguration<>();
        config.setStoreByValue(true)
                .setTypes(key, value)
                .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(expiry))
                .setStatisticsEnabled(false);

        //create the cache
        return cacheManager.createCache(name, config);
    }

    public DataSourceService getDataSourceService() {
        return dataSourceService;
    }

    public void setDataSourceService(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }
}
