package com.adenon.sp.administration.persist;

import com.adenon.sp.administration.interceptors.Call;
import com.adenon.sp.administration.interceptors.PropertyChangeInterceptor;
import com.adenon.sp.administration.jmx.BeanInfo;

public class PersistenceIncerteptor extends PropertyChangeInterceptor {

    private IPersistence dbService;

    public PersistenceIncerteptor(IPersistence dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void propertyChanged(Call call,
                                   String attr,
                                   Object newValue) {
        this.persist(call.beanInfo());
    }

    @Override
    protected void propertyChangeFail(Call call,
                                      String attr,
                                      Object newValue) {
        this.persist(call.beanInfo());
    }

    private void persist(BeanInfo info) {
        this.dbService.persist(info);
    }

}
