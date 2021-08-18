package com.skrivet.supports.data.web.impl.tree;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.Within;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.core.common.exception.ValidationExp;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.data.service.core.tree.TreeService;
import com.skrivet.supports.data.service.core.tree.entity.add.TreeAddSQ;
import com.skrivet.supports.data.service.core.tree.entity.select.TreeListSP;
import com.skrivet.supports.data.service.core.tree.entity.select.TreeSelectPageSQ;
import com.skrivet.supports.data.service.core.tree.entity.update.TreeUpdateSQ;
import com.skrivet.supports.data.web.core.tree.entity.add.TreeAddVQ;
import com.skrivet.supports.data.web.core.tree.entity.select.*;
import com.skrivet.supports.data.web.core.tree.entity.update.TreeUpdateVQ;
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
@RequestMapping("/data/tree")
@ApiSort(value = 2)
@DynamicDataBase
@Api(tags = "树接口")
public class TreeController extends BasicController {
    @Autowired
    private TreeService treeService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "树模块所有接口所需权限")
    @ResponseBody
    @ApiOperation(value = "树模块所有接口所需权限")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "添加树")
    @ResponseBody
    @ApiOperation(value = "添加树")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:tree:basic", "skrivet:tree:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody TreeAddVQ entity, LoginUser loginUser) {
        TreeAddSQ parameter = entityConvert.convert(entity, TreeAddSQ.class);
        String id = treeService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "删除树")
    @ResponseBody
    @ApiOperation(value = "删除树")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:tree:basic", "skrivet:tree:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @NotNull(message = "树编号不能为空")
            @ApiParam(value = "树编号", required = true)
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(treeService.deleteById(id, loginUser));
    }


    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "批量删除树")
    @ResponseBody
    @ApiOperation(value = "批量删除树")
    @DeleteMapping("/deleteMulti")
    @RequiresPermissions({"skrivet:tree:basic", "skrivet:tree:delete", "skrivet:backstage"})
    public ResponseJson<Long> deleteMulti(
            @NotNull(message = "树编号不能为空")
            @ApiParam(value = "树编号", required = true)
                    String[] ids, LoginUser loginUser) {
        return ResponseBuilder.success(treeService.deleteMultiById(ids, loginUser));
    }

    @ApiOperationSupport(order = 6)
    @ResMsg(tag = "修改树")
    @ResponseBody
    @ApiOperation(value = "修改树")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:tree:basic", "skrivet:tree:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody TreeUpdateVQ entity, LoginUser loginUser) {
        if (entity.getId().equals(entity.getParentId())) {
            throw new ValidationExp("不能选择自身作为父级");
        }
        TreeUpdateSQ parameter = entityConvert.convert(entity, TreeUpdateSQ.class);
        return ResponseBuilder.success(treeService.update(parameter, loginUser));
    }


    @ApiOperationSupport(order = 8)
    @ResMsg(tag = "树分页列表")
    @ResponseBody
    @ApiOperation(value = "树分页列表")
    @GetMapping("/pageList")
    @RequiresPermissions({"skrivet:tree:basic", "skrivet:tree:list", "skrivet:backstage"})
    public ResponseJson<List<TreeListVP>> pageList(@Valid TreeSelectPageVQ entity, LoginUser loginUser) {
        PageList<TreeListSP> page = treeService.selectPageList(entityConvert.convert(entity, TreeSelectPageSQ.class), loginUser);
        List<TreeListVP> treeListVPList = entityConvert.convertList(page.getData(), TreeListVP.class);
        return ResponseBuilder.success(treeListVPList, page.getCount());
    }


    @ApiOperationSupport(order = 10)
    @ResMsg(tag = "根据组编号查找树")
    @ResponseBody
    @ApiOperation(value = "根据组编号查找树")
    @GetMapping("/selectListByGroupId")
    public ResponseJson<Map<String, List<TreeListVP>>> selectListByGroupId(@NotNull(message = "组编号不能为空")
                                                                           @ApiParam(value = "组编号", required = true)
                                                                                   String[] groupIds, LoginUser loginUser) {
        Map<String, List<TreeListVP>> result = new HashMap<>();
        for (String groupId : groupIds) {
            result.put(groupId, entityConvert.convertList(treeService.selectListByGroupId(groupId, LoginUser.IGNORED), TreeListVP.class));
        }
        return ResponseBuilder.success(result);
    }

    @ApiOperationSupport(order = 11)
    @ResMsg(tag = "根据树编号查找树详情")
    @ResponseBody
    @ApiOperation(value = "根据树编号查找树详情")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:tree:basic", "skrivet:tree:detail", "skrivet:backstage"})
    public ResponseJson<TreeDetailVP> selectOneById(
            @NotNull(message = "树编号不能为空")
            @ApiParam(value = "树编号", required = true)
                    String id, LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(treeService.selectOneById(id, loginUser), TreeDetailVP.class));
    }


    @ApiOperationSupport(order = 13)
    @ResMsg(tag = "获取所有的树分组")
    @ResponseBody
    @ApiOperation(value = "获取所有的树分组")
    @GetMapping("/groups")
    @RequiresPermissions({"skrivet:tree:basic", "skrivet:tree:groups", "skrivet:backstage"})
    public ResponseJson<List<TreeGroupVP>> groups(LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convertList(treeService.groups(loginUser), TreeGroupVP.class));
    }


}
