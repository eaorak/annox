package us.elron.sp.kernel;

import org.osgi.framework.BundleContext;

import us.elron.sp.kernel.osgi.ApiActivator;


public class ApiKernel extends ApiActivator {

    public static BundleContext bundleContext;

    @Override
    public void start(BundleContext context) throws Exception {
        bundleContext = context;
    }


}
