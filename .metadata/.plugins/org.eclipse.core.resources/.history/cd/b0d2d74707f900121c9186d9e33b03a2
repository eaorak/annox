package com.adenon.sp.administration.persist.mongo;

import java.lang.reflect.Method;

import com.adenon.sp.administration.annotate.Attribute;
import com.adenon.sp.administration.annotate.Join;
import com.adenon.sp.kernel.utils.StringUtils;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;


public class GsonExcluder implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        String name = "get" + StringUtils.cap(f.getName());
        Method getter = null;
        try {
            getter = f.getDeclaringClass().getDeclaredMethod(name, (Class<?>[]) null);
        } catch (Exception e) {
            return true;
        }
        Attribute attribute = getter.getAnnotation(Attribute.class);
        Join parent = getter.getAnnotation(Join.class);
        if ((attribute == null) && (parent == null)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        if (clazz.equals(Object.class)) {
            return true;
        }
        return false;
    }

}
