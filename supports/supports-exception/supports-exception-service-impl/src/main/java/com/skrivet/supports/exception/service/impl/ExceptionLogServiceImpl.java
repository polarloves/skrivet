package com.skrivet.supports.exception.service.impl;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.service.core.BasicService;

import com.skrivet.supports.exception.dao.core.ExceptionLogDao;
import com.skrivet.supports.exception.dao.core.entity.select.*;
import com.skrivet.supports.exception.service.core.ExceptionLogService;
import com.skrivet.supports.exception.service.core.entity.select.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
	
import java.util.List;

@Service("exceptionLogService")
@DynamicDataBase
public class ExceptionLogServiceImpl extends BasicService implements ExceptionLogService {
	@Autowired
	private ExceptionLogDao exceptionLogDao;

	@RequiresPermissions(value = "skrivet:exception:delete")
	@Transactional(transactionManager = "exceptionTM")
	public Long deleteById(String id, LoginUser loginUser) {
		return exceptionLogDao.deleteById(id);
	}
	
	@RequiresPermissions(value = "skrivet:exception:delete")
	@Transactional(transactionManager = "exceptionTM")
	public Long deleteMultiById(String[] ids, LoginUser loginUser) {
		ExceptionLogService current = current(ExceptionLogService.class);
		long result = 0;
		for (String id : ids) {
			result = result + current.deleteById(id, loginUser);
		}
		return result;
	}

	@RequiresPermissions(value = "skrivet:exception:detail")
	public ExceptionLogDetailSP selectOneById(String id, LoginUser loginUser) {
		ExceptionLogDetailDP entity = exceptionLogDao.selectOneById(id);
		return entityConvert.convert(entity, ExceptionLogDetailSP.class);
	}
	
	@RequiresPermissions(value = "skrivet:exception:list")
	public PageList<ExceptionLogListSP> selectPageList(ExceptionLogSelectPageSQ condition, LoginUser loginUser) {
		PageList<ExceptionLogListSP> page = new PageList<ExceptionLogListSP>();
		ExceptionLogSelectPageDQ request = entityConvert.convert(condition, ExceptionLogSelectPageDQ.class);
		page.setCount(exceptionLogDao.selectPageCount(request));
		page.setData(entityConvert.convertList(exceptionLogDao.selectPageList(request), ExceptionLogListSP.class));
		return page;
	}
}