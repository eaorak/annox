package com.adenon.sp.administration.util;

import java.lang.reflect.Method;

import com.adenon.sp.administration.annotate.Attribute;
import com.adenon.sp.administration.annotate.MBean;
import com.adenon.sp.administration.annotate.Dynamic;
import com.adenon.sp.administration.annotate.Join;
import com.adenon.sp.administration.jmx.AttributeInfo;
import com.adenon.sp.kernel.utils.StringUtils;


public class BeanUtils {

    private BeanUtils() {
    }

    public static AttributeInfo getAttributeInfo(Method getter,
                                                 MBean beanConfig) throws Exception {
        Attribute attribute = getter.getAnnotation(Attribute.class);
        if (attribute == null) {
            return null;
        }
        checkMethod(getter);
        String attr = ReflectUtils.toAttr(getter.getName());
        Method setter = null;
        if (beanConfig.persist()) {
            setter = ReflectUtils.getMethodWithClassParams(true, getter.getDeclaringClass(), "set" + StringUtils.cap(attr), getter.getReturnType());
        }
        return AttributeInfo.createGenericAttributeInfo(attribute, getter, setter);
    }

    public static boolean isDynamic(Class<?> beanClass) {
        return beanClass.getAnnotation(Dynamic.class) != null;
    }

    public static String getIdValue(Object bean) {
        MBean beanAnt = bean.getClass().getAnnotation(MBean.class);
        String id = null;
        if ((beanAnt == null) || (id = beanAnt.id()).equals(MBean.DEF_STR)) {
            return null;
        }
        String methodName = "get" + StringUtils.cap(id);
        String value = null;
        Method method;
        try {
            method = bean.getClass().getMethod(methodName, (Class<?>[]) null);
            Object val = method.invoke(bean);
            if (val instanceof String) {
                value = (String) val;
            } else {
                throw new Exception("Id definition type error in class '" + bean.getClass().getName() + "' !");
            }
        } catch (Exception e) {
            return null;
        }
        return value;
    }

    public static String getJoinValue(Object bean) {
        String value = null;
        Method[] methods = bean.getClass().getMethods();
        boolean joinFound = false;
        for (Method m : methods) {
            Join annotation = m.getAnnotation(Join.class);
            if (annotation == null) {
                continue;
            }
            joinFound = !joinFound;
            if (!joinFound) {
                throw new RuntimeException("Beans can not have more than one Join field. Error on [" + bean.getClass().getName() + "] !");
            }
            try {
                Object val = m.invoke(bean);
                value = (String) val;
                break;
            } catch (Exception e) {
                throw new RuntimeException("Error on getting join value for bean '" + bean.getClass().getName() + "'", e);
            }
        }
        return value;
    }

    // ---------------------------------------------------------

    private static void checkMethod(Method m) throws Exception {
        String methodName = m.getName();
        if (!methodName.startsWith("get")) {
            throw new Exception("Method is not a getter : [" + methodName + "] !");
        }
    }

}
