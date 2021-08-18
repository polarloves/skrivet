package com.skrivet.core.common.sacnner;

import com.skrivet.core.common.io.ResolverUtil;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 * 类扫描器
 */
public class PackageScanner {
    /**
     * 扫描指定路径，获取class
     *
     * @param basePackage 包名
     * @param clz         需要扫描的接口类
     * @param callBack    扫描回调
     * @param <T>         需要扫描的类
     */
    public <T> void scan(String basePackage, Class<T> clz, ScannerCallBack<T> callBack) {
        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
        resolverUtil.find(new ResolverUtil.IsA(clz), basePackage);
        Set<Class<? extends Class<?>>> handlerSet = resolverUtil.getClasses();
        for (Class<?> type : handlerSet) {
            if (!type.isAnonymousClass() && !type.isInterface() && !Modifier.isAbstract(type.getModifiers())) {
                callBack.doWith((Class<T>) type);
            }
        }
    }

    public static interface ScannerCallBack<T> {
        public void doWith(Class<T> clz);
    }
}
