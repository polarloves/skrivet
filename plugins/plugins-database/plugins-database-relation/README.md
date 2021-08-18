# 一、数据库配置

## 1. 介绍

此模块为结构化数据核心模块，其内提供了数据源、事务管理器的自动配置

## 2.动态数据源配置

此模块支持动态数据源配置，动态数据源指的是可以对数据源连接参数、账号、密码等进行在线配置，其需要集成nacos做在线配置管理

- 由于数据源修改时会造成各种问题，不建议使用动态配置
- 无法动态追加数据源
- 使用``exclude = {DataSourceAutoConfiguration.class}``排除spring boot数据库自动配置
- 如果想要是有动态数据源，则你需要在``Service``以及``Controller``中加入如下注解``@DynamicDataBase``,当然``@RefreshScope``也能生效，但是其无法按照配置文件进行生效，例如：测试环境中需要使用动态数据源，而生产环境不需要，当你使用``@RefreshScope``注解后，当配置文件改变时，所有含有注解的bean都会初始化
## 3.依赖模块

| 模块名称   |
|----------|
| core-common |
| core-configuration |
| core-toolkit |

## 4.配置示例

```
  skrivet:
      dynamic-database: true --是否启用动态数据源，启用时，数据源会随着配置文件更新而更新，默认为false
      database: ---根节点
          system: ---数据库编号,会注册名称为 systemDataSource的数据源以及名称为systemTransactionManager的事务管理器
              type: com.zaxxer.hikari.HikariDataSource  ---数据源类型
              names: system  ---支持的数据库名称，多个以","隔开
              destroyMethod: close  ---数据源关闭的方法
              default: true   ---是否为默认数据源，如果为默认数据源，则可以使用@Autowired标签自动装载,同时事务管理时无需指定事务管理器名称
              properties:     ---数据源的所有配置属性
                  username: root
                  password: root
                  jdbcUrl: jdbc:mysql://127.0.0.1:3306/maindb?characterEncoding=utf-8
                  driverClassName: com.mysql.jdbc.Driver
                  idleTimeout: 30000
          auth:
              type: com.zaxxer.hikari.HikariDataSource
              names: auth
              destroyMethod: close
              properties: 
                  username: root
                  password: root
                  jdbcUrl: jdbc:mysql://127.0.0.1:3306/subdb?characterEncoding=utf-8
                  driverClassName: com.mysql.jdbc.Driver
                  idleTimeout: 3000
```