package com.adenon.sp.administration.jmx;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;

import com.adenon.sp.administration.util.MBeanInfoBuilder;


public class BeanJmxInfo {

    private MBeanInfo                        mBeanInfo;
    private final MBeanConstructorInfo       constructors;
    private final Map<String, AttributeInfo> attributes;
    private final Map<String, OperationInfo> operations;
    private final Class<?>                   beanClass;

    public BeanJmxInfo(Class<?> beanClass,
                       MBeanConstructorInfo constructors,
                       Map<String, AttributeInfo> attributes,
                       Map<String, OperationInfo> operations) {
        this.beanClass = beanClass;
        this.constructors = constructors;
        this.attributes = attributes;
        this.operations = operations;
        this.mBeanInfo = MBeanInfoBuilder.createMBean(beanClass.getName(), constructors, this.attributes(), this.operations());
    }

    public Class<?> getBeanClass() {
        return this.beanClass;
    }

    public MBeanInfo getmBeanInfo() {
        return this.mBeanInfo;
    }

    public AttributeInfo getAttribute(String attribute) {
        return this.attributes.get(attribute);
    }

    public Method getMethod(String actionName,
                            final Object[] params,
                            String[] signatureStr) {
        String signature = OperationInfo.signature(actionName, signatureStr);
        OperationInfo methodInfo = this.operations.get(signature);
        if (methodInfo == null) {
            throw new RuntimeException("No operation could be found with signature [" + signature + "] !");
        }
        return methodInfo.getMethod();
    }

    public void merge(BeanJmxInfo child) {
        // Attributes
        for (AttributeInfo info : child.attributes.values()) {
            if (this.attributes.containsKey(info)) {
                throw new RuntimeException("Bean [" + this.beanClass.getName() + "] already contains an attribute named [" + info.getName() + "] !");
            }
            this.attributes.put(info.getName(), info);
        }
        // Operations
        for (OperationInfo info : child.operations.values()) {
            String signature = info.signature();
            if (this.operations.containsKey(signature)) {
                throw new RuntimeException("Bean [" + this.beanClass.getName() + "] already contains an operation as [" + signature + "] !");
            }
            this.operations.put(signature, info);
        }
        //
        this.mBeanInfo = MBeanInfoBuilder.createMBean(this.mBeanInfo.getClassName(), this.constructors, this.attributes(), this.operations());
    }

    private List<MBeanAttributeInfo> attributes() {
        List<MBeanAttributeInfo> attrList = new ArrayList<MBeanAttributeInfo>();
        for (AttributeInfo info : this.attributes.values()) {
            attrList.add(info.getAttrInfo());
        }
        return attrList;
    }

    private List<MBeanOperationInfo> operations() {
        List<MBeanOperationInfo> opList = new ArrayList<MBeanOperationInfo>();
        for (OperationInfo info : this.operations.values()) {
            opList.add(info.getInfo());
        }
        return opList;
    }

    @Override
    public String toString() {
        return "[BeanInfo : " + this.beanClass.getName() + "]";
    }

}
