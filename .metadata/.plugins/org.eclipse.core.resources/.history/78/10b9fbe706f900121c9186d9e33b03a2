package com.adenon.sp.kernel.event.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Message {

    public String messageName();

    public MessageType messageType() default MessageType.BEGIN;

    public String[] dispatchClassNames() default {};

}
