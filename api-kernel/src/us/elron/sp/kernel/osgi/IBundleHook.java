package us.elron.sp.kernel.osgi;

public interface IBundleHook {

    void injectBundle(BundleInfo headers);

}
