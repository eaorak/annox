package com.adenon.sp.administration.jmx;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.management.MBeanOperationInfo;

public class OperationInfo {

    private static final String      SEP = "#";
    private final MBeanOperationInfo operationMbeanInfo;
    private final Method             method;

    public OperationInfo(Method method,
                         MBeanOperationInfo info) {
        this.method = method;
        this.operationMbeanInfo = info;
    }

    public static String signature(String name,
                                   Class<?>[] params) {
        String[] strings = new String[params.length];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = params[i].getName();
        }
        return name + SEP + Arrays.toString(strings);
    }

    public static String signature(String name,
                                   String[] params) {
        return name + SEP + Arrays.toString(params);
    }

    public Method getMethod() {
        return this.method;
    }

    public MBeanOperationInfo getInfo() {
        return this.operationMbeanInfo;
    }

    public String signature() {
        return signature(this.operationMbeanInfo.getName(), this.method.getParameterTypes());
    }

}