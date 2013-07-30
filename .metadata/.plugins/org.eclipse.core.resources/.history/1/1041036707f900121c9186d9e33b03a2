package com.adenon.sp.administration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.ObjectName;

import com.adenon.sp.administration.jmx.BeanInfo;


public class BeanCache {

    private Map<Object, BeanInfo>         beanInfoMap   = new ConcurrentHashMap<Object, BeanInfo>();
    private Map<Class<?>, List<BeanInfo>> beanInfoGroup = new ConcurrentHashMap<Class<?>, List<BeanInfo>>();
    private Map<ObjectName, BeanInfo>     beanNameMap   = new ConcurrentHashMap<ObjectName, BeanInfo>();

    public BeanCache() {
    }

    public synchronized void put(Object bean,
                                 BeanInfo info) {
        if (!this.beanInfoMap.containsKey(bean)) {
            this.beanInfoMap.put(bean, info);
        }
        if (!this.beanNameMap.containsKey(info.objectName())) {
            this.beanNameMap.put(info.objectName(), info);
        }
        //
        Class<?> beanClass = bean.getClass();
        List<BeanInfo> list = this.beanInfoGroup.get(beanClass);
        if (list == null) {
            list = new ArrayList<BeanInfo>();
            this.beanInfoGroup.put(beanClass, list);
        }
        list.add(info);
    }

    public BeanInfo get(Object obj) {
        return this.beanInfoMap.get(obj);
    }

    public BeanInfo get(ObjectName objectName) {
        return this.beanNameMap.get(objectName);
    }

    public List<BeanInfo> getInfoGroup(Class<?> clazz) {
        return this.beanInfoGroup.get(clazz);
    }

    public BeanInfo remove(Object obj) {
        BeanInfo info = this.beanInfoMap.remove(obj);
        if (info != null) {
            this.beanNameMap.remove(info.objectName());
            this.beanInfoGroup.remove(obj.getClass());
        }
        return info;
    }

}
