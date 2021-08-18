package com.skrivet.supports.menu.web.core.entity.item.select;

import com.skrivet.core.common.entity.BasicEntity;
import com.skrivet.core.common.entity.NameValueEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "菜单列表")
public class MenuItemListVP extends BasicEntity {
	@ApiModelProperty(value = "编号")
	//编号
	private String id;
	@ApiModelProperty(value = "父编号")
	//父编号
	private String parentId;
	@ApiModelProperty(value = "名称")
	//名称
	private String menuKey;
	@ApiModelProperty(value = "标题")
	//标题
	private String title;
	@ApiModelProperty(value = "页面")
	//页面
	private String component;
	@ApiModelProperty(value = "图标")
	//图标
	private String icon;
	@ApiModelProperty(value = "路径")
	//路径
	private String path;
	@ApiModelProperty(value = "排序号")
	//排序号
	private Integer orderNum;
	@ApiModelProperty(value = "重定向地址")
	//重定向地址
	private String redirect;
	@ApiModelProperty(value = "权限列表")
	//权限列表
	private List<NameValueEntity> permissionList;
	@ApiModelProperty(value = "角色列表")
	//角色列表
	private List<NameValueEntity> roleList;
	@ApiModelProperty(value = "子菜单为空时隐藏")
	//子菜单为空时隐藏
	private String hideOnEmpty;
	
	public String  getId() 	{
		return this.id;
	}

	public void setId(String id)  {
		this.id = id;
	}

	public String  getParentId() 	{
		return this.parentId;
	}

	public void setParentId(String parentId)  {
		this.parentId = parentId;
	}
	
	public String getMenuKey() 	{
		return this.menuKey;
	}

	public void setMenuKey(String menuKey)  {
		this.menuKey = menuKey;
	}
	
	public String getTitle() 	{
		return this.title;
	}

	public void setTitle(String title)  {
		this.title = title;
	}
	
	public String getComponent() 	{
		return this.component;
	}

	public void setComponent(String component)  {
		this.component = component;
	}
	
	public String getIcon() 	{
		return this.icon;
	}

	public void setIcon(String icon)  {
		this.icon = icon;
	}
	
	public String getPath() 	{
		return this.path;
	}

	public void setPath(String path)  {
		this.path = path;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getRedirect() 	{
		return this.redirect;
	}

	public void setRedirect(String redirect)  {
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

	public String getHideOnEmpty() 	{
		return this.hideOnEmpty;
	}

	public void setHideOnEmpty(String hideOnEmpty)  {
		this.hideOnEmpty = hideOnEmpty;
	}
}