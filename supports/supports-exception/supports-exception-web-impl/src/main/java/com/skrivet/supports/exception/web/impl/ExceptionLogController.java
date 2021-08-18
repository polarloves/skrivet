package com.skrivet.supports.exception.web.impl;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.web.impl.controller.BasicController;

import com.skrivet.supports.exception.service.core.ExceptionLogService;
import com.skrivet.supports.exception.service.core.entity.select.ExceptionLogListSP;
import com.skrivet.supports.exception.service.core.entity.select.ExceptionLogSelectPageSQ;
import com.skrivet.supports.exception.web.core.entity.select.*;
import com.skrivet.web.core.annotations.AuthMappingIgnore;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.AuthMapping;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping("/exception")
@ApiSort(value = 16)
@DynamicDataBase
@Api(tags = "异常日志接口")
public class ExceptionLogController extends BasicController {
	@Autowired
	private ExceptionLogService exceptionLogService;
	
	@ApiOperationSupport(order = 1)
	@ResMsg(tag = "异常日志模块所有接口所需权限")
	@ResponseBody
	@ApiOperation(value = "异常日志模块所有接口所需权限")
	@GetMapping("/permissionList")
	@AuthMappingIgnore
	public ResponseJson<List<AuthMapping>> permissionList() {
		return ResponseBuilder.success(authMappingList());
	}
	

	@ApiOperationSupport(order = 3)
	@ResMsg(tag = "删除异常日志")
	@ResponseBody
	@ApiOperation(value = "删除异常日志")
	@DeleteMapping("/delete")
	@RequiresPermissions({"skrivet:exception:basic", "skrivet:exception:delete", "skrivet:backstage"})
	public ResponseJson<Long> delete(
			@NotNull(message = "主键不能为空")
			@ApiParam(value = "主键", required = true)
					String id,
			LoginUser loginUser) {
		return ResponseBuilder.success(exceptionLogService.deleteById(id, loginUser));
	}
	
	
	@ApiOperationSupport(order = 4)
	@ResMsg(tag = "批量删除异常日志")
	@ResponseBody
	@ApiOperation(value = "批量删除异常日志")
	@DeleteMapping("/deleteMulti")
	@RequiresPermissions({"skrivet:exception:basic", "skrivet:exception:delete", "skrivet:backstage"})
	public ResponseJson<Long> deleteMulti(
			@NotNull(message = "主键不能为空")
			@ApiParam(value = "主键", required = true)
					String[] ids, LoginUser loginUser) {
		return ResponseBuilder.success(exceptionLogService.deleteMultiById(ids, loginUser));
	}
	

	@ApiOperationSupport(order = 6)
	@ResMsg(tag = "异常日志分页列表")
	@ResponseBody
	@ApiOperation(value = "异常日志分页列表")
	@GetMapping("/pageList")
	@RequiresPermissions({"skrivet:exception:basic", "skrivet:exception:list", "skrivet:backstage"})
	public ResponseJson<List<ExceptionLogListVP>> pageList(@Valid ExceptionLogSelectPageVQ entity, LoginUser loginUser) {
		PageList<ExceptionLogListSP> page = exceptionLogService.selectPageList(entityConvert.convert(entity, ExceptionLogSelectPageSQ.class), loginUser);
		List<ExceptionLogListVP> exceptionLogListVPList = entityConvert.convertList(page.getData(), ExceptionLogListVP.class);
		return ResponseBuilder.success(exceptionLogListVPList, page.getCount());
	}
	
	
	@ApiOperationSupport(order = 7)
	@ResMsg(tag = "根据主键查找异常日志详情")
	@ResponseBody
	@ApiOperation(value = "根据主键查找异常日志详情")
	@GetMapping("/selectOneById")
	@RequiresPermissions({"skrivet:exception:basic", "skrivet:exception:detail", "skrivet:backstage"})
	public ResponseJson<ExceptionLogDetailVP> selectOneById(
			@NotNull(message = "主键不能为空")
			@ApiParam(value = "主键", required = true)
					String id, LoginUser loginUser) {
		return ResponseBuilder.success(entityConvert.convert(exceptionLogService.selectOneById(id, loginUser), ExceptionLogDetailVP.class));
	}
}