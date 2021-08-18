# 一、基础配置

## 1. 配置

```
  skrivet:
    name: yourprojectname  --你的项目名称,必填,用于缓存隔离等
``` 

## 2.注解

### 1. DynamicDataBase

动态数据源注解,其配置于Service、Controller中,用于告知当数据源变更时，其是否同步变更。

### 2. ValidateIgnore

用于Service中，当开启基于Service的校验时，如果方法包含此注解，则不进行校验

## 2. 转换器

### 1.编码转换器-CodeConvert

编码转换器支持对一个/多个参数按照指定的模板进行编码转换，详见``com.skrivet.core.common.convert.impl``
如果想要自定义，则重写此接口并注入spring

### 2.实体转换器-EntityConvert

实体转换器支用于将实体进行转换,当转换异常时,抛出ConvertExp异常
如果想要自定义，则重写此接口并注入spring
