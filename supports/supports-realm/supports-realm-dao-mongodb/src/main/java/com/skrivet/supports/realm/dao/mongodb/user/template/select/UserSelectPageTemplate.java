package com.skrivet.supports.realm.dao.mongodb.user.template.select;

import com.skrivet.plugins.database.mongodb.annotations.Query;
import com.skrivet.plugins.database.mongodb.annotations.Sortable;
import com.skrivet.plugins.database.mongodb.enums.MatchStyle;

public class UserSelectPageTemplate {
    @Query(MatchStyle.LIKE)
    @Sortable
    private String userName;
    @Sortable
    @Query(MatchStyle.LIKE)
    private String nickName;
    @Query
    @Sortable
    private String createDate;
    @Query(MatchStyle.LIKE)
    @Sortable
    private String phone;
    @Query(MatchStyle.LIKE)
    @Sortable
    private String email;
    @Query
    private String orgId;
    @Query
    @Sortable
    private Integer loginCount;
    @Query
    @Sortable
    private String lastLoginIp;
    @Query
    @Sortable
    private String lastLoginDate;
    @Query
    @Sortable
    private Integer state;

}
