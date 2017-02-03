package com.ffwatl.common.persistence;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

@Component("entity_configuration")
public class EntityConfiguration {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ApplicationContext webApplicationContext;

    private final HashMap<String, Class<?>> entityMap = new HashMap<>(50);
    private ApplicationContext applicationcontext;
    private Resource[] entityContexts;

    @javax.annotation.Resource(name="mergedEntityContexts")
    protected Set<String> mergedEntityContexts;

    @PostConstruct
    public void configureMergedItems() {
        Set<Resource> temp = new LinkedHashSet<>();
        if (mergedEntityContexts != null && !mergedEntityContexts.isEmpty()) {
            for (String location : mergedEntityContexts) {
                temp.add(webApplicationContext.getResource(location));
            }
        }
        if (entityContexts != null) {
            Collections.addAll(temp, entityContexts);
        }
        entityContexts = temp.toArray(new Resource[temp.size()]);
        applicationcontext = new GenericXmlApplicationContext(entityContexts);
    }

    public Class<?> lookupEntityClass(String beanId) {
        Class<?> clazz;
        if (entityMap.containsKey(beanId)) {
            clazz = entityMap.get(beanId);
        } else {
            Object object = applicationcontext.getBean(beanId);
            clazz = object.getClass();
            entityMap.put(beanId, clazz);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Returning class (" + clazz.getName() + ") configured with bean id (" + beanId + ')');
        }
        return clazz;
    }

    public String[] getEntityBeanNames() {
        return applicationcontext.getBeanDefinitionNames();
    }

    public <T> Class<T> lookupEntityClass(String beanId, Class<T> resultClass) {
        Class<T> clazz;
        if (entityMap.containsKey(beanId)) {
            clazz = (Class<T>) entityMap.get(beanId);
        } else {
            Object object = applicationcontext.getBean(beanId);
            clazz = (Class<T>) object.getClass();
            entityMap.put(beanId, clazz);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Returning class (" + clazz.getName() + ") configured with bean id (" + beanId + ')');
        }
        return clazz;
    }

    public Object createEntityInstance(String beanId) {
        Object bean = applicationcontext.getBean(beanId);
        if (logger.isDebugEnabled()) {
            logger.debug("Returning instance of class (" + bean.getClass().getName() + ") configured with bean id (" + beanId + ')');
        }
        return bean;
    }

    public <T> T createEntityInstance(String beanId, Class<T> resultClass) {
        T bean = (T) applicationcontext.getBean(beanId);
        if (logger.isDebugEnabled()) {
            logger.debug("Returning instance of class (" + bean.getClass().getName() + ") configured with bean id (" + beanId + ')');
        }
        return bean;
    }

    public Resource[] getEntityContexts() {
        return entityContexts;
    }

    public void setEntityContexts(Resource[] entityContexts) {
        this.entityContexts = entityContexts;
    }
}
