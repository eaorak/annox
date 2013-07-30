package com.adenon.sp.administration.jmx;

import java.util.List;

import javax.management.ObjectName;

import com.adenon.sp.administration.BeanCache;
import com.adenon.sp.administration.annotate.MBean;
import com.adenon.sp.administration.util.BeanUtils;
import com.adenon.sp.administration.util.MBeanInfoBuilder;
import com.adenon.sp.administration.util.ReflectUtils;
import com.adenon.sp.kernel.utils.StringUtils;


public class BeanInfo {

    private final ObjectName  objectName;
    private final Object      bean;
    private final BeanJmxInfo beanJmxInfo;
    private JmxBean           jmxBean;
    //
    private final MBean        beanConfig;
    private final String      id;
    private final String      join;
    //
    private final BeanCache   cache;

    public BeanInfo(Object beanObj,
                    BeanCache cache) throws Exception {
        this.cache = cache;
        this.bean = beanObj;
        //
        this.beanConfig = beanObj.getClass().getAnnotation(MBean.class);
        this.checkBeanConfig(beanObj);
        this.id = BeanUtils.getIdValue(beanObj);
        this.join = BeanUtils.getJoinValue(beanObj);
        this.objectName = this.createObjectName(beanObj, this.beanConfig);
        //
        this.beanJmxInfo = MBeanInfoBuilder.createBeanJmxInfo(beanObj, this.beanConfig);
    }

    private void checkBeanConfig(Object beanObj) throws Exception {
        if (this.beanConfig == null) {
            throw new Exception("Class [" + beanObj.getClass() + "] does not have Bean annotation !");
        }
        boolean noParent = (this.beanConfig.parent() == MBean.class) && this.beanConfig.subLocation().equals(MBean.DEF_STR);
        if (noParent) {
            throw new Exception("Class ["
                                + beanObj.getClass().getName()
                                + "] does not have Bean parent definition ! It should have either parent class or sublocation.");
        }
    }

    private ObjectName createObjectName(Object beanObj,
                                        MBean beanAnt) throws Exception {
        StringBuilder oname = new StringBuilder();
        this.buildParentInfo(beanAnt, oname);
        //
        String subLocation = beanAnt.subLocation();
        if (!subLocation.equals(MBean.DEF_STR)) {
            boolean localParent = oname.toString().endsWith(":");
            oname.append(localParent ? "" : ",");
            oname.append(subLocation);
        }
        //
        String id = beanAnt.id();
        if ((id != null) && !id.equals(MBean.DEF_STR)) {
            oname.append(",");
            oname.append(id);
            oname.append("=");
            oname.append(ReflectUtils.invoke(beanObj, "get" + StringUtils.cap(id)));
        }
        return new ObjectName(oname.toString());
    }

    private void buildParentInfo(MBean beanAnt,
                                 StringBuilder infoStr) throws Exception {
        Class<?> parent = beanAnt.parent();
        if (parent == MBean.class) {
            infoStr.append(beanAnt.location().getLocation()).append(":");
            return;
        }
        List<BeanInfo> infoGroup = this.cache.getInfoGroup(parent);
        if ((infoGroup == null) || (infoGroup.size() == 0)) {
            throw new Exception("No parent info found for bean [" + this.bean.getClass() + "] !");
        }
        BeanInfo parentInfo = infoGroup.get(0);
        if (infoGroup.size() > 1) {
            for (BeanInfo info : infoGroup) {
                if (info.id == null) {
                    continue;
                }
                if (this.join.equals(info.id)) {
                    parentInfo = info;
                    break;
                }
            }
        }
        if (parentInfo == null) {
            throw new Exception("No parent info found for bean [" + this.bean.getClass() + "] !");
        }
        infoStr.append(parentInfo.objectName().toString());
    }

    public MBean beanConfig() {
        return this.beanConfig;
    }

    public ObjectName objectName() {
        return this.objectName;
    }

    public Object bean() {
        return this.bean;
    }

    public BeanJmxInfo getBeanJmxInfo() {
        return this.beanJmxInfo;
    }

    public JmxBean getJmxBean() {
        return this.jmxBean;
    }

    public void setDynamicBean(JmxBean dynamicBean) {
        this.jmxBean = dynamicBean;
    }

}
