package us.elron.sp.kernel.utils;

import us.elron.sp.kernel.osgi.IOsgiUtilService;
import us.elron.sp.kernel.services.IAssistService;
import us.elron.sp.kernel.services.IBundleInfoService;
import us.elron.sp.kernel.utils.log.ILoggingService;


public interface IFrameworkServices {

    IAssistService assistService();

    IBundleScanService bundleScanner();

    IBundleInfoService bundleInfoService();

    IOsgiUtilService osgiUtilService();

    ILoggingService loggingService();

    // IBundleClassService classUtilService();

}
