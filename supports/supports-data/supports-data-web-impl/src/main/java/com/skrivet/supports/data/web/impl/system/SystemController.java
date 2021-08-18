package com.skrivet.supports.data.web.impl.system;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.Within;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.data.service.core.system.SystemService;
import com.skrivet.supports.data.service.core.system.entity.add.SystemAddSQ;
import com.skrivet.supports.data.service.core.system.entity.select.SystemListSP;
import com.skrivet.supports.data.service.core.system.entity.select.SystemSelectPageSQ;
import com.skrivet.supports.data.service.core.system.entity.update.SystemUpdateSQ;
import com.skrivet.supports.data.web.core.system.entity.add.SystemAddVQ;
import com.skrivet.supports.data.web.core.system.entity.select.*;
import com.skrivet.supports.data.web.core.system.entity.update.SystemUpdateVQ;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SkrivetDoc
@Controller
@RequestMapping("/data/system")
@ApiSort(value = 3)
@DynamicDataBase
@Api(tags = "系统配置接口")
public class SystemController extends BasicController {
    @Autowired
    private SystemService systemService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "系统配置模块所有接口所需权限")
    @ResponseBody
    @ApiOperation(value = "系统配置模块所有接口所需权限")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "添加系统配置")
    @ResponseBody
    @ApiOperation(value = "添加系统配置")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:system:basic", "skrivet:system:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody SystemAddVQ entity, LoginUser loginUser) {
        SystemAddSQ parameter = entityConvert.convert(entity, SystemAddSQ.class);
        String id = systemService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "删除系统配置")
    @ResponseBody
    @ApiOperation(value = "删除系统配置")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:system:basic", "skrivet:system:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @NotNull(message = "系统配置编号不能为空")
            @ApiParam(value = "系统配置编号", required = true)
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(systemService.deleteById(id, loginUser));
    }


    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "批量删除系统配置")
    @ResponseBody
    @ApiOperation(value = "批量删除系统配置")
    @DeleteMapping("/deleteMulti")
    @RequiresPermissions({"skrivet:system:basic", "skrivet:system:delete", "skrivet:backstage"})
    public ResponseJson<Long> deleteMulti(
            @NotNull(message = "系统配置编号不能为空")
            @ApiParam(value = "系统配置编号", required = true)
                    String[] ids, LoginUser loginUser) {
        return ResponseBuilder.success(systemService.deleteMultiById(ids, loginUser));
    }

    @ApiOperationSupport(order = 6)
    @ResMsg(tag = "修改系统配置全部数据")
    @ResponseBody
    @ApiOperation(value = "修改系统配置全部数据")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:system:basic", "skrivet:system:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody SystemUpdateVQ entity, LoginUser loginUser) {
        SystemUpdateSQ parameter = entityConvert.convert(entity, SystemUpdateSQ.class);
        return ResponseBuilder.success(systemService.update(parameter, loginUser));
    }


    @ApiOperationSupport(order = 8)
    @ResMsg(tag = "系统配置分页列表")
    @ResponseBody
    @ApiOperation(value = "系统配置分页列表")
    @GetMapping("/pageList")
    @RequiresPermissions({"skrivet:system:basic", "skrivet:system:list", "skrivet:backstage"})
    public ResponseJson<List<SystemListVP>> pageList(@Valid SystemSelectPageVQ entity, LoginUser loginUser) {
        PageList<SystemListSP> page = systemService.selectPageList(entityConvert.convert(entity, SystemSelectPageSQ.class), loginUser);
        List<SystemListVP> systemListVPList = entityConvert.convertList(page.getData(), SystemListVP.class);
        return ResponseBuilder.success(systemListVPList, page.getCount());
    }



    @ApiOperationSupport(order = 10)
    @ResMsg(tag = "根据组编号查找系统配置")
    @ResponseBody
    @ApiOperation(value = "根据组编号查找系统配置")
    @GetMapping("/selectListByGroupId")
    public ResponseJson<Map<String, List<SystemListVP>>> selectListByGroupId(@NotNull(message = "组编号不能为空")
                                                                             @ApiParam(value = "组编号", required = true)
                                                                                     String[] groupIds, LoginUser loginUser) {
        Map<String, List<SystemListVP>> result = new HashMap<>();
        for (String groupId : groupIds) {
            result.put(groupId, entityConvert.convertList(systemService.selectListByGroupId(groupId, LoginUser.IGNORED), SystemListVP.class));
        }
        return ResponseBuilder.success(result);
    }

    @ApiOperationSupport(order = 11)
    @ResMsg(tag = "根据系统配置编号查找系统配置详情")
    @ResponseBody
    @ApiOperation(value = "根据系统配置编号查找系统配置详情")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:system:basic", "skrivet:system:detail", "skrivet:backstage"})
    public ResponseJson<SystemDetailVP> selectOneById(
            @NotNull(message = "系统配置编号不能为空")
            @ApiParam(value = "系统配置编号", required = true)
                    String id, LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(systemService.selectOneById(id, loginUser), SystemDetailVP.class));
    }


    @ApiOperationSupport(order = 13)
    @ResMsg(tag = "获取所有的系统配置分组")
    @ResponseBody
    @ApiOperation(value = "获取所有的系统配置分组")
    @GetMapping("/groups")
    @RequiresPermissions({"skrivet:system:basic", "skrivet:system:groups", "skrivet:backstage"})
    public ResponseJson<List<SystemGroupVP>> groups(LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convertList(systemService.groups(loginUser), SystemGroupVP.class));
    }


}
