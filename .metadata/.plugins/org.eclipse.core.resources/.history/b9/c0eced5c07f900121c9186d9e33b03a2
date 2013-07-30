package com.adenon.sp.administration;

public interface IAdministrationService {

    /**
     * Register bean. Returned bean object SHOULD be used instead the bean object thereafter.
     */
    <T> T registerBean(T beanObj) throws AdministrationException;

    /**
     * Get beans in beanClass type.
     */
    <T> IBeanHelper<T> getBeans(Class<T> beanClass) throws AdministrationException;

    /**
     * Unregister bean.
     */
    boolean unregisterBean(Object bean) throws AdministrationException;


}
