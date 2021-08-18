package com.skrivet.components.mybatis.builder;

import com.skrivet.core.toolkit.PropertyToolkit;
import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.loader.ProxyFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.session.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;


public class YmlBuilder extends BaseBuilder {
    public static final String DATABASE_PREFIX = "skrivet.database";
    private Environment environment;
    private String name;
    private final ReflectorFactory localReflectorFactory;

    public YmlBuilder(Properties props, Environment environment, String name) {
        super(new Configuration());
        this.localReflectorFactory = new DefaultReflectorFactory();
        ErrorContext.instance().resource("SQL Mapper Configuration");
        this.configuration.setVariables(props);
        this.name = name;
        this.environment = environment;
    }

    public Configuration parse() {
        this.parseConfiguration();
        return this.configuration;
    }

    private void parseConfiguration() {
        try {
            this.propertiesElement("properties");
            Properties settings = this.settingsAsProperties("settings");
            this.loadCustomVfs(settings);
            this.loadCustomLogImpl(settings);
            this.pluginElement("plugins");
            this.settingsElement(settings);
            this.typeHandlerElement("typeHandlers");
        } catch (Exception var3) {
            throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + var3, var3);
        }
    }

    private void settingsElement(Properties props) {
        this.configuration.setAutoMappingBehavior(AutoMappingBehavior.valueOf(props.getProperty("autoMappingBehavior", "PARTIAL")));
        this.configuration.setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.valueOf(props.getProperty("autoMappingUnknownColumnBehavior", "NONE")));
        this.configuration.setCacheEnabled(this.booleanValueOf(props.getProperty("cacheEnabled"), true));
        this.configuration.setProxyFactory((ProxyFactory) this.createInstance(props.getProperty("proxyFactory")));
        this.configuration.setLazyLoadingEnabled(this.booleanValueOf(props.getProperty("lazyLoadingEnabled"), false));
        this.configuration.setAggressiveLazyLoading(this.booleanValueOf(props.getProperty("aggressiveLazyLoading"), false));
        this.configuration.setMultipleResultSetsEnabled(this.booleanValueOf(props.getProperty("multipleResultSetsEnabled"), true));
        this.configuration.setUseColumnLabel(this.booleanValueOf(props.getProperty("useColumnLabel"), true));
        this.configuration.setUseGeneratedKeys(this.booleanValueOf(props.getProperty("useGeneratedKeys"), false));
        this.configuration.setDefaultExecutorType(ExecutorType.valueOf(props.getProperty("defaultExecutorType", "SIMPLE")));
        this.configuration.setDefaultStatementTimeout(this.integerValueOf(props.getProperty("defaultStatementTimeout"), (Integer) null));
        this.configuration.setDefaultFetchSize(this.integerValueOf(props.getProperty("defaultFetchSize"), (Integer) null));
        this.configuration.setDefaultResultSetType(this.resolveResultSetType(props.getProperty("defaultResultSetType")));
        this.configuration.setMapUnderscoreToCamelCase(this.booleanValueOf(props.getProperty("mapUnderscoreToCamelCase"), false));
        this.configuration.setSafeRowBoundsEnabled(this.booleanValueOf(props.getProperty("safeRowBoundsEnabled"), false));
        this.configuration.setLocalCacheScope(LocalCacheScope.valueOf(props.getProperty("localCacheScope", "SESSION")));
        this.configuration.setJdbcTypeForNull(JdbcType.valueOf(props.getProperty("jdbcTypeForNull", "OTHER")));
        this.configuration.setLazyLoadTriggerMethods(this.stringSetValueOf(props.getProperty("lazyLoadTriggerMethods"), "equals,clone,hashCode,toString"));
        this.configuration.setSafeResultHandlerEnabled(this.booleanValueOf(props.getProperty("safeResultHandlerEnabled"), true));
        this.configuration.setDefaultScriptingLanguage(this.resolveClass(props.getProperty("defaultScriptingLanguage")));
        this.configuration.setDefaultEnumTypeHandler(this.resolveClass(props.getProperty("defaultEnumTypeHandler")));
        this.configuration.setCallSettersOnNulls(this.booleanValueOf(props.getProperty("callSettersOnNulls"), false));
        this.configuration.setUseActualParamName(this.booleanValueOf(props.getProperty("useActualParamName"), true));
        this.configuration.setReturnInstanceForEmptyRow(this.booleanValueOf(props.getProperty("returnInstanceForEmptyRow"), false));
        this.configuration.setLogPrefix(props.getProperty("logPrefix"));
        this.configuration.setConfigurationFactory(this.resolveClass(props.getProperty("configurationFactory")));
    }

    private void propertiesElement(String name) throws Exception {
        if (PropertyToolkit.containsKey(DATABASE_PREFIX + "." + this.name + ".mybatis." + name, environment)) {
            Properties defaults = getChildProperties(name);
            Properties vars = this.configuration.getVariables();
            if (vars != null) {
                defaults.putAll(vars);
            }
            this.configuration.setVariables(defaults);
        }
    }

    private Properties getChildProperties(String name) {
        Set<String> names = PropertyToolkit.getChildNames(DATABASE_PREFIX + "." + this.name + ".mybatis." + name, environment);
        Properties defaults = new Properties();
        for (String tmp : names) {
            defaults.put(tmp, environment.getProperty(DATABASE_PREFIX + "." + this.name + ".mybatis." + name + "." + tmp));
        }
        return defaults;
    }

    private Properties settingsAsProperties(String name) {
        Properties defautProps = new Properties();
        defautProps.put("cacheEnabled", "true");
        defautProps.put("lazyLoadingEnabled", "true");
        defautProps.put("aggressiveLazyLoading", "true");
        defautProps.put("multipleResultSetsEnabled", "true");
        defautProps.put("useColumnLabel", "true");
        defautProps.put("useGeneratedKeys", "true");
        defautProps.put("autoMappingBehavior", "PARTIAL");
        defautProps.put("defaultExecutorType", "SIMPLE");
        defautProps.put("mapUnderscoreToCamelCase", "true");
        defautProps.put("localCacheScope", "SESSION");
        defautProps.put("jdbcTypeForNull", "NULL");
        defautProps.put("logImpl", "STDOUT_LOGGING");
        if (PropertyToolkit.containsKey(DATABASE_PREFIX + "." + this.name + ".mybatis." + name, environment)) {
            Properties props = getChildProperties(name);
            for (Object key : defautProps.keySet()) {
                //将其与默认值合并合并
                if (!props.containsKey(key)) {
                    props.put(key,defautProps.get(key));
                }
            }
            MetaClass metaConfig = MetaClass.forClass(Configuration.class, this.localReflectorFactory);
            Iterator var4 = props.keySet().iterator();
            Object key;
            do {
                if (!var4.hasNext()) {
                    return props;
                }
                key = var4.next();
            } while (metaConfig.hasSetter(String.valueOf(key)));
            return props;
        } else {
            return defautProps;
        }
    }

    private void loadCustomVfs(Properties props) throws ClassNotFoundException {
        String value = props.getProperty("vfsImpl");
        if (value != null) {
            String[] clazzes = value.split(",");
            String[] var4 = clazzes;
            int var5 = clazzes.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String clazz = var4[var6];
                if (!clazz.isEmpty()) {
                    Class<? extends VFS> vfsImpl = (Class<? extends VFS>) Resources.classForName(clazz);
                    this.configuration.setVfsImpl(vfsImpl);
                }
            }
        }

    }

    private void loadCustomLogImpl(Properties props) {
        Class<? extends Log> logImpl = this.resolveClass(props.getProperty("logImpl"));
        this.configuration.setLogImpl(logImpl);
    }

    private void pluginElement(String name) throws Exception {
        if (PropertyToolkit.containsKey(DATABASE_PREFIX + "." + this.name + ".mybatis." + name, environment)) {
            Properties props = getChildProperties(name);
            for (Object tmp : props.values()) {
                Interceptor interceptorInstance = (Interceptor) this.resolveClass(tmp.toString()).getDeclaredConstructor().newInstance();
                this.configuration.addInterceptor(interceptorInstance);
            }
        } else {
            //注册默认的拦截器
            Interceptor interceptorInstance = (Interceptor) this.resolveClass("com.skrivet.plugins.database.mybatis.interceptor.PageInterceptor").getDeclaredConstructor().newInstance();
            this.configuration.addInterceptor(interceptorInstance);
        }
    }

    private void typeHandlerElement(String name) {
        if (PropertyToolkit.containsKey(DATABASE_PREFIX + "." + this.name + ".mybatis." + name, environment)) {
            Set<String> names = PropertyToolkit.getChildNames(DATABASE_PREFIX + "." + this.name + ".mybatis." + name, environment);
            if (!CollectionUtils.isEmpty(names)) {
                for (String tmp : names) {
                    String key = DATABASE_PREFIX + "." + this.name + ".mybatis." + name + "." + tmp;
                    String typeHandlerPackage = environment.getProperty(key + ".package");
                    if (!StringUtils.isEmpty(typeHandlerPackage)) {
                        this.typeHandlerRegistry.register(typeHandlerPackage);
                        continue;
                    }
                    typeHandlerPackage = environment.getProperty(key + ".javaType");
                    String jdbcTypeName = environment.getProperty(key + ".jdbcType");
                    String handlerTypeName = environment.getProperty(key + ".handler");
                    Class<?> javaTypeClass = this.resolveClass(typeHandlerPackage);
                    JdbcType jdbcType = this.resolveJdbcType(jdbcTypeName);
                    Class<?> typeHandlerClass = this.resolveClass(handlerTypeName);
                    if (javaTypeClass != null) {
                        if (jdbcType == null) {
                            this.typeHandlerRegistry.register(javaTypeClass, typeHandlerClass);
                        } else {
                            this.typeHandlerRegistry.register(javaTypeClass, jdbcType, typeHandlerClass);
                        }
                    } else {
                        this.typeHandlerRegistry.register(typeHandlerClass);
                    }
                }
            }
        } else {
            this.typeHandlerRegistry.register("com.skrivet.plugins.database.mybatis.handler");
        }
    }
}
