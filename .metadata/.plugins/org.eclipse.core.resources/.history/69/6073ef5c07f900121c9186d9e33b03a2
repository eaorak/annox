package com.adenon.sp.administration.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Attribute definition
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Attribute {

    String DEF_STR = MBean.DEF_STR;

    boolean readOnly() default false;

    String name() default DEF_STR;

    String description() default DEF_STR;

}
