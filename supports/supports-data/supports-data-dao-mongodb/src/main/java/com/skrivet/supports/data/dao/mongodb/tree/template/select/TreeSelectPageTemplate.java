package com.skrivet.supports.data.dao.mongodb.tree.template.select;

import com.skrivet.plugins.database.mongodb.annotations.Query;
import com.skrivet.plugins.database.mongodb.annotations.Sortable;
import com.skrivet.plugins.database.mongodb.enums.MatchStyle;

public class TreeSelectPageTemplate {
    @Query(MatchStyle.LIKE)
    @Sortable
    private String text;
    @Sortable
    @Query(MatchStyle.LIKE)
    private String value;
    @Query(MatchStyle.LIKE)
    @Sortable
    private String remark;
    @Query
    @Sortable
    private Long orderNum;
    @Query
    @Sortable
    private String groupId;
    @Query(MatchStyle.LIKE)
    private String groupName;

}
