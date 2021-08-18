package com.skrivet.core.toolkit;

import org.springframework.core.env.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyToolkit {
    private PropertyToolkit() {
    }

    private static Iterator<PropertySource<?>> iterator(Environment environment) {
        if (!(environment instanceof ConfigurableEnvironment)) {
            throw new IllegalStateException("无法获取配置信息...");
        }
        ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
        MutablePropertySources mutablePropertySources = configurableEnvironment.getPropertySources();
        Iterator<PropertySource<?>> var2 = mutablePropertySources.iterator();
        return var2;
    }

    /**
     * 获取某个节点下面所有节点名称
     *
     * @param key         父节点
     * @param environment 环境
     * @return
     */
    public static Set<String> getChildNames(String key, Environment environment) {
        Set<String> sets = new HashSet<>();
        Iterator<PropertySource<?>> var2 = iterator(environment);
        while (var2.hasNext()) {
            PropertySource<?> propertySource = var2.next();
            if (propertySource instanceof EnumerablePropertySource) {
                EnumerablePropertySource propertySource2 = (EnumerablePropertySource) propertySource;
                for (String propertyName : propertySource2.getPropertyNames()) {
                    if (propertyName.startsWith(key + ".")) {
                        String name = propertyName.substring(key.length() + 1);
                        if (name.contains(".")) {
                            name = name.substring(0, name.indexOf("."));
                            sets.add(name);
                        } else {
                            sets.add(name);
                        }
                    }
                }
            }
        }
        return sets;
    }

    public static void main(String[] args) {
        String str = "skrivet.database[1]asd";
        Pattern pattern = Pattern.compile("^skrivet.database\\[\\d\\]*");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.err.println(matcher.group());
        }
    }

    public static int getChildListCount(String key, Environment environment) {
        Set<String> sets = new HashSet<>();
        Iterator<PropertySource<?>> var2 = iterator(environment);
        Pattern pattern = Pattern.compile("^" + key + "\\[\\d\\]*");
        while (var2.hasNext()) {
            PropertySource<?> propertySource = var2.next();
            if (propertySource instanceof EnumerablePropertySource) {
                EnumerablePropertySource propertySource2 = (EnumerablePropertySource) propertySource;
                for (String propertyName : propertySource2.getPropertyNames()) {
                    Matcher matcher = pattern.matcher(propertyName);
                    while (matcher.find()) {
                        sets.add(matcher.group());
                    }
                }
            }
        }
        return sets.size();
    }

    public static boolean containsKey(String key, Environment environment) {
        Iterator<PropertySource<?>> var2 = iterator(environment);
        while (var2.hasNext()) {
            PropertySource<?> propertySource = var2.next();
            if (propertySource instanceof EnumerablePropertySource) {
                EnumerablePropertySource propertySource2 = (EnumerablePropertySource) propertySource;
                for (String propertyName : propertySource2.getPropertyNames()) {
                    if (propertyName.equals(key) || propertyName.startsWith(key + ".")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
