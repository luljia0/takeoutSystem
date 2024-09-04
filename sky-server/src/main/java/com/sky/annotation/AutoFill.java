package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * custom annotation that is used to identify the methods which needs auto-population
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) //@Retention(RetentionPolicy.RUNTIME) allows the annotation to be available during the entire runtime of the application, making it accessible for reflective operations.
public @interface AutoFill {
    // specify the type of operation including insert and update
    OperationType value();


}
