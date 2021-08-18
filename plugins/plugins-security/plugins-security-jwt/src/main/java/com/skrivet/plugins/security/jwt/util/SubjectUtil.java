package com.skrivet.plugins.security.jwt.util;

import com.skrivet.components.jwt.subject.Action;
import com.skrivet.components.jwt.subject.Subject;
import com.skrivet.plugins.security.jwt.filter.JWTFilter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

public class SubjectUtil {
    public static ThreadLocal<Subject> subjectThreadLocal = new ThreadLocal<>();

    public static Subject getSubject() {
        return subjectThreadLocal.get();
    }

    public static void bindSubject(Subject subject) {
        subjectThreadLocal.set(subject);
    }

    public static void noticeSubjectChanged(Action action) {
        if (subjectThreadLocal.get() != null) {
            subjectThreadLocal.get().setAction(action);
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            Subject subject = subjectThreadLocal.get();
            if (subject != null && subject.getAction() != null) {
                String actionName = subject.getAction().getName();
                response.setHeader("Access-Control-Expose-Headers", JWTFilter.tokenKey + "," + JWTFilter.actionKey);
                response.setHeader(JWTFilter.tokenKey, subject.getToken());
                response.setHeader(JWTFilter.actionKey, actionName);
            }
        }
    }

    public static void noticeSubjectChanged(Action action, HttpServletResponse response) {
        if (subjectThreadLocal.get() != null) {
            subjectThreadLocal.get().setAction(action);
            Subject subject = subjectThreadLocal.get();
            if (subject != null && subject.getAction() != null) {
                String actionName = subject.getAction().getName();
                response.setHeader("Access-Control-Expose-Headers", JWTFilter.tokenKey + "," + JWTFilter.actionKey);
                if (subject.getToken() != null) {
                    response.setHeader(JWTFilter.tokenKey, subject.getToken());
                }
                response.setHeader(JWTFilter.actionKey, actionName);
            }
        }
    }

    public static void clear() {
        if (subjectThreadLocal.get() != null) {
            subjectThreadLocal.remove();
        }
    }
}
