package com.skrivet.supports.data.dao.core.system;

import com.skrivet.supports.data.dao.core.system.entity.add.SystemAddDQ;
import com.skrivet.supports.data.dao.core.system.entity.select.*;
import com.skrivet.supports.data.dao.core.system.entity.update.SystemUpdateDQ;

import java.util.List;

public interface SystemDao {
    public void insert(SystemAddDQ entity);

    public Long deleteById(String id);

    public Long update(SystemUpdateDQ entity);

    public SystemDetailDP selectOneById(String id);

    public SystemDetailDP selectByGroupKey(String groupId, String key);

    public List<SystemListDP> selectListByGroupId(String groupId);

    public List<SystemListDP> selectPageList(SystemSelectPageDQ condition);

    public Long selectPageCount(SystemSelectPageDQ condition);

    public List<SystemGroupDP> groups();
}
