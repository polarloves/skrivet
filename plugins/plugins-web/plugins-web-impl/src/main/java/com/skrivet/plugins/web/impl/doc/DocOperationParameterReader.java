package com.skrivet.plugins.web.impl.doc;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.skrivet.core.common.entity.BasicEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.EnumTypeDeterminer;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.readers.parameter.ExpansionContext;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Predicates.not;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.Collections.isContainerType;
import static springfox.documentation.schema.Maps.isMapType;
import static springfox.documentation.schema.Types.isBaseType;
import static springfox.documentation.schema.Types.typeNameFor;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class DocOperationParameterReader implements OperationBuilderPlugin, ApplicationContextAware {
    @Value("${skrivet.security.type:shiro}")
    private String securityType;
    private final ModelAttributeParameterExpander expander;
    private final EnumTypeDeterminer enumTypeDeterminer;
    private ApplicationContext applicationContext;
    private boolean initialized = false;
    private PathMatcher pathMatcher = new AntPathMatcher();
    private Map<String, List<String>> sources = null;
    private Class<Annotation> classes[] = new Class[3];
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DocumentationPluginsManager pluginsManager;
    @Value("${skrivet.security.webCheck:true}")
    private boolean check;

    public synchronized void init() {
        int i = 0;
        String[] clzNames = new String[]{"com.skrivet.plugins.security.core.annotations.RequiresUser", "com.skrivet.plugins.security.core.annotations.RequiresRoles", "com.skrivet.plugins.security.core.annotations.RequiresPermissions"};
        for (String name : clzNames) {
            try {
                Class<Annotation> a = (Class<Annotation>) Class.forName(name);
                classes[i] = a;
            } catch (ClassNotFoundException e) {

            }
            i++;
        }

        // 从数据库读取资源权限列表
        String resourceServiceName = "com.skrivet.plugins.security.core.service.ResourceService";
        try {
            //判断在数据库中是否存在....
            Class<?> a = Class.forName(resourceServiceName);
            Object resourceService = applicationContext.getBean(a);
            Method method = a.getMethod("resourcePermissions");
            sources = (Map<String, List<String>>) method.invoke(resourceService);
        } catch (ClassNotFoundException e) {
            logger.info("this doc have't use permission, it will not check permission...");
        } catch (BeansException beansException) {
            logger.info("no bean named resourceService, the permission info in database will not work!");
        } catch (Exception e) {
            logger.warn("init failed,error:{}", e);
        }
        initialized = true;
    }

    @Autowired
    public DocOperationParameterReader(
            ModelAttributeParameterExpander expander,
            EnumTypeDeterminer enumTypeDeterminer) {
        this.expander = expander;
        this.enumTypeDeterminer = enumTypeDeterminer;
    }

    @Override
    public void apply(OperationContext context) {
        if ((!initialized) && check) {
            init();
        }
        if (securityType.equals("jwt") && check) {
            boolean added = false;
            for (Class<Annotation> a : classes) {
                if (a != null) {
                    if (context.findAnnotation(a).isPresent()) {
                        added = true;
                        context.operationBuilder().parameters(context.getGlobalOperationParameters());
                        break;
                    }
                }
            }
            if (!CollectionUtils.isEmpty(sources) && !added) {
                for (String path : sources.keySet()) {
                    if (pathMatcher.match(path, context.requestMappingPattern())) {
                        context.operationBuilder().parameters(context.getGlobalOperationParameters());
                        break;
                    }
                }
            }

        }
        context.operationBuilder().parameters(readParameters(context));
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    private List<Parameter> readParameters(final OperationContext context) {

        List<ResolvedMethodParameter> methodParameters = context.getParameters();
        List<Parameter> parameters = newArrayList();

        for (ResolvedMethodParameter methodParameter : methodParameters) {
            ResolvedType alternate = context.alternateFor(methodParameter.getParameterType());
            if (!shouldIgnore(methodParameter, alternate, context.getIgnorableParameterTypes())) {

                ParameterContext parameterContext = new ParameterContext(methodParameter,
                        new ParameterBuilder(),
                        context.getDocumentationContext(),
                        context.getGenericsNamingStrategy(),
                        context);

                if (shouldExpand(methodParameter, alternate)) {
                    parameters.addAll(
                            expander.expand(
                                    new ExpansionContext("", alternate, context)));
                } else {
                    parameters.add(pluginsManager.parameter(parameterContext));
                }
            }
        }
        return FluentIterable.from(parameters).filter(not(hiddenParams())).toList();
    }

    private Predicate<Parameter> hiddenParams() {
        return new Predicate<Parameter>() {
            @Override
            public boolean apply(Parameter input) {
                return input.isHidden();
            }
        };
    }

    private boolean shouldIgnore(
            final ResolvedMethodParameter parameter,
            ResolvedType resolvedParameterType,
            final Set<Class> ignorableParamTypes) {

        if (ignorableParamTypes.contains(resolvedParameterType.getErasedType())) {
            return true;
        }
        return FluentIterable.from(ignorableParamTypes)
                .filter(isAnnotation())
                .filter(parameterIsAnnotatedWithIt(parameter)).size() > 0;

    }

    private Predicate<Class> parameterIsAnnotatedWithIt(final ResolvedMethodParameter parameter) {
        return new Predicate<Class>() {
            @Override
            public boolean apply(Class input) {
                return parameter.hasParameterAnnotation(input);
            }
        };
    }

    private Predicate<Class> isAnnotation() {
        return new Predicate<Class>() {
            @Override
            public boolean apply(Class input) {
                return Annotation.class.isAssignableFrom(input);
            }
        };
    }

    private boolean shouldExpand(final ResolvedMethodParameter parameter, ResolvedType resolvedParamType) {
        return !parameter.hasParameterAnnotation(RequestBody.class)
                && !parameter.hasParameterAnnotation(RequestPart.class)
                && !parameter.hasParameterAnnotation(RequestParam.class)
                && !parameter.hasParameterAnnotation(PathVariable.class)
                && !isBaseType(typeNameFor(resolvedParamType.getErasedType()))
                && !enumTypeDeterminer.isEnum(resolvedParamType.getErasedType())
                && !isContainerType(resolvedParamType)
                && !isMapType(resolvedParamType);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
