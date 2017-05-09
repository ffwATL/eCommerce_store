package com.ffwatl.admin.workflow.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ffw_ATL.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RetryOnFail {

    int numOfTries() default 6;

    Class<? extends Exception>[] cause();

    long sleepTime() default 0;
}