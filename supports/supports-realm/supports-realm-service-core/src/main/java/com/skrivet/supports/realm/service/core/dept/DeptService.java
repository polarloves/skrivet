package com.skrivet.supports.realm.service.core.dept;

import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.supports.realm.service.core.dept.entity.add.DeptAddSQ;
import com.skrivet.supports.realm.service.core.dept.entity.select.DeptDetailSP;
import com.skrivet.supports.realm.service.core.dept.entity.select.DeptListSP;
import com.skrivet.supports.realm.service.core.dept.entity.update.DeptUpdateSQ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface DeptService {
    public String insert(@Valid DeptAddSQ entity, LoginUser loginUser);

    public Long deleteById(@NotNull(message = "部门编号不能为空") String id, LoginUser loginUser);


    public Long update(@Valid DeptUpdateSQ entity, LoginUser loginUser);

    public DeptDetailSP selectOneById(@NotNull(message = "部门编号不能为空") String id, LoginUser loginUser);

    public List<DeptListSP> selectList(LoginUser loginUser);

}
