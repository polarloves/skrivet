package com.skrivet.supports.authority.web.impl.permission;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.ValidationExp;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.security.core.service.SecurityService;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.authority.service.core.permission.PermissionService;
import com.skrivet.supports.authority.service.core.permission.entity.add.PermissionAddSQ;
import com.skrivet.supports.authority.service.core.permission.entity.select.PermissionListSP;
import com.skrivet.supports.authority.service.core.permission.entity.update.PermissionUpdateSQ;
import com.skrivet.supports.authority.web.core.permission.entity.add.PermissionAddVQ;
import com.skrivet.supports.authority.web.core.permission.entity.select.PermissionDetailVP;
import com.skrivet.supports.authority.web.core.permission.entity.select.PermissionListVP;
import com.skrivet.supports.authority.web.core.permission.entity.update.PermissionUpdateVQ;
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
@RequestMapping("/authority/permission")
@DynamicDataBase
@Api(tags = "权限接口")
@ApiSort(value = 7)
public class PermissionController extends BasicController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SecurityService securityService;
    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "权限模块所有接口所需权限")
    @ResponseBody
    @ApiOperation(value = "权限模块所有接口所需权限")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "添加权限")
    @ResponseBody
    @ApiOperation(value = "添加权限")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:permission:basic","skrivet:permission:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody PermissionAddVQ entity, LoginUser loginUser) {
        PermissionAddSQ parameter = entityConvert.convert(entity, PermissionAddSQ.class);
        String id = permissionService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "删除权限")
    @ResponseBody
    @ApiOperation(value = "删除权限")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:permission:basic","skrivet:permission:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @ApiParam(value = "权限编号", required = true)
            @NotNull(message = "权限编号不能为空")
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(permissionService.deleteById(id, loginUser));
    }

    @ApiOperationSupport(order = 4)
    @ResMsg(tag = "批量删除权限")
    @ResponseBody
    @ApiOperation(value = "批量删除权限")
    @DeleteMapping("/deleteMulti")
    @RequiresPermissions({"skrivet:permission:basic","skrivet:permission:delete", "skrivet:backstage"})
    public ResponseJson<Long> deleteMulti(
            @ApiParam(value = "权限编号", required = true)
            @NotNull(message = "权限编号不能为空") String[] ids,
            LoginUser loginUser) {
        return ResponseBuilder.success(permissionService.deleteMultiById(ids, loginUser));
    }
    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "修改权限全部数据")
    @ResponseBody
    @ApiOperation(value = "修改权限全部数据")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:permission:basic","skrivet:permission:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody PermissionUpdateVQ entity, LoginUser loginUser) {
        PermissionUpdateSQ parameter = entityConvert.convert(entity, PermissionUpdateSQ.class);
        if(parameter.getId().equals(parameter.getParentId())){
            throw new ValidationExp("不能选择自身作为父级");
        }
        return ResponseBuilder.success(permissionService.update(parameter, loginUser));
    }
    @ApiOperationSupport(order = 6)
    @ResMsg(tag = "修改角色权限")
    @ResponseBody
    @ApiOperation(value = "修改角色权限")
    @PutMapping("/updateRolePermission")
    @RequiresPermissions({"skrivet:permission:basic","skrivet:permission:role:update", "skrivet:backstage"})
    public ResponseJson updateRolePermission(
            @ApiParam(value = "角色编号", required = true)
            @NotNull(message = "角色编号不能为空")
                    String roleId,
            @ApiParam(value = "权限编号", required = true)
                    String[] permissionIds,
            LoginUser loginUser) {
        permissionService.updateRolePermissions(roleId, permissionIds, loginUser);
        return ResponseBuilder.success();
    }
    @ApiOperationSupport(order = 7)
    @ResMsg(tag = "权限列表")
    @ResponseBody
    @ApiOperation(value = "权限列表")
    @GetMapping("/selectList")
    @RequiresPermissions({"skrivet:permission:basic","skrivet:permission:list", "skrivet:backstage"})
    public ResponseJson<List<PermissionListVP>> selectList(LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convertList(permissionService.selectList(loginUser), PermissionListVP.class));
    }
    @ApiOperationSupport(order = 8)
    @ResMsg(tag = "角色权限列表")
    @ResponseBody
    @ApiOperation(value = "角色权限列表")
    @GetMapping("/selectRolePermission")
    @RequiresPermissions({"skrivet:permission:basic","skrivet:permission:role:list", "skrivet:backstage"})
    public ResponseJson<List<PermissionDetailVP>> selectRolePermission(
            @ApiParam(value = "角色编号", required = true)
            @NotNull(message = "角色编号不能为空")
                    String roleId,
            LoginUser loginUser) {
        List<PermissionListSP> permissionListSPList = permissionService.selectList(LoginUser.IGNORED);
        List<PermissionDetailVP> result = new ArrayList<>();
        List<String> rolePermissionIds = permissionService.selectRolePermissionIds(roleId, loginUser);
        if (!CollectionUtils.isEmpty(rolePermissionIds) && !CollectionUtils.isEmpty(permissionListSPList)) {
            permissionListSPList.stream().forEach((permissionListSP) -> {
                String tmp = com.skrivet.core.toolkit.CollectionUtils.findOne(rolePermissionIds, data -> permissionListSP.getId().equals(data));
                if (tmp != null) {
                    result.add(entityConvert.convert(permissionListSP, PermissionDetailVP.class));
                }
            });
        }
        return ResponseBuilder.success(result);
    }
    @ApiOperationSupport(order = 9)
    @ResMsg(tag = "资源权限列表")
    @ResponseBody
    @ApiOperation(value = "资源权限列表")
    @GetMapping("/selectResourcePermissions")
    @RequiresPermissions({"skrivet:permission:basic","skrivet:permission:resource:list", "skrivet:backstage"})
    public ResponseJson<List<PermissionListVP>> selectResourcePermissions(
            @ApiParam(value = "资源编号", required = true)
            @NotNull(message = "资源编号不能为空")
                    String resourceId,
            LoginUser loginUser) {
        List<PermissionListSP> permissionListSPList = permissionService.selectList(LoginUser.IGNORED);
        List<PermissionListVP> result = new ArrayList<>();
        List<String> permissionIds = permissionService.selectResourcePermissionIds(resourceId, loginUser);
        if (!CollectionUtils.isEmpty(permissionIds) && !CollectionUtils.isEmpty(permissionListSPList)) {
            permissionIds.stream().forEach(permissionId -> {
                PermissionListSP tmp = com.skrivet.core.toolkit.CollectionUtils.findOne(permissionListSPList, data -> data.getId().equals(permissionId));
                if (tmp != null) {
                    result.add(entityConvert.convert(tmp, PermissionListVP.class));
                }
            });
        }
        return ResponseBuilder.success(result);
    }
    @ApiOperationSupport(order = 10)
    @ResMsg(tag = "修改资源权限")
    @ResponseBody
    @ApiOperation(value = "修改资源权限")
    @PutMapping("/updateResourcePermission")
    @RequiresPermissions({"skrivet:permission:basic","skrivet:permission:resource:update", "skrivet:backstage"})
    public ResponseJson updateResourcePermissions(
            @ApiParam(value = "资源编号", required = true)
            @NotNull(message = "资源编号不能为空")
                    String resourceId,
            @ApiParam(value = "权限编号", required = true)
                    String[] permissionIds,
            LoginUser loginUser) {
        permissionService.updateResourcePermissions(resourceId, permissionIds, loginUser);
        return ResponseBuilder.success();
    }
    @ApiOperationSupport(order = 11)
    @ResMsg(tag = "根据权限编号查找权限详情")
    @ResponseBody
    @ApiOperation(value = "根据权限编号查找权限详情")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:permission:basic","skrivet:permission:detail", "skrivet:backstage"})
    public ResponseJson<PermissionDetailVP> selectOneById(
            @ApiParam(value = "权限编号", required = true)
            @NotNull(message = "权限编号不能为空")
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(permissionService.selectOneById(id, loginUser), PermissionDetailVP.class));
    }
    @ApiOperationSupport(order = 12)
    @ResMsg(tag = "重新加载资源权限")
    @ResponseBody
    @ApiOperation(value = "重新加载资源权限")
    @GetMapping("/reloadResourcePermissions")
    @RequiresPermissions({"skrivet:permission:basic","skrivet:permission:resource:reload", "skrivet:backstage"})
    public ResponseJson reloadResourcePermissions(LoginUser loginUser) {
        permissionService.reloadResourcePermissions();
        return ResponseBuilder.success();
    }
    @ApiOperationSupport(order = 13)
    @ResMsg(tag = "重新加载用户权限")
    @ResponseBody
    @ApiOperation(value = "重新加载用户权限")
    @GetMapping("/reloadUserPermissions")
    @RequiresPermissions({"skrivet:permission:basic","skrivet:permission:user:reload", "skrivet:backstage"})
    public ResponseJson reloadUserPermissions(@ApiParam(value = "用户编号", required = false)
                                                      String userId, LoginUser loginUser) {
        securityService.removePermissionCache(userId);
        return ResponseBuilder.success();
    }
}
