package us.elron.sp.administration;

import us.elron.sp.administration.annotate.MBean;

@MBean(parent = Bean1.class, subLocation = "bean2=Second Bean")
public class Bean2 {

}
