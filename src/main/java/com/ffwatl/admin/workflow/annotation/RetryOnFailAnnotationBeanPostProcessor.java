package com.ffwatl.admin.workflow.annotation;

import com.ffwatl.admin.workflow.annotation.RetryOnFail;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ffw_ATL.
 */
public class RetryOnFailAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class<?>> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = bean.getClass();
        Method[] methods = beanClass.getMethods();

        for(Method method: methods){
            if(method.getAnnotation(RetryOnFail.class) != null){
                map.put(beanName, beanClass);
                break;
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClazz = map.get(beanName);

        if(beanClazz == null) {
            return bean;
        }

        ArrayList<Class<?>> interfacesList = new ArrayList<>();

        extractInterface(beanClazz, interfacesList);
        Class<?>[] interfaces = new Class[interfacesList.size()];

        for(int i=0; i< interfacesList.size(); i++){
            interfaces[i] = interfacesList.get(i);
        }

        return Proxy.newProxyInstance(beanClazz.getClassLoader(), interfaces, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();
                Method originalMethod = beanClazz.getMethod(name, method.getParameterTypes());

                RetryOnFail annotation = originalMethod.getAnnotation(RetryOnFail.class);

                if (annotation == null) {
                    return method.invoke(bean, args);
                }

                int numOfTries = annotation.numOfTries();
                Class<? extends Exception> retryOn = annotation.retryOn();

                while (true) {
                    try {
                        return method.invoke(bean, args);
                    } catch (Exception e) {
                        if(numOfTries > 0 && e.getCause().getClass().getName().equals(retryOn.getName())) {
                            numOfTries -= 1;
                        }else {
                            throw e.getCause();
                        }
                    }
                }
            }
        });
    }


    private Class extractInterface(Class<?> clazz, ArrayList<Class<?>> classArrayList) {
        Class<?> superClazz = clazz.getSuperclass();

        if(superClazz != null) {
            extractInterface(superClazz, classArrayList);
        }

        Class<?>[] interfaces = clazz.getInterfaces();
        for(Class<?> c: interfaces) {
            extractInterface(c, classArrayList);
            classArrayList.add(c);
        }
        return clazz;
    }
}