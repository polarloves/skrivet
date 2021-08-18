package com.skrivet.plugins.service.core.aop;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.core.common.exception.ValidationExp;
import com.skrivet.core.toolkit.CollectionUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;

import javax.validation.ConstraintViolationException;

public class ServiceExceptionMethodInterceptor implements MethodInterceptor {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        logger.debug("service intercept start ,class:{},method:{}", methodInvocation.getThis().getClass().getName(), methodInvocation.getMethod().getName());
        try {
            return methodInvocation.proceed();
        } catch (ConstraintViolationException e) {
            throw new ValidationExp(CollectionUtils.asString(e.getConstraintViolations(), o1 -> {
                return o1.getMessage();
            }));
        } catch (Throwable throwable) {
            throw new BizExp(Code.SERVER_UNKNOWN.getCode()).copyStackTrace(throwable);
        }
    }
}
