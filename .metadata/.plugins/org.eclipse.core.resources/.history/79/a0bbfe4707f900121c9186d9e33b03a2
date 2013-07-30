package com.adenon.sp.kernel.utils.log;

public enum BundleType {

    BUNDLE("bundles"), //
    SERVICE("services"), //
    CHANNEL("channels"), //
    API("api");

    private String path;

    private BundleType(String path) {
        this.path = path;
    }

    public String path() {
        return this.path;
    }

    public boolean isEsential() {
        return this == BUNDLE;
    }

}
