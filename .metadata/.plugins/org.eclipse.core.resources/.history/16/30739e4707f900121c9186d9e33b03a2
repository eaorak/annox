package com.adenon.sp.kernel.osgi.activator;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.adenon.sp.kernel.osgi.BundleInfo;
import com.adenon.sp.kernel.osgi.ManifestHeaders;
import com.adenon.sp.kernel.osgi.Services;
import com.adenon.sp.kernel.properties.SysProps;
import com.adenon.sp.kernel.services.IAssistService;
import com.adenon.sp.kernel.utils.log.BundleType;
import com.adenon.sp.kernel.utils.log.ILogInfo;
import com.adenon.sp.kernel.utils.log.LoggingService;


public abstract class Activator implements BundleActivator {

    protected Services       services;
    protected BundleInfo     bundleInfo;
    protected Logger         logger;
    protected String         confPath;
    protected BundleContext  context;
    //
    protected IAssistService generation;
    private final BundleType bundleType;

    public Activator() {
        this.bundleType = this.getType();
        if (this.bundleType == null) {
            throw new RuntimeException("Bundle type should be defined ! Error @ [" + this.getClass().getName() + "]");
        }
    }

    @Override
    public final void start(final BundleContext context) throws Exception {
        this.context = context;
        this.bundleInfo = BundleInfo.create(context.getBundle(), this.bundleType);
        this.services = new Services(this.bundleInfo);
        final String bundleSymName = this.bundleInfo.get(ManifestHeaders.BUNDLE_SYMBOLICNAME);
        this.createLogger(bundleSymName);
        this.confPath = this.setConfPath(bundleSymName);
        try {
            this.start();
        } catch (final Throwable e) {
            this.logger.error("Error on startup ! " + e.getMessage(), e);
            if (!this.getType().isEsential()) {
                throw new Exception(e);
            }
            System.err.println("Error on essential bundle : " + e.getMessage());
            this.exit();
        }
    }

    private String setConfPath(final String bundleSymName) {
        return SysProps.HOME_PATH.value()
               + SysProps.PATH_SEP.value()
               + SysProps.CONF_PATH.value()
               + SysProps.PATH_SEP.value()
               + this.getType().path()
               + SysProps.PATH_SEP.value()
               + bundleSymName.split("-")[1];
    }

    @Override
    public final void stop(final BundleContext context) throws Exception {
        this.logger.info(">> Stopping Bundle :: " + context.getBundle().getSymbolicName());
        this.stop();
        this.bundleInfo.unregister();
        this.context = null;
    }

    public final <T> T getService(final Class<T> serviceClass) {
        T service = this.getServiceInternal(serviceClass);
        this.services.addService(serviceClass, service);
        return service;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private <T> T getServiceInternal(final Class<T> serviceClass) {
        T service = null;
        final ServiceReference srvRef = this.context.getServiceReference(serviceClass.getName());
        if ((srvRef == null) || ((service = (T) this.context.getService(srvRef)) == null)) {
            throw new RuntimeException("Error ! Could not load '" + serviceClass.getName() + "' service !");
        }
        return service;
    }

    public final Services getServices(final Class<?>... serviceClasses) {
        for (final Class<?> c : serviceClasses) {
            this.getService(c);
        }
        return this.services;
    }

    protected <T> T registerService(final Class<T> inf,
                                    final T service) {
        return this.registerService(inf, service, true);
    }

    @SuppressWarnings("unchecked")
    protected <T> T registerService(final Class<T> inf,
                                    final T service,
                                    boolean proxy) {
        if (this.generation == null) {
            this.generation = (inf == IAssistService.class) ? (IAssistService) service : this.getService(IAssistService.class);
        }
        T serviceProxy = service;
        if (proxy) {
            try {
                serviceProxy = (T) this.generation.addLoggingTo(this.generation.createProxyFor(service, false).addInterface(inf));
            } catch (final Exception e) {
                throw new RuntimeException("Error while creating service logging for [" + inf.getName() + "] !", e);
            }
        }
        this.context.registerService(inf.getName(), serviceProxy, null);
        return serviceProxy;
    }

    private void createLogger(final String bundleSymName) throws IOException {
        if (bundleSymName.contains("-api")) {
            return;
        }
        final String loggerName = this.loggerName(this.getClass());
        ILogInfo logInfo = LoggingService.getInstance().createBundleLogger(this.bundleInfo, loggerName, this.bundleType);
        this.logger = logInfo.getLogger();
        this.logger.info(">> Starting Bundle :: " + bundleSymName);
    }

    private String loggerName(final Class<?> clazz) {
        final String name = clazz.getName();
        return name.substring(0, name.indexOf(".", name.indexOf(".sp.") + ".sp.".length()));
    }

    private void exit() {
        String error = "ESSENTIAL BUNDLE [" + this.bundleInfo.get(ManifestHeaders.BUNDLE_SYMBOLICNAME) + "] COULD NOT BE STARTED ! SHUTTING DOWN SYSTEM !";
        System.err.println(error);
        String warn = "*********************************************************************************";
        this.logger.fatal(warn);
        this.logger.fatal(error);
        this.logger.fatal(warn);
        System.exit(1);
    }

    public Bundle getBundle() {
        return this.context.getBundle();
    }

    public BundleInfo getBundleInfo() {
        return this.bundleInfo;
    }

    //

    protected abstract void start() throws Exception;

    protected abstract void stop() throws Exception;

    public abstract BundleType getType();

}
