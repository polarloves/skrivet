package com.skrivet.plugins.security.core.aop;

import com.skrivet.core.common.aop.AbstractAnnotationAdvisor;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.security.core.annotations.RequiresRoles;
import com.skrivet.plugins.security.core.annotations.RequiresUser;
import com.skrivet.plugins.security.core.enums.SecurityType;
import com.skrivet.plugins.security.core.service.PermissionSetService;
import com.skrivet.plugins.security.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.annotation.AnnotationUtils;
import com.skrivet.plugins.security.core.aop.handler.SecurityHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractSecurityAdvisor extends AbstractAnnotationAdvisor {
    public static final Class<? extends Annotation>[] AUTHZ_ANNOTATION_CLASSES = new Class[]{RequiresPermissions.class, RequiresRoles.class, RequiresUser.class};
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public AbstractSecurityAdvisor() {
        setAdvice(new SecurityMethodInterceptor(securityType(), handlers()));
    }

    protected abstract SecurityType securityType();

    public abstract boolean isClassMatches(Class<?> aClass);

    public abstract List<SecurityHandler> handlers();

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return !isProxy(aClass) && containsAnnotation(method, AUTHZ_ANNOTATION_CLASSES) && isClassMatches(aClass);
    }

}
