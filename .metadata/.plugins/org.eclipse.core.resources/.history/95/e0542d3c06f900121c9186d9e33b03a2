package com.adenon.sp.administration.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

import com.adenon.sp.administration.annotate.MBean;
import com.adenon.sp.administration.annotate.Dynamic;
import com.adenon.sp.administration.annotate.Operation;
import com.adenon.sp.administration.annotate.Parameter;
import com.adenon.sp.administration.jmx.AttributeInfo;
import com.adenon.sp.administration.jmx.BeanJmxInfo;
import com.adenon.sp.administration.jmx.OperationInfo;
import com.adenon.sp.kernel.utils.StringUtils;


public class MBeanInfoBuilder {

    private MBeanInfoBuilder() {
    }

    public static BeanJmxInfo createBeanJmxInfo(final Object bean,
                                                MBean beanConfig) throws Exception {
        Class<? extends Object> beanClass = bean.getClass();
        // Constructors
        MBeanConstructorInfo constructors = generateMBeanConstructorInfo(beanClass);
        // Attributes
        Map<String, AttributeInfo> attributes = generateAttributeInfo(bean, beanConfig);
        // Operations
        Map<String, OperationInfo> operations = generateOperationInfo(beanClass);
        // BeanJmxInfo
        return new BeanJmxInfo(beanClass, constructors, attributes, operations);
    }

    public static MBeanInfo createMBean(final String className,
                                        final MBeanConstructorInfo constructor,
                                        final List<MBeanAttributeInfo> attributes,
                                        final List<MBeanOperationInfo> operations) {
        final List<MBeanConstructorInfo> constList = Arrays.asList(new MBeanConstructorInfo[] { constructor });
        return createMBean(className, constList, attributes, operations);
    }

    // -------------------------------------------------------------------------------

    private static MBeanInfo createMBean(final String className,
                                         final List<MBeanConstructorInfo> constructors,
                                         final List<MBeanAttributeInfo> attributes,
                                         final List<MBeanOperationInfo> operations) {
        return new MBeanInfo(className,
                             className + " mbean info.",
                             attributes.toArray(new MBeanAttributeInfo[attributes.size()]),
                             constructors.toArray(new MBeanConstructorInfo[constructors.size()]),
                             operations.toArray(new MBeanOperationInfo[operations.size()]),
                             new MBeanNotificationInfo[] { new MBeanNotificationInfo(new String[] {}, "", "") });
    }

    private static AttributeInfo generateDynamicAttributeInfo(final Object bean) throws Exception {
        final Class<?> beanClass = bean.getClass();
        final Dynamic annotation = beanClass.getAnnotation(Dynamic.class);
        if (annotation == null) {
            return null;
        }
        final Method keyMethod = ReflectUtils.getMethodWithClassParams(true, beanClass, "get" + StringUtils.cap(annotation.key()), new Class<?>[] {});
        final Method getter = ReflectUtils.getMethodWithClassParams(true, beanClass, "get" + StringUtils.cap(annotation.value()), new Class<?>[] {});
        final Class<?> type = getter.getReturnType();
        final Method setter = ReflectUtils.getMethodWithClassParams(false, beanClass, "set" + StringUtils.cap(annotation.value()), new Class<?>[] { type });
        //
        final String name = (String) ReflectUtils.invoke(bean, keyMethod, new Object[] {});
        final AttributeInfo info = AttributeInfo.createDynamicAttributeInfo(name, getter, setter);
        return info;
    }

    private static MBeanConstructorInfo generateMBeanConstructorInfo(final Class<?> beanClass) {
        final Constructor<?> constructor = beanClass.getConstructors()[0];
        final MBeanConstructorInfo constInfo = new MBeanConstructorInfo(beanClass.getName() + " constructor.", constructor);
        return constInfo;
    }

    private static Map<String, AttributeInfo> generateAttributeInfo(final Object bean,
                                                                    final MBean beanConfig) throws Exception {
        final Class<?> beanClass = bean.getClass();
        final Map<String, AttributeInfo> attributes = new HashMap<String, AttributeInfo>();
        // Get dynamic attribute info
        final AttributeInfo dynamicInfo = generateDynamicAttributeInfo(bean);
        boolean dynamic = false;
        if (dynamicInfo != null) {
            attributes.put(dynamicInfo.getName(), dynamicInfo);
            dynamic = true;
        }
        // Get attribute info
        for (final Method m : beanClass.getMethods()) {
            final AttributeInfo info = BeanUtils.getAttributeInfo(m, beanConfig);
            if (info == null) {
                continue;
            }
            if (dynamic) {
                throw new RuntimeException("Dynamic beans [" + beanClass.getName() + "] can not have attributes !");
            }
            if (attributes.containsKey(info.getName())) {
                throw new RuntimeException("MBean [" + beanClass + "] already contains attribute with name [" + info.getName() + "] !");
            }
            attributes.put(info.getName(), info);
        }
        return attributes;
    }

    private static Map<String, OperationInfo> generateOperationInfo(final Class<?> beanClass) {
        Map<String, OperationInfo> infoMap = new Hashtable<String, OperationInfo>();

        final Method[] methods = beanClass.getMethods();
        for (final Method m : methods) {
            Operation operation = m.getAnnotation(Operation.class);
            if ((operation == null) || !Modifier.isPublic(m.getModifiers())) {
                continue;
            }
            final Annotation[][] annotations = m.getParameterAnnotations();
            final List<MBeanParameterInfo> params = new ArrayList<MBeanParameterInfo>();
            int param = 0;
            for (final Class<?> type : m.getParameterTypes()) {
                params.add(createParameter(annotations, params, param++, type));
            }
            String name = operation.name();
            name = (name.equals(Operation.DEF_STR)) ? m.getName() : name;
            String desc = operation.desc();
            desc = (desc.equals(Operation.DEF_STR)) ? name : desc;
            OperationInfo info = new OperationInfo(m, new MBeanOperationInfo(name,
                                                                             desc,
                                                                             params.toArray(new MBeanParameterInfo[params.size()]),
                                                                             m.getReturnType().getName(),
                                                                             MBeanOperationInfo.ACTION));
            infoMap.put(info.signature(), info);
        }
        // TODO : Add Export/Import
        // addStandartOperations(beanClass, infoMap);
        return infoMap;
    }

    private static MBeanParameterInfo createParameter(final Annotation[][] annotations,
                                                      final List<MBeanParameterInfo> params,
                                                      int index,
                                                      final Class<?> type) {
        final Parameter paramAnt = getParamAnnotation(annotations[index]);
        String name = "p" + index;
        String desc = name;
        if (paramAnt != null) {
            name = paramAnt.name().equals(Parameter.DEF_STR) ? name : paramAnt.name();
            desc = name;
            desc = paramAnt.desc().equals(Parameter.DEF_STR) ? desc : paramAnt.desc();
        }
        return new MBeanParameterInfo(name, type.getName(), desc);
    }

    private static void addStandartOperations(Class<?> beanClass,
                                              final Map<String, OperationInfo> infoMap) {
        if (BeanUtils.isDynamic(beanClass)) {
            return;
        }
        for (MBeanOperationInfo opr : DefaultOperations.getDefaultOperations()) {
            OperationInfo info = new OperationInfo(null, opr);
            infoMap.put(info.signature(), info);
        }
    }

    private static Parameter getParamAnnotation(final Annotation[] anns) {
        for (final Annotation a : anns) {
            if (a instanceof Parameter) {
                return (Parameter) a;
            }
        }
        return null;
    }

}
