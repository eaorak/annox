package com.adenon.sp.kernel.osgi;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class Services implements IServices {

    private Map<Class<?>, Object> services = new Hashtable<Class<?>, Object>();
    private final BundleInfo      bundleInfo;

    public Services(BundleInfo bundleInfo) {
        this.bundleInfo = bundleInfo;
    }

    public <T> void addService(Class<T> srvIface,
                               T service) {
        this.services.put(srvIface, service);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> srvIface) {
        return (T) this.services.get(srvIface);
    }

    public void clear() {
        this.services.clear();
        this.services = null;
    }

    public Collection<Object> getAll() {
        return this.services.values();
    }

    @Override
    public BundleInfo getBundleInfo() {
        return this.bundleInfo;
    }

}
