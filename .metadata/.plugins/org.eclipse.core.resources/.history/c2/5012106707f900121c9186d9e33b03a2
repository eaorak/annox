package com.adenon.sp.administration.interceptors;

import com.adenon.sp.administration.util.ReflectUtils;

public abstract class PropertyChangeInterceptor implements BeanInterceptor {

    public PropertyChangeInterceptor() {
    }

    @Override
    public void invoke(Call call) throws Exception {
        if (call.isSetterMethod()) {
            this.propertyChanged(call, ReflectUtils.toAttr(call.method().getName()), call.args()[0]);
        }
    }

    @Override
    public void fail(Call call) {
        if (call.isSetterMethod()) {
            this.propertyChangeFail(call, ReflectUtils.toAttr(call.method().getName()), call.args()[0]);
        }
    }

    protected abstract void propertyChanged(Call bean,
                                            String attr,
                                            Object newValue);

    protected abstract void propertyChangeFail(Call bean,
                                               String attr,
                                               Object object);
}
