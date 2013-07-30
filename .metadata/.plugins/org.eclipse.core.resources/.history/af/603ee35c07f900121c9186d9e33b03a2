package com.adenon.sp.administration.jmx;

import java.lang.reflect.Method;

import javax.management.MBeanAttributeInfo;
import javax.management.modelmbean.DescriptorSupport;

import com.adenon.sp.administration.annotate.Attribute;
import com.adenon.sp.administration.util.ReflectUtils;


public class AttributeInfo {

    private String                   name;
    private Class<?>                 type;
    private String                   description;
    private Method                   getter;
    private Method                   setter;
    private boolean                  readOnly;
    //
    private final MBeanAttributeInfo attrInfo;

    public static AttributeInfo createGenericAttributeInfo(Attribute info, Method getter, Method setter) {
        String name = info.name().equals(Attribute.DEF_STR) ? ReflectUtils.toAttr(getter.getName()) : info.name();
        String description = info.description().equals(Attribute.DEF_STR) ? name : info.description();
        return new AttributeInfo(name, description, getter, setter, info.readOnly());
    }

    // TODO @Key @Value annotations ?
    public static AttributeInfo createDynamicAttributeInfo(String name, Method getter, Method setter) {
        return new AttributeInfo(name, name, getter, setter, true);
    }

    private AttributeInfo(String name, String description, Method getter, Method setter, boolean readOnly) {
        this.name = name;
        this.description = description;
        this.getter = getter;
        this.setter = setter;
        this.readOnly = readOnly;
        this.type = getter.getReturnType();
        this.attrInfo = this.toMBeanAttributeInfo();
    }

    public String getName() {
        return this.name;
    }

    public AttributeInfo setName(String name) {
        this.name = name;
        return this;
    }

    public Class<?> getType() {
        return this.type;
    }

    public AttributeInfo setType(Class<?> type) {
        this.type = type;
        return this;
    }

    public AttributeInfo setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public Method getGetter() {
        return this.getter;
    }

    public AttributeInfo setGetter(Method getter) {
        this.getter = getter;
        return this;
    }

    public Method getSetter() {
        return this.setter;
    }

    public AttributeInfo setSetter(Method setter) {
        this.setter = setter;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public MBeanAttributeInfo getAttrInfo() {
        return this.attrInfo;
    }

    private MBeanAttributeInfo toMBeanAttributeInfo() {
        return new MBeanAttributeInfo(this.name, this.type.getName(), this.description, true, !this.readOnly, false, new DescriptorSupport());
    }

    @Override
    public String toString() {
        return "[" + this.name + ":" + this.type.getName() + "]";
    }

}