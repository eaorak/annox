package com.adenon.sp.administration.util;

import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

public class DefaultOperations {

    public static List<MBeanOperationInfo> getDefaultOperations() {
        List<MBeanOperationInfo> list = new ArrayList<MBeanOperationInfo>();
        //
        MBeanParameterInfo parameterPath = new MBeanParameterInfo("File path", String.class.getName(), "Path of the configuration file to import");
        MBeanOperationInfo mbeanInfo = new MBeanOperationInfo("Export",
                                                              "Export",
                                                              new MBeanParameterInfo[] { parameterPath },
                                                              String.class.getName(),
                                                              MBeanOperationInfo.ACTION);
        list.add(mbeanInfo);
        //
        return list;
    }
}
