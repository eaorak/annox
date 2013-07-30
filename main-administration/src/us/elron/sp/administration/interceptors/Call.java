package us.elron.sp.administration.interceptors;

import java.lang.reflect.Method;

import us.elron.sp.administration.jmx.BeanInfo;
import us.elron.sp.administration.util.ReflectUtils;



public class Call {

    private BeanInfo beanInfo;
    private Object   object;
    private Method   method;
    private Object[] args;
    private boolean  setterMethod;

    public Call(BeanInfo beanInfo,
                Object object,
                Method method,
                Object... args) {
        this.beanInfo = beanInfo;
        this.object = object;
        this.method = method;
        this.args = args;
        this.setterMethod = ReflectUtils.isSetter(method);
    }

    public BeanInfo beanInfo() {
        return this.beanInfo;
    }

    public Object object() {
        return this.object;
    }

    public Method method() {
        return this.method;
    }

    public Object[] args() {
        return this.args;
    }

    public boolean isSetterMethod() {
        return this.setterMethod;
    }

}
