package com.custom.cache.service;

import com.custom.cache.interceptor.CacheKey;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-Memory cache which caches the data in the ConcurrentHashMap for the faster access.
 * @param <T>
 * @param <V>
 */
public class Cache<T extends CacheKey, V extends Object> {

    private final ConcurrentHashMap<T , V> cache;

    public Cache() {
        cache = new ConcurrentHashMap<>();
    }

    public void put(T key, V value){
        cache.put(key, value);
    }

    public V get(T key){
        return (V) cache.get(key);
    }
}
