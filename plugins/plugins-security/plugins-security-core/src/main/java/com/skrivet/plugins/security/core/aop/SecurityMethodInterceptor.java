package com.skrivet.plugins.security.core.aop;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.account.AccountNotLoginExp;
import com.skrivet.plugins.security.core.annotations.SecurityIgnore;
import com.skrivet.plugins.security.core.aop.handler.SecurityHandler;
import com.skrivet.plugins.security.core.enums.SecurityType;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import java.lang.annotation.Annotation;

import java.lang.reflect.Parameter;
import java.util.List;

import static com.skrivet.plugins.security.core.enums.SecurityType.SERVICE;

public class SecurityMethodInterceptor implements MethodInterceptor {
    private List<SecurityHandler> handlerList;
    private SecurityType securityType;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public SecurityMethodInterceptor(SecurityType securityType, List<SecurityHandler> handlerList) {
        this.securityType = securityType;
        this.handlerList = handlerList;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //执行鉴权逻辑
        String[] paramNames = new DefaultParameterNameDiscoverer().getParameterNames(methodInvocation.getMethod());
        logger.debug("start check permission,the type is:{},class:{},method:{},paramNames:{}", securityType, methodInvocation.getThis().getClass().getName(), methodInvocation.getMethod().getName(), paramNames);
        boolean ignoreSecurityCheck = false;
        for (Class<? extends Annotation> clz : AbstractSecurityAdvisor.AUTHZ_ANNOTATION_CLASSES) {
            if (ignoreSecurityCheck) {
                break;
            }
            Annotation a = AnnotationUtils.findAnnotation(methodInvocation.getMethod(), clz);
            if (a != null) {
                LoginUser loginUser = null;
                if (securityType == SERVICE) {
                    for (int i = 0; i < methodInvocation.getMethod().getParameterCount(); i++) {
                        Parameter p = methodInvocation.getMethod().getParameters()[i];
                        Class<?> parameterType = methodInvocation.getMethod().getParameterTypes()[i];
                        if (LoginUser.class.isAssignableFrom(parameterType)) {
                            if (p.getAnnotation(SecurityIgnore.class) != null) {
                                continue;
                            }
                            Object parameter = methodInvocation.getArguments()[i];
                            if (parameter != null) {
                                loginUser = (LoginUser) parameter;
                                break;
                            }
                        }
                    }
                    if (loginUser == null) {
                        throw new AccountNotLoginExp();
                    }
                    if (loginUser != null) {
                        ignoreSecurityCheck = loginUser.isIgnoreSecurityCheck();
                    }
                }
                if (!ignoreSecurityCheck) {
                    for (SecurityHandler handler : handlerList) {
                        if (handler.support(clz)) {
                            handler.check(loginUser, a);
                        }
                    }
                }
            }
        }
        return methodInvocation.proceed();
    }


}
