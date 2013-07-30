package com.adenon.sp.kernel.properties;

import java.io.File;

public enum SysProps {

    HOME_PATH("sp.home", "/tmp"), //
    CONF_PATH("sp.conf", "conf"), //
    BUNDLE_HOME_PATH("bundle.home", "osgi/bundles"), //
    PATH_SEP(File.separator, "/"); //

    private String key;
    private String value;

    private SysProps(String key,
                     String defValue) {
        this.key = key;
        String val = System.getProperty(key);
        this.value = (val == null) ? defValue : val;
    }

    public String key() {
        return this.key;
    }

    public String value() {
        return this.value;
    }

    public static String fullPathOf(SysProps prop) {
        return SysProps.HOME_PATH.value() + PATH_SEP.value() + prop.value();
    }

    @Override
    public String toString() {
        return this.value;
    }

}
