package com.skrivet.supports.realm.dao.core.dept;

import com.skrivet.supports.realm.dao.core.dept.entity.add.DeptAddDQ;
import com.skrivet.supports.realm.dao.core.dept.entity.select.DeptDetailDP;
import com.skrivet.supports.realm.dao.core.dept.entity.select.DeptListDP;
import com.skrivet.supports.realm.dao.core.dept.entity.update.DeptUpdateDQ;

import java.util.List;

public interface DeptDao {
    public void insert(DeptAddDQ entity);

    public Long deleteById(String id);

    public Long update(DeptUpdateDQ entity);

    public DeptDetailDP selectOneById(String id);

    public List<DeptListDP> selectList();

}
