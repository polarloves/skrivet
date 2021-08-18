package com.skrivet.plugins.web.impl.resolver;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.plugins.security.core.entity.User;
import com.skrivet.plugins.security.core.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private SecurityService securityService;

    public LoginUserArgumentResolver(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.getParameterType() == LoginUser.class) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        User user = securityService.currentUser();
        if (user != null) {
            LoginUser loginUser = new LoginUser();
            loginUser.setAccount(user.getAccount());
            loginUser.setId(user.getId());
            loginUser.setPermissions(user.getPermissions());
            loginUser.setRoles(user.getRoles());
            return loginUser;
        }else {
            return LoginUser.EMPTY;
        }
    }
}
