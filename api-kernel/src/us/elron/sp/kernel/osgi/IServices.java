package us.elron.sp.kernel.osgi;

public interface IServices {

    <T> T getService(Class<T> srvInterface);

    BundleInfo getBundleInfo();

}
