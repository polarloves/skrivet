package com.skrivet.supports.authority.dao.mongodb.resource.template.select;

import com.skrivet.plugins.database.mongodb.annotations.Query;
import com.skrivet.plugins.database.mongodb.annotations.Sortable;
import com.skrivet.plugins.database.mongodb.enums.MatchStyle;

public class ResourceSelectPageTemplate {
    @Sortable
    @Query(MatchStyle.LIKE)
    private String text;
    @Sortable
    @Query(MatchStyle.LIKE)
    private String value;
    @Query(MatchStyle.LIKE)
    private String remark;
    @Sortable
    private Long orderNum;
    @Sortable
    @Query
    private Integer type;
}
