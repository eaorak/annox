package com.adenon.sp.kernel.utils.log;


public enum LoggingProps {

    SYSTEM_LOG_LEVEL("system.log_level"),
    SYSTEM_OVERRIDE_LEVELS("system.override"),
    LEVEL_BUNDLE_NAME("level.");

    private final String property;

    private LoggingProps(String property) {
        this.property = property;
    }

    public String property() {
        return this.property;
    }

    public static String toBundleName(String key) {
        if (key.startsWith(LEVEL_BUNDLE_NAME.property())) {
            return key.replace(LEVEL_BUNDLE_NAME.property(), "");
        }
        return key;
    }

    public static String toKey(String bundleName) {
        return LEVEL_BUNDLE_NAME.property + bundleName;
    }


}
