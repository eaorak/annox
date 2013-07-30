package com.adenon.sp.administration.beans;

import java.util.ArrayList;
import java.util.List;

import com.adenon.sp.administration.AdministrationException;
import com.adenon.sp.administration.IAdministrationService;
import com.adenon.sp.administration.IBeanHelper;
import com.adenon.sp.administration.util.BeanUtils;


public class BeanHelper<T> implements IBeanHelper<T> {

    private final IAdministrationService administration;
    private List<T>                      beans;

    public BeanHelper(IAdministrationService administration, List<T> beans) {
        this.administration = administration;
        this.beans = beans;
    }

    @Override
    public IAdministrationService service() {
        return this.administration;
    }

    @Override
    public List<T> getAllBeans() {
        return this.beans;
    }

    @Override
    public IBeanHelper<T> getChildsOf(final Object parent) throws AdministrationException {
        final String parentId = BeanUtils.getIdValue(parent);
        final List<T> beanList = new ArrayList<T>();
        for (final T bean : this.beans) {
            final String joinValue = BeanUtils.getJoinValue(bean);
            if (joinValue == null) {
                continue;
                // throw new AdministrationException("Object class : " + parent.getClass().getName() + " has no join attribute !");
            }
            if (joinValue.equals(parentId)) {
                beanList.add(bean);
            }
        }
        return new BeanHelper<T>(this.administration, beanList);
    }

    @Override
    public List<T> register() throws AdministrationException {
        List<T> registeredBeans = new ArrayList<T>();
        for (T bean : this.beans) {
            registeredBeans.add(this.administration.registerBean(bean));
        }
        this.beans = registeredBeans;
        return this.beans;
    }

    @Override
    public List<T> search(String idValue) {
        List<T> matchList = new ArrayList<T>();
        for (T bean : this.beans) {
            String value = BeanUtils.getIdValue(bean);
            if ((value != null) && value.equals(idValue)) {
                matchList.add(bean);
            }
        }
        return matchList;
    }

}
