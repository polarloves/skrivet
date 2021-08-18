package com.skrivet.components.shiro.session;

import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionIdCookie extends SimpleCookie {
    @Override
    public String readValue(HttpServletRequest request, HttpServletResponse ignored) {
        String value = super.readValue(request, ignored);
        if (StringUtils.isEmpty(value)) {
            //读取sessionId
            String headerValue = request.getHeader(getName());
            if (!StringUtils.isEmpty(headerValue)) {
                // 从头文件中读取
                return headerValue;
            }
        }
        return value;
    }
}
