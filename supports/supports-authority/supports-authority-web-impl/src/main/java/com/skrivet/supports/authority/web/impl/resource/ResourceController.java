package com.skrivet.supports.authority.web.impl.resource;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.authority.service.core.permission.PermissionService;
import com.skrivet.supports.authority.service.core.permission.entity.select.PermissionListSP;
import com.skrivet.supports.authority.service.core.resource.ResourceService;
import com.skrivet.supports.authority.service.core.resource.entity.add.ResourceAddSQ;
import com.skrivet.supports.authority.service.core.resource.entity.select.ResourceListSP;
import com.skrivet.supports.authority.service.core.resource.entity.select.ResourceSelectPageSQ;
import com.skrivet.supports.authority.service.core.resource.entity.update.ResourceUpdateSQ;
import com.skrivet.supports.authority.web.core.resource.entity.add.ResourceAddVQ;
import com.skrivet.supports.authority.web.core.resource.entity.select.ResourceDetailVP;
import com.skrivet.supports.authority.web.core.resource.entity.select.ResourceListVP;
import com.skrivet.supports.authority.web.core.resource.entity.select.ResourcePermissionsVQ;
import com.skrivet.supports.authority.web.core.resource.entity.select.ResourceSelectPageVQ;
import com.skrivet.supports.authority.web.core.resource.entity.update.ResourceUpdateVQ;
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
@RequestMapping("/authority/resource")
@DynamicDataBase
@Api(tags = "????????????")
@ApiSort(value = 8)
public class ResourceController extends BasicController {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private PermissionService permissionService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "????????????????????????????????????")
    @ResponseBody
    @ApiOperation(value = "????????????????????????????????????")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "????????????")
    @ResponseBody
    @ApiOperation(value = "????????????")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:resource:basic", "skrivet:resource:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody ResourceAddVQ entity, LoginUser loginUser) {
        ResourceAddSQ parameter = entityConvert.convert(entity, ResourceAddSQ.class);
        String id = resourceService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "????????????")
    @ResponseBody
    @ApiOperation(value = "????????????")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:resource:basic", "skrivet:resource:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @ApiParam(value = "????????????", required = true)
            @NotNull(message = "????????????????????????")
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(resourceService.deleteById(id, loginUser));
    }

    @ApiOperationSupport(order = 4)
    @ResMsg(tag = "??????????????????")
    @ResponseBody
    @ApiOperation(value = "??????????????????")
    @DeleteMapping("/deleteMulti")
    @RequiresPermissions({"skrivet:resource:basic", "skrivet:resource:delete", "skrivet:backstage"})
    public ResponseJson<Long> deleteMulti(
            @ApiParam(value = "????????????", required = true)
            @NotNull(message = "????????????????????????")
                    String[] ids,
            LoginUser loginUser) {
        return ResponseBuilder.success(resourceService.deleteMultiById(ids, loginUser));
    }

    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "????????????????????????")
    @ResponseBody
    @ApiOperation(value = "????????????????????????")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:resource:basic", "skrivet:resource:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody ResourceUpdateVQ entity, LoginUser loginUser) {
        ResourceUpdateSQ parameter = entityConvert.convert(entity, ResourceUpdateSQ.class);
        return ResponseBuilder.success(resourceService.update(parameter, loginUser));
    }

    @ApiOperationSupport(order = 6)
    @ResMsg(tag = "??????????????????")
    @ResponseBody
    @ApiOperation(value = "??????????????????")
    @GetMapping("/resourcePermissions")
    public ResponseJson<List<ResourcePermissionsVQ>> resourcePermissions() {
        List<ResourceListSP> resourceListSPS = resourceService.selectList(LoginUser.IGNORED);
        if (!CollectionUtils.isEmpty(resourceListSPS)) {
            List<ResourcePermissionsVQ> result = new ArrayList<>(resourceListSPS.size());
            List<PermissionListSP> permissionListSPList = permissionService.selectList(LoginUser.IGNORED);
            resourceListSPS.stream().forEach(resourceListSP -> {
                List<String> permissionIds = permissionService.selectResourcePermissionIds(resourceListSP.getId(), LoginUser.IGNORED);
                List<String> permissionList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(permissionIds)) {
                    permissionIds.stream().forEach(permissionId -> {
                        PermissionListSP permissionListSP = com.skrivet.core.toolkit.CollectionUtils.findOne(permissionListSPList, data -> permissionId.equals(data.getId()));
                        if (permissionListSP != null) {
                            permissionList.add(permissionListSP.getValue());
                        }
                    });
                }
                ResourcePermissionsVQ tmp=new ResourcePermissionsVQ();
                tmp.setPermissions(permissionList);
                tmp.setType(resourceListSP.getType());
                tmp.setValue(resourceListSP.getValue());
                result.add(tmp);
            });
            return ResponseBuilder.success(result);
        }
        return ResponseBuilder.success();
    }

    @ApiOperationSupport(order = 7)
    @ResMsg(tag = "??????????????????")
    @ResponseBody
    @ApiOperation(value = "??????????????????")
    @GetMapping("/pageList")
    @RequiresPermissions({"skrivet:resource:basic", "skrivet:resource:list", "skrivet:backstage"})
    public ResponseJson<List<ResourceListVP>> pageList(@Valid ResourceSelectPageVQ entity, LoginUser loginUser) {
        PageList<ResourceListSP> page = resourceService.selectPageList(entityConvert.convert(entity, ResourceSelectPageSQ.class), loginUser);
        List<ResourceListVP> dictListVPList = entityConvert.convertList(page.getData(), ResourceListVP.class);
        return ResponseBuilder.success(dictListVPList, page.getCount());
    }

    @ApiOperationSupport(order = 8)
    @ResMsg(tag = "????????????????????????????????????")
    @ResponseBody
    @ApiOperation(value = "????????????????????????????????????")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:resource:basic", "skrivet:resource:detail", "skrivet:backstage"})
    public ResponseJson<ResourceDetailVP> selectOneById(
            @ApiParam(value = "????????????", required = true)
            @NotNull(message = "????????????????????????")
                    String id, LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(resourceService.selectOneById(id, loginUser), ResourceDetailVP.class));
    }


}
