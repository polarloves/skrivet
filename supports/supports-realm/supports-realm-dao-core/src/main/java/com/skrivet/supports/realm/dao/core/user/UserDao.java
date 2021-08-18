package com.skrivet.supports.realm.dao.core.user;

import com.skrivet.supports.realm.dao.core.user.entity.add.UserAddDQ;
import com.skrivet.supports.realm.dao.core.user.entity.select.*;
import com.skrivet.supports.realm.dao.core.user.entity.update.PasswordUpdateDQ;
import com.skrivet.supports.realm.dao.core.user.entity.update.UserUpdateDQ;
import com.skrivet.supports.realm.dao.core.user.entity.update.UserUpdateMineDQ;

import java.util.List;

public interface UserDao {
    public void insert(UserAddDQ entity);

    public Long deleteById(String id);

    public Long update(UserUpdateDQ entity);

    public Long updateMine(UserUpdateMineDQ entity);

    public UserDetailDP selectOneById(String id);

    public List<UserListDP> selectPageList(UserSelectPageDQ condition);

    public Long selectPageCount(UserSelectPageDQ condition);

    public SecurityUserDP selectSecurityUserByUserName(String userName);

    public Long login(String userName, String ip, String lastLoginDate);

    public Long updatePassword(PasswordUpdateDQ condition);

    public Long enable(String id);

    public Long disable(String id, String reason);
}
