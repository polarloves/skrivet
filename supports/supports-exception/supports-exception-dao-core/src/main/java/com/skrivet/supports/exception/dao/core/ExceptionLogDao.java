package com.skrivet.supports.exception.dao.core;

import com.skrivet.supports.exception.dao.core.entity.add.ExceptionLogAddDQ;
import com.skrivet.supports.exception.dao.core.entity.select.*;
import java.util.List;

public interface ExceptionLogDao {
	public void insert(ExceptionLogAddDQ entity);

	public Long deleteById(String id);

	public ExceptionLogDetailDP selectOneById(String id);

	public List<ExceptionLogListDP> selectPageList(ExceptionLogSelectPageDQ condition);

	public Long selectPageCount(ExceptionLogSelectPageDQ condition);

}