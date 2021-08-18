# 一、数据库配置

## 1. 介绍

此模块为mybatis自动配置

## 2.依赖模块

| 模块名称   |
|----------|
| core-common |
| core-configuration |
| core-toolkit |
| plugins-database-core|

## 3. 使用方式

在配置文件中加入如下标签：

```
@DaoScan(basePackages="",db="",location="")
```

- `basePackages` 指定此处需要扫描的含有`@Dao`注解的包路径
- `db` 指定数据库名称，其会根据名称去找到指定的数据源。例如：数据源配置中，编号为`test`,支持的数据库名称为`system,auth`,其值为`system`,则会使用编号为`test`的数据库
- `location` mapper文件所在的地址，例如：`classpath*:mapping/**/*.xml`
- `DaoScan` 在一个项目中可能存在多个，配置时，各个之间应该`互不影响`

## 4. 自动化分页

- 如果某个接口需要自动化分页，则需要在接口加入如下注解：``@Page``,系统会自动扫描含有注解的接口，并且拼接上分页语句
- 当然，如果你不想使用此注解，可以在mapper中判断数据库类型，编写相应的sql语句。
- ``@Page``属性如下

| 属性名称 | 含义|
|----------|----------|
| supports | 需要自动分页的数据库类型|
| start | 在参数中，存储开始条目的方法/参数名称，如果时方法，则以()结尾 |
| offset | 在参数中，存储偏移条目的方法/参数名称，如果时方法，则以()结尾 |

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
## 5. like语句自动匹配

在mapper文件中可以使用如下语法实现like语句多数据库匹配
```
<if test="value != null and value != ''">
	AND a.VALUE like SKRIVET_LIKE('%',#{value},'%')
</if>

```

## 6. 自定义mybatis配置

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