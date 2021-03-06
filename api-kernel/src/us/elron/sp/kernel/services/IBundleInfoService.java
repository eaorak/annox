package us.elron.sp.kernel.services;

import java.util.List;

import us.elron.sp.kernel.osgi.BundleInfo;
import us.elron.sp.kernel.utils.log.IBundleListener;



public interface IBundleInfoService {

    List<BundleInfo> register(IBundleListener listener);

    void unregister(IBundleListener listener);

}
