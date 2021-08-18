package com.skrivet.supports.realm.service.core.user;

import com.skrivet.core.common.annotations.Within;
import com.skrivet.core.common.entity.LoginUser;

import com.skrivet.core.common.entity.PageList;
import com.skrivet.supports.realm.service.core.user.entity.add.UserAddSQ;
import com.skrivet.supports.realm.service.core.user.entity.select.UserDetailSP;
import com.skrivet.supports.realm.service.core.user.entity.select.UserListSP;
import com.skrivet.supports.realm.service.core.user.entity.select.UserSelectPageSQ;
import com.skrivet.supports.realm.service.core.user.entity.update.PasswordUpdateSQ;
import com.skrivet.supports.realm.service.core.user.entity.update.UserUpdateSQ;
import com.skrivet.supports.realm.service.core.user.entity.update.UserUpdateMineSQ;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService {
    public String insert(@Valid UserAddSQ entity, LoginUser loginUser);

    public Long deleteById(@NotNull(message = "用户编号不能为空") String id, LoginUser loginUser);

    public Long deleteMultiById(@NotNull(message = "用户编号不能为空") String[] ids, LoginUser loginUser);

    public Long update(@Valid UserUpdateSQ entity, LoginUser loginUser);

    public Long updateMine(@Valid UserUpdateMineSQ entity, LoginUser loginUser);

    public UserDetailSP selectOneById(@NotNull(message = "用户编号不能为空") String id, LoginUser loginUser);

    public PageList<UserListSP> selectPageList(@Valid UserSelectPageSQ condition, LoginUser loginUser);

    public Long login(@NotNull(message = "用户名") String userName, String ip);

    public Long updatePassword(@Valid PasswordUpdateSQ condition, LoginUser loginUser);

    public Long disable(@NotNull(message = "用户编号不能为空") String id,
                        @NotNull(message = "禁用原因不能为空")
                        @Length(min = 10, max = 500, message = "禁用原因最少10位,最多为500位") String reason, LoginUser loginUser);

    public Long enable(@NotNull(message = "用户编号不能为空") String id, LoginUser loginUser);
}
