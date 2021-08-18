package com.skrivet.supports.menu.service.core.template.entity.select;

import com.skrivet.core.common.entity.BasicEntity;
	

public class MenuTemplateDetailSP extends BasicEntity {
	//主键编号
	private String id;
	//模板名称
	private String templateName;
	//默认模板
	private String defaultTemplate;
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