package com.custom.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation indicating that a method (or all the methods on a class) can be cached.
 *
 * The method arguments are used for computing the key while the
 * returned instance is used as the cache value.
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomCacheable {

    /**
     * Name of the cache in which the put/update takes place.
     */
    String cacheName();
}
