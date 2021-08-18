package com.skrivet.supports.authority.web.impl.role;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.authority.service.core.role.RoleService;
import com.skrivet.supports.authority.service.core.role.entity.add.RoleAddSQ;
import com.skrivet.supports.authority.service.core.role.entity.select.RoleListSP;
import com.skrivet.supports.authority.service.core.role.entity.update.RoleUpdateSQ;
import com.skrivet.supports.authority.web.core.role.entity.add.RoleAddVQ;
import com.skrivet.supports.authority.web.core.role.entity.select.RoleDetailVP;
import com.skrivet.supports.authority.web.core.role.entity.select.RoleListVP;
import com.skrivet.supports.authority.web.core.role.entity.update.RoleUpdateVQ;
import com.skrivet.web.core.annotations.AuthMappingIgnore;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.AuthMapping;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@SkrivetDoc
@Controller
@RequestMapping("/authority/role")
@DynamicDataBase
@Api(tags = "角色接口")
@ApiSort(value = 6)
public class RoleController extends BasicController {

    @Autowired
    private RoleService roleService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "角色模块所有接口所需权限")
    @ResponseBody
    @ApiOperation(value = "角色模块所有接口所需权限")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "添加角色")
    @ResponseBody
    @ApiOperation(value = "添加角色")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:role:basic", "skrivet:role:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody RoleAddVQ entity, LoginUser loginUser) {
        RoleAddSQ parameter = entityConvert.convert(entity, RoleAddSQ.class);
        String id = roleService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "删除角色")
    @ResponseBody
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:role:basic", "skrivet:role:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @ApiParam(value = "角色编号", required = true)
            @NotNull(message = "角色编号不能为空")
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(roleService.deleteById(id, loginUser));
    }

    @ApiOperationSupport(order = 4)

    @ResMsg(tag = "批量删除角色")
    @ResponseBody
    @ApiOperation(value = "批量删除角色")
    @DeleteMapping("/deleteMulti")
    @RequiresPermissions({"skrivet:role:basic", "skrivet:role:delete", "skrivet:backstage"})
    public ResponseJson<Long> deleteMulti(
            @ApiParam(value = "角色编号", required = true)
            @NotNull(message = "角色编号不能为空")
                    String[] ids,
            LoginUser loginUser) {
        return ResponseBuilder.success(roleService.deleteMultiById(ids, loginUser));
    }

    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "修改角色全部数据")
    @ResponseBody
    @ApiOperation(value = "修改角色全部数据")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:role:basic", "skrivet:role:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody RoleUpdateVQ entity, LoginUser loginUser) {
        RoleUpdateSQ parameter = entityConvert.convert(entity, RoleUpdateSQ.class);
        return ResponseBuilder.success(roleService.update(parameter, loginUser));
    }

    @ApiOperationSupport(order = 6)
    @ResMsg(tag = "修改用户角色")
    @ResponseBody
    @ApiOperation(value = "修改用户角色")
    @PutMapping("/updateUserRole")
    @RequiresPermissions({"skrivet:role:basic", "skrivet:role:user:update", "skrivet:backstage"})
    public ResponseJson updateUserRole(
            @ApiParam(value = "用户编号", required = true)
            @NotNull(message = "用户角色编号不能为空")
                    String userId,
            @ApiParam(value = "角色编号", required = true)
             String[] roleIds,
            LoginUser loginUser) {
        roleService.updateUserRoles(userId, roleIds, loginUser);
        return ResponseBuilder.success();
    }

    @ApiOperationSupport(order = 7)
    @ResMsg(tag = "角色列表")
    @ResponseBody
    @ApiOperation(value = "角色列表")
    @GetMapping("/selectList")
    @RequiresPermissions({"skrivet:role:basic", "skrivet:role:list", "skrivet:backstage"})
    public ResponseJson<List<RoleListVP>> selectList(LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convertList(roleService.selectList(loginUser), RoleListVP.class));
    }

    @ApiOperationSupport(order = 8)
    @ResMsg(tag = "用户角色列表")
    @ResponseBody
    @ApiOperation(value = "用户角色列表")
    @GetMapping("/selectUserRole")
    @RequiresPermissions({"skrivet:role:basic", "skrivet:role:user:list", "skrivet:backstage"})
    public ResponseJson<List<RoleDetailVP>> selectUserRole(
            @ApiParam(value = "用户编号", required = true)
            @NotNull(message = "用户编号不能为空")
                    String userId,
            LoginUser loginUser) {
        List<RoleListSP> roleListSPList = roleService.selectList(LoginUser.IGNORED);
        List<RoleDetailVP> result = new ArrayList<>();
        List<String> roleRoleIds = roleService.selectUserRoleIds(userId, loginUser);
        if (!CollectionUtils.isEmpty(roleRoleIds) && !CollectionUtils.isEmpty(roleListSPList)) {
            roleListSPList.stream().forEach(roleListSP -> {
                String tmp = com.skrivet.core.toolkit.CollectionUtils.findOne(roleRoleIds, data -> data.equals(roleListSP.getId()));
                if (tmp != null) {
                    result.add(entityConvert.convert(roleListSP, RoleDetailVP.class));
                }
            });
        }
        return ResponseBuilder.success(result);
    }

    @ApiOperationSupport(order = 9)
    @ResMsg(tag = "根据角色编号查找角色详情")
    @ResponseBody
    @ApiOperation(value = "根据角色编号查找角色详情")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:role:basic", "skrivet:role:detail", "skrivet:backstage"})
    public ResponseJson<RoleDetailVP> selectOneById(
            @NotNull(message = "角色编号不能为空")
            @ApiParam(value = "角色编号", required = true)
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(roleService.selectOneById(id, loginUser), RoleDetailVP.class));
    }

}
