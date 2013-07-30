package com.adenon.sp.administration.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.management.ReflectionException;

import org.apache.log4j.Logger;

import com.adenon.sp.administration.Primitives;
import com.adenon.sp.kernel.utils.StringUtils;


public class ReflectUtils {

    private static final Logger logger = Logger.getLogger(ReflectUtils.class);

    private ReflectUtils() {
    }

    public static boolean isSetter(Method m) {
        String name = m.getName();
        if (name.startsWith("set") && (m.getParameterTypes().length == 1) /*&& (m.getReturnType() == Void.TYPE)*/) {
            return true;
        }
        return false;
    }

    public static Object get(Object obj,
                             Method method) throws Exception {
        return get(obj, toAttr(method.getName()));
    }

    public static Object invoke(Object obj,
                                Method method,
                                Object... params) throws Exception {
        Object o = null;
        checkAccessible(obj, method);
        o = method.invoke(obj, params);
        return o;
    }

    public static Object invoke(Object obj,
                                String methodName,
                                Object... params) throws Exception {
        Method method = getMethodWithObjectParams(obj.getClass(), methodName, params);
        if (method == null) {
            throw new RuntimeException("No such method '" + methodName + "' in class '" + obj.getClass() + " !");
        }
        Object o = null;
        checkAccessible(obj, method);
        method.setAccessible(true);
        try {
            o = method.invoke(obj, params);
        } catch (InvocationTargetException e) {
            throw new Exception(e.getTargetException().getMessage());
        } catch (Exception e) {
            throw e;
        }
        return o;
    }

    public static Method getMethodWithClassParams(boolean failIfNotFound,
                                                  Class<?> clazz,
                                                  String methodName,
                                                  Class<?>... classes) {
        Method method = null;
        try {
            method = clazz.getMethod(methodName, classes);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            if (failIfNotFound) {
                throw new RuntimeException(e);
            }
        }
        return method;
    }

    public static String toAttr(String str) {
        if (str.startsWith("get") || str.startsWith("set")) {
            return str.substring(3, 4).toLowerCase() + str.substring(4);
        }
        throw new InvalidParameterException("Invalid attribute method : " + str);
    }

    // ------------------------------------------------------------------

    private static Object get(Object obj,
                              String attr) throws Exception {
        String getterName = "get" + StringUtils.cap(attr);
        Method getterMethod = obj.getClass().getMethod(getterName, (Class[]) null);
        return ReflectUtils.invoke(obj, getterMethod, (Object[]) null);
    }

    private static void checkAccessible(Object obj,
                                        Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
            logger.warn("[ReflectUtils][invoke] : Warn : Method [" + obj.getClass().getName() + "." + method.getName() + "] was not accessible !");
        }
    }

    private static Method getMethodWithObjectParams(Class<?> clazz,
                                                    String methodName,
                                                    Object... params) throws ReflectionException {
        Class<?>[] classes = classesOf(params);
        Method method = getMethodWithClassParams(false, clazz, methodName, classes);
        if (method == null) {
            List<Class<?>> paramTypes = new ArrayList<Class<?>>();
            for (Object o : params) {
                Primitives primitive = Primitives.getTypeOf(o.getClass());
                paramTypes.add(primitive != null ? primitive.getPrimitive() : o.getClass());
            }
            method = getMethodWithClassParams(true, clazz, methodName, paramTypes.toArray(new Class<?>[paramTypes.size()]));
        }
        return method;
    }

    public static Class<?>[] classesOf(final Object[] objects) {
        if (objects == null) {
            return null;
        }
        final List<Class<?>> paramTypes = new ArrayList<Class<?>>();
        for (final Object o : objects) {
            paramTypes.add(o.getClass());
        }
        return paramTypes.toArray(new Class<?>[objects.length]);
    }

}
