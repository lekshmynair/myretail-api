package com.myretail.api.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class CacheStore <T> {
    Logger log = Logger.getLogger(CacheStore.class.getName());
    private Cache<Integer, T> cache;

    //build cache store
    public CacheStore(int timeToLive, TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(timeToLive, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    //access info from cache
    public T get(Integer key) {
        return cache.getIfPresent(key);
    }

    //add new data to cache
    public void add(Integer key, T value) {
        if (key != null && value != null) {
            cache.put(key, value);
            log.info("Adding to cache : Key :" + key + ", Value:" + value);
        }
    }
}
