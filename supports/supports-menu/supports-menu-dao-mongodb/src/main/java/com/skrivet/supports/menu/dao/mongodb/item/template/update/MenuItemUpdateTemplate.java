package com.skrivet.supports.menu.dao.mongodb.item.template.update;


import com.skrivet.core.common.entity.NameValueEntity;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public class MenuItemUpdateTemplate {
    private String parentId;
    //名称
    private String menuKey;
    //标题
    private String title;
    //页面
    private String component;
    //图标
    private String icon;
    //路径
    private String path;
    //排序号
    private String orderNum;
    //重定向地址
    private String redirect;
    //权限列表
    private List<NameValueEntity> permissionList;
    //角色列表
    private List<NameValueEntity> roleList;
    //子菜单为空时隐藏
    private String hideOnEmpty;
}