package com.skrivet.supports.exception.service.core;


import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.supports.exception.service.core.entity.select.*;
import javax.validation.constraints.NotNull;

public interface ExceptionLogService {

    public Long deleteById(@NotNull(message = "主键不能为空") String id, LoginUser loginUser);

    public Long deleteMultiById(@NotNull(message = "主键不能为空") String[] ids, LoginUser loginUser);

    public ExceptionLogDetailSP selectOneById(@NotNull(message = "主键不能为空") String id, LoginUser loginUser);

    public PageList<ExceptionLogListSP> selectPageList(ExceptionLogSelectPageSQ condition, LoginUser loginUser);

}