package com.skrivet.supports.menu.service.core.template.entity.select;

import com.skrivet.plugins.service.core.entity.BasicPageSQ;
	

public class MenuTemplateSelectPageSQ extends BasicPageSQ	{
	//模板名称
	private String templateName;
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