package com.adenon.sp.administration.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Operation definition
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Operation {

    String DEF_STR = MBean.DEF_STR;

    String name() default DEF_STR;

    String desc() default DEF_STR;

}
