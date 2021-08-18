package com.skrivet.supports.data.dao.core.dict;

import com.skrivet.supports.data.dao.core.dict.entity.add.DictAddDQ;
import com.skrivet.supports.data.dao.core.dict.entity.select.*;
import com.skrivet.supports.data.dao.core.dict.entity.update.DictUpdateDQ;

import java.util.List;

public interface DictDao {
    public void insert(DictAddDQ entity);

    public Long deleteById(String id);

    public Long update(DictUpdateDQ entity);

    public DictDetailDP selectOneById(String id);

    public List<DictListDP> selectListByGroupId(String groupId);

    public List<DictListDP> selectPageList(DictSelectPageDQ condition);

    public Long selectPageCount(DictSelectPageDQ condition);

    public List<DictGroupDP> groups();
}
