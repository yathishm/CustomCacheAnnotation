package com.custom.cache.service;

import com.custom.cache.interceptor.CacheKey;

public class CacheManager {

    private static Cache<CacheKey, Object> cache= new Cache<>();

    public static void put(CacheKey key, Object value){
        cache.put(key, value);
    }

    public static Object get(CacheKey key){
        return cache.get(key);
    }
}
