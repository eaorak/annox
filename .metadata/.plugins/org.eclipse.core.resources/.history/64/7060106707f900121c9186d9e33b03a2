package com.adenon.sp.administration.interceptors;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.log4j.Logger;

import com.adenon.sp.administration.jmx.BeanInfo;
import com.adenon.sp.administration.util.ReflectUtils;


public class StandartInterceptor implements MethodInterceptor {

    private static Logger           logger = Logger.getLogger(StandartInterceptor.class);
    private final BeanInfo          beanInfo;
    private final BeanInterceptor[] interceptors;

    public StandartInterceptor(final BeanInfo beanInfo,
                               final BeanInterceptor[] interceptors) {
        this.beanInfo = beanInfo;
        this.interceptors = interceptors;
    }

    @Override
    public Object intercept(final Object obj,
                            final Method method,
                            final Object[] args,
                            final MethodProxy proxy) throws Throwable {
        if (!ReflectUtils.isSetter(method)) {
            return ReflectUtils.invoke(this.beanInfo.bean(), method, args);
        }
        boolean success = true;
        final Call call = new Call(this.beanInfo, obj, method, args);
        Object oldValue = null;
        Object result = null;
        final Object bean = this.beanInfo.bean();
        try {
            oldValue = ReflectUtils.get(bean, method);
            result = ReflectUtils.invoke(bean, method, args);
            for (final BeanInterceptor interceptor : this.interceptors) {
                interceptor.invoke(call);
            }
        } catch (final Exception e) {
            logger.error("[StandartInterceptor][intercept] : Error : " + e.getMessage(), e);
            success = false;
            throw new Exception(e);
        } finally {
            if (!success) {
                result = this.rollback(method, call, oldValue, bean);
            }
        }
        return result;
    }

    private Object rollback(final Method method,
                            final Call call,
                            final Object oldValue,
                            final Object bean) throws Exception {
        Object result = null;
        if (call.isSetterMethod()) {
            result = ReflectUtils.invoke(bean, method, new Object[] { oldValue });
            for (final BeanInterceptor interceptor : this.interceptors) {
                try {
                    interceptor.fail(call);
                } catch (final Throwable e) {
                    e.printStackTrace();
                }
            }
        } else {
            // TODO : LOG
        }
        return result;
    }

}
