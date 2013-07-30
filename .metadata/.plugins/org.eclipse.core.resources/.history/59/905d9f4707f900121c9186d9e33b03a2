package com.adenon.sp.administration;

import java.util.HashSet;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import org.apache.log4j.Logger;

import com.adenon.sp.administration.beans.BeanHelper;
import com.adenon.sp.administration.interceptors.BeanInterceptor;
import com.adenon.sp.administration.interceptors.StandartInterceptor;
import com.adenon.sp.administration.interceptors.ValidationInterceptor;
import com.adenon.sp.administration.jmx.BeanInfo;
import com.adenon.sp.administration.jmx.JmxBean;
import com.adenon.sp.administration.persist.IPersistence;
import com.adenon.sp.administration.persist.PersistenceIncerteptor;
import com.adenon.sp.kernel.osgi.Services;


public class AdministrationService implements IAdministrationService {

    private static final Logger     logger            = Logger.getLogger(AdministrationService.class);
    private final MBeanServer       mbs;
    private final BeanInterceptor[] interceptors;
    private final IPersistence      dbService;
    private final BeanCache         cache             = new BeanCache();
    private final Set<Class<?>>     registeredClasses = new HashSet<Class<?>>();

    public AdministrationService(final IPersistence dbService,
                                 final int port,
                                 final Services services) throws Exception {
        this.dbService = dbService;
        this.mbs = new JmxAdaptor(port).start();
        this.interceptors = new BeanInterceptor[] { new ValidationInterceptor(), new PersistenceIncerteptor(dbService) }; // TODO Log, Authorization
                                                                                                                          // ... interceptors
    }

    @Override
    public <T> T registerBean(final T beanObj) throws AdministrationException {
        T bean = null;
        try {
            bean = this.registerBean(beanObj, true);
        } catch (Exception e) {
            throw new AdministrationException(e);
        }
        return bean;
    }

    @SuppressWarnings("unchecked")
    private <T> T registerBean(final T beanObj,
                               final boolean persist) throws Exception {
        final Class<T> beanClass = (Class<T>) beanObj.getClass();
        final BeanInfo beanInfo = new BeanInfo(beanObj, this.cache);
        //
        boolean willMerge = !this.registeredClasses.contains(beanClass);
        this.registeredClasses.add(beanClass);
        this.cache.put(beanObj, beanInfo);
        final StandartInterceptor incerteptor = new StandartInterceptor(beanInfo, this.interceptors);
        final T beanInstance = this.newInstance(beanClass, incerteptor);
        final JmxBean jmxBean = new JmxBean(beanObj, beanInfo, this.interceptors);
        beanInfo.setDynamicBean(jmxBean);

        if (this.mbs.isRegistered(beanInfo.objectName())) {
            final ObjectName objectName = beanInfo.objectName();
            this.mbs.unregisterMBean(objectName);
            //
            final BeanInfo parentInfo = this.cache.get(objectName);
            parentInfo.getJmxBean().addChild(jmxBean, willMerge);
            this.mbs.registerMBean(parentInfo.getJmxBean(), objectName);
        } else {
            this.mbs.registerMBean(jmxBean, beanInfo.objectName());
        }
        if (persist && beanInfo.beanConfig().persist()) {
            this.dbService.persist(beanInfo);
        }
        return beanInstance;
    }

    @Override
    public <T> BeanHelper<T> getBeans(final Class<T> beanClass) throws AdministrationException {
        return new BeanHelper<T>(this, this.dbService.get(beanClass));
    }

    @SuppressWarnings("unchecked")
    private <T> T newInstance(final Class<T> clazz,
                              final MethodInterceptor interceptor) {
        final Enhancer e = new Enhancer();
        e.setSuperclass(clazz);
        e.setCallback(interceptor);
        return (T) e.create();
    }

    @Override
    public boolean unregisterBean(final Object bean) throws AdministrationException {
        if (bean == null) {
            throw new NullPointerException("Bean object can not be null !");
        }
        final BeanInfo beanInfo = this.cache.remove(bean);
        if (beanInfo == null) {
            throw new AdministrationException("No bean found for instance [" + bean + "] !");
        }
        this.dbService.remove(beanInfo);
        this.unregisterJmx(beanInfo);
        return true;
    }

    private void unregisterJmx(final BeanInfo beanInfo) throws AdministrationException {
        try {
            this.mbs.unregisterMBean(beanInfo.objectName());
        } catch (MBeanRegistrationException e) {
            logger.error("Error : " + e.getMessage(), e);
            throw new AdministrationException("JMX Error !", e);
        } catch (InstanceNotFoundException e) {
            logger.error("Error : " + e.getMessage(), e);
            throw new AdministrationException("No instance of [" + beanInfo.objectName() + "] could be found !");
        }
    }

}
