package com.skrivet.plugins.database.mybatis.interceptor;

import com.skrivet.core.common.exception.NotSupportExp;
import com.skrivet.plugins.database.mybatis.annotations.Page;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PageInterceptor implements Interceptor {
    private String databaseTypeKey = "_databaseType";

    private String buildLikePart(String group, String type) {
        Pattern pattern = Pattern.compile("'[^']*'");
        Matcher matcher = pattern.matcher(group);
        String args[] = new String[2];
        int i = 0;
        while (matcher.find()) {
            args[i] = matcher.group();
            i++;
        }
        switch (type) {
            case "sqlServer":
                return args[0] + "+?+" + args[1];
            default:
                return "concat(" + args[0] + ",?," + args[1] + ")";
        }
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        if (!Proxy.isProxyClass(statementHandler.getClass())) {
            boolean changed = false;

            MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            String id = mappedStatement.getId();
            Configuration configuration = (Configuration) metaObject.getValue("delegate.configuration");
            String type = configuration.getVariables().getProperty(databaseTypeKey, "mysql");
            BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
            String sql = boundSql.getSql();
            //处理like语句
            Pattern pattern = Pattern.compile("SKRIVET_LIKE\\s*\\(\\s*'[^']*'\\s*,\\s*\\?\\s*,\\s*'[^']*'\\s*\\)");
            Matcher matcher = pattern.matcher(sql);
            StringBuffer stringBuffer = new StringBuffer();
            while (matcher.find()) {
                changed = true;
                String group = matcher.group();
                String sqlPart = buildLikePart(group, type);
                matcher.appendReplacement(stringBuffer, sqlPart);
            }
            matcher.appendTail(stringBuffer);
            sql = stringBuffer.toString();
            String clzName = id.substring(0, id.lastIndexOf("."));
            String methodName = id.substring(id.lastIndexOf(".") + 1);
            Class cls = Class.forName(clzName);
            Method method = BeanUtils.findMethodWithMinimalParameters(cls, methodName);
            Page page = method.getAnnotation(Page.class);
            if (page != null) {
                Page.Type[] types = page.supports();
                boolean matches = false;
                for (Page.Type tp : types) {
                    if (tp.getCode().equals(type)) {
                        matches = true;
                        break;
                    }
                }
                if (matches) {
                    changed = true;
                    //找到页码
                    String startValue = parseValue(boundSql.getParameterObject(), page.start());
                    String endValue = parseValue(boundSql.getParameterObject(), page.offset());
                    switch (type) {
                        case "sqlServer":
                            throw new NotSupportExp().variable( "sqlServer");
                        case "oracle":
                            sql = " SELECT * FROM (" +
                                    "  select rownum rowsize,innertable.* from" +
                                    " (" + sql + ") innertable )" +
                                    "WHERE rowsize > " + startValue + " and rowsize <= " + endValue;
                            break;
                        default:
                            sql = sql + " " + "limit " + startValue + "," + endValue;
                            break;
                    }
                }
            }
            if (changed) {
                metaObject.setValue("delegate.boundSql.sql", sql);
            }
        }

        return invocation.proceed();
    }

    private String parseValue(Object object, String key) throws Exception {
        if (key.contains("()")) {
            //表示为方法
            Method method = BeanUtils.findMethodWithMinimalParameters(object.getClass(), key.substring(0, key.indexOf("(")));
            return method.invoke(object).toString();
        }
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(object.getClass(), key);
        return propertyDescriptor.getReadMethod().invoke(object).toString();
    }

    @Override
    public void setProperties(Properties properties) {

    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

}
