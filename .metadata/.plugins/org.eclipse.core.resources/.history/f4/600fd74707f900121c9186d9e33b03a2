package com.adenon.sp.kernel.services;

import java.util.ArrayList;
import java.util.List;

import com.adenon.sp.kernel.osgi.BundleInfo;
import com.adenon.sp.kernel.utils.log.IBundleListener;


public class BundleInfoService implements IBundleInfoService {

    private static BundleInfoService    INSTANCE  = new BundleInfoService();
    //
    private final List<BundleInfo>      infoList  = new ArrayList<BundleInfo>();
    private final List<IBundleListener> listeners = new ArrayList<IBundleListener>();

    public static BundleInfoService getInstance() {
        return INSTANCE;
    }

    private BundleInfoService() {
    }

    public synchronized void add(BundleInfo info) {
        this.infoList.add(info);
        this.notifyListeners(info, true);
    }

    public void remove(BundleInfo info) {
        this.infoList.remove(info);
        this.notifyListeners(info, false);
    }

    @Override
    public synchronized List<BundleInfo> register(final IBundleListener listener) {
        this.listeners.add(listener);
        return this.infoList;
    }

    @Override
    public synchronized void unregister(final IBundleListener listener) {
        this.listeners.remove(listener);
    }

    private void notifyListeners(BundleInfo info,
                                 boolean registered) {
        try {
            for (IBundleListener listener : this.listeners) {
                if (registered) {
                    listener.registered(info);
                } else {
                    listener.unregistered(info);
                }
            }
        } catch (Throwable e) {
            // ignore
        }
    }

}
