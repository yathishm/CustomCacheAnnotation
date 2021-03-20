package com.custom.cache.exception;

public class CustomCacheAnnotationRunTimeException extends RuntimeException {

    public CustomCacheAnnotationRunTimeException(String message, Throwable t){
        super(message, t);
    }
}
