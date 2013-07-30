package com.adenon.sp.configuration;

import com.adenon.sp.administration.ConfigLocation;
import com.adenon.sp.administration.annotate.MBean;

@MBean(location = ConfigLocation.SYSTEM, subLocation = "bean1=First Bean")
public class Bean1 {

    public void test() {
        for (int i = 1; i <= 50; i++) {
            System.out.println("<persistence-unit name=\"jpadb" + i + "\" transaction-type=\"RESOURCE_LOCAL\">");
            System.out.println("<!-- $$CLASSES$$ -->");
            System.out.println("</persistence-unit>");
        }

    }

    public static void main(String[] args) {
        new Bean1().test();
    }

}
