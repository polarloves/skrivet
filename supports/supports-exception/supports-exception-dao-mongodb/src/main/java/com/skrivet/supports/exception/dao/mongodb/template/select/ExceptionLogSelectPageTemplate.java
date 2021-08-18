package com.skrivet.supports.exception.dao.mongodb.template.select;

import com.skrivet.plugins.database.mongodb.annotations.Query;
import com.skrivet.plugins.database.mongodb.annotations.Sortable;
import com.skrivet.plugins.database.mongodb.enums.MatchStyle;

public class ExceptionLogSelectPageTemplate {
    @Query
    @Sortable
    //接口
    private String interfaceTag;

    @Sortable
    //报错时间
    private String happenTime;
}