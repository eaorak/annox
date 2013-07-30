package us.elron.sp.kernel.utils.assist;

import java.lang.reflect.Method;


public interface IAdviceListener {

    boolean addFor(Advice advice,
                   Method m,
                   String code);

}
