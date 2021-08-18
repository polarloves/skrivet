package com.skrivet.supports.data.web.impl.dict;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.Within;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;

import com.skrivet.supports.data.service.core.dict.DictService;
import com.skrivet.supports.data.service.core.dict.entity.add.DictAddSQ;
import com.skrivet.supports.data.service.core.dict.entity.select.DictListSP;
import com.skrivet.supports.data.service.core.dict.entity.select.DictSelectPageSQ;
import com.skrivet.supports.data.service.core.dict.entity.update.DictUpdateSQ;

import com.skrivet.supports.data.web.core.dict.entity.add.DictAddVQ;
import com.skrivet.supports.data.web.core.dict.entity.select.*;
import com.skrivet.supports.data.web.core.dict.entity.update.DictUpdateVQ;
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
@RequestMapping("/data/dict")
@ApiSort(value = 1)
@DynamicDataBase
@Api(tags = "字典接口")
public class DictController extends BasicController {
    @Autowired
    private DictService dictService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "字典模块所有接口所需权限")
    @ResponseBody
    @ApiOperation(value = "字典模块所有接口所需权限")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "添加字典")
    @ResponseBody
    @ApiOperation(value = "添加字典")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:dict:basic", "skrivet:dict:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody DictAddVQ entity, LoginUser loginUser) {
        DictAddSQ parameter = entityConvert.convert(entity, DictAddSQ.class);
        String id = dictService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "删除字典")
    @ResponseBody
    @ApiOperation(value = "删除字典")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:dict:basic", "skrivet:dict:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @NotNull(message = "字典编号不能为空")
            @ApiParam(value = "字典编号", required = true)
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(dictService.deleteById(id, loginUser));
    }


    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "批量删除字典")
    @ResponseBody
    @ApiOperation(value = "批量删除字典")
    @DeleteMapping("/deleteMulti")
    @RequiresPermissions({"skrivet:dict:basic", "skrivet:dict:delete", "skrivet:backstage"})
    public ResponseJson<Long> deleteMulti(
            @NotNull(message = "字典编号不能为空")
            @ApiParam(value = "字典编号", required = true)
                    String[] ids, LoginUser loginUser) {
        return ResponseBuilder.success(dictService.deleteMultiById(ids, loginUser));
    }

    @ApiOperationSupport(order = 6)
    @ResMsg(tag = "修改字典")
    @ResponseBody
    @ApiOperation(value = "修改字典")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:dict:basic", "skrivet:dict:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody DictUpdateVQ entity, LoginUser loginUser) {
        DictUpdateSQ parameter = entityConvert.convert(entity, DictUpdateSQ.class);
        return ResponseBuilder.success(dictService.update(parameter, loginUser));
    }


    @ApiOperationSupport(order = 8)
    @ResMsg(tag = "字典分页列表")
    @ResponseBody
    @ApiOperation(value = "字典分页列表")
    @GetMapping("/pageList")
    @RequiresPermissions({"skrivet:dict:basic", "skrivet:dict:list", "skrivet:backstage"})
    public ResponseJson<List<DictListVP>> pageList(@Valid DictSelectPageVQ entity, LoginUser loginUser) {
        PageList<DictListSP> page = dictService.selectPageList(entityConvert.convert(entity, DictSelectPageSQ.class), loginUser);
        List<DictListVP> dictListVPList = entityConvert.convertList(page.getData(), DictListVP.class);
        return ResponseBuilder.success(dictListVPList, page.getCount());
    }


    @ApiOperationSupport(order = 10)
    @ResMsg(tag = "根据组编号查找字典")
    @ResponseBody
    @ApiOperation(value = "根据组编号查找字典")
    @GetMapping("/selectListByGroupId")
    public ResponseJson<Map<String, List<DictListVP>>> selectListByGroupId(@NotNull(message = "组编号不能为空")
                                                                           @ApiParam(value = "组编号", required = true)
                                                                                   String[] groupIds) {
        Map<String, List<DictListVP>> result = new HashMap<>();
        for (String groupId : groupIds) {
            result.put(groupId, entityConvert.convertList(dictService.selectListByGroupId(groupId, LoginUser.IGNORED), DictListVP.class));
        }
        return ResponseBuilder.success(result);
    }

    @ApiOperationSupport(order = 11)
    @ResMsg(tag = "根据字典编号查找字典详情")
    @ResponseBody
    @ApiOperation(value = "根据字典编号查找字典详情")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:dict:basic", "skrivet:dict:detail", "skrivet:backstage"})
    public ResponseJson<DictDetailVP> selectOneById(
            @NotNull(message = "字典编号不能为空")
            @ApiParam(value = "字典编号", required = true)
                    String id, LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(dictService.selectOneById(id, loginUser), DictDetailVP.class));
    }


    @ApiOperationSupport(order = 13)
    @ResMsg(tag = "获取所有的字典分组")
    @ResponseBody
    @ApiOperation(value = "获取所有的字典分组")
    @GetMapping("/groups")
    @RequiresPermissions({"skrivet:dict:basic", "skrivet:dict:groups", "skrivet:backstage"})
    public ResponseJson<List<DictGroupVP>> groups(LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convertList(dictService.groups(loginUser), DictGroupVP.class));
    }


}
