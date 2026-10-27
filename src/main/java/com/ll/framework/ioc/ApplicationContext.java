package com.ll.framework.ioc;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class ApplicationContext {
    public ApplicationContext(String basePackage) {

    }

    public void init() {
    }

    public <T> T genBean(String beanName) {
        StringBuilder sb = new StringBuilder("com.ll.domain.testPost.testPost");
//        package com.ll.domain.testPost.testPost.service;
//        repository
        if (beanName.endsWith("Service")) {
            sb.append(".service");
        } else if (beanName.endsWith("Repository")) {
            sb.append(".repository");
        } else if (beanName.endsWith("Controller")) {
            sb.append(".controller");
        }

        try {
            String className = Character.toUpperCase(beanName.charAt(0)) + beanName.substring(1);
            Class<?> newClass = Class.forName(sb.append(".").append(className).toString());

            Method getInstance = newClass.getDeclaredMethod("getInstance");
            return (T) getInstance.invoke(null);

//            return (T) cons.newInstance();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
