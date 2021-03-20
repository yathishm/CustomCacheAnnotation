package com.custom.cache.interceptor;

import com.custom.cache.annotation.CustomCacheable;
import com.custom.cache.exception.CustomCacheAnnotationRunTimeException;
import com.custom.cache.service.CacheManager;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  1) Checks if the method invoked on the proxy instance is annotated with @CustomCacheable
 *  2) Uses the proxy object's method arguments{args} as a key to check if the key is cached in the In-Memory cache
 *       2a) if Key exists, returns data from cache.
 *       2b)  if Key doesn't exists, it will invoke the method on the target object,  caches and returns the result returned from the target object.
 *  3) If the method invoked on the proxy instance is NOT annotated with @CustomCacheable, it simply invokes the method on the target object and returns the result.
 */
public class CustomCacheableInvocationHandler implements InvocationHandler {

    private final Object target;
    private final Class<?> targetClass;
    private static final Logger logger = LoggerFactory.getLogger(CustomCacheableInvocationHandler.class);

    public CustomCacheableInvocationHandler(Object target){
        this.target = target;
        this.targetClass = target.getClass();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            if(isMethodAnnotatedWithCustomCacheable(method)){
                result = invokeIfNotPresentInCache(proxy, method, args);
            } else{
                result = method.invoke(target, args);
            }
        } catch(Exception e){
            logger.error("Exception when invoking the method " + method + " with args " + args + "on the target object : {} ", e);
            throw new CustomCacheAnnotationRunTimeException(e.getMessage(), e);
        }
        return result;
    }

    /**
     * checks if the method is annonated with @CustomCacheable annotation
     * @param method
     * @return boolean
     * @throws NoSuchMethodException
     */
    private boolean isMethodAnnotatedWithCustomCacheable(Method method) throws NoSuchMethodException {
        Method targetMethod = targetClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
         if(null != targetMethod.getDeclaredAnnotation(CustomCacheable.class)){
             return true;
         }
        return false;
    }

    private Object invokeIfNotPresentInCache(Object proxy, Method method, Object[] args) throws Exception {
        CacheKey cacheKey = new CacheKey(args);
        Object result = getFromCacheIfKeyExists(cacheKey);
        if(null != result){
            logger.info("Key {} is found in the Cache ", cacheKey);
            logger.info(" Data returned from Cache for key {} is : {} ", cacheKey , result);
            return result;
        }
        result = method.invoke(target, args);
        logger.info(" Data returned from Database for key {} is : {} ", cacheKey , result);
        logger.info("Inserting the Key {} into the cache for accessing it faster the next time ", cacheKey);
        updateInCache(cacheKey, result);
        return result;
    }

    private void updateInCache(CacheKey cacheKey, Object value) throws Exception {
        CacheManager.put(cacheKey, value);
    }

    private Object getFromCacheIfKeyExists(CacheKey cacheKey) throws Exception {
        return CacheManager.get(cacheKey);
    }
}
