package com.skrivet.plugins.web.impl.controller;

import com.skrivet.core.common.convert.EntityConvert;
import com.skrivet.core.toolkit.DateUtils;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.security.core.annotations.RequiresRoles;
import com.skrivet.plugins.security.core.annotations.RequiresUser;
import com.skrivet.plugins.security.core.enums.Logical;
import com.skrivet.web.core.annotations.AuthMappingIgnore;
import com.skrivet.web.core.entity.AuthMapping;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 基础的Controller类。
 *
 * @author PolarLoves
 */
@Validated
public abstract class BasicController implements InitializingBean {
    @Value("${skrivet.project.name}")
    public String projectName;
    @Autowired
    public EntityConvert entityConvert;
    /**
     * 日志记录器
     **/
    public final Logger logger = LoggerFactory.getLogger(getClass());
    private static final Class<? extends Annotation>[] METHOD_MAPPING_ANNOTATIONS =
            new Class[]{PutMapping.class, GetMapping.class, RequestMapping.class,
                    DeleteMapping.class, PatchMapping.class, PostMapping.class};
    public static final Class<? extends Annotation>[] AUTHZ_ANNOTATION_CLASSES =
            new Class[]{RequiresPermissions.class, RequiresRoles.class, RequiresUser.class};


    private List<AuthMapping> cache;

    /**
     * 初始化数据绑定 1. 将所有传递进来的String进行HTML编码，防止XSS攻击 2. 将字段中Date类型转换为String类型
     *
     * @param binder spring的写法
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
        //binder.setValidator(validator);
    }




    public List<AuthMapping> authMappingList() {
        return cache;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = new ArrayList<>();
        Class<?> clz = getClass();
        for (Method method : clz.getDeclaredMethods()) {
            if (method.getAnnotation(AuthMappingIgnore.class) != null) {
                continue;
            }
            if (containsAnnotation(method, METHOD_MAPPING_ANNOTATIONS)) {
                AuthMapping authMapping = new AuthMapping();
                authMapping.setName(method.getName());
                if (containsAnnotation(method, AUTHZ_ANNOTATION_CLASSES)) {
                    authMapping.setRequireLogin(true);
                    RequiresPermissions requiresPermissions = AnnotationUtils.findAnnotation(method, RequiresPermissions.class);
                    if (requiresPermissions != null) {
                        authMapping.setPermissions(requiresPermissions.value());
                        authMapping.setPermissionLogical(requiresPermissions.logical() == Logical.AND ? "and" : "or");
                    }
                    RequiresRoles requiresRoles = AnnotationUtils.findAnnotation(method, RequiresRoles.class);
                    if (requiresRoles != null) {
                        authMapping.setRoles(requiresRoles.value());
                        authMapping.setRoleLogical(requiresRoles.logical() == Logical.AND ? "and" : "or");
                    }
                } else {
                    authMapping.setRequireLogin(false);
                }
                cache.add(authMapping);
            }

        }
    }


    protected boolean containsAnnotation(Method method, Class<? extends Annotation>[] classes) {
        for (Class<? extends Annotation> clz : classes) {
            Annotation a = AnnotationUtils.findAnnotation(method, clz);
            if (a != null) {
                return true;
            }
        }
        return false;
    }
}
