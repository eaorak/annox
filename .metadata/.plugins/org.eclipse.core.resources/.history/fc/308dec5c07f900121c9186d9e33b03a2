package com.adenon.sp.administration;

import java.util.List;

public interface IBeanHelper<T> {

    /**
     * Get administration service.
     */
    IAdministrationService service();

    /**
     * Get all beans
     */
    List<T> getAllBeans();

    /**
     * Get beans in beanClass type, whose parent is given by parent parameter.
     */
    IBeanHelper<T> getChildsOf(Object parent) throws AdministrationException;

    /**
     * Register all beans.
     */
    List<T> register() throws AdministrationException;

    /**
     * Find bean classes that have given id value as their bean ids.
     */
    List<T> search(String idValue);

}
