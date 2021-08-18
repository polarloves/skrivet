package com.skrivet.supports.realm.dao.mybatis.user;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mybatis.annotations.Page;

import com.skrivet.supports.realm.dao.core.user.entity.select.SecurityUserDP;
import com.skrivet.supports.realm.dao.core.user.entity.select.UserDetailDP;
import com.skrivet.supports.realm.dao.core.user.entity.select.UserListDP;
import com.skrivet.supports.realm.dao.core.user.entity.select.UserSelectPageDQ;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Dao
public interface UserDao extends com.skrivet.supports.realm.dao.core.user.UserDao {
    public Long deleteById(@Param("id") String id);

    public UserDetailDP selectOneById(@Param("id") String id);

    @Page
    @Override
    public List<UserListDP> selectPageList(UserSelectPageDQ condition);

    public SecurityUserDP selectSecurityUserByUserName(@Param("userName") String userName);

    public Long login(@Param("userName") String userName, @Param("ip") String ip, @Param("lastLoginDate") String lastLoginDate);

    public Long enable(@Param("id") String id);

    public Long disable(@Param("id") String id, @Param("reason") String reason);
}
