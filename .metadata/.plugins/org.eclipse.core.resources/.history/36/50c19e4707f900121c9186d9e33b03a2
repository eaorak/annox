package com.adenon.sp.administration;

import com.adenon.sp.administration.IAdministrationService;
import com.adenon.sp.administration.beans.AdministrationBean;
import com.adenon.sp.administration.persist.mongo.MongoPersistence;
import com.adenon.sp.kernel.osgi.activator.BundleActivator;


public class AdministrationActivator extends BundleActivator {

    private static int JMX_PORT = 6666;

    @Override
    protected void start() throws Exception {
        final MongoPersistence dbService = new MongoPersistence();
        AdministrationService administration = new AdministrationService(dbService, JMX_PORT, this.services);
        this.registerService(IAdministrationService.class, administration);
        administration.registerBean(new AdministrationBean());
    }

    @Override
    protected void stop() throws Exception {

    }

}
