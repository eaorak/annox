package us.elron.sp.kernel.osgi.activator;

import us.elron.sp.kernel.utils.log.BundleType;

public abstract class BundleActivator extends Activator {

    @Override
    public BundleType getType() {
        return BundleType.BUNDLE;
    }

}
