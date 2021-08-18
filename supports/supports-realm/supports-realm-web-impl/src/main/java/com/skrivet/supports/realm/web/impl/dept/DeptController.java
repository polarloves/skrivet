package com.skrivet.supports.realm.web.impl.dept;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.exception.ValidationExp;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.realm.service.core.dept.DeptService;
import com.skrivet.supports.realm.service.core.dept.entity.add.DeptAddSQ;
import com.skrivet.supports.realm.service.core.dept.entity.update.DeptUpdateSQ;
import com.skrivet.supports.realm.web.core.dept.entity.add.DeptAddVQ;
import com.skrivet.supports.realm.web.core.dept.entity.select.DeptDetailVP;
import com.skrivet.supports.realm.web.core.dept.entity.select.DeptListVP;
import com.skrivet.supports.realm.web.core.dept.entity.update.DeptUpdateVQ;
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

@SkrivetDoc
@Controller
@RequestMapping("/realm/dept")
@DynamicDataBase
@Api(tags = "部门接口")
@ApiSort(value = 5)
public class DeptController extends BasicController {
    @Autowired
    private DeptService deptService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "部门模块所有接口所需权限")
    @ResponseBody
    @ApiOperation(value = "部门模块所有接口所需权限")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "添加部门")
    @ResponseBody
    @ApiOperation(value = "添加部门")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:dept:basic", "skrivet:dept:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody DeptAddVQ entity, LoginUser loginUser) {
        DeptAddSQ parameter = entityConvert.convert(entity, DeptAddSQ.class);
        String id = deptService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "删除部门")
    @ResponseBody
    @ApiOperation(value = "删除部门")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:dept:basic", "skrivet:dept:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @ApiParam(value = "部门编号", required = true)
            @NotNull(message = "部门编号不能为空")
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(deptService.deleteById(id, loginUser));
    }


    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "修改部门全部数据")
    @ResponseBody
    @ApiOperation(value = "修改部门全部数据")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:dept:basic", "skrivet:dept:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody DeptUpdateVQ entity, LoginUser loginUser) {
        DeptUpdateSQ parameter = entityConvert.convert(entity, DeptUpdateSQ.class);
        if (parameter.getId().equals(parameter.getParentId())) {
            throw new ValidationExp("不能选择自身作为父级");
        }
        return ResponseBuilder.success(deptService.update(parameter, loginUser));
    }

    @ApiOperationSupport(order = 7)
    @ResMsg(tag = "部门列表")
    @ResponseBody
    @ApiOperation(value = "部门列表")
    @GetMapping("/selectList")
    @RequiresPermissions({"skrivet:dept:basic", "skrivet:dept:list", "skrivet:backstage"})
    public ResponseJson<List<DeptListVP>> selectList(LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convertList(deptService.selectList(loginUser), DeptListVP.class));
    }

    @ApiOperationSupport(order = 8)
    @ResMsg(tag = "部门详情")
    @ResponseBody
    @ApiOperation(value = "部门详情")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:dept:basic", "skrivet:dept:detail", "skrivet:backstage"})
    public ResponseJson<DeptDetailVP> selectOneById(
            @ApiParam(value = "部门编号", required = true)
            @NotNull(message = "部门编号不能为空")
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(deptService.selectOneById(id, loginUser), DeptDetailVP.class));
    }
}
