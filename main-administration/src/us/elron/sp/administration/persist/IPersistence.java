package us.elron.sp.administration.persist;

import java.util.List;

import us.elron.sp.administration.jmx.BeanInfo;



public interface IPersistence {

    boolean isConnected();

    void persist(BeanInfo info);

    <T> List<T> get(Class<T> beanClass);

    void remove(BeanInfo info);

}
