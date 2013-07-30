package com.adenon.sp.kernel.osgi;

import java.io.IOException;
import java.util.Dictionary;

import org.apache.log4j.Level;
import org.osgi.framework.Bundle;

import com.adenon.sp.kernel.services.BundleInfoService;
import com.adenon.sp.kernel.utils.log.BundleType;
import com.adenon.sp.kernel.utils.log.ILogInfo;
import com.adenon.sp.kernel.utils.log.ILoggingService;
import com.adenon.sp.kernel.utils.log.LoggingService;


public class BundleInfo {

    private final ILoggingService   logging;
    private final String[]          headers = new String[ManifestHeaders.values().length];
    private final int               bundleId;
    private final BundleInfoService service;
    private ILogInfo                logInfo;

    public static BundleInfo create(Bundle bundle,
                                    BundleType type) throws IOException {
        LoggingService logging = LoggingService.getInstance();
        BundleInfoService service = BundleInfoService.getInstance();
        //
        BundleInfo bundleInfo = new BundleInfo(logging, bundle, type, service);
        // Create log info
        final String loggerName = loggerName(bundleInfo);
        bundleInfo.logInfo = LoggingService.getInstance().createBundleLogger(bundleInfo, loggerName, type);
        service.add(bundleInfo);
        return bundleInfo;
    }

    private static String loggerName(BundleInfo info) {
        String activatorName = info.get(ManifestHeaders.BUNDLE_ACTIVATOR);
        return activatorName.substring(0, activatorName.indexOf(".", activatorName.indexOf(".sp.") + ".sp.".length()));
    }

    private BundleInfo(ILoggingService logging,
                       Bundle bundle,
                       BundleType type,
                       BundleInfoService service) throws IOException {
        this.logging = logging;
        Dictionary<?, ?> bundleHeaders = bundle.getHeaders();
        for (ManifestHeaders header : ManifestHeaders.values()) {
            String value = (String) bundleHeaders.get(header.getHeader());
            this.put(header, value);
        }
        this.bundleId = (int) bundle.getBundleId();
        this.service = service;
    }

    public String get(ManifestHeaders header) {
        return this.headers[header.ordinal()];
    }

    public void put(ManifestHeaders header,
                    String value) {
        this.headers[header.ordinal()] = value;
    }

    public ILogInfo getLogInfo() {
        return this.logInfo;
    }

    public int getBundleId() {
        return this.bundleId;
    }

    public void setBundleLogLevel(Level level) {
        this.logging.changeLevelOf(this.get(ManifestHeaders.BUNDLE_SYMBOLICNAME), level);
    }

    public void unregister() {
        this.service.remove(this);
    }

    @Override
    public String toString() {
        return "BundleInfo [name="
               + this.get(ManifestHeaders.BUNDLE_NAME)
               + ", sym-name="
               + this.get(ManifestHeaders.BUNDLE_SYMBOLICNAME)
               + ", version="
               + this.get(ManifestHeaders.BUNDLE_VERSION)
               + "]";
    }

}