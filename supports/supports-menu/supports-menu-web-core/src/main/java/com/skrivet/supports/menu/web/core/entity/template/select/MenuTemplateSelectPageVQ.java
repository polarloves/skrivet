package com.skrivet.supports.menu.web.core.entity.template.select;

import com.skrivet.web.core.entity.BasicPageVQ;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
	
@ApiModel(description = "菜单模板分页查询参数")
public class MenuTemplateSelectPageVQ extends BasicPageVQ	{
	@ApiModelProperty(value = "模板名称")
	//模板名称
	private String templateName;
	@ApiModelProperty(value = "默认模板")
	//默认模板
	private String defaultTemplate;
	
	
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
}