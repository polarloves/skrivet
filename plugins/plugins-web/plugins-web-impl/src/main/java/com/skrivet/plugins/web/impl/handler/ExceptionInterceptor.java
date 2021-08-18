package com.skrivet.plugins.web.impl.handler;

import com.skrivet.plugins.security.core.holder.FilterHolder;
import com.skrivet.plugins.security.core.service.SecurityService;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExceptionInterceptor implements HandlerInterceptor {
    private SecurityService securityService;

    public ExceptionInterceptor(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (FilterHolder.getException() != null) {
            throw FilterHolder.getException();
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            Exception exception = (Exception) session.getAttribute(FilterHolder.EXCEPTION_KEY);
            if (exception != null) {
                securityService.logout();
                throw exception;
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        FilterHolder.clear();
    }
}
