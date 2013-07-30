package com.adenon.sp.kernel.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.adenon.sp.kernel.utils.log.BundleType;


public abstract class ApiActivator implements BundleActivator {

    protected BundleContext context;
    protected BundleInfo    headers;

    public ApiActivator() {
    }

    @Override
    public void start(BundleContext context) throws Exception {
        this.context = context;
        this.headers = BundleInfo.create(context.getBundle(), BundleType.API);
    }

    @Override
    public final void stop(BundleContext context) throws Exception {
        this.context = null;
    }

}
