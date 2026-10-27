package com.ll.framework.ioc;

import com.ll.framework.ioc.annotations.Repository;
import com.ll.framework.ioc.annotations.Service;
import javassist.bytecode.annotation.NoSuchClassError;
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
    private Map<String, Class<?>> classMap = new HashMap<>();
    private Map<Class<?>, Object> beans = new HashMap<>();

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

    public <T> T genBean(String beanName) throws NoSuchClassError {
        try {
            String className = Character.toUpperCase(beanName.charAt(0)) + beanName.substring(1);
            Class<?> searched = classMap.get(className);
            if (searched == null) throw new NoSuchClassError(className, new Error());

            if (beans.containsKey(searched)) return (T) beans.get(searched);

            Constructor<?> constructor = searched.getDeclaredConstructors()[0];
            var params = constructor.getParameterTypes();

            Object[] args = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                System.out.println(params[i].getName());
                args[i] = genBean(params[i].getSimpleName());
            }
            constructor.setAccessible(true);
            T instance = (T) constructor.newInstance(args);
            beans.put(searched, instance);
            return instance;

        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
//        return (T) null;
    }
}
