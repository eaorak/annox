package com.adenon.sp.administration.jmx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ReflectionException;

import com.adenon.sp.administration.interceptors.BeanInterceptor;
import com.adenon.sp.administration.interceptors.Call;
import com.adenon.sp.administration.util.ReflectUtils;
import com.adenon.sp.kernel.utils.Primitives;


public class JmxBean implements DynamicMBean {

    private final Object            bean;
    private final BeanInfo          beanInfo;
    private final List<JmxBean>     childBeans = new ArrayList<JmxBean>();
    private final BeanInterceptor[] interceptors;

    public JmxBean(final Object bean,
                   final BeanInfo info,
                   final BeanInterceptor[] interceptors) throws Exception {
        this.bean = bean;
        this.beanInfo = info;
        this.interceptors = interceptors;
    }

    @Override
    public Object getAttribute(final String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        Object attr = null;
        attr = this.searchChilds(attribute, attr);
        if (attr != null) {
            return attr;
        }
        final AttributeInfo info = this.beanInfo.getBeanJmxInfo().getAttribute(attribute);
        if (info == null) {
            throw new AttributeNotFoundException("Info could not be found for attribute [" + attribute + "] !");
        }
        Object value = null;
        try {
            value = ReflectUtils.invoke(this.bean, info.getGetter());
        } catch (final Exception e) {
            throw new ReflectionException(e);
        }
        if ((value != null) && !Primitives.isPrimitiveOrWrapper(value.getClass())) {
            value = value.toString();
        }
        attr = new Attribute(attribute, value);
        return attr;
    }

    @Override
    public void setAttribute(final Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        AttributeInfo info = null;
        for (final JmxBean bean : this.childBeans) {
            try {
                bean.setAttribute(attribute);
                return;
            } catch (final AttributeNotFoundException e) {
            }
        }
        info = this.beanInfo.getBeanJmxInfo().getAttribute(attribute.getName());
        if (info == null) {
            throw new AttributeNotFoundException("Info could not be found for attribute [" + attribute + "] !");
        }
        try {
            ReflectUtils.invoke(this.bean, info.getSetter(), attribute.getValue());
            final Call call = new Call(this.beanInfo, this.bean, info.getSetter(), attribute.getValue());
            for (final BeanInterceptor interceptor : this.interceptors) {
                interceptor.invoke(call);
            }
        } catch (final InvocationTargetException e) {
            throw new ReflectionException((Exception) e.getTargetException());
        } catch (final Exception e) {
            throw new MBeanException(e);
        }
    }

    @Override
    public Object invoke(final String actionName,
                         final Object[] params,
                         final String[] signature) throws MBeanException, ReflectionException {
        try {
            Method method = this.beanInfo.getBeanJmxInfo().getMethod(actionName, params, signature);
            return ReflectUtils.invoke(this.bean, method, params);
        } catch (final Exception e) {
            return "Error ! " + e.getMessage();
        }
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return this.beanInfo.getBeanJmxInfo().getmBeanInfo();
    }

    @Override
    public AttributeList getAttributes(final String[] attributes) {
        final AttributeList list = new AttributeList();
        for (int i = 0; i < attributes.length; i++) {
            try {
                list.add(i, this.getAttribute(attributes[i]));
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    @Override
    public AttributeList setAttributes(final AttributeList attributes) {
        // throw new RuntimeException("Not implemented ! [DynamicBean.setAttributes(..)]");
        return attributes;
    }

    public void addChild(final JmxBean childBean,
                         boolean willMerge) {
        this.childBeans.add(childBean);
        // if (willMerge) {
        this.getBeanJmxInfo().merge(childBean.getBeanJmxInfo());
        // }
    }

    public BeanJmxInfo getBeanJmxInfo() {
        return this.beanInfo.getBeanJmxInfo();
    }

    private Object searchChilds(final String attribute,
                                Object attr) throws MBeanException, ReflectionException {
        for (final JmxBean bean : this.childBeans) {
            try {
                attr = bean.getAttribute(attribute);
                if (attr != null) {
                    return attr;
                }
            } catch (final AttributeNotFoundException e) {
            }
        }
        return attr;
    }

    @Override
    public String toString() {
        return "[DynamicBean: Obj: " + this.bean.getClass().getName() + " + " + this.beanInfo.getBeanJmxInfo().toString() + "]";
    }

}
