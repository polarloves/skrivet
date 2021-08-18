package com.skrivet.supports.realm.web.impl.user;

import com.skrivet.core.common.annotations.DynamicDataBase;
import com.skrivet.core.common.annotations.Within;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.core.common.entity.PageList;
import com.skrivet.core.common.exception.NotSupportExp;
import com.skrivet.core.toolkit.DateUtils;
import com.skrivet.plugins.security.core.annotations.RequiresPermissions;
import com.skrivet.plugins.security.core.encryption.EncryptionService;
import com.skrivet.plugins.security.core.service.SecurityService;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import com.skrivet.plugins.web.impl.controller.BasicController;
import com.skrivet.supports.realm.web.core.user.entity.select.UserDetailVP;
import com.skrivet.supports.realm.web.core.user.entity.select.UserListVP;
import com.skrivet.supports.realm.web.core.user.entity.select.UserSelectPageVQ;
import com.skrivet.supports.realm.service.core.user.UserService;
import com.skrivet.supports.realm.service.core.user.entity.add.UserAddSQ;
import com.skrivet.supports.realm.service.core.user.entity.select.UserDetailSP;
import com.skrivet.supports.realm.service.core.user.entity.select.UserListSP;
import com.skrivet.supports.realm.service.core.user.entity.select.UserSelectPageSQ;
import com.skrivet.supports.realm.service.core.user.entity.update.PasswordUpdateSQ;
import com.skrivet.supports.realm.service.core.user.entity.update.UserUpdateSQ;
import com.skrivet.supports.realm.web.core.user.entity.add.UserAddVQ;
import com.skrivet.supports.realm.web.core.user.entity.update.PasswordUpdateVQ;
import com.skrivet.supports.realm.web.core.user.entity.update.UserUpdateVQ;
import com.skrivet.web.core.annotations.AuthMappingIgnore;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.AuthMapping;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@SkrivetDoc
@Controller
@RequestMapping("/realm/user")
@DynamicDataBase
@ApiSort(value = 4)
@Api(tags = "用户接口")
public class UserController extends BasicController {
    @Autowired
    private UserService userService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private SecurityService securityService;

    @ApiOperationSupport(order = 1)
    @ResMsg(tag = "用户模块所有接口所需权限")
    @ResponseBody
    @ApiOperation(value = "用户模块所有接口所需权限")
    @GetMapping("/permissionList")
    @AuthMappingIgnore
    public ResponseJson<List<AuthMapping>> permissionList() {
        return ResponseBuilder.success(authMappingList());
    }

    @ApiOperationSupport(order = 2)
    @ResMsg(tag = "添加用户")
    @ResponseBody
    @ApiOperation(value = "添加用户", notes = "添加一个用户,其会对用户密码进行加密,同时清空此用户的缓存信息,并且发布用户创建的广播")
    @PutMapping("/insert")
    @RequiresPermissions({"skrivet:user:basic", "skrivet:user:add", "skrivet:backstage"})
    public ResponseJson<String> insert(@Valid @RequestBody UserAddVQ entity, LoginUser loginUser) {
        UserAddSQ parameter = entityConvert.convert(entity, UserAddSQ.class);
        parameter.setSalt(encryptionService.generateSalt());
        parameter.setPassword(encryptionService.encryptionPassword(parameter.getPassword(), parameter.getSalt()));
        parameter.setCreateDate(DateUtils.formatDateTime(new Date()));
        String id = userService.insert(parameter, loginUser);
        return ResponseBuilder.success(id);
    }

    @ApiOperationSupport(order = 3)
    @ResMsg(tag = "删除用户")
    @ResponseBody
    @ApiOperation(value = "删除用户", notes = "删除一个用户,对用同时清空此用户的缓存信息,并且在调用前发布用户删除的广播")
    @DeleteMapping("/delete")
    @RequiresPermissions({"skrivet:user:basic", "skrivet:user:delete", "skrivet:backstage"})
    public ResponseJson<Long> delete(
            @NotNull(message = "用户编号不能为空")
            @ApiParam(value = "用户编号", required = true)
                    String id,
            LoginUser loginUser) {
        return ResponseBuilder.success(userService.deleteById(id, loginUser));
    }

    @ApiOperationSupport(order = 4)

    @ResMsg(tag = "批量删除用户")
    @ResponseBody
    @ApiOperation(value = "批量删除用户")
    @DeleteMapping("/deleteMulti")
    @RequiresPermissions({"skrivet:user:basic", "skrivet:user:delete", "skrivet:backstage"})
    public ResponseJson<Long> deleteMulti(
            @NotNull(message = "用户编号不能为空")
            @ApiParam(value = "用户编号", required = true)
                    String[] ids, LoginUser loginUser) {
        return ResponseBuilder.success(userService.deleteMultiById(ids, loginUser));
    }

    @ApiOperationSupport(order = 5)
    @ResMsg(tag = "修改用户全部数据")
    @ResponseBody
    @ApiOperation(value = "修改用户全部数据")
    @PutMapping("/update")
    @RequiresPermissions({"skrivet:user:basic", "skrivet:user:update", "skrivet:backstage"})
    public ResponseJson<Long> update(@Valid @RequestBody UserUpdateVQ entity, LoginUser loginUser) {
        UserUpdateSQ parameter = entityConvert.convert(entity, UserUpdateSQ.class);
        return ResponseBuilder.success(userService.update(parameter, loginUser));
    }


    @ApiOperationSupport(order = 7)
    @ResMsg(tag = "用户分页列表")
    @ResponseBody
    @ApiOperation(value = "用户分页列表")
    @GetMapping("/pageList")
    @RequiresPermissions({"skrivet:user:basic", "skrivet:user:list", "skrivet:backstage"})
    public ResponseJson<List<UserListVP>> pageList(@Valid UserSelectPageVQ entity, LoginUser loginUser) {
        PageList<UserListSP> page = userService.selectPageList(entityConvert.convert(entity, UserSelectPageSQ.class), loginUser);
        List<UserListVP> userListVPList = entityConvert.convertList(page.getData(), UserListVP.class);
        return ResponseBuilder.success(userListVPList, page.getCount());
    }



    @ApiOperationSupport(order = 9)
    @ResMsg(tag = "根据用户编号查找用户详情")
    @ResponseBody
    @ApiOperation(value = "根据用户编号查找用户详情")
    @GetMapping("/selectOneById")
    @RequiresPermissions({"skrivet:user:basic", "skrivet:user:detail", "skrivet:backstage"})
    public ResponseJson<UserDetailVP> selectOneById(@NotNull(message = "用户编号不能为空") @ApiParam(value = "用户编号", required = true) String id, LoginUser loginUser) {
        return ResponseBuilder.success(entityConvert.convert(userService.selectOneById(id, loginUser), UserDetailVP.class));
    }

    @ApiOperationSupport(order = 12)
    @ResMsg(tag = "修改密码")
    @ResponseBody
    @ApiOperation(value = "修改密码")
    @PutMapping("/changePassword")
    @RequiresPermissions({"skrivet:user:basic", "skrivet:user:password:update", "skrivet:backstage"})
    public ResponseJson changePassword(@RequestBody PasswordUpdateVQ passwordUpdateVQ, LoginUser loginUser) {
        PasswordUpdateSQ parameter = new PasswordUpdateSQ();
        parameter.setSalt(encryptionService.generateSalt());
        parameter.setId(passwordUpdateVQ.getId());
        parameter.setPassword(encryptionService.encryptionPassword(passwordUpdateVQ.getPassword(), parameter.getSalt()));
        UserDetailSP user = userService.selectOneById(parameter.getId(), LoginUser.IGNORED);
        userService.updatePassword(parameter, loginUser);
        //删除用户缓存...
        securityService.removeTokenCache(user.getUserName());
        //踢出在线用户
        try {
            securityService.shotOffByUserId(passwordUpdateVQ.getId(), "用户密码已被变更,请重新登录!");
        } catch (NotSupportExp e) {

        }
        return ResponseBuilder.success();
    }

    @ApiOperationSupport(order = 13)
    @ResMsg(tag = "禁用用户")
    @ResponseBody
    @ApiOperation(value = "禁用用户")
    @PutMapping("/disable")
    @RequiresPermissions({"skrivet:user:basic", "skrivet:user:disable", "skrivet:backstage"})
    public ResponseJson disable(@NotNull(message = "用户编号不能为空") @ApiParam(value = "用户编号", required = true) String id,
                                @NotNull(message = "禁用原因不能为空") @ApiParam(value = "禁用原因", required = true)
                                @Length(min = 10, max = 500, message = "禁用原因最少10位,最多为500位") String reason, LoginUser loginUser) {

        UserDetailSP user = userService.selectOneById(id, LoginUser.IGNORED);
        userService.disable(id, reason, loginUser);
        //删除用户缓存...
        securityService.removeTokenCache(user.getUserName());
        //踢出在线用户
        try {
            securityService.shotOffByUserId(id, reason);
        } catch (NotSupportExp e) {

        }

        return ResponseBuilder.success();
    }

    @ApiOperationSupport(order = 14)
    @ResMsg(tag = "启用用户")
    @ResponseBody
    @ApiOperation(value = "启用用户")
    @PutMapping("/enable")
    @RequiresPermissions({"skrivet:user:basic", "skrivet:user:enable", "skrivet:backstage"})
    public ResponseJson enable(@NotNull(message = "用户编号不能为空") @ApiParam(value = "用户编号", required = true) String id,
                               LoginUser loginUser) {
        UserDetailSP user = userService.selectOneById(id, LoginUser.IGNORED);
        userService.enable(id, loginUser);
        //删除用户缓存...
        securityService.removeTokenCache(user.getUserName());
        return ResponseBuilder.success();
    }
}
