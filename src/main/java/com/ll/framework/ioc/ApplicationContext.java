package com.ll.framework.ioc;

import com.ll.framework.ioc.annotations.Repository;
import com.ll.framework.ioc.annotations.Service;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {
    private String basePackage = "";
//    private Set<Class<?>> scandClassSet;
    private Map<String, Class<?>> classMap = new HashMap<>();

    public ApplicationContext(String basePackage) {
        this.basePackage = basePackage;
    }

    public void init() {
        Reflections reflections = new Reflections(basePackage);

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Service.class);
        Set<Class<?>> repositories = reflections.getTypesAnnotatedWith(Repository.class);
        classes.addAll(repositories);
        classes.forEach(c -> classMap.put(c.getSimpleName(), c));
    }

    public <T> T genBean(String beanName) {
        try {
            String className = Character.toUpperCase(beanName.charAt(0)) + beanName.substring(1);
            Class<?> searched = classMap.get(className);
            if (searched != null) {
                Method getInstance = searched.getDeclaredMethod("getInstance");
                return (T) getInstance.invoke(null);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return (T) null;
    }
}
