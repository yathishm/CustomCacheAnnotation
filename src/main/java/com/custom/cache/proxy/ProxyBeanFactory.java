package com.custom.cache.proxy;

import com.custom.cache.annotation.CustomCacheable;
import com.custom.cache.exception.CustomCacheAnnotationRunTimeException;
import com.custom.cache.interceptor.CustomCacheableInvocationHandler;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 1.  Scans the package to identify all the classes which contains method annotated with @CustomCacheable
 * 2. creates an instance for each of the identified class{target object} and also creates a Proxy object for each of the target object
 * 3. stores the proxies in the Map{beansMap} with key = className, value= proxiedobject
 */
public class ProxyBeanFactory {

    private String packageToScan;
    private Map<Class<?>, Object> beansMap = new HashMap<>();

    private static Logger logger = LoggerFactory.getLogger(ProxyBeanFactory.class);

    public ProxyBeanFactory(String packageNameToScan){
        packageToScan = packageNameToScan;
        initialize();
    }

    /**
     * returns an proxied instance of Class<T> from the beansMap
     * @param clazz
     * @return an instance of Class<T>
     */
    public Object getBean(Class<?> clazz){
        return beansMap.get(clazz);
    }

    private void initialize(){
        try{
            Set<Class<?>> classesWithMethodsAnnotatedWithCustomCacheable = getClassesWithMethodAnnotatedWithCustomCacheableAnnotation();
            instantiateProxyBeans(classesWithMethodsAnnotatedWithCustomCacheable);
        } catch(CustomCacheAnnotationRunTimeException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Creates proxy beans for classes annotated with @CustomCacheable and stores them in a Map for later access using the getbean(clazz) method
     * @param classesWithMethodsAnnotatedWithCustomCacheable
     */
    private void instantiateProxyBeans(Set< Class<?>> classesWithMethodsAnnotatedWithCustomCacheable){
        if(null != classesWithMethodsAnnotatedWithCustomCacheable && classesWithMethodsAnnotatedWithCustomCacheable.size() > 0){
            for(Class<?> clazz : classesWithMethodsAnnotatedWithCustomCacheable){
                beansMap.put(clazz, createProxyBean(clazz));
            }
        }
    }

    /**
     * creates an object for the given class(clazz) and its proxy.
     * @param clazz
     * @return proxyBean
     */
    private Object createProxyBean(Class<?> clazz){
        Object proxyBean = null;
        try {
            Object bean = clazz.newInstance();
            CustomCacheableInvocationHandler customCacheableInvocationHandler = new CustomCacheableInvocationHandler(bean);
            proxyBean = Proxy.newProxyInstance(ProxyBeanFactory.class.getClassLoader(), bean.getClass().getInterfaces(), customCacheableInvocationHandler);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Error instantiating the beans {} ", e);
            throw new CustomCacheAnnotationRunTimeException("Error instantiating the bean for class " + clazz , e);
        }
        return proxyBean;
    }

    /**
     * using Reflection it scans through the package and identify and returns all the those classes wich has @CustomCacheable Annotation annotated on their methods.
     * @return classesWithMethodsAnnotatedWithCustomCacheable
     */
    private Set<Class<?>> getClassesWithMethodAnnotatedWithCustomCacheableAnnotation() {
        Set<Class<?>> classesWithMethodsAnnotatedWithCustomCacheable = null;
        logger.info(" Scanning the package {} to find classes which has methods annotated with @CustomCacheable", packageToScan);
        try {
            Reflections reflections = new Reflections(packageToScan, new MethodAnnotationsScanner());
            Set<Method> methodsAnnotatedWithCustomCacheable = reflections.getMethodsAnnotatedWith(CustomCacheable.class);
            classesWithMethodsAnnotatedWithCustomCacheable = new HashSet<>();
            for(Method method : methodsAnnotatedWithCustomCacheable){
                classesWithMethodsAnnotatedWithCustomCacheable.add(method.getDeclaringClass());
            }
        } catch (Exception e) {
            logger.error("Error scanning the packages to identify classes with method Annotated with CustomCacheable Annotation {} ", e);
            throw new CustomCacheAnnotationRunTimeException("Error scanning the packages to identify classes with method Annotated with CustomCacheable Annotation " , e);
        }
        logger.info(" Classes which has methods annotated with @CustomCacheable are : {} ", classesWithMethodsAnnotatedWithCustomCacheable);
        return classesWithMethodsAnnotatedWithCustomCacheable;
    }
}
