package com.skrivet.plugins.service.core.aop;

import com.skrivet.core.common.annotations.ValidateIgnore;
import com.skrivet.core.common.aop.Order;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Method;

public class MethodValidationPostProcessor extends org.springframework.validation.beanvalidation.MethodValidationPostProcessor {
    @Nullable
    private Validator validator;

    public void setValidator(Validator validator) {
        if (validator instanceof LocalValidatorFactoryBean) {
            this.validator = ((LocalValidatorFactoryBean) validator).getValidator();
        } else if (validator instanceof SpringValidatorAdapter) {
            this.validator = validator.unwrap(Validator.class);
        } else {
            this.validator = validator;
        }
    }


    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validator = validatorFactory.getValidator();
    }

    @Override
    public void afterPropertiesSet() {
        Pointcut pointcut = new Pointcut() {
            @Override
            public ClassFilter getClassFilter() {
                return aClass ->
                        (!AnnotatedElementUtils.hasAnnotation(aClass, ValidateIgnore.class))
                                && (AnnotatedElementUtils.hasAnnotation(aClass, Service.class) || AnnotatedElementUtils.hasAnnotation(aClass, Validated.class));

            }

            @Override
            public MethodMatcher getMethodMatcher() {
                return new MethodMatcher() {
                    @Override
                    public boolean matches(Method method, Class<?> aClass) {
                        return method.getAnnotation(ValidateIgnore.class) == null;
                    }

                    @Override
                    public boolean isRuntime() {
                        return false;
                    }

                    @Override
                    public boolean matches(Method method, Class<?> aClass, Object... objects) {
                        throw new UnsupportedOperationException("Illegal MethodMatcher usage");
                    }
                };
            }
        };
        DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor(pointcut, createMethodValidationAdvice(this.validator));
        defaultPointcutAdvisor.setOrder(Order.VALIDATE_SERVICE);
        this.advisor = defaultPointcutAdvisor;
    }
}
