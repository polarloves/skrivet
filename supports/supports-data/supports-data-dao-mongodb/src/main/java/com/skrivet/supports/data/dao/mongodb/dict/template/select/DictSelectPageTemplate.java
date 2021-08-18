package com.skrivet.supports.data.dao.mongodb.dict.template.select;

import com.skrivet.plugins.database.mongodb.annotations.Query;
import com.skrivet.plugins.database.mongodb.annotations.Sortable;
import com.skrivet.plugins.database.mongodb.enums.MatchStyle;

public class DictSelectPageTemplate {
    @Query(MatchStyle.LIKE)
    @Sortable
    private String text;
    @Query(MatchStyle.LIKE)
    @Sortable
    private String value;
    @Query(MatchStyle.LIKE)
    @Sortable
    private String remark;
    @Query()
    @Sortable
    private String groupId;
    @Query(MatchStyle.LIKE)
    @Sortable
    private String groupName;
    @Query()
    @Sortable
    private Long orderNum;

}
