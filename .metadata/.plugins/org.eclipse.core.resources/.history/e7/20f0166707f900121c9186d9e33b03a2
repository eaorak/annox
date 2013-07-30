package com.adenon.sp.administration.interceptors;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

public class ValidationInterceptor implements BeanInterceptor {

    private final Validator validator;

    public ValidationInterceptor() {
        HibernateValidatorConfiguration config = Validation.byProvider(HibernateValidator.class).configure();
        ValidatorFactory factory = config.buildValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Override
    public void invoke(Call call) throws Exception {
        if (!call.isSetterMethod()) {
            return;
        }
        Object bean = call.beanInfo().bean();
        Set<ConstraintViolation<Object>> violations = this.validator.validate(bean);
        if (violations.size() > 0) {
            StringBuilder error = new StringBuilder();
            for (ConstraintViolation<Object> v : violations) {
                error.append(v.getMessage()).append("! ");
            }
            throw new Exception(error.toString());
        }
    }

    @Override
    public void fail(Call call) {
    }

}
