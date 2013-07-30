package com.adenon.sp.configuration.test;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.adenon.sp.administration.jmx.JmxBean;


public class BeanProxyTest implements InvocationHandler {

    private Object object;

    public BeanProxyTest(Object obj) {
        this.object = obj;
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<?> beanClass,
                               Class<T> intf) throws Exception {
        Object object = beanClass.newInstance();
        BeanProxyTest proxy = new BeanProxyTest(object);
        return (T) Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), proxy);
    }

    @Override
    public Object invoke(Object proxy,
                         Method method,
                         Object[] args) throws Throwable {
        System.out.println(method.getName() + " has called.");
        return method.invoke(this.object, args);
    }

    public static void main(String[] args) throws Exception {
        JmxBean mbean = null;// new DynamicBean(null, new Hello());
        //
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        // Unique identification of MBeans
        try {
            // Uniquely identify the MBeans and register them with the platform MBeanServer
            ObjectName helloName = new ObjectName("SimpleAgent:name=hellothere");
            mbs.registerMBean(mbean, helloName);
            waitForEnterPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void waitForEnterPressed() {
        try {
            System.out.println("Press  to continue...");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
