package com.adenon.sp.administration.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.adenon.sp.administration.ConfigLocation;


/**
 * MBean definition.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MBean {

    String DEF_STR = "";

    /**
     * Configuration location, default is SYSTEM. Where do you wanna be ?
     */
    ConfigLocation location() default ConfigLocation.SYSTEM;

    /**
     * Either sublocation or parent definition should be done for all beans.<br>
     * So be a good one and define one.
     */
    String subLocation() default DEF_STR;

    /**
     * Because id definition is used in generation of object name definition, <br>
     * parent and sub-beans CAN NOT have same "id" values. OK ?
     */
    String id() default DEF_STR;

    /**
     * Specify parent bean class if you wanna define a sub-bean. Also @see Join.class.<br>
     */
    Class<?> parent() default MBean.class;

    /**
     * Persist to database or not. That's the problem.
     */
    boolean persist() default false;

}
