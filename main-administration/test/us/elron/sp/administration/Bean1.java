package us.elron.sp.administration;

import us.elron.sp.administration.ConfigLocation;
import us.elron.sp.administration.annotate.MBean;

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
