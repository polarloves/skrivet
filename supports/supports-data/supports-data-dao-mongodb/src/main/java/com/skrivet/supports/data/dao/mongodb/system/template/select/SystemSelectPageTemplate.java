package com.skrivet.supports.data.dao.mongodb.system.template.select;

import com.skrivet.plugins.database.mongodb.annotations.Query;
import com.skrivet.plugins.database.mongodb.annotations.Sortable;
import com.skrivet.plugins.database.mongodb.enums.MatchStyle;

public class SystemSelectPageTemplate {
    @Query(MatchStyle.LIKE)
    @Sortable
    private String text;
    @Query(MatchStyle.LIKE)
    @Sortable
    private String sysKey;
    @Sortable
    @Query(MatchStyle.LIKE)
    private String sysValue;
    @Sortable
    @Query(MatchStyle.LIKE)
    private String remark;
    @Query
    @Sortable
    private Long orderNum;
    @Query
    @Sortable
    private String groupId;
    @Query(MatchStyle.LIKE)
    @Sortable
    private String groupName;

}
