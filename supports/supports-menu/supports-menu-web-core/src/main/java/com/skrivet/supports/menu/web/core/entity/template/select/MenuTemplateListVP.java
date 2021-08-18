package com.skrivet.supports.menu.web.core.entity.template.select;

import com.skrivet.core.common.entity.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
	
@ApiModel(description = "菜单模板列表")
public class MenuTemplateListVP extends BasicEntity {
	@ApiModelProperty(value = "主键编号")
	//主键编号
	private String id;
	@ApiModelProperty(value = "模板名称")
	//模板名称
	private String templateName;
	@ApiModelProperty(value = "默认模板")
	//默认模板
	private String defaultTemplate;
	@ApiModelProperty(value = "备注")
	//备注
	private String templateRemark;
	
	public String  getId() 	{
		return this.id;
	}

	public void setId(String id)  {
		this.id = id;
	}
	
	public String getTemplateName() 	{
		return this.templateName;
	}

	public void setTemplateName(String templateName)  {
		this.templateName = templateName;
	}
	
	public String getDefaultTemplate() 	{
		return this.defaultTemplate;
	}

	public void setDefaultTemplate(String defaultTemplate)  {
		this.defaultTemplate = defaultTemplate;
	}
	
	public String getTemplateRemark() 	{
		return this.templateRemark;
	}

	public void setTemplateRemark(String templateRemark)  {
		this.templateRemark = templateRemark;
	}
}