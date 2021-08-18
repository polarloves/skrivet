# 一、介绍

## 1.整体架构

### 1.1. core-核心层

包含基础组件，用于为框架提供各类支撑，主要包含：``公共模块`` ,``配置模块``,``工具类模块``,其主要为了给框架提供基础支撑。

### 1.2. components-组件层

组件层用于将各种``中间件``进行整合、扩展,为后续的插件层提供支撑,例如:``components-mybatis``集成了mybatis,并在此基础上增加了动态的SqlSessionFactory、各种类型处理器（TypeHandler）,同时改造了mybatis读取yml文件的部分逻辑

### 1.3. plugins-插件层

* 插件层用于为框架提供各类功能支撑,例如：``plugins-cache``则提供了对于缓存的支撑,``plugins-database``则提供了数据库访问的支撑。
* 插件层细分为两个部分：``标准化接口-core``,``各类实现方式``,例如：``plugins-security``主要为系统提供鉴权相关内容，则 ``plugins-security-core`` 负责将鉴权相关内容标准化、提取公共接口。而``plugins-security-jwt``则负责以jwt形式实现``plugins-security-core``的各种接口，``plugins-security-shiro``则负责以shiro形式实现``plugins-security-core``的各种接口

### 1.4. supports-支撑层

* 支撑层主要为框架做业务逻辑支撑，内置了各种业务组件，例如：权限组件、代码生成器、数据管理、异常日志、用户管理等。
* 支撑层一般会对数据进行持久化,针对不同的数据库类型提供不同的实现类，例如：权限组件提供了基于``关系型数据库``/``mongodb数据库``的存储。

### 1.5 samples-示例层

* 此层主要提供搭建框架的示例,pom的写法.

## 2.框架优势

### 2.1. 分层解耦

框架采取多级分层的设计理念，将各个层次剥离开来，将各个层次的耦合度降到最低，同时采用最少依赖的方式进行开发，确保每个层次直接的依赖最少，减轻项目的压力。

### 2.2. 使用简单与灵活多变

框架有最简单的使用方式，仅需一句话就可搭建起整个框架：

```
    <dependency>
        <groupId>com.skrivet</groupId>
        <artifactId>samples-bom</artifactId>
        <version>1.0.0-RELEASE</version>
    </dependency>
        
```

同时，框架也可以依据各自的情况进行个性话定制，可以随时对其中的模块进行卸载与装载。

示例：仅使用用户与部门管理部分，且使用关系型数据库：

```
      <dependency>
          <groupId>com.skrivet</groupId>
          <artifactId>supports-realm-dao-mybatis</artifactId>
          <version>1.0.0-RELEASE</version>
      </dependency>
      <dependency>
          <groupId>com.skrivet</groupId>
          <artifactId>supports-realm-service-impl</artifactId>
          <version>1.0.0-RELEASE</version>
      </dependency>
      <dependency>
          <groupId>com.skrivet</groupId>
          <artifactId>supports-realm-web-impl</artifactId>
          <version>1.0.0-RELEASE</version>
      </dependency>
        
```

示例：仅使用用户与部门管理部分的服务部分，且使用关系型数据库：

```
      <dependency>
          <groupId>com.skrivet</groupId>
          <artifactId>supports-realm-dao-mybatis</artifactId>
          <version>1.0.0-RELEASE</version>
      </dependency>
      <dependency>
          <groupId>com.skrivet</groupId>
          <artifactId>supports-realm-service-impl</artifactId>
          <version>1.0.0-RELEASE</version>
      </dependency>
        
```

# 二、开始使用

## 2.1. 环境要求

1. maven
2. jdk1.8+

## 2.2. 安装项目

使用idea等开发工具导入项目，同时发布至``本地仓库``/``maven私有库``

## 2.3. 初始化数据库

* 框架提供了Mysql初始化数据库的脚本,脚本路径：``/samples/samples-background/init.sql``，将此脚本导入至mysql数据库内即可
* init.sql内提供了本框架的全量sql,但需要卸载某个模块时，可以删除对应的表,各个业务模块对应表关系如下：

|  业务模块   |   说明   |数据库表  |
|  ----  | ----  | ----  |
| supports-authority  | 权限相关业务模块,包含：权限、资源、角色管理 | skrivet_permission<br>skrivet_resource<br>skrivet_resource_permission<br>skrivet_role<br>skrivet_role_permission<br>skrivet_user_role |
| supports-code  | 代码生成器 | skrivet_code_column<br>skrivet_code_form<br>skrivet_code_template |
| supports-data  | 数据管理，包括：字典管理、树结构数据管理，系统配置信息管理 | skrivet_dict<br>skrivet_tree<br>skrivet_system |
| supports-exception  | 系统异常日志管理 | skrivet_exception |
| supports-file  | 文件管理 | 无 |
| supports-menu  | 菜单管理 | skrivet_menu_item<br>skrivet_menu_template<br>skrivet_menu_template_item<br>skrivet_user_menu_template|
| supports-reaml  | 域管理，包含：用户管理、部门管理 | skrivet_user<br>skrivet_dept|

## 2.4. 创建项目

创建一个maven项目，在pom.xml中添加如下内容：
```
    <dependency>
        <groupId>com.skrivet</groupId>
        <artifactId>samples-bom</artifactId>
        <version>1.0.0-RELEASE</version>
    </dependency>
        
```

## 2.5. 修改配置文件

### 2.5.1. 使用nacos作为配置中心

在``resources/bootstrap.yml``中作如下修改:

```
spring:
  application:
    name: emergency
  profiles:
    active: @filter-resource-name@ 
  cloud:
    nacos:
      config:
        server-addr: 192.168.31.251:8848  --nacos地址
        group: frametest  --nacos中配置文件分组
        file-extension: yaml
        namespace: aa189fb7-2b01-47b9-9800-8cccd3a2520c  --nacos中namespace
```

在nacos中添加配置文件，具体配置规则将2.5.3

### 2.5.2. 使用本地文件作为配置

在``resources/bootstrap.yml``中作如下修改:

```
spring:
  port: 1110
  application:
    name: emergency
  profiles:
    active: @filter-resource-name@ 
  cloud:
    nacos:
      enable: false --关闭nacos
```

在``application-dev.yml``中添加配置文件，具体配置规则将2.4.3

### 2.5.3. 配置文件说明

```
logging:
  config: classpath:logback-cloud.xml #配置文件路径，固定
  path: e:/testlog #日志文件路径
skrivet:
    project:
        name: test #项目名称
        package: cn.skrivet.test #项目包名
    validate:
        service: false  #是否在service层启用数据校验
        web: false  #是否在web层启用数据校验
    security:
        type: jwt #权限管理类型，支持：shiro,jwt,默认为shiro
        serviceCheck: false #是否在service层启用权限校验,默认不启用
        webCheck: true #是否在web层启用权限校验,默认启用
    doc:
        enable: true #是否启用接口文档,默认不启用
        enableSkrivetDoc: true #是否启用框架的接口文档，默认启用
        enableProjectDoc: true #是否启用项目的接口文档，默认启用
    dynamicDatabase: true #是否支持动态数据源，默认不支持
    jwt:
        signKey: 123456 #jwt秘钥
        tokenKey: token #jwt中,token保存的key,默认为token
        actionKey: action #jwt中,action保存的key,默认为action,其值有三个:create,delete,update
        timeout: 1800000 #token超时时间，单位毫秒，默认为30分钟
        refreshBeforeTimeout: 300000 #token刷新时间，默认5分钟
    shiro:  #shiro配置，当security.type为shiro时才有效
        loginUrl: /login #登录的路径，多个以,隔开,默认为login
        cookie:
            name: sid  #cookie key，默认为sid
            maxAge: -1 #cookie过期时间,单位为秒，-1表示浏览器关闭就过期 ,默认为-1
        session:
            deleteInvalid: true #是否删除无效的session，默认为true
            timeout: 10000     #session过期时间，单位为毫秒,默认为30分钟，当值为-1000时，永不过期
            cacheName: shiroSession #session缓存key,默认为：shiroSession
     redis:  #redis的配置文件，可以参照spring-cloud redis中配置
            host: 127.0.0.1
            port: 6379
            timeout: 2000
            jedis:
                pool:
                    maxIdle: 10
                    minIdle: 5
                    maxActive: 20
    cache:
        type: redis  #缓存类型,目前仅支持redis
        value-serialization: json
    database: #数据库配置，可以在此次配置多个数据库
        main: #唯一编号，保证唯一即可
            type: com.zaxxer.hikari.HikariDataSource #数据源类型,支撑mongodb或者其他类型数据源
            names: data,realm,authority,code,menu #此数据源支持的业务模块
            destroyMethod: close
            default: true
            properties: #数据源详细配置
                username: root
                password: root
                jdbcUrl: jdbc:mysql://127.0.0.1:3306/ttttt?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true
                driverClassName: com.mysql.jdbc.Driver
                idleTimeout: 30000
```

## 2.6. 编写启动类

编写启动类，如下：

```
@ComponentScan({"com.skrivet","${skrivet.project.package}"})
public class Starter {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Starter.class);
    }
}
```

## 2.7. 运行项目

运行2.6中编写好的启动类，启动后访问:``http://127.0.0.1:1110/doc.html`` 即可访问到接口文档

## 2.7. 项目分离式打包

* 将``samples-background/package``、``samples-background/resources/logback-cloud.xml``、``samples-background/resources/server.sh`` 拷贝至指定目录
* 将``samples-background/pom.xml``中build节点内容拷贝至pom.xml，修改如下内容``<mainClass>com.skrivet.core.common.starter.Starter</mainClass>``
* 运行``maven install``命令，完成打包
* 项目打包后，将会在target目录下生成一个压缩包,将压缩包上传至服务器下即可启动.

```
linux下运行：
    sh server.sh start  --启动项目
    sh server.sh stop   --关闭项目
    sh server.sh restart --重启项目
    
windows下运行：
    java -jar  xxx.jar
```

# 三、高级开发

## 3.1. 启动监听

依赖模块：``core-common``

```
  public interface InitializeListener {
      public void onInitialize(ApplicationContext context);
      public int sort();
  }
  
``` 

## 3.2. spel表达式解析

依赖模块：``core-common``,项目内提供了spel表达式解析的方式，详见``com.skrivet.core.common.util.SpringElUtil``

## 3.3. 接口实现类扫描器

可以依据包名进行扫描，扫描指定接口的实现类，其内部实现原理与mybatis类扫描原理相同，使用方法：
```
PackageScanner sacnner=new PackageScanner();
sacnner.scan("你的包名",接口class,扫描回调);

```

## 3.4. 更新redis缓存失效时间

对于spring的redis而言，无法存入缓存时间，框架对此处进行了修改，但需要指定某个value的缓存时间时，需要使此类实现如下接口:``com.skrivet.plugins.cache.core.CacheTimeoutChangeable``

## 3.5. 单个/多个cache特殊化配置

spring boot 不支持对特定缓存进行特殊化配置，包括：超时时间、序列化方式，系统提供特殊化配置方法,在系统内配置如下bean即可。

```
public interface CacheSerializerInitialConfigurations {
    public Map<String, CacheConfig> initialCacheSerializerConfigurations();
}

```

## 3.6. mybatis扫描

框架基于多数据源，提供了多数据源扫描注解:

```
@Configuration
@DaoScan(basePackages = "com.skrivet.supports.data.dao.mybatis", db = "data", location = "classpath*:mapping/supports/data/**.xml")
public class DataConfig {
}

```

- `basePackages` 指定此处需要扫描的含有`@Dao`注解的包路径
- `db` 指定数据库名称，其会根据名称去找到指定的数据源。例如：数据源配置中，编号为`test`,支持的数据库名称为`system,auth`,其值为`system`,则会使用编号为`test`的数据库
- `location` mapper文件所在的地址，例如：`classpath*:mapping/**/*.xml`
- `DaoScan` 在一个项目中可能存在多个，配置时，各个之间应该`互不影响`

## 3.7. mongodb数据源扫描

框架基于多数据源，提供了多数据源扫描注解:

```
@Configuration
@MongoScan(basePackages = "com.skrivet.supports.data.dao.mongodb", db = "data")
public class DataConfig {
}

```

## 3.8. mybatis高级使用

### 3.8.1. 自动化分页

- 如果某个接口需要自动化分页，则需要在接口加入如下注解：``@Page``,系统会自动扫描含有注解的接口，并且拼接上分页语句
- 当然，如果你不想使用此注解，可以在mapper中判断数据库类型，编写相应的sql语句。
- ``@Page``属性如下

| 属性名称 | 含义|
|----------|----------|
| supports | 需要自动分页的数据库类型|
| start | 在参数中，存储开始条目的方法/参数名称，如果时方法，则以()结尾 |
| offset | 在参数中，存储偏移条目的方法/参数名称，如果时方法，则以()结尾 |

在mapper.xml文件中判断数据库类型：

```
<choose>
	<when test="'${_databaseType}' == 'oracle'">
		SELECT * FROM (
	  		select rownum rowsize,innertable.* from
			(
				SELECT <include refid="listColumns" />
				FROM <include refid="tables" />
				<include refid="searchMultiWheres" />
				<include refid="orderCondition" />
			) innertable
		)
		WHERE rowsize > #{pageStartNumber} and rowsize &lt;= #{pageOffsetNumber}
	</when>
	<when test="'${_databaseType}' == 'sqlServer'">
		SELECT TOP ${rows} A.*
		FROM
		(
		SELECT
		ROW_NUMBER() OVER (<include refid="orderCondition" />) AS RowNumber,
		<include refid="listColumns" />
		FROM <include refid="tables" />
		<include refid="searchMultiWheres" />
		)   as A
		WHERE RowNumber > #{pageStartNumber}
	</when>
	<otherwise>
		SELECT
		<include refid="listColumns" />
		FROM
		<include refid="tables" />
		<include refid="searchMultiWheres" />
		<include refid="orderCondition" />
		limit #{pageStartNumber},#{pageOffsetNumber}
	</otherwise>
</choose>
```
### 3.8.2. like语句自动匹配

在mapper文件中可以使用如下语法实现like语句多数据库匹配
```
<if test="value != null and value != ''">
	AND a.VALUE like SKRIVET_LIKE('%',#{value},'%')
</if>

```

### 3.8.3. 自定义mybatis配置

- 根节点为:``skrivet.database.xx.mybatis``,可以对mybatis的``properties``,``settings``,``typeHandlers``进行配置
- 如果没有这些节点，则系统会默认配置，同时识别数据库为``mysql``

```
properties: 
     _databaseType: mysql --数据库类型，默认mysql
settings:
    cacheEnabled: true
    lazyLoadingEnabled: true
    aggressiveLazyLoading: true
    multipleResultSetsEnabled: true
    useColumnLabel: true
    useGeneratedKeys: false
    autoMappingBehavior: PARTIAL
    defaultExecutorType: SIMPLE
    mapUnderscoreToCamelCase: true
    localCacheScope: SESSION
    jdbcTypeForNull: 'NULL'
    logImpl: STDOUT_LOGGING
typeHandlers:
    jdbc:
       javaType: java.lang.String
       jdbcType: BLOB
       handler: com.skrivet.plugins.database.mybatis.handler.BlobStringTypeHandler
plugins:
    test1: plugin的class

```

### 3.8.4. 内置typeHandlers

框架内置了部分类型转换器，可以将数据库类型与java类型进行映射：

- BlobStringTypeHandler,数据库类型：blob,Java类型：String
- DateStringTypeHandler,数据库类型：Date,Time,Java类型：String,格式化方式：yyyy-MM-dd HH:mm:ss
- IntVarcharBooleanTypeHandler，数据库类型：int,varchar,Java类型：boolean
- VarcharDateTypeHandler，数据库类型：varchar,Java类型：Date
- BlobJsonTypeHandler,数据库类型：未指定,Java类型：任意

示例：java中使用自定义实体类，数据库使用blob字段存储

```
insert中使用：

 #{display,typeHandler=com.skrivet.components.mybatis.handler.BlobStringTypeHandler},

select 中使用：

<result column="extInfo" property="extInfo"  typeHandler="com.skrivet.components.mybatis.handler.VarcharJsonTypeHandler"/>

```

## 3.9. 权限使用

### 3.9.1. 权限配置

```
skrivet:
    security:
        type: jwt #权限管理类型，支持：shiro,jwt,默认为shiro
        serviceCheck: false #是否在service层启用权限校验,默认不启用
        webCheck: true #是否在web层启用权限校验,默认启用
```
#### 3.9.1.1 jwt

##### 3.9.1.1.1 刷新流程

当用户token未过期，将要过期时,系统将会在响应头文件中追加如下内容：
```
action: update
token: 新的token
```
action的值有三种，分别为:create,update,delete

- create 用户首次生成token时
- update token处于过期临界值时
- delete token已过期时


##### 3.9.1.1.2 配置参数

```
skrivet:
    jwt:
        tokenKey: token  #头文件中存储token的key,默认为token
        actionKey: action #头文件中存储action的key,默认为action
        webCheck: true #是否在web层启用权限校验,默认启用
        signKey: #签名秘钥
        timeout: #token超时时间,单位毫秒
        refreshBeforeTimeout: #token刷新时间，当token离超时不到refreshBeforeTimeout时，将会在action中存入刷新动作，交由客户端进行刷新
```

#### 3.9.1.1 shiro

shiro内部使用的是``spring的CacheManager``，其会与系统共用一套缓存管理规则.

```
skrivet:
    shiro:
        cookie:  #cookie相关配置
            name: sid #sessionid在cookie中保存的名称
            maxAge: -1 b#cookie过期时间,单位为秒，-1表示浏览器关闭就过期 
            enable: true #是否启用cookie
        session: #session 相关配置
            deleteInvalid: true #是否删除无效的session
            timeout: 1800000 #会话超时时间,单位为毫秒，-1000标识永不超时
            cacheName: shiroSession #session在缓存中存储key
        cache: #缓存配置
            tokenCacheName: 用户登录凭证缓存key
            permissionInfoCacheName: 用户权限信息缓存key
            retryCacheName: 重试密码缓存key
            onlineUserCacheName: 在线用户缓存key
            retryCacheName: 重试密码缓存key
         accountProperties:
            passwordRetryCount:密码重试次数，>0时生效
            accountLockTime：密码重试过多时，锁定时间，单位毫秒
            maxAccount：最多同时在线用户，-1表示不限制
```


### 3.9.2. 权限注解

```
@RequiresPermissions 标识此接口/方法需要用户包含的权限信息，其前置条件为用户已登录
@RequiresRoles       标识此接口/方法需要用户包含的角色信息，其前置条件为用户已登录
@RequiresUser        标识此接口/方法需要用户登录
@SecurityIgnore      标识此接口/方法为开放性接口

```

## 3.10. 数据校验

### 3.10.1. 配置

```
skrivet:
    validate:
        service: false  #是否在service层启用数据校验
        web: false #是否在web层启用数据校验

```

### 3.10.2. 使用

```
web层：
 public ResponseJson<String> insert(@Valid @RequestBody DictAddVQ entity, LoginUser loginUser)
 
 service层：
 public String insert(@Valid DictAddSQ entity, LoginUser loginUser);

```

## 3.11. 扩展自定义异常代码模板

实现如下接口,并将实现类交由spring管理.

```
public interface CodeConvert {
    public String convert(String code, String template, Serializable[] variables);

    public Map<String, ConvertTemplate> codes();
}

```

## 3.12. 使用自定义缓存管理器

系统提供了一套不同于spring cache的管理器，用于一些数据的手动管理，当然，你也可以使用spring的cacheManager进行管理。
```
public interface CacheProvider {
    public Cache getCache(String name);
}

/**
 * 自定义的缓存，和spring的缓存不一样
 */
public interface Cache {

    public <T> T get(Object key);

    public void set(Object key,Object value);

    public void delete(Object key);

    public void clear();

    public void setCacheName(String name);

    public String cacheName();

}
```

## 3.13. 动态数据源

系统使用了动态的数据源，可以通过nacos修改配置文件后使数据源实时生效，目前动态数据源可能会出现各种问题，``不建议``于生产环境使用

# 四、 未来规划

- 完善消息队列、内部通知方式，增加kafka、activemq、rocketmq等
- 优化缓存策略，增加ehcache支持类，以便单机环境下效率更高
- 追加工作流支持组件
- 追加spring cloud alibaba组件，包括：远程调用、日志采集、流量管控等。
