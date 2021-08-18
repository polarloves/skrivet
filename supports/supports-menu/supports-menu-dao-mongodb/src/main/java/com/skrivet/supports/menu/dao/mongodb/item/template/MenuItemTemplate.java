package com.skrivet.supports.menu.dao.mongodb.item.template;

import com.skrivet.core.common.entity.BasicEntity;
import com.skrivet.core.common.entity.NameValueEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "skrivet_menu")
public class MenuItemTemplate extends BasicEntity {
    @Id
    @Field("ID")
    //编号
    private String id;
    @Field("PARENT_ID")
    //父编号
    private String parentId;
    @Field("MENU_KEY")
    //名称
    private String menuKey;
    @Field("TITLE")
    //标题
    private String title;
    @Field("COMPONENT")
    //页面
    private String component;
    @Field("ICON")
    //图标
    private String icon;
    @Field("PATH")
    //路径
    private String path;
    @Field("ORDER_NUM")
    //排序号
    private String orderNum;
    @Field("REDIRECT")
    //重定向地址
    private String redirect;
    @Field("PERMISSION_LIST")
    //权限列表
    private List<NameValueEntity> permissionList;
    //角色列表
    @Field("ROLE_LIST")
    private List<NameValueEntity> roleList;
    @Field("HIDE_ON_EMPTY")
    //子菜单为空时隐藏
    private String hideOnEmpty;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMenuKey() {
        return this.menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComponent() {
        return this.component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getRedirect() {
        return this.redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

	public List<NameValueEntity> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<NameValueEntity> permissionList) {
		this.permissionList = permissionList;
	}

	public List<NameValueEntity> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<NameValueEntity> roleList) {
		this.roleList = roleList;
	}

	public String getHideOnEmpty() {
        return this.hideOnEmpty;
    }

    public void setHideOnEmpty(String hideOnEmpty) {
        this.hideOnEmpty = hideOnEmpty;
    }
}