package com.skrivet.supports.exception.service.impl;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.service.ExceptionLogService;
import com.skrivet.core.toolkit.DateUtils;
import com.skrivet.core.toolkit.ExceptionToolkit;
import com.skrivet.plugins.service.core.BasicService;
import com.skrivet.supports.exception.dao.core.ExceptionLogDao;
import com.skrivet.supports.exception.dao.core.entity.add.ExceptionLogAddDQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("skrivetExceptionLogServiceImpl")
@DynamicDataBase
public class SkrivetExceptionLogServiceImpl extends BasicService implements ExceptionLogService {
    @Autowired
    private ExceptionLogDao exceptionLogDao;

    @Override
    public void addLog(String tag, Exception ex) {
        ExceptionLogAddDQ data = new ExceptionLogAddDQ();
        data.setInterfaceTag(tag);
        data.setExceptionMsg(ex.getMessage());
        data.setHappenTime(DateUtils.getDateTime());
        data.setId(idGenerator.generate());
        data.setExceptionDetail(ExceptionToolkit.getStackMsg(ex));
        exceptionLogDao.insert(data);
    }
}
