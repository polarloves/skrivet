package com.skrivet.supports.menu.web.core.entity.item.update;

import com.skrivet.core.common.entity.BasicEntity;
import com.skrivet.core.common.entity.NameValueEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.validator.constraints.Length;	
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description = "菜单修改的参数")
public class MenuItemUpdateVQ extends BasicEntity {
	@ApiModelProperty(value = "编号",required = true)
	@NotNull(message = "编号不能为空")
	//编号
	private String id;
	
	@ApiModelProperty(value = "父编号")
	//父编号
	private String parentId;
	@NotNull(message = "名称不能为空")
	@Length(max = 255,min=0,message = "名称长度为0-255")
	@ApiModelProperty(value = "名称",required = true)
	//名称
	private String menuKey;
	@NotNull(message = "标题不能为空")
	@Length(max = 255,min=0,message = "标题长度为0-255")
	@ApiModelProperty(value = "标题",required = true)
	//标题
	private String title;
	@NotNull(message = "页面不能为空")
	@Length(max = 255,min=0,message = "页面长度为0-255")
	@ApiModelProperty(value = "页面",required = true)
	//页面
	private String component;
	@Length(max = 255,min=0,message = "图标长度为0-255")
	@ApiModelProperty(value = "图标")
	//图标
	private String icon;
	@Length(max = 255,min=0,message = "路径长度为0-255")
	@ApiModelProperty(value = "路径")
	//路径
	private String path;
	@NotNull(message = "排序号不能为空")
	@ApiModelProperty(value = "排序号",required = true)
	//排序号
	private Integer orderNum;
	@Length(max = 255,min=0,message = "重定向地址长度为0-255")
	@ApiModelProperty(value = "重定向地址")
	//重定向地址
	private String redirect;
	@ApiModelProperty(value = "权限列表")
	//权限列表
	private List<NameValueEntity> permissionList;
	@ApiModelProperty(value = "角色列表")
	//角色列表
	private List<NameValueEntity> roleList;
	@NotNull(message = "子菜单为空时隐藏不能为空")
	@Length(max = 10,min=0,message = "子菜单为空时隐藏长度为0-10")
	@ApiModelProperty(value = "子菜单为空时隐藏",required = true)
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