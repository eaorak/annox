package com.adenon.sp.kernel.osgi;

public enum ManifestHeaders {

    BUNDLE_NAME("Bundle-Name"), /**/
    BUNDLE_SYMBOLICNAME("Bundle-SymbolicName"), /**/
    BUNDLE_VERSION("Bundle-Version"), /**/
    BUNDLE_ACTIVATOR("Bundle-Activator"), /**/
    BUNDLE_VENDOR("Bundle-Vendor"), /**/
    SP_BUNDLE_ID("Sp-Bundle-Id");

    private String header;
    private String desc;

    private ManifestHeaders(String header) {
        this.header = header;
        this.desc = header.replace("-", " ");
    }

    public String getHeader() {
        return this.header;
    }

    public String getDesc() {
        return this.desc;
    }

}
