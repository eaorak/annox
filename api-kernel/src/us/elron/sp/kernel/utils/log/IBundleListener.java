package us.elron.sp.kernel.utils.log;

import us.elron.sp.kernel.osgi.BundleInfo;

public interface IBundleListener {

    void registered(BundleInfo info);

    void unregistered(BundleInfo info);

}
